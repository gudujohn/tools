package com.support.config;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestConfig {

	@Bean("restTemplate")
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(0);
		requestFactory.setReadTimeout(0);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		setConverterDateFormat(restTemplate);
		setDefaultCharset(restTemplate);
		return restTemplate;
	}

	private void setConverterDateFormat(RestTemplate restTemplate) {
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof AbstractJackson2HttpMessageConverter) {
				ObjectMapper objectMapper = ((AbstractJackson2HttpMessageConverter) converter).getObjectMapper();
				objectMapper.setDateFormat(dateFormat);
			}
		}
	}

	private void setDefaultCharset(RestTemplate restTemplate) {
		List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> httpMessageConverter : httpMessageConverters) {
			if (httpMessageConverter instanceof StringHttpMessageConverter) {
				StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
				messageConverter.setDefaultCharset(Charset.forName("UTF-8"));
			}
		}
	}
}