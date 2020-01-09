package com.house365.collector.base.vo;

import java.io.Serializable;

public class ProxyVO implements Serializable {

    private static final long serialVersionUID = 5310798372687117759L;

    private String host;

    private int port;

    private long expireTime;

    public ProxyVO(String host, int port, int ttl) {
        this.host = host;
        this.port = port;
        this.expireTime = System.currentTimeMillis() + ttl - 10000;// 提前10000毫秒过期
    }

    public ProxyVO(String host, int port, long expireTime) {
        this.host = host;
        this.port = port;
        this.expireTime = expireTime;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return host + ":" + port + ":" + expireTime;
    }

    /**
     * 验证代理ip是否过期
     * @return
     */
    public boolean isExpired() {

        return  expireTime < System.currentTimeMillis() ? true : false;
    }
}
