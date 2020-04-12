package org.john.constant;

public class SysConfigConst {

	private SysConfigConst() {
		throw new IllegalStateException("Constant class");
	}

	public static final String VERSION = "version";

	// 测试csf相关配置
	public static class RestTestConfig {
		public static final String PRE_KEY = "rest.test.";

		public static final String REST_TEST_THREAD = PRE_KEY + "thread";
		public static final String REST_TEST_REST = PRE_KEY + "rest";
	}

}
