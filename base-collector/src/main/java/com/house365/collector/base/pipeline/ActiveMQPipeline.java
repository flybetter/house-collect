package com.house365.collector.base.pipeline;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.IOException;

public class ActiveMQPipeline extends AbstractMQPipeline {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQPipeline.class);

    private static ActiveMQConnectionFactory connectionFactory;

    private static Connection connection;

    private static Session session;

    private static Destination destination;

    private static MessageProducer producer;

    private void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String queueName;

    public ActiveMQPipeline(String queueName) {
        this.queueName = queueName;
        init();
    }

    @Override
    protected void sendToMQ(String msg) {
        try {
            // Create a messages
            TextMessage message = session.createTextMessage(msg);

            // Tell the producer to send the message
            producer.send(message);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        try {
            if (null != connection) {
                this.connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
