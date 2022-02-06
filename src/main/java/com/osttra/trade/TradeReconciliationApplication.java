package com.osttra.trade;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.osttra.trade.reader.TradeReader;

@SpringBootApplication
public class TradeReconciliationApplication {
	@Autowired 
	TradeReader tradeReader;
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(TradeReconciliationApplication.class, args);
	}
	
	@PostConstruct
	public void postConstruct() {
	
		//if (!Arrays.asList(env.getActiveProfiles()).contains("test")) { 
			tradeReader.readTrade();
		//}
	}

}
