package org.john.views.component.book;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.*;

import org.enhance.common.util.Assertion;
import org.enhance.common.util.Detect;
import org.enhance.swing.action.MAction;
import org.enhance.swing.message.MessageUtil;
import org.john.constant.PathConst;
import org.john.constant.PictureConst;
import org.john.model.MenuXmlModel;
import org.john.util.MenuXmlUtil;
import org.john.views.ComponentPool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateBookmarkMenuItem extends JMenuItem {

	private static final long serialVersionUID = 4089369458810791L;

	public CreateBookmarkMenuItem() {
		addListener();
		this.setText("新建");
		this.setIcon(PictureConst.IMAGE_BOOKMARKADD);
	}

	private void addListener() {
		this.setAction(new MAction("新建", PictureConst.IMAGE_BOOKMARKADD) {
			private static final long serialVersionUID = 8956663202617647137L;

			@Override
			public void execute(ActionEvent e) {
				try {
					// create a new bookmark
					String bookName = JOptionPane.showInputDialog(Frame.getFrames()[0], (Object) "输入名称", "输入名称", JOptionPane.INFORMATION_MESSAGE);
					if (Detect.isNull(bookName)) {
						return;
					}
					// add bookmark menu item
					String url = ComponentPool.getInstance().getUrlCombo().getSelectedItem().toString();
					Assertion.notEmpty(url, "url为空不能添加书签");
					String method = ComponentPool.getInstance().getMethodCombo().getSelectedItem().toString();
					Map<String, String> headerMap = ComponentPool.getInstance().getHeaderTable().getAll();
					String requestBody = ComponentPool.getInstance().getBodyPanel().getJsonTextArea().getText();

					MenuXmlModel book = new MenuXmlModel();
					book.setId(url);
					book.setName(bookName);
					book.setUrl(url);
					book.setRequestMethod(method);
					book.setHeaderMap(headerMap);
					book.setRequestBody(requestBody);

					MenuXmlUtil.appendBookModelListToXml(PathConst.BOOK_MENU_CONFIG_FILE_PATH, book);

					ComponentPool.getInstance().getBookMenu().add(new BookmarkMenuItem(book));
				} catch (Exception ex) {
					log.error(ex.getMessage(), ex);
					MessageUtil.showExceptionDialog_Safe(ex.getMessage());
				}
			}
		});
	}
}