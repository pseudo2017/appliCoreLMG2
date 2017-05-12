package com.lmg.config;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.lmg.model.Event;
import com.lmg.model.lmgData;
import com.amazon.sqs.javamessaging.message.SQSTextMessage;


@Configuration
@DependsOn("coreJMSlmg")
@Component
public class coreJMSListenerLmg implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(coreJMSListenerLmg.class);
	
    @Autowired
    private EventReader eventReader;

    private String getMessageText(SQSTextMessage message) {
        try {
            return message.getText();
        } catch (JMSException e) {
            throw new UnreadableSqsMessageException("Failed to get message text from message", e);
        }
    }

	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
        String messageText = getMessageText((SQSTextMessage) arg0);
        LOGGER.info("Message SQS reçu : {}", messageText);
//        Event<lmgData> purchaseEvent = eventReader.readEvent(messageText, lmgData.class);
//        LOGGER.info("Purchase event received: {}", purchaseEvent);
	}

    /*
	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
        String messageText = getMessageText((SQSTextMessage) arg0);
        LOGGER.info("Message SQS reçu : {}", messageText);
        Event<lmgData> purchaseEvent = eventReader.readEvent(messageText, lmgData.class);
        LOGGER.info("Purchase event received: {}", purchaseEvent);
    }
		
   */ 
}
