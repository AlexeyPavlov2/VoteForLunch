package org.javatraining.voteforlunch;

import org.javatraining.voteforlunch.service.user.UserService;
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
@EnableCaching  //https://spring.io/guides/gs/caching/
@EnableWebSecurity
public class VoteforlunchApplication implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(VoteforlunchApplication.class);

	@Autowired
	private UserService userService;


	@Autowired
	private CacheManager cacheManager;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(VoteforlunchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Spring Boot Ehcache 2 Caching Configuration - " +
				"using cache manager: " + cacheManager.getClass().getName());
	}

/*
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = jsonConverter.getObjectMapper();
		mapper.registerModule(new Hibernate5Module());
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return jsonConverter;
	}
*/


}




