package com.house365.collector.proxy.consumer.controller;

import com.house365.collector.base.interfaces.ProxyService;
import com.house365.collector.base.vo.ProxyVO;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:20880")
    private ProxyService proxyService;

    @RequestMapping("/get")
    public ProxyVO getProxy() {

        return proxyService.getProxy();
    }

    @RequestMapping("/invalid")
    public void invalidProxy(@RequestParam("host") String host) {

        proxyService.invalidProxy(host);
    }
}
