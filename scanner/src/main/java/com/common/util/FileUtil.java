package com.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件工具
 * 
 * @author JiangGengchao
 * @date 2017年12月21日
 */
public class FileUtil {
	public static List<File> getFilterFiles(String packageName, FileFilter filter) throws Exception {
		File dir = new File(packageName);
		if (dir.isDirectory()) {
			List<File> resultFileList = new ArrayList<>();
			File[] tempFiles = dir.listFiles(filter);
			if (null != tempFiles && tempFiles.length > 0) {
				resultFileList.addAll(Arrays.asList(tempFiles));
			}
			for (File file : Arrays.asList(dir.listFiles())) {
				if (file.isDirectory()) {
					List<File> tempFileList = getFilterFiles(file, filter);
					if (null != tempFileList) {
						resultFileList.addAll(tempFileList);
					}
				}
			}
			return resultFileList;
		} else {
			throw new IllegalArgumentException(packageName + "是个文件路径，不是文件夹路径，不需要扫描");
		}
	}

	/**
	 * 获取指定目录下的过滤文件(单层文件夹扫描)
	 * 
	 * @param packageName
	 * @param filter
	 * @return
	 */
	public static List<File> getFilterFiles(File file, FileFilter filter) {
		if (file.isDirectory()) {
			List<File> resultFileList = new ArrayList<>();
			File[] tempFiles = file.listFiles(filter);
			if (null != tempFiles && tempFiles.length > 0) {
				resultFileList.addAll(Arrays.asList(tempFiles));
			}
			for (File tempFile : Arrays.asList(file.listFiles())) {
				if (tempFile.isDirectory()) {
					resultFileList.addAll(getFilterFiles(tempFile, filter));
				}
			}
			return resultFileList;
		}
		return null;
	}

	/**
	 * 获取classname
	 * 
	 * @param file
	 * @return
	 */
	public static String getClassName(File file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String str = null;
			while ((str = br.readLine()) != null) {
				if (str.indexOf("package ") != -1) {
					return str.split("package ")[1].split(";")[0] + "." + file.getName().split("\\.")[0];
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != br)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}
}
