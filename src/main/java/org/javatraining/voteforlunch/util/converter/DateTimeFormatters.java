package org.javatraining.voteforlunch.util.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.javatraining.voteforlunch.util.DateTimeUtil.parseLocalTime;

@Configuration
@EnableWebMvc
public class DateTimeFormatters {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeFormatters.class);

    @Bean
    @Primary
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                logger.info("Parse string to LocalDate....");
                return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                logger.info("Print LocaleDate....");
                return DateTimeFormatter.ISO_DATE.format(object);
            }
        };
    }

    @Bean
    @Primary
    public Formatter<LocalTime> localTimeFormatter() {
        return new Formatter<>() {
            @Override
            public LocalTime parse(String text, Locale locale) {
                logger.info("Parse string to LocalTime....");
                return parseLocalTime(text);
            }

            @Override
            public String print(LocalTime lt, Locale locale) {
                logger.info("Print LocaleTime....");
                return lt.format(DateTimeFormatter.ISO_LOCAL_TIME);
            }


        };
    }

}
