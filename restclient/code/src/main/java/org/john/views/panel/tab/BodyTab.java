package org.john.views.panel.tab;

import java.awt.*;

import javax.swing.*;

import org.john.views.ComponentPool;
import org.john.views.component.JsonTextAreaScrollPane;

public class BodyTab extends JPanel {

	private static final long serialVersionUID = 3404875271014593168L;

	public BodyTab() {
		this.setLayout(new BorderLayout());

		JsonTextAreaScrollPane bodyPanel = ComponentPool.getInstance().getBodyPanel();
		this.add(bodyPanel, BorderLayout.CENTER);
	}

}
