package org.john.support;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.apache.commons.lang3.StringUtils;
import org.enhance.common.util.Detect;
import org.enhance.swing.action.MAction;
import org.john.constant.PathConst;
import org.john.constant.PictureConst;
import org.john.model.MenuXmlModel;
import org.john.util.MenuXmlUtil;
import org.john.util.SpringUtil;
import org.john.views.ComponentPool;
import org.john.views.component.book.BookmarkMenuItem;

import lombok.extern.slf4j.Slf4j;
import org.john.views.component.book.CreateBookmarkMenuItem;

@Slf4j
public class MenuInitSupport {

	private static MenuInitSupport instance;

	private static List<MenuXmlModel> restXmlModleList = new ArrayList<>();

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
		String baseMenuConfigfilePath = PathConst.BASE_MENU_CONFIG_FILE_PATH;
		String bookMenuConfigfilePath = PathConst.BOOK_MENU_CONFIG_FILE_PATH;
		try {
			log.info("load base menu config:" + baseMenuConfigfilePath);
			MenuXmlUtil.getRestXmlModelList(baseMenuConfigfilePath);
			if (Detect.notEmpty(MenuXmlUtil.restXmlModelList)) {
				restXmlModleList.addAll(MenuXmlUtil.restXmlModelList);
			}
			log.info("load book menu config:" + bookMenuConfigfilePath);
			MenuXmlUtil.getRestXmlModelList(bookMenuConfigfilePath);
			if (Detect.notEmpty(MenuXmlUtil.restXmlModelList)) {
				restXmlModleList.addAll(MenuXmlUtil.restXmlModelList);
			}
			restXmlModleList = MenuXmlUtil.mergeXmlModleList(restXmlModleList);
		} catch (Exception e) {
			log.info("没有" + baseMenuConfigfilePath + "或" + bookMenuConfigfilePath + "相关配置", e);
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
					((JMenu) menu).setIcon(PictureConst.IMAGE_BOOKMARK);
					((JMenu) menu).add(new CreateBookmarkMenuItem());
					((JMenu) menu).addSeparator();
					List<MenuXmlModel> sceneMenuModelList = menuXmlModel.getChild();
					if (Detect.notEmpty(sceneMenuModelList)) {
						for (MenuXmlModel item : sceneMenuModelList) {
							appendSceneMenuItem(item, (JMenu) menu);
						}
					} else {
						continue;
					}
					ComponentPool.getInstance().setBookMenu((JMenu) menu);
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
		JMenuItem menuItem = new BookmarkMenuItem(sceneModel);
		sceneMenu.add(menuItem);
	}

}
