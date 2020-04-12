package com.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.RestManagementService;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringBeanUtil
 * 
 * @author JiangGengchao
 * @date 2019年12月11日
 */
@Slf4j
public class SpringUtil {

	private static SpringUtil instance = new SpringUtil();

	private ApplicationContext applicationContext;

	private SpringUtil() {
		init();
	}

	public static SpringUtil getInstance() {
		return instance;
	}

	private void init() {
		String httpRemote = "classpath:config/application.xml";
		String[] paths = new String[] { httpRemote };
		this.applicationContext = new ClassPathXmlApplicationContext(paths);
	}

	public Object getBean(String name) {
		try {
			return this.applicationContext.getBean(name);
		} catch (BeansException be) {
			log.error("bean: {} can not be found!", name);
			throw be;
		}
	}

	public RestManagementService getRestController() {
		return (RestManagementService) this.getBean("restController");
	}

}
