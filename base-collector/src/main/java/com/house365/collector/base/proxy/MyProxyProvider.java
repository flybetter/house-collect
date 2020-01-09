package com.house365.collector.base.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;

import java.io.IOException;

public class MyProxyProvider implements IProxyProvider {

    private static final Logger logger = LoggerFactory.getLogger(MyProxyProvider.class);
    private static final String GET_PROXY_API = "http://192.168.10.221:8080/proxy/get";
    private static final String INVALID_PROXY_API = "http://192.168.10.221:8080/proxy/invalid";

    /**
     * Return proxy to Provider when complete a download.
     *
     * @param proxy the proxy config contains host,port and identify info
     * @param page  the download result
     * @param task  the download task
     */
    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {

    }

    /**
     * Get a proxy for task by some strategy.
     *
     * @param task the download task
     * @return proxy
     */
    @Override
    public Proxy getProxy(Task task) {

        Proxy proxy = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(GET_PROXY_API).build();
        Response response = null;
        try {

            response = client.newCall(request).execute();
            if (response.code() == 200) {

                String bodyStr = response.body().string();
                JSONObject body = JSON.parseObject(bodyStr);
                if (body != null) {

                    String host = body.getString("host");
                    int port = body.getIntValue("port");
                    proxy = new Proxy(host, port);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            response.body().close();
        }

        return proxy;
    }

    @Override
    public void invalidProxy(Proxy proxy) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(INVALID_PROXY_API + "?host=" + proxy.getHost()).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
