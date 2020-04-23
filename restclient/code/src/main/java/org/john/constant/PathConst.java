package org.john.constant;

import org.john.util.CommonUtil;

public class PathConst {

	private PathConst() {
		throw new IllegalStateException("Constant class");
	}

	public static final String BASE_MENU_CONFIG_FILE_PATH = CommonUtil.getSystemAbsolutePath() + "config/viewconfig/base.menu.config.xml";
	public static final String BOOK_MENU_CONFIG_FILE_PATH = CommonUtil.getSystemAbsolutePath() + "config/viewconfig/book.menu.config.xml";
}
