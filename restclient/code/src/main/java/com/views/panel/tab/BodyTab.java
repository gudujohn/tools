package com.views.panel.tab;

import java.awt.*;

import javax.swing.*;

import com.views.ComponentPool;
import com.views.component.JsonTextAreaScrollPane;

public class BodyTab extends JPanel {

	private static final long serialVersionUID = 3404875271014593168L;

	public BodyTab() {
		this.setLayout(new BorderLayout());

		JsonTextAreaScrollPane bodyPanel = ComponentPool.getInstance().getBodyPanel();
		this.add(bodyPanel, BorderLayout.CENTER);
	}

}
