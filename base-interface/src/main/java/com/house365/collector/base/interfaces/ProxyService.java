package com.house365.collector.base.interfaces;

import com.house365.collector.base.vo.ProxyVO;

public interface ProxyService {

    ProxyVO getProxy();

    void invalidProxy(String host);

    String sayHello();
}
