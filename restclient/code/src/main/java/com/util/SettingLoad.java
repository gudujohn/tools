package com.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;

import com.constant.SysConfigConst;
import com.enhance.common.util.Detect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SettingLoad {

	private static Properties properties = new Properties();

	static {
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/application.properties"));
			loadSettings();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			System.exit(0);
		}
	}

	private static SettingLoad instance = new SettingLoad();

	private SettingLoad() {
		loadSettings();
	}

	public static SettingLoad getInstance() {
		return instance;
	}

	private static void loadSettings() {
		String[] configKey = new String[] {};
		configKey = ArrayUtils.addAll(configKey, SysConfigConst.VERSION);
		configKey = ArrayUtils.addAll(configKey, SysConfigConst.RestTestConfig.REST_TEST_THREAD);
		configKey = ArrayUtils.addAll(configKey, SysConfigConst.RestTestConfig.REST_TEST_REST);

		String[] filter = new String[] {};
		filter = ArrayUtils.addAll(filter);

		load(configKey, filter);
	}

	private static void load(String[] settingKeys, String... filter) {
		if (Detect.notEmpty(settingKeys)) {
			for (String settingKey : settingKeys) {
				if (Arrays.asList(filter).contains(settingKey)) {
					continue;
				}
				if (Detect.notEmpty(properties.getProperty(settingKey))) {
					String settingValue = properties.getProperty(settingKey);
					System.setProperty(settingKey, settingValue);
					log.info("loading property:" + settingKey + " and value:" + settingValue);
				} else {
					log.warn("miss property:" + settingKey);
				}
			}
		}
	}

}
