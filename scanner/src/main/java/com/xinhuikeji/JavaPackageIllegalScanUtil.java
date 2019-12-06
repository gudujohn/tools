package com.xinhuikeji;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.file.common.util.StopWatch;
import com.util.Detect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaPackageIllegalScanUtil {

	public static void main(String[] args) {
		StopWatch watch = new StopWatch();
		// 1、删除csf
		log.info("=========开始扫描");
		watch.start();
		String[] ignore = new String[] { "D:\\work\\workspace\\xh\\irmsng\\trunk\\code\\static" };
		String subPackage = "src\\main\\java";
		List<String> illegalPath = getAllIllegalPackagePath("D:\\work\\workspace\\xh\\irmsng\\trunk\\code", subPackage, ignore);
		watch.stop();
		log.info("=========扫描结束;用时 {} ms", watch.getLastTaskTimeMillis());
		for (String path : illegalPath) {
			System.out.println(path);
		}
	}

	private static List<String> getAllIllegalPackagePath(String rootDir, String subPackage, String[] existPath) {
		List<String> illegalPackagePathList = new ArrayList<>();
		File rootDirFile = new File(rootDir);
		File[] files = rootDirFile.listFiles();
		if (Detect.notEmpty(files)) {
			List<File> dirFiles = new ArrayList<>();
			for (File file1 : files) {
				String absolutePath = file1.getAbsolutePath();
				boolean ignore = isIgnore(existPath, absolutePath);
				if (file1.isDirectory() && !ignore) {
					dirFiles.add(file1);

					String dirName = file1.getName();
					boolean illegalPacakagePath = isIllegalPacakagePath(dirName);
					boolean inSubPackage = isInSubPackage(subPackage, absolutePath);
					if (illegalPacakagePath && inSubPackage) {
						illegalPackagePathList.add(absolutePath);
					}
				}
			}
			if (Detect.notEmpty(dirFiles)) {
				for (File file2 : dirFiles) {
					List<String> illegal = getAllIllegalPackagePath(file2.getAbsolutePath(), subPackage, existPath);
					if (Detect.notEmpty(illegal)) {
						illegalPackagePathList.addAll(illegal);
					}
				}
			}
		}
		return illegalPackagePathList;
	}

	private static boolean isIllegalPacakagePath(String dirName) {
		for (char c : dirName.toCharArray()) {
			if (Character.isUpperCase(c)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isIgnore(String[] ignorePath, String curPath) {
		boolean result = false;
		if (Detect.notEmpty(ignorePath)) {
			for (String item : ignorePath) {
				result = curPath.startsWith(item);
				break;
			}
		}
		return result;
	}

	private static boolean isInSubPackage(String subPackage, String curPath) {
		boolean result = true;
		if (Detect.notEmpty(subPackage)) {
			result = curPath.contains(subPackage);
		}
		return result;
	}
}
