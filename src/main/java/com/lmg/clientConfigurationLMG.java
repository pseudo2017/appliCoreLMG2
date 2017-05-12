package com.lmg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;



public class clientConfigurationLMG {
	boolean proxyMode = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(clientConfigurationLMG.class);
	ClientConfiguration clientConfig;
	
	private String sportP = null;
	private int portP = 0;
	private String hostP = null;
	private String userP = null;
	private String pwdP = null;
	
	public clientConfigurationLMG(boolean pm)
	{
		proxyMode = pm;
	}
	
	public clientConfigurationLMG(corePropertiesConfiguration propertie)
	{
		if ( propertie != null)
		{
			if ( propertie.getParamProxyHost() != null)
			{
				hostP = propertie.getParamProxyHost();
			}
			if ( propertie.getParamProxyPort() != null)
			{
				sportP = propertie.getParamProxyPort();
				portP = Integer.parseInt(sportP);
			}
			if ( propertie.getParamProxyUserName() != null)
			{
				userP = propertie.getParamProxyUserName();
			}
			if ( propertie.getParamProxyPassWord() != null)
			{
				pwdP = propertie.getParamProxyPassWord();
			}
			if ( hostP != null) proxyMode = true;
		}
	}
	private boolean isProxyMode()
	{
		return proxyMode;
	}
	
	public void basculerHTTP()
	{
		clientConfig.setProtocol(Protocol.HTTP);
	}
	public void basculerHTTPS()
	{
		clientConfig.setProtocol(Protocol.HTTPS);
	}
	
	public ClientConfiguration factory()
	{
		clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTPS);

		if (isProxyMode()) {
	        LOGGER.info("... Client en mode proxy --> ");
	        LOGGER.info("ProxyHost : " + hostP + "  ProxyPort : " + portP);
	        LOGGER.info("UserName  : " + userP );
		    clientConfig.setProxyHost(hostP);
		    clientConfig.setProxyPort(portP);
		    if (userP != null && userP.length() > 0)  clientConfig.setProxyUsername(userP);
		    if (pwdP != null && pwdP.length() > 0)    clientConfig.setProxyPassword(pwdP);
		  } else {
		        LOGGER.info("... Client en mode normal ( sans Proxy)  --> ");
		  }
		return clientConfig;
	}
}
