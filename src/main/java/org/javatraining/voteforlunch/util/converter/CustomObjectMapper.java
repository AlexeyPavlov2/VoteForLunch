package org.javatraining.voteforlunch.util.converter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@Configuration
public class CustomObjectMapper implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(CustomObjectMapper.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.info("Object Mapper registered");
        ObjectMapper webObjectMapper = objectMapper.copy();
        webObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        webObjectMapper.registerModule(new Hibernate5Module());
        webObjectMapper.registerModule(new JavaTimeModule());
        webObjectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        webObjectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        webObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        webObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		webObjectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        webObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        converters.add(new MappingJackson2HttpMessageConverter(webObjectMapper));
    }
}
