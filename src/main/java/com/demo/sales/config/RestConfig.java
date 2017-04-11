package com.demo.sales.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Configuration
@EnableWebMvc
public class RestConfig extends WebMvcConfigurerAdapter {
	
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(
                new Jackson2ObjectMapperBuilder().propertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE)
                        .serializationInclusion(Include.NON_NULL).build()));

        converters.add(new MappingJackson2XmlHttpMessageConverter());
    }

}