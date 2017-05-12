package com.lmg.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.ClientConfiguration;

import com.amazonaws.auth.AWSCredentialsProvider;


import com.lmg.coreConfiguration;
import com.lmg.sqs.SqsQueue;

@Configuration
@DependsOn("coreConfiguration")
@Component
public class coreJMSlmg {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(coreJMSlmg.class);

    
    @Autowired
    coreConfiguration LaConfig;
    
    private SQSConnection connection;
    
    @PostConstruct
    public void init() throws JMSException {
    	
        connection = createConnection();
        connection.start();
        LOGGER.info("initialisation du client JMS OK ..............");
    }
    
    @PreDestroy
    public void cleanUp() throws JMSException {
        connection.close();
    }
    
    private SQSConnection createConnection() throws JMSException {
        SQSConnectionFactory connectionFactory = getSqsConnectionFactory();
        return connectionFactory.createConnection();
    }
    
    private SQSConnectionFactory getSqsConnectionFactory() {
    	
    	AWSCredentialsProvider AWSP = LaConfig.getCredentialProvider(); 
    	ClientConfiguration cC = LaConfig.getClientConfiguration();
    	return SQSConnectionFactory.builder()
                .withRegion(LaConfig.getRegion())
                .withAWSCredentialsProvider(AWSP)
                .withClientConfiguration(cC)
                .build();
    }
    @Bean
    public SQSConnection getConnection()
    {
    	return connection;
    }
    
    public void afficheConfig()
    {
    	LaConfig.affiche();
    }
    
	public SqsQueue getQueue()
	{
    	String PQN = LaConfig.getQueueName();
    	String PQU = LaConfig.getQueueUrl();
		SqsQueue ret = new SqsQueue(PQN, PQU);
		return ret;
	}
    
    
    
    
}
