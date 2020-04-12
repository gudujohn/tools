package com.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.enhance.common.support.ThreadCallBack;
import com.enhance.common.util.Assertion;
import com.enhance.common.util.Detect;
import com.enums.MethodEnum;
import com.service.RestManagementService;
import com.service.RestService;
import com.support.executor.RestSupportExecutor;
import com.views.ComponentPool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("restController")
public class RestManagementSersviceImpl implements RestManagementService {

	@Autowired
	private RestService restService;
	@Autowired
	private RestSupportExecutor restSupportExecutor;

	@Override
	public void threadExchange() {
		String url = ComponentPool.getInstance().getUrlCombo().getSelectedItem().toString().trim();
		Assertion.notEmpty(url, "url不能为空");
		HttpEntity<String> httpEntity = createHttpEntity();

		ThreadCallBack callBack = new ThreadCallBack() {
			@Override
			public <T> void execute(T response) {
				String result = (String) response;
				ComponentPool.getInstance().getResultPanel().getJsonTextArea().setText(result);
			}
		};
		restSupportExecutor.threadExchange(url, getHttpMethod(), httpEntity, String.class, callBack);
	}

	@Override
	public void singThreadExchange() {

	}

	@Override
	public void multiThreadExchange() {

	}

	private HttpEntity<String> createHttpEntity() {
		Map<String, String> headerMap = ComponentPool.getInstance().getHeaderTable().getAll();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
		if (Detect.notEmpty(headerMap)) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				if (headerMap.containsKey(HttpHeaders.CONTENT_TYPE)) {
					headers.setContentType(MediaType.parseMediaType(headerMap.get(HttpHeaders.CONTENT_TYPE)));
					continue;
				}
				headers.add(entry.getKey(), entry.getValue());
			}
		}

		String businessParams = ComponentPool.getInstance().getBodyPanel().getJsonTextArea().getText().trim();

		return new HttpEntity<>(businessParams, headers);
	}

	private HttpMethod getHttpMethod() {
		String methodStr = (String) ComponentPool.getInstance().getMethodCombo().getSelectedItem();
		MethodEnum method = MethodEnum.get(methodStr);
		Assertion.notNull(method, "请先设置请求方式");
		switch (method) {
		case POST:
			return HttpMethod.POST;
		case GET:
			return HttpMethod.GET;
		case PUT:
			return HttpMethod.PUT;
		case DELETE:
			return HttpMethod.DELETE;
		}
		return null;
	}

}
