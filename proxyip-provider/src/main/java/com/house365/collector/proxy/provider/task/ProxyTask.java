package com.house365.collector.proxy.provider.task;

import com.house365.collector.base.vo.ProxyVO;
import com.house365.collector.proxy.provider.ipprovider.ProxyProvider;
import com.house365.collector.proxy.provider.repo.ProxyRepo;
import com.house365.collector.proxy.provider.util.ProxyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProxyTask {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProxyProvider proxyProvider;

    @Autowired
    private ProxyRepo proxyRepo;

    /**
     * 添加代理
     */
    @Scheduled(cron="*/1 * * * * ?") // 每隔1秒执行一次
    private void addProxy() {

        ProxyVO proxy = proxyProvider.getProxyIp();
        if (proxy != null) {
            if (ProxyUtil.isValid(proxy)) {

                proxyRepo.pushProxy(proxy);
            }
        }
    }

    /**
     * 验证代理
     */
    @Scheduled(cron="*/1 * * * * ?") // 每隔1秒执行一次
    private void validateProxy() {

        ProxyVO proxy = proxyRepo.popProxy();
        if (proxy != null) {

            // 验证代理可用性
            if (ProxyUtil.isValid(proxy)) {

                proxyRepo.pushProxy(proxy);
            }
        }
    }
}
