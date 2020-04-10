package com.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LargeDbApplication {
	private BatchProcessor batchProcessor;

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(LargeDbApplication.class, args);
		BatchProcessor batchProcessor = applicationContext.getBean(BatchProcessor.class);
		batchProcessor.insertData();


	}

}
