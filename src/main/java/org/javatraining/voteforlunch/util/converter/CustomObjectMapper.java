package org.javatraining.voteforlunch.util.converter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class CustomObjectMapper  {
    private static final Logger logger = LoggerFactory.getLogger(CustomObjectMapper.class);
    private ObjectMapper webObjectMapper = new ObjectMapper();

    public CustomObjectMapper() {
        logger.info("Object Mapper registered");
        webObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        webObjectMapper.registerModules(new Hibernate5Module(), new JavaTimeModule(), new Jdk8Module());
        webObjectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        webObjectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        webObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        webObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        webObjectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        webObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Bean
    @Primary
    public ObjectMapper getMapper() {
        return webObjectMapper;
    }


}
