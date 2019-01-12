package org.javatraining.voteforlunch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*Алексей Павлов, выпускной проект, курс Topjava15, http://javaops.ru/*/

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableWebSecurity
public class VoteforlunchApplication implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(VoteforlunchApplication.class);

	@Autowired
	private CacheManager cacheManager;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(VoteforlunchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Spring Boot Ehcache 2 Caching Configuration - " +
				"using cache manager: " + cacheManager.getClass().getName());
	}

}




