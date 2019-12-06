package com.file.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.file.common.filter.JavaFilter;
import com.file.common.util.FileUtil;

/**
 * 标注服务名称规范话比对逻辑
 * 
 * @author JiangGengchao
 * @date 2017年12月22日
 */
public class AnnotationServiceNameStandardLogic {

	/**
	 * 校验标注名称是否存在
	 * 
	 * @param path
	 */
	public static void validateAnnotationName(String path) {
		try {
			List<String> noNameBean = new ArrayList<>();
			List<File> javaFiles = FileUtil.getFilterFiles(path, new JavaFilter());
			System.out.println("java number:" + javaFiles.size());
			for (File file : javaFiles) {
				getNoNameAnnotationService(file, noNameBean);
			}
			System.out.println("annotation service without name size：" + noNameBean.size());
			for (String name : noNameBean) {
				System.out.println(name);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 校验服务是否重名
	 * 
	 * @param path
	 */
	public static void validateRepeatingService(String path) {
		try {
			Map<String, List<String>> serviceBean = new HashMap<>();
			List<File> javaFiles = FileUtil.getFilterFiles(path, new JavaFilter());
			System.out.println("java number:" + javaFiles.size());
			for (File file : javaFiles) {
				getRepeatingService(file, serviceBean);
			}
			Iterator<Entry<String, List<String>>> it = serviceBean.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, List<String>> entry = it.next();
				if (entry.getValue().size() <= 1) {
					it.remove();
				}
			}
			System.out.println("annotation repeating service name size：" + serviceBean.size());
			for (Entry<String, List<String>> entry : serviceBean.entrySet()) {
				for (String name : entry.getValue()) {
					System.out.println(name);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取没有名称的标注服务
	 * 
	 * @param file
	 * @param noNameBean
	 */
	private static void getNoNameAnnotationService(File file, List<String> noNameBean) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String str = null;
			while ((str = br.readLine()) != null) {
				if (str.indexOf("@Service") != -1 && str.indexOf("*") == -1 && str.indexOf("////") == -1) {
					if (!(str.indexOf("@Service(\"") != -1)) {
						noNameBean.add(FileUtil.getClassName(file));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static void getRepeatingService(File file, Map<String, List<String>> serviceBean) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String str = null;
			while ((str = br.readLine()) != null) {
				if (str.indexOf("@Service") != -1) {
					if (str.indexOf("@Service(\"") != -1) {
						String serviceName = str.split("@Service\\(\"")[1].split("\"\\)")[0];
						String className = FileUtil.getClassName(file);
						if (null != serviceBean.get(serviceName)) {
							serviceBean.get(serviceName).add(className);
						} else {
							List<String> classNameList = new ArrayList<>();
							classNameList.add(className);
							serviceBean.put(serviceName, classNameList);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
