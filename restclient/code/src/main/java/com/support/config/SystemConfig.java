package com.support.config;

import com.constant.SysConfigConst;

public class SystemConfig {

	public static String getVersion() {
		return System.getProperty(SysConfigConst.VERSION, "V-dev");
	}

	private static boolean testing = true;

	public static void setTesting(boolean testing) {
		SystemConfig.testing = testing;
	}

	public static boolean isTesting() {
		return testing;
	}

}
