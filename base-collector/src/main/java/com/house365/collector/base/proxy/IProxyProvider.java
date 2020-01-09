package com.house365.collector.base.proxy;

import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;

public interface IProxyProvider extends ProxyProvider {

    void invalidProxy(Proxy proxy);
}
