package com.file.common.filter;

import java.io.File;
import java.io.FileFilter;

public class JavaFilter implements FileFilter {
	@Override
	public boolean accept(File pathname) {
		String filename = pathname.getName();
		return filename.endsWith(".java");
	}
}
