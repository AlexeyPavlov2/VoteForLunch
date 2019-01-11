package org.javatraining.voteforlunch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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


	/*@Bean
	@Primary
	public MappingJackson2HttpMessageConverter getMapper() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = jsonConverter.getObjectMapper();
		mapper.registerModule(new Hibernate5Module());
		mapper.registerModule(new JavaTimeModule());
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		logger.info("Get ObjectMapper");
		return jsonConverter;
	}*/

	@Bean
	@Primary
	public MappingJackson2HttpMessageConverter getMapper() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = converter.getObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(new Hibernate5Module());
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		logger.info("Get ObjectMapper");
		return converter;
	}

}




