package org.john.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.john.model.MenuXmlModel;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.enhance.common.util.Assertion;
import com.enhance.common.util.Detect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuXmlUtil {

	// 叶子节点遍历结果集
	public static List<MenuXmlModel> restXmlModleList = null;

	/**
	 * 查询xml
	 */
	@SneakyThrows
	public static void getRestXmlModleList(String filePath) {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), StandardCharsets.UTF_8)));
		restXmlModleList = new ArrayList<>();
		Element root = document.getRootElement();
		getElementList(root.elements(), null, true);
	}

	/**
	 * 递归遍历方法 （定制需求只做了两层循环）
	 * 
	 * @param elements
	 * @param parentMenuModel
	 * @param isRootElement
	 */
	@SuppressWarnings("unchecked")
	private static void getElementList(List<Element> elements, MenuXmlModel parentMenuModel, boolean isRootElement) {
		if (Detect.notEmpty(elements)) {
			for (Element elem : elements) {
				if (elem.getName().equals("menu")) {
					MenuXmlModel menuModel = new MenuXmlModel();
					menuModel.setId(getAtrributeValue(elem, "id"));
					menuModel.setName(getAtrributeValue(elem, "name"));
					if (menuModel.isScene()) {
						appendScene(menuModel, elem);
					} else {
						getElementList(elem.elements(), menuModel, false);
					}
					if (isRootElement) {
						restXmlModleList.add(menuModel);
					} else {
						parentMenuModel.addChild(menuModel);
					}
				} else if (elem.getName().equals("menuItem")) {
					MenuXmlModel menuModel = new MenuXmlModel();
					menuModel.setId(getAtrributeValue(elem, "id"));
					menuModel.setName(getAtrributeValue(elem, "name"));
					menuModel.setMenuItem(true);
					getElementList(elem.elements(), menuModel, false);
					if (isRootElement) {
						restXmlModleList.add(menuModel);
					} else {
						parentMenuModel.addChild(menuModel);
					}
				} else if (elem.getName().equals("separator")) {
					MenuXmlModel menuModel = new MenuXmlModel();
					menuModel.setSeparator(true);
					if (isRootElement) {
						restXmlModleList.add(menuModel);
					} else {
						parentMenuModel.addChild(menuModel);
					}
				}
			}
		}
	}

	private static void appendScene(MenuXmlModel sceneMenuModel, Element sceneElem) {
		List<Element> elements = sceneElem.elements();
		if (Detect.notEmpty(elements)) {
			for (Element elem : elements) {
				MenuXmlModel menuModel = new MenuXmlModel();
				menuModel.setId(getAtrributeValue(elem, "id"));
				menuModel.setName(getAtrributeValue(elem, "name"));
				menuModel.setMenuItem(true);
				List<Element> sceneItemConfigEelements = elem.elements();
				Assertion.notEmpty(sceneItemConfigEelements, "场景属性不能为空,至少一个url");
				for (Element sceneItemConfigEelement : sceneItemConfigEelements) {
					if (StringUtils.equals(sceneItemConfigEelement.getName(), "url")) {
						menuModel.setUrl(sceneItemConfigEelement.getData().toString());
					} else if (StringUtils.equals(sceneItemConfigEelement.getName(), "method")) {
						menuModel.setRequestMethod(sceneItemConfigEelement.getData().toString());
					} else if (StringUtils.equals(sceneItemConfigEelement.getName(), "header")) {
						String key = getAtrributeValue(sceneItemConfigEelement, "id");
						String value = sceneItemConfigEelement.getData().toString();
						menuModel.appendHeader(key, value);
					} else if (StringUtils.equals(sceneItemConfigEelement.getName(), "reqeustBody")) {
						menuModel.setRequestBody(sceneItemConfigEelement.getData().toString());
					}
				}
				sceneMenuModel.addChild(menuModel);
			}
		}
	}

	private static String getAtrributeValue(Element elem, String name) {
		Attribute attribute = elem.attribute(name);
		if (Detect.notNull(attribute)) {
			return elem.attribute(name).getValue();
		}
		return null;
	}
}
