package org.john.views.component.book;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.*;

import org.apache.commons.lang3.StringUtils;
import org.enhance.common.util.Detect;
import org.enhance.swing.message.MessageUtil;
import org.john.constant.PathConst;
import org.john.constant.PictureConst;
import org.john.enums.MethodEnum;
import org.john.model.MenuXmlModel;
import org.john.util.MenuXmlUtil;
import org.john.views.ComponentPool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookmarkMenuItem extends JMenuItem {

	private static final long serialVersionUID = 520015178394976498L;

	private MenuXmlModel sceneModel;

	public BookmarkMenuItem(MenuXmlModel sceneModel) {
		this.sceneModel = sceneModel;
		this.setText(sceneModel.getName());
		this.setIcon(PictureConst.IMAGE_BOOKMARKNEW);
		addListener();
	}

	private void addListener() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				onMousePressed(e);
			}
		});
	}

	private void onMousePressed(MouseEvent e) {
		try {
			if (e.getButton() == MouseEvent.BUTTON1) {
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
			} else {
				// delete bookmark
				int confirm = MessageUtil.showConfirmDialog_YesOrNo("是否删除当前选择的书签");
				if (confirm == JOptionPane.YES_OPTION) {
					// 删除当前选择的书签
					MenuXmlUtil.removeBook(PathConst.BOOK_MENU_CONFIG_FILE_PATH, sceneModel);
					ComponentPool.getInstance().getBookMenu().remove(this);
				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			MessageUtil.showExceptionDialog_Safe(ex.getMessage());
		}
	}

}
