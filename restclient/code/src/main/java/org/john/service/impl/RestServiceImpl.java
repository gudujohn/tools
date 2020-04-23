package org.john.service.impl;

import java.util.concurrent.Executor;

import org.john.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.enhance.common.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("restService")
public class RestServiceImpl implements RestService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Executor threadRestExecutor;

	@Override
	public <T> T exchange(String restUrl, HttpMethod httpMethod, HttpEntity<?> httpEntity, Class<T> responseType) {
		log.info(restUrl + ";" + httpMethod.name());
		if (null != httpEntity) {
			log.debug(httpEntity.toString());
		}
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		T response = null;
		try {
			response = restTemplate.exchange(restUrl, httpMethod, httpEntity, responseType).getBody();
		} finally {
			stopWatch.stop();
			log.info("current rest cost: {} ms", stopWatch.getTotalTimeMillis());
		}
		return response;
	}

}
