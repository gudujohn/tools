package com.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

public interface RestService {

	<T> T exchange(String restUrl, HttpMethod httpMethod, HttpEntity<?> httpEntity, Class<T> responseType);

}
