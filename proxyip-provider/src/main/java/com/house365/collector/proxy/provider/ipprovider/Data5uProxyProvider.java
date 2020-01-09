package com.house365.collector.proxy.provider.ipprovider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house365.collector.base.vo.ProxyVO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * http://www.data5u.com 代理ip提供者
 */
@Component
public class Data5uProxyProvider implements ProxyProvider {

    private static final Logger logger = LoggerFactory.getLogger(Data5uProxyProvider.class);

    @Value("${proxy.provider.data5u}")
    private String apiUrl;

    @Override
    public ProxyVO getProxyIp() {

        ProxyVO proxy = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl).build();
        Response response = null;
        try {

            response = client.newCall(request).execute();
            if (response.code() == 200) {

                String bodyStr = response.body().string();
                JSONObject body = JSON.parseObject(bodyStr);
                if (body != null && StringUtils.equals("ok", body.getString("msg"))) {

                    JSONArray data = body.getJSONArray("data");
                    if (data != null && data.size() > 0) {

                        JSONObject proxyObj = data.getJSONObject(0);
                        proxy = new ProxyVO(proxyObj.getString("ip"), proxyObj.getIntValue("port"), proxyObj
                                .getIntValue("ttl"));
                        logger.info("get " + proxy.getHost());
                    }
                }
            }
        } catch (IOException e) {

            logger.error("data5u provider " + e.getMessage());
        } finally {

            if (response != null) {
                response.close();
            }
        }

        return proxy;
    }
}
