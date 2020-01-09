package com.house365.collector.proxy.provider.util;

import com.house365.collector.base.vo.ProxyVO;

import java.io.IOException;
import java.net.*;

public class ProxyUtil {

    /**
     * 字符串转vo
     *
     * @param proxyString
     * @return
     */
    public static ProxyVO proxyStringToVO(String proxyString) {

        String[] arr = proxyString.split(":");
        if (arr.length == 3) {

            String host = arr[0];
            int port = Integer.valueOf(arr[1]);
            long expireTime = Long.valueOf(arr[2]);
            ProxyVO proxy = new ProxyVO(host, port, expireTime);
            return proxy;
        } else {

            return null;
        }
    }

    /**
     * 验证代理可用性
     *
     * @param proxyVO
     * @return
     */
    public static boolean isValid(ProxyVO proxyVO) {

        if (proxyVO.isExpired()) {
            return false;
        }

        // 发送测试请求
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyVO.getHost(), proxyVO.getPort()));
        try {
            URLConnection httpCon = new URL("https://www.baidu.com/").openConnection(proxy);
            httpCon.setConnectTimeout(5000);
            httpCon.setReadTimeout(5000);
            int code = ((HttpURLConnection) httpCon).getResponseCode();
            return code == 200;
        } catch (IOException e) {
            // do nothing
            return false;
        }
    }
}
