package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.enhance.common.util.Detect;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuXmlModel {
	private String id;
	private String name;
	private List<MenuXmlModel> child;
	private boolean scene = false;
	private boolean separator = false;
	private boolean menuItem = false;

	private String url;
	private String requestMethod;
	private Map<String, String> headerMap;
	private String requestBody;

	public void appendHeader(String key, String value) {
		if (Detect.isNull(headerMap)) {
			headerMap = new HashMap<>();
		}
		headerMap.put(key, value);
	}

	public boolean isScene() {
		return StringUtils.equals(id, "scene");
	}

	public void addChild(MenuXmlModel childMenuModel) {
		if (Detect.isNull(child)) {
			child = new ArrayList<>();
		}
		child.add(childMenuModel);
	}
}
