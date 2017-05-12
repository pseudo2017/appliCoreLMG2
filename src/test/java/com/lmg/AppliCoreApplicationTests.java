package com.lmg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.lmg.config.coreJMSListenerLmg;
import com.lmg.config.coreJMSlmg;
import com.lmg.config.coreSNSlmg;
import com.lmg.sqs.SqsQueue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(coreJMSlmg.class)
public class AppliCoreApplicationTests {


	@Autowired
	coreJMSlmg ConfJMS;
	
	@Autowired
	coreJMSListenerLmg TestListener;
	
	@Autowired
	coreSNSlmg ConfSNS;
	
	
/*	
	@Test
	public void contextLoads() {
	}
*/

	
	/*
	 * 
	 * Pour ce test, le nom de la queue est fixé dans le code ci-dessous
	 * La queue doit exister sur AWS 
	 * 
	 */
	@Test
	public void testEnvoiMessage()
	{
		System.out.println("----------- testEnvoiMessage ----------------------" );

		Session session;
		
		try {
			ConfJMS.afficheConfig();
			session = ConfJMS.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("ramiQ");
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage("Test nom queue code en dur");
			producer.send(message);

			System.out.println("JMS Message " + message.getJMSMessageID());
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("JMS Message " );
		System.out.println("----------- FIN testEnvoiMessage ----------------------" );

	}

	/*
	 * 
	 * Pour ce test, on récupère le nom de la queue qui est configurée
	 * dans le fichier application.properties
	 * 
	 */
	@Test
	public void testEnvoiMessageAvecQueuePropertiesFile()
	{
		System.out.println("----------- testEnvoiMessageAvecQueuePropertiesFile ----------------------" );
		Session session;
		
		try {
			ConfJMS.afficheConfig();
	    	SqsQueue Q = ConfJMS.getQueue();
			session = ConfJMS.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(Q.getName());
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage("Test nom queue dans propertie file");
			producer.send(message);

			System.out.println("JMS Message " + message.getJMSMessageID());
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("JMS Message " );
		System.out.println("----------- FIN  testEnvoiMessageAvecQueuePropertiesFile ----------------------" );

	}
	/*
	 *  A corriger pour pouvoir lire un fichier 
	 *  de puis un path fixé pour les tests
	 * 
	 * 
	 */
	@Test
	public void testLireFichierJSON()
	{
		
		System.out.println("----------- testLireFichierJSON ----------------------" );

		String chaine="";
		String fichier ="/samples/event.json";
		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				System.out.println(ligne);
				chaine+=ligne+"\n";
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		////////////////////////////////////////////////
		System.out.println("----------- FIN testLireFichierJSON ----------------------" );
		
	}
	@Test
	public void testEnvoiMessageJSON()
	{
		System.out.println("----------- testEnvoiMessageJSON ----------------------" );
		
		String mess = "{" + 
					"\"eventId\": \"12345\", " +
					"\"timestamp\": \"2016-05-26T12:00:00.000Z\", " + 
					"\"type\": \"purchase\", " + 
					"\"data\": { " + 
					"\"@class\": \"com.lmg.model.lmgData\", " + 
					"\"lmgId\": \"123\" " + 
 					"}" + 
					"}";
		Session session;
		
		try {
			ConfJMS.afficheConfig();
	    	SqsQueue Q = ConfJMS.getQueue();
			session = ConfJMS.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("testQ");
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage(mess);
			producer.send(message);

			System.out.println("JMS Message " + message.getJMSMessageID());
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("JMS Message " );
		
		System.out.println("---------FIN -- testEnvoiMessageJSON ----------------------" );
	}
	
	
	@Test
	public void testReceptionMessage()
	{
		

		Session session;
		System.out.println("----------- testReceptionMessage ----------------------" );
		try {
	    	SqsQueue Q = ConfJMS.getQueue();
			session = ConfJMS.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("testQ");
	        MessageConsumer consumer = session.createConsumer(queue);
	        consumer.setMessageListener(TestListener);
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("JMS Message " );
		System.out.println("----------- FIN testReceptionMessage ----------------------" );

	}
	
	@Test
	public void testEnvoiMessageSNS()
	{
		System.out.println("----------- testEnvoiMessageSNS ----------------------" );
		ConfSNS.publier("MESSAGE SNS");
		System.out.println("----------- FIN testEnvoiMessageSNS ----------------------" );
	}
	
}
