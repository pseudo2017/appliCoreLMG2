package com.lmg.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqsQueue {

 
    private String name;
    private String url;

    public SqsQueue(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }	
    public String getCompleteName()
    {
    	return url + name;
    }
    
    
	
}
