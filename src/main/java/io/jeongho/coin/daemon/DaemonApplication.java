package io.jeongho.coin.daemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching(proxyTargetClass = true)
public class DaemonApplication {	
	public static void main(String[] args) {
		SpringApplication.run(DaemonApplication.class, args);
	}
}