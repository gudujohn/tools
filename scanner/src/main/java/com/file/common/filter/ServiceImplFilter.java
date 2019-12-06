package com.file.common.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * serviceimpl文件过滤器
 * 
 * @author JiangGengchao
 * @date 2017年12月21日
 */
public class ServiceImplFilter implements FileFilter {
	@Override
	public boolean accept(File pathname) {
		String filename = pathname.getName();
		return filename.endsWith("ServiceImpl.java");
	}
}
