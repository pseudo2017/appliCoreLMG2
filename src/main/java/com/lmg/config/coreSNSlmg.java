package com.lmg.config;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.lmg.coreConfiguration;

@Configuration
@DependsOn("coreConfiguration")
@Component
public class coreSNSlmg {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(coreJMSlmg.class);
    
    @Autowired
    coreConfiguration LaConfig;
    
    
    private AmazonSNSClient snsClient;
    

    @PostConstruct
    public void init() {
    	/*
    	 * On recupere un credential temporaire pour l'accès à l'environnement SAND-BOX
    	 * 
    	 */
        LOGGER.info("Création du client SNS OK ..............");
    	AWSCredentialsProvider AWSP = LaConfig.getCredentialProvider();
    	ClientConfiguration cC = LaConfig.getClientConfiguration();
        cC.setProtocol(Protocol.HTTPS);
        
        try {
        	
		snsClient = new AmazonSNSClient(AWSP,cC);		      
		snsClient.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
        LOGGER.info("initialisation du client SNS OK ..............");
        }
        catch ( Exception e)
        {
            LOGGER.info("Echec de l'initialisation du client SNS..............");
            LOGGER.info("Service Name : " + snsClient.getServiceName());
            LOGGER.info("Excpetion : " + e.getMessage());
        }
        
        
        //
        // On modifie la configuration du client pour repasser en HTTP
        //
    }    
    //  Configurer 
    //
    
    
    
    //   Publisher
    //
    
    public void publier(String message)
    {
        LOGGER.info("Création du client SNS OK ..............");
        String TopicArn = LaConfig.getSNSTopic();
        LOGGER.info("ARN : " + TopicArn);
        LOGGER.info("Message : " + message);
		PublishRequest publishRequest = new PublishRequest(TopicArn, message);
		PublishResult publishResult = snsClient.publish(publishRequest);
    }
    
    public void publier(String message, String Topic)
    {
		PublishRequest publishRequest = new PublishRequest(Topic, message);
		PublishResult publishResult = snsClient.publish(publishRequest);
    }
}
