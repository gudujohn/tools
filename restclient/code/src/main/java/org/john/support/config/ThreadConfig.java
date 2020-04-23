package org.john.support.config;

import org.john.constant.SysConfigConst;
import org.enhance.common.util.Detect;

public class ThreadConfig {
	private static int testThreadCount;
	private static int testRestcount;

	public static int getTestThreadCount() {
		if (!Detect.isPositive(testThreadCount)) {
			testThreadCount = Integer.parseInt(System.getProperty(SysConfigConst.RestTestConfig.REST_TEST_THREAD));
		}
		return testThreadCount;
	}

	public static int getTestRestcount() {
		if (!Detect.isPositive(testRestcount)) {
			testRestcount = Integer.parseInt(System.getProperty(SysConfigConst.RestTestConfig.REST_TEST_REST));
		}
		return testRestcount;
	}

}
