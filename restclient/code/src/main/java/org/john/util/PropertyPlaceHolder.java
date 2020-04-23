package org.john.util;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;

import org.john.constant.SysConfigConst;
import org.enhance.common.util.Detect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyPlaceHolder extends PropertySourcesPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
		super.processProperties(beanFactoryToProcess, propertyResolver);
		loadSettings(propertyResolver);
	}

	private static void loadSettings(ConfigurablePropertyResolver propertyResolver) {
		String[] configKey = new String[] {};
		configKey = ArrayUtils.addAll(configKey, SysConfigConst.VERSION);

		String[] filter = new String[] {};

		load(propertyResolver, configKey, filter);
	}

	private static void load(ConfigurablePropertyResolver propertyResolver, String[] settingKeys, String... filter) {
		try {
			if (Detect.notEmpty(settingKeys)) {
				for (String settingKey : settingKeys) {
					if (Arrays.asList(filter).contains(settingKey)) {
						continue;
					}
					if (propertyResolver.containsProperty(settingKey)) {
						String settingValue = propertyResolver.getProperty(settingKey);
						System.setProperty(settingKey, settingValue);
						log.info("loading property:" + settingKey + " and value:" + settingValue);
					} else {
						log.warn("miss property:" + settingKey);
					}
				}
			}
		} catch (Exception e) {
			log.error("load settings error: " + e.getMessage(), e);
			throw e;
		}
	}

}