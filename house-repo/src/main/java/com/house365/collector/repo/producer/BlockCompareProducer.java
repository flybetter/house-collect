package com.house365.collector.repo.producer;

import com.alibaba.fastjson.JSON;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.vo.BlockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class BlockCompareProducer {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public BlockCompareProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void produceBlock(BlockVO block) {

        jmsTemplate.send("block_compare", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSON.toJSONString(block));
            }
        });
    }
}
