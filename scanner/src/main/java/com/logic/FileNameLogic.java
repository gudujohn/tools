package com.logic;

import java.io.File;
import java.util.List;

import com.common.filter.PngFilter;
import com.common.util.FileUtil;

public class FileNameLogic {
	public static void upperCaseAllPngFileName(String path) {
		try {
			List<File> pngFiles = FileUtil.getFilterFiles(path, new PngFilter());
			if (null != pngFiles && pngFiles.size() > 0) {
				int i = 1;
				for (File file : pngFiles) {
					String fileName = file.getName().substring(0, file.getName().length() - 4);
					String fileNameUpper = fileName.toUpperCase();
					if (!fileName.equals(fileNameUpper)) {
						String parentPath = file.getParent();
						String fileDest = parentPath + File.separator + fileNameUpper + ".png";
						System.out.println(fileName + "rename to" + fileNameUpper);
						file.renameTo(new File(fileDest));
						i ++;
					}
				}
				System.out.println("总共修改数量:" + i);
			}
			List<File> pngFiles2 = FileUtil.getFilterFiles(path, new PngFilter());
			System.out.println("修改前文件数量:" + pngFiles.size());
			System.out.println("修改后文件数量:" + pngFiles2.size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
