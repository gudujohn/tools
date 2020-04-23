package org.john.util;

import org.enhance.common.util.FileUtil;

public class CommonUtil {

	private CommonUtil() {
		throw new IllegalStateException("Constant class");
	}

	public static boolean isWindows() {
		return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
	}

	public static String getSystemAbsolutePath() {
	    String systemAbsolutePath =  FileUtil.getSystemAbsolutePath();
	    if(CommonUtil.isWindows() && systemAbsolutePath.startsWith("/")) {
	        systemAbsolutePath = systemAbsolutePath.substring(1);
        }
	    return systemAbsolutePath;
    }
}
