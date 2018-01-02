package com.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.common.filter.JavaFilter;
import com.common.filter.ServiceFilter;
import com.common.filter.ServiceImplFilter;
import com.common.util.FileUtil;

public class InterfaceFileScannerLogic {
	public static void getAllUnUsedInterFaceFile(String path) {
		try {
			List<File> javaFiles = FileUtil.getFilterFiles(path, new JavaFilter());
			System.out.println("java number:" + javaFiles.size());
			List<File> serviceFiles = FileUtil.getFilterFiles(path, new ServiceFilter());
			System.out.println("service number:" + serviceFiles.size());
			List<File> serviceImplfiles = FileUtil.getFilterFiles(path, new ServiceImplFilter());
			System.out.println("serviceImpl number:" + serviceImplfiles.size());
			Map<String, Map<String, File>> javaMap = changeFileListToFileMap(javaFiles);
			Map<String, Map<String, File>> serviceMap = changeFileListToFileMap(serviceFiles);
			Map<String, Map<String, File>> serviceImplMap = changeFileListToFileMap(serviceImplfiles);
			Map<String, Map<String, File>> noImplInterFace = filterByFileName(serviceMap, serviceImplMap);
			int count = 0;
			for (Entry<String, Map<String, File>> entry : noImplInterFace.entrySet()) {
				for (@SuppressWarnings("unused")
				Entry<String, File> tempEntry : entry.getValue().entrySet()) {
					count++;
				}
			}
			System.out.println("没有标准命名规则的实现的接口数量：" + count);
			List<String> classNameList = getNoImplInterFace(noImplInterFace, javaMap);
			System.out.println("实际没有实现的接口数量：" + classNameList.size());
			for (String name : classNameList) {
				System.out.println(name);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static List<String> getNoImplInterFace(Map<String, Map<String, File>> noImplInterFace, Map<String, Map<String, File>> javaMap) {
		List<String> result = new ArrayList<>();
		boolean flag = false;
		for (Entry<String, Map<String, File>> entry : noImplInterFace.entrySet()) {
			for (Entry<String, File> tempEntry : entry.getValue().entrySet()) {
				loop: for (Entry<String, Map<String, File>> fileEntry : javaMap.entrySet()) {
					for (Entry<String, File> tempFileEntry : fileEntry.getValue().entrySet()) {
						String[] name = tempEntry.getKey().split("\\.");
						if (isImplementsInterface(name[name.length - 1], tempFileEntry.getValue())) {
							flag = true;
							break loop;
						}
					}
				}
				if (!flag) {
					result.add(tempEntry.getKey());
				} else {
					flag = false;
				}
			}
		}
		return result;
	}

	private static boolean isImplementsInterface(String interfaceName, File file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String str = null;
			int javaType = 1;// 1:java,2:interface
			int type = 0;// 1:extends,2:implements
			while ((str = br.readLine()) != null) {
				if (str.indexOf("interface") != -1) {
					javaType = 2;
				}
				if (str.indexOf("interface " + interfaceName) != -1) {
					return false;
				} else if (type == 1) {
					if (javaType == 1) {
						if (str.indexOf("implements") != -1) {
							type = 2;
							if (str.indexOf(interfaceName) != -1) {
								return true;
							} else {
								continue;
							}
						} else if (str.indexOf(interfaceName) != -1) {
							return false;
						} else if (str.indexOf("{") != -1) {
							return false;
						}
					} else {
						if (str.indexOf(interfaceName) != -1) {
							return true;
						} else if (str.indexOf("\\{") != -1) {
							return false;
						} else {
							continue;
						}
					}
				} else if (type == 2) {
					if (javaType == 1) {
						if (str.indexOf(interfaceName) != -1) {
							return false;
						} else if (str.indexOf("\\{") != -1) {
							return false;
						} else {
							continue;
						}
					} else {
						return false;
					}
				} else {
					if (str.indexOf("extends") != -1) {
						type = 1;
						if (str.indexOf(interfaceName) != -1) {
							return true;
						} else if (str.indexOf("\\{") != -1) {
							return false;
						}
						continue;
					}
				}
				if (str.indexOf("implements") != -1) {
					type = 2;
					if (str.indexOf(interfaceName) != -1) {
						return true;
					} else if (str.indexOf("\\{") != -1) {
						return false;
					} else {
						continue;
					}
				}
			}
		} catch (

		Exception e) {
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
		return false;
	}

	private static Map<String, Map<String, File>> filterByFileName(Map<String, Map<String, File>> serviceMap, Map<String, Map<String, File>> serviceImplMap) {
		Map<String, Map<String, File>> noImplClass = new HashMap<>();
		for (Entry<String, Map<String, File>> entry : serviceMap.entrySet()) {
			if (null == serviceImplMap.get(entry.getKey())) {
				noImplClass.put(entry.getKey(), entry.getValue());
			}
		}
		return noImplClass;
	}

	/**
	 * 将文件列表转化为可操作的map
	 * 
	 * @param fileList
	 * @return
	 */
	private static Map<String, Map<String, File>> changeFileListToFileMap(List<File> fileList) {
		Map<String, Map<String, File>> fileMap = new HashMap<>();
		for (File file : fileList) {
			String entityName = getEntityName(file);
			String className = FileUtil.getClassName(file);
			if (null != fileMap.get(entityName)) {
				fileMap.get(entityName).put(className, file);
			} else {
				Map<String, File> classNameMap = new HashMap<>();
				classNameMap.put(className, file);
				fileMap.put(entityName, classNameMap);
			}
		}
		return fileMap;
	}

	/**
	 * 获取entityname
	 * 
	 * @param file
	 * @return
	 */
	private static String getEntityName(File file) {
		String[] entityName = file.getName().split("Service");
		if (entityName.length > 2) {
			String resultEntityName = entityName[0] + "Service" + entityName[1];
			for (int i = 2; i < entityName.length - 1; i++) {
				resultEntityName = "Service" + entityName[i];
			}
			return resultEntityName;
		} else {
			return entityName[0];
		}
	}
}
