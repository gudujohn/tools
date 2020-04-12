package com.support;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import org.apache.commons.lang3.StringUtils;

import com.constant.PictureConst;
import com.enhance.common.util.Detect;
import com.enhance.common.util.FileUtil;
import com.enhance.swing.action.MAction;
import com.enums.MethodEnum;
import com.model.MenuXmlModel;
import com.util.MenuXmlUtil;
import com.util.SpringUtil;
import com.views.ComponentPool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuInitSupport {

	private static MenuInitSupport instance;

	private static List<MenuXmlModel> restXmlModleList;

	private MenuInitSupport() {
		initMenuInitSupport();
	}

	public synchronized static MenuInitSupport getInstance() {
		if (instance == null) {
			instance = new MenuInitSupport();
		}
		return instance;
	}

	private void initMenuInitSupport() {
		String filePath = FileUtil.getSystemAbsolutePath() + "config/viewconfig/menu.config.xml";
		log.info("load menu config:" + filePath);
		try {
			MenuXmlUtil.getRestXmlModleList(filePath);
			restXmlModleList = MenuXmlUtil.restXmlModleList;
		} catch (Exception e) {
			log.info("没有" + filePath + "相关配置", e);
		}
	}

	public static void initMenu(JMenuBar menuBar, JMenu parentMenu, List<MenuXmlModel> child, boolean isChild) {
		if (Detect.isNull(child)) {
			child = restXmlModleList;
		}
		if (Detect.notEmpty(child)) {
			for (MenuXmlModel menuXmlModel : child) {
				JComponent menu = null;

				if (StringUtils.equals(menuXmlModel.getId(), "singThreadTest")) {
					menu = new JMenuItem(menuXmlModel.getName());
					appendSingThreadTestMenuItem((JMenuItem) menu);
				} else if (StringUtils.equals(menuXmlModel.getId(), "multiThreadTest")) {
					menu = new JMenuItem(menuXmlModel.getName());
					appendMultiThreadTestMenuItem((JMenuItem) menu);
				} else if (menuXmlModel.isSeparator()) {
					menu = new JSeparator();
				} else if (menuXmlModel.isMenuItem()) {
					menu = new JMenuItem(menuXmlModel.getName());
				} else if (menuXmlModel.isScene()) {
					menu = new JMenu(menuXmlModel.getName());
					List<MenuXmlModel> sceneMenuModelList = menuXmlModel.getChild();
					if (Detect.notEmpty(sceneMenuModelList)) {
						for (MenuXmlModel item : sceneMenuModelList) {
							appendSceneMenuItem(item, (JMenu) menu);
						}
					} else {
						continue;
					}
				} else {
					menu = new JMenu(menuXmlModel.getName());
				}

				List<MenuXmlModel> nextChild = menuXmlModel.getChild();
				if (Detect.notEmpty(nextChild) && !menuXmlModel.isScene()) {
					initMenu(menuBar, (JMenu) menu, nextChild, true);
				}

				if (isChild) {
					parentMenu.add(menu);
				} else {
					menuBar.add(menu);
				}
			}
		}
	}

	private static void appendSingThreadTestMenuItem(JMenuItem menuItem) {
		menuItem.setAction(new MAction(menuItem.getText()) {
			private static final long serialVersionUID = 6134715913035251403L;

			@Override
			public void execute(ActionEvent e) {
				try {
					ComponentPool.getInstance().getRequestProcessBar().setVisible(true);
					ComponentPool.getInstance().getGo().setIcon(PictureConst.IMAGE_STOP);
					SpringUtil.getInstance().getRestController().singThreadExchange();
				} catch (Exception ex) {
					ExceptionSupport.handler(ex);
					ComponentPool.getInstance().getRequestProcessBar().setVisible(false);
					ComponentPool.getInstance().getGo().setIcon(PictureConst.IMAGE_GO);
				}
			}
		});
	}

	private static void appendMultiThreadTestMenuItem(JMenuItem menuItem) {
		menuItem.setAction(new MAction(menuItem.getText()) {
			private static final long serialVersionUID = 7232938133342635349L;

			@Override
			public void execute(ActionEvent e) {
				try {
					ComponentPool.getInstance().getRequestProcessBar().setVisible(true);
					ComponentPool.getInstance().getGo().setIcon(PictureConst.IMAGE_STOP);
					SpringUtil.getInstance().getRestController().multiThreadExchange();
				} catch (Exception ex) {
					ExceptionSupport.handler(ex);
					ComponentPool.getInstance().getRequestProcessBar().setVisible(false);
					ComponentPool.getInstance().getGo().setIcon(PictureConst.IMAGE_GO);
				}
			}
		});
	}

	private static void appendSceneMenuItem(MenuXmlModel sceneModel, JMenu sceneMenu) {

		JMenuItem menuItem = new JMenuItem(new MAction(sceneModel.getName()) {
			private static final long serialVersionUID = 6511468245059478243L;

			@Override
			public void execute(ActionEvent actionEvent) {
				String url = sceneModel.getUrl();
				String requestMethod = sceneModel.getRequestMethod();
				Map<String, String> headerMap = sceneModel.getHeaderMap();
				String requestBody = sceneModel.getRequestBody();
				if (Detect.notEmpty(url)) {
					int count = ComponentPool.getInstance().getUrlCombo().getItemCount();
					boolean contains = false;
					for (int i = 0; i < count; i++) {
						String urlHistory = ComponentPool.getInstance().getUrlCombo().getItemAt(i);
						if (urlHistory.equals(url)) {
							contains = true;
							break;
						}
					}
					if (!contains) {
						ComponentPool.getInstance().getUrlCombo().addItem(url);
					}
					ComponentPool.getInstance().getUrlCombo().setSelectedItem(url);
				}
				if (Detect.notEmpty(requestMethod)) {
					MethodEnum methodEnum = MethodEnum.get(requestMethod);
					ComponentPool.getInstance().getMethodCombo().setSelectedItem(methodEnum.getText());
				}
				ComponentPool.getInstance().getBodyPanel().getJsonTextArea().setText(StringUtils.EMPTY);
				if (Detect.notEmpty(requestBody)) {
					ComponentPool.getInstance().getBodyPanel().getJsonTextArea().setText(requestBody);
				}
				ComponentPool.getInstance().getHeaderTable().removeAllData();
				if (Detect.notEmpty(headerMap)) {
					int size = headerMap.size();
					String[][] datas = new String[size][2];
					Iterator iterator = headerMap.entrySet().iterator();
					for (int i = 0; i < size; i++) {
						Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
						String key = entry.getKey();
						String value = entry.getValue();
						datas[i][0] = key;
						datas[i][1] = value;
					}
					ComponentPool.getInstance().getHeaderTable().addValue(datas);
				}
			}
		});

		sceneMenu.add(menuItem);
	}

}
