package com.house365.collector.repo.producer;

import com.alibaba.fastjson.JSON;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.vo.BlockVO;
import com.house365.collector.base.vo.SellHouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class HandlerProducer {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public HandlerProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void produceHandler(SellHouseVO sell) {

        jmsTemplate.send("handler", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSON.toJSONString(sell));
            }
        });
    }
}
