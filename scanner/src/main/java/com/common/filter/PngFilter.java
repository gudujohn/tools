package com.common.filter;

import java.io.File;
import java.io.FileFilter;

public class PngFilter implements FileFilter {
	@Override
	public boolean accept(File pathname) {
		String filename = pathname.getName();
		return filename.endsWith(".png");
	}
}