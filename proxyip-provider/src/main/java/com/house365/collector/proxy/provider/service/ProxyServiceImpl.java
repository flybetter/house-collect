package com.house365.collector.proxy.provider.service;

import com.house365.collector.base.interfaces.ProxyService;
import com.house365.collector.base.vo.ProxyVO;
import com.house365.collector.proxy.provider.repo.ProxyRepo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0")
public class ProxyServiceImpl implements ProxyService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProxyRepo proxyRepo;

    @Override
    public ProxyVO getProxy() {

        ProxyVO proxy = proxyRepo.popProxy();
        if (proxy != null) {

            proxyRepo.pushProxy(proxy);
        }
        return proxy;
    }

    @Override
    public void invalidProxy(String host) {

        long size = proxyRepo.size();
        for (int i = 0; i < size; i++) {
            ProxyVO proxy = proxyRepo.popProxy();
            if (!StringUtils.equals(proxy.getHost(), host)) {

                proxyRepo.pushProxy(proxy);
            }
        }
    }

    @Override
    public String sayHello() {
        return "Hello World!";
    }
}
