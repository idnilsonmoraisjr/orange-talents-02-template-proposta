package com.desafio.proposta.monitoring.health;

import java.net.URL;
import java.net.URLConnection;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		Health health = null;
	
		health = checkInternet() == true ? Health.up().withDetail("OK!", "Connection Successfuly!").build() 
			: Health.down().withDetail("Error!", "Fail at try to connect!").build();
		
		return health;
	}
	
	private boolean checkInternet() {
		boolean flag = false;
		try {
			URL url = new URL("http://www.google.com");
			URLConnection connection = url.openConnection();
			connection.connect();
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
}
