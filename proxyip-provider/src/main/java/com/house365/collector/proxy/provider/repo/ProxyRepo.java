package com.house365.collector.proxy.provider.repo;

import com.house365.collector.base.vo.ProxyVO;
import com.house365.collector.proxy.provider.util.ProxyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 代理仓库
 */
@Component
public class ProxyRepo {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${proxy.repo.iplist-key}")
    private String ipListKey;

    @Value("${proxy.repo.ipset-key}")
    private String ipSetKey;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Lock lock = new ReentrantLock();

    @PostConstruct
    public void clean() {

        lock.lock();
        logger.info("clean");
        redisTemplate.delete(ipListKey);
        redisTemplate.delete(ipSetKey);
        lock.unlock();
    }

    /**
     * push
     *
     * @param proxy
     */
    public void pushProxy(ProxyVO proxy) {

        lock.lock();
        if (!redisTemplate.opsForSet().isMember(ipSetKey, proxy.getHost())) {

            logger.info("push " + proxy.getHost());
            redisTemplate.opsForList().leftPush(ipListKey, proxy.toString());
            redisTemplate.opsForSet().add(ipSetKey, proxy.getHost());
        }
        lock.unlock();
    }

    /**
     * pop
     */
    public ProxyVO popProxy() {

        ProxyVO proxy = null;
        lock.lock();
        long size = redisTemplate.opsForList().size(ipListKey);
        if (size > 0) {

            String proxyString = redisTemplate.opsForList().rightPop(ipListKey);
            proxy = ProxyUtil.proxyStringToVO(proxyString);
            redisTemplate.opsForSet().remove(ipSetKey, proxy.getHost());
            logger.info("pop " + proxy.getHost());
        }
        lock.unlock();
        return proxy;
    }

    /**
     * size
     */
    public long size() {

        lock.lock();
        long size = redisTemplate.opsForList().size(ipListKey);
        lock.unlock();
        return size;
    }
}
