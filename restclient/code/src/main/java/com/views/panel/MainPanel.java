package com.views.panel;

import javax.swing.*;

import com.views.ComponentPool;
import com.views.component.JsonTextAreaScrollPane;

public class MainPanel extends JSplitPane {
	private static final long serialVersionUID = -5676707587497157951L;

	public MainPanel() {
		this.setOrientation(JSplitPane.VERTICAL_SPLIT); // 设置分隔条的方向
//		this.setEnabled(false);
		this.setDividerLocation(300);// 设置分隔条的位置
		this.setOneTouchExpandable(false);
		initFormPanel();
		initResultPanel();
	}

	private void initFormPanel() {
		JPanel formPanel = new FormPanel();
		this.setTopComponent(formPanel);
	}

	private void initResultPanel() {
		JsonTextAreaScrollPane resultPanel = ComponentPool.getInstance().getResultPanel();
		new JsonTextAreaScrollPane();
		this.setBottomComponent(resultPanel);
	}
}
