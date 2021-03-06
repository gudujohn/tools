package org.john.action;

import java.awt.event.ActionEvent;

import javax.swing.*;

import org.john.constant.PictureConst;
import org.enhance.common.util.Assertion;
import org.enhance.swing.action.MAction;
import org.enhance.swing.message.MessageUtil;
import org.john.support.ExceptionSupport;
import org.john.util.SpringUtil;
import org.john.views.ComponentPool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecuteAction extends MAction {
	private static final long serialVersionUID = -5985737801133992942L;

	public ExecuteAction() {
		this.putValue(Action.SMALL_ICON, PictureConst.IMAGE_GO);
		this.setToolTipText("执行");
	}

	@Override
	public void execute(ActionEvent e) {
		if (ComponentPool.getInstance().getGo().getIcon() == PictureConst.IMAGE_STOP) {
			MessageUtil.showMessageDialog_Information_Safe("中断功能还没有做，后面再说");
		} else {
			try {
				setURLHistory();
				ComponentPool.getInstance().getRequestProcessBar().setVisible(true);
				ComponentPool.getInstance().getGo().setIcon(PictureConst.IMAGE_STOP);
				SpringUtil.getInstance().getRestController().threadExchange();
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				ComponentPool.getInstance().getRequestProcessBar().setVisible(false);
				ComponentPool.getInstance().getGo().setIcon(PictureConst.IMAGE_GO);
				ExceptionSupport.handler(ex);
			}
		}
	}

	private void setURLHistory() {
		Object selectedItem = ComponentPool.getInstance().getUrlCombo().getSelectedItem();
		Assertion.notNull(selectedItem, "url不能为空");
		String urlText = selectedItem.toString().trim();
		int count = ComponentPool.getInstance().getUrlCombo().getItemCount();
		boolean contains = false;
		for (int i = 0; i < count; i++) {
			String urlHistory = ComponentPool.getInstance().getUrlCombo().getItemAt(i);
			if (urlHistory.equals(urlText)) {
				contains = true;
				break;
			}
		}
		if (!contains) {
			ComponentPool.getInstance().getUrlCombo().addItem(urlText);
		}
	}
}