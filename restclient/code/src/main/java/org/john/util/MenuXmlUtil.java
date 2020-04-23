package org.john.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.enhance.common.util.Detect;
import org.enhance.common.util.FileUtil;
import org.john.model.MenuXmlModel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuXmlUtil {

	// 叶子节点遍历结果集
	public static List<MenuXmlModel> restXmlModelList = null;

	/**
	 * 查询xml
	 */
	@SneakyThrows
	public static void getRestXmlModelList(String filePath) {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), StandardCharsets.UTF_8)));
		restXmlModelList = new ArrayList<>();
		Element root = document.getRootElement();
		getElementList(root.elements(), null, true);
	}

	@SneakyThrows
	public static List<MenuXmlModel> mergeXmlModleList(List<MenuXmlModel> tempRestXmlModleList) {
		restXmlModelList = new ArrayList<>();
		if (Detect.notEmpty(tempRestXmlModleList)) {
			Map<String, MenuXmlModel> menuXmlModelMap = new LinkedHashMap<>();
			if (Detect.notEmpty(tempRestXmlModleList)) {
				for (MenuXmlModel menuXmlModel : tempRestXmlModleList) {
					MenuXmlModel oldMenuXmlModel = menuXmlModelMap.get(menuXmlModel.getId());
					if (Detect.notNull(oldMenuXmlModel)) {
						oldMenuXmlModel.getChild().addAll(menuXmlModel.getChild());
					} else {
						menuXmlModelMap.put(menuXmlModel.getId(), menuXmlModel);
					}
				}
			}
			if (Detect.notEmpty(menuXmlModelMap)) {
				for (Map.Entry<String, MenuXmlModel> entry : menuXmlModelMap.entrySet()) {
					restXmlModelList.add(entry.getValue());
				}
			}
		}
		return restXmlModelList;
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
						restXmlModelList.add(menuModel);
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
						restXmlModelList.add(menuModel);
					} else {
						parentMenuModel.addChild(menuModel);
					}
				} else if (elem.getName().equals("separator")) {
					MenuXmlModel menuModel = new MenuXmlModel();
					menuModel.setSeparator(true);
					if (isRootElement) {
						restXmlModelList.add(menuModel);
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
				for (Element sceneItemConfigEelement : sceneItemConfigEelements) {
					if (StringUtils.equals(sceneItemConfigEelement.getName(), "url")) {
						menuModel.setUrl(sceneItemConfigEelement.getData().toString());
					} else if (StringUtils.equals(sceneItemConfigEelement.getName(), "method")) {
						menuModel.setRequestMethod(sceneItemConfigEelement.getData().toString());
					} else if (StringUtils.equals(sceneItemConfigEelement.getName(), "header")) {
						String key = getAtrributeValue(sceneItemConfigEelement, "id");
						String value = sceneItemConfigEelement.getData().toString();
						menuModel.appendHeader(key, value);
					} else if (StringUtils.equals(sceneItemConfigEelement.getName(), "requestBody")) {
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

	/**
	 * 深度查询
	 *
	 * @param root
	 * @param id
	 * @param element
	 * @return
	 */
	private static Element getElementById(Element root, String id, Element element) {
		List elements = root.elements();
		for (Object obj : elements) {
			Element elementx = (Element) obj;
			if (id.equals(elementx.attributeValue("id"))) {
				element = elementx;
				break;
			} else {
				element = getElementById(elementx, id, element);
				if (element != null) {
					break;
				}
			}
		}
		return element;
	}

	// =================================================================
	public static void appendBookModelListToXml(String filePath, MenuXmlModel book) {
		if (FileUtil.exists(filePath)) {
			// 已有记录
			addBook(filePath, book);
		} else {
			// 从无到有
			createBook(filePath, book);
		}
	}

	@SneakyThrows
	public static void removeBook(String filePath, MenuXmlModel sceneModel) {
		if (FileUtil.exists(filePath)) {
			SAXReader saxReader = new SAXReader();
			File saveFile = FileUtil.getFile(filePath);
			Document doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8")));
			Element root = doc.getRootElement();
			Element oldBook = getElementById(root, sceneModel.getId(), null);
			if (Detect.notNull(oldBook)) {
				oldBook.getParent().remove(oldBook);
			}
			saveDocument(doc, null, filePath);
		}
	}

	@SneakyThrows
	private static void addBook(String filePath, MenuXmlModel book) {
		SAXReader saxReader = new SAXReader();
		File saveFile = FileUtil.getFile(filePath);
		Document doc = saxReader.read(new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8")));
		Element root = doc.getRootElement();
		Element oldBook = getElementById(root, book.getId(), null);
		if (Detect.notNull(oldBook)) {
			oldBook.getParent().remove(oldBook);
		}

		addBookAttrbute(doc, book);

		saveDocument(doc, null, filePath);
	}

	private static void createBook(String filePath, MenuXmlModel... books) {
		if (Detect.notEmpty(books)) {
			File saveFile = FileUtil.getFile(filePath);
			Document doc = DocumentHelper.createDocument();
			doc.addElement("menu-config"); // 添加根节点
			for (MenuXmlModel book : books) {
				doc = addBookAttrbute(doc, book);
			}
			saveDocument(doc, saveFile, filePath);
		}
	}

	private static Document addBookAttrbute(Document doc, MenuXmlModel book) {
		Element root = doc.getRootElement();
		// 添加属性节点
		Element scene = getElementById(root, "scene", null);
		if (Detect.isNull(scene)) {
			Element rootMenu = root.addElement("menu");
			rootMenu.addAttribute("id", "toolMenu");
			rootMenu.addAttribute("name", "工具");
		}
		Element bookElement = scene.addElement("menuItem");
		bookElement.addAttribute("id", book.getId());
		bookElement.addAttribute("name", book.getName());

		String url = book.getUrl();
		if (Detect.notEmpty(url)) {
			Element urlElement = bookElement.addElement("url");
			urlElement.addCDATA(url);
		}

		String requestMethod = book.getRequestMethod();
		if (Detect.notEmpty(requestMethod)) {
			Element methodElement = bookElement.addElement("method");
			methodElement.addCDATA(requestMethod);
		}

		Map<String, String> headerMap = book.getHeaderMap();
		if (Detect.notEmpty(headerMap)) {
			for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
				String id = headerEntry.getKey();
				String value = headerEntry.getValue();
				Element header = bookElement.addElement("header");
				header.addAttribute("id", id);
				header.addCDATA(value);
			}
		}

		String requestBody = book.getRequestBody();
		if (Detect.notEmpty(requestBody)) {
			Element methodElement = bookElement.addElement("requestBody");
			methodElement.addCDATA(requestBody);
		}

		return doc;
	}

	private static void saveDocument(Document doc, File file, String filePath) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = null;
		try {
			writer = Detect.isNull(file) ? new XMLWriter(new FileOutputStream(new File(filePath)), format) : new XMLWriter(new FileOutputStream(file), format);

			writer.write(doc);
			writer.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
}
