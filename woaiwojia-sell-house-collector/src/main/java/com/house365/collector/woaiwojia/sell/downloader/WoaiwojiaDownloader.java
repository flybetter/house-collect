package com.house365.collector.woaiwojia.sell.downloader;

import com.house365.collector.base.proxy.MyProxyProvider;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.downloader.HttpClientGenerator;
import us.codecraft.webmagic.downloader.HttpClientRequestContext;
import us.codecraft.webmagic.downloader.HttpUriRequestConverter;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.CharsetUtils;
import us.codecraft.webmagic.utils.HttpClientUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 我爱我家Downloader
 */
public class WoaiwojiaDownloader extends AbstractDownloader {

    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();
    private Logger logger = LoggerFactory.getLogger(WoaiwojiaDownloader.class);
    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();
    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();
    private MyProxyProvider proxyProvider;
    private boolean responseHeader = true;
    private boolean directDownloadFirst = true;

    public WoaiwojiaDownloader(boolean directDownloadFirst) {
        this.directDownloadFirst = directDownloadFirst;
    }

    public void setHttpUriRequestConverter(HttpUriRequestConverter httpUriRequestConverter) {
        this.httpUriRequestConverter = httpUriRequestConverter;
    }

    public void setProxyProvider(MyProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    private CloseableHttpClient getHttpClient(Site site) {
        if (site == null) {
            return httpClientGenerator.getClient(null);
        }
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }

    @Override
    public Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = getHttpClient(task.getSite());

        Proxy proxy = null;
        if (!directDownloadFirst) {
            // 优先使用代理下载
            proxy = proxyProvider != null ? proxyProvider.getProxy(task) : null;
        }

        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request, task.getSite(), proxy);
        Page page = Page.fail();
        try {
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(), requestContext.getHttpClientContext
                    ());

            // 被我爱我家检测到爬虫
            if (httpResponse.getStatusLine().getStatusCode() != 200 || httpResponse.getEntity().getContentLength() ==
                    151) {

                logger.warn("被检测出爬虫, request="+request.getUrl());
                // 关闭直连请求的response
                EntityUtils.consumeQuietly(httpResponse.getEntity());
                // 使用代理ip重新下载
                httpResponse = downloadWithProxy(request, task, proxyProvider);

                if (httpResponse == null || httpResponse.getStatusLine().getStatusCode() != 200) {

                    return page;
                } else if (httpResponse.getEntity().getContentLength() == 151) {

                    EntityUtils.consumeQuietly(httpResponse.getEntity());
                    return page;
                }
            }

            page = handleResponse(request, request.getCharset() != null ? request.getCharset() : task.getSite()
                    .getCharset(), httpResponse, task);
            onSuccess(request);
            logger.info("download page success {}", request.getUrl());
            return page;
        } catch (IOException e) {
            logger.warn("download page {} error", request.getUrl());
            onError(request);
            return page;
        } finally {
            if (httpResponse != null) {
                //ensure the connection is released back to pool
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
            if (proxyProvider != null && proxy != null) {
                proxyProvider.returnProxy(proxy, page, task);
            }
        }
    }

    /**
     * 使用代理下载
     *
     * @param request
     * @param task
     * @param proxyProvider
     * @return
     */
    private CloseableHttpResponse downloadWithProxy(Request request, Task task, MyProxyProvider proxyProvider) {

        CloseableHttpResponse httpResponse = null;

        CloseableHttpClient httpClient = getHttpClient(task.getSite());
        Proxy proxy = proxyProvider != null ? proxyProvider.getProxy(task) : null;
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request, task.getSite(), proxy);
        try {
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(), requestContext
                    .getHttpClientContext
                            ());
            logger.info("download with proxy page success {}", request.getUrl());
        } catch (IOException e) {
            logger.warn("download with proxy page {} error", request.getUrl());
            // invalid proxy
            proxyProvider.invalidProxy(proxy);
        }
        return httpResponse;
    }

    @Override
    public void setThread(int thread) {
        httpClientGenerator.setPoolSize(thread);
    }

    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws
            IOException {
        byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        String contentType = httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity()
                .getContentType().getValue();
        Page page = new Page();
        page.setBytes(bytes);
        if (!request.isBinaryContent()) {
            if (charset == null) {
                charset = getHtmlCharset(contentType, bytes);
            }
            page.setCharset(charset);
            page.setRawText(new String(bytes, charset));
        }
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setDownloadSuccess(true);
        if (responseHeader) {
            page.setHeaders(HttpClientUtils.convertHeaders(httpResponse.getAllHeaders()));
        }
        return page;
    }

    private String getHtmlCharset(String contentType, byte[] contentBytes) throws IOException {
        String charset = CharsetUtils.detectCharset(contentType, contentBytes);
        if (charset == null) {
            charset = Charset.defaultCharset().name();
            logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()",
                    Charset.defaultCharset());
        }
        return charset;
    }
}
