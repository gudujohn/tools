package org.john.views.panel.tab.header;

import java.awt.*;

import javax.swing.*;

public class HeaderTab extends JPanel {

	private static final long serialVersionUID = -8389508612277928665L;

	public HeaderTab() {
		this.setLayout(new BorderLayout());
		initView();
	}

	private void initView() {
		this.add(new AddHeaderPanel(), BorderLayout.NORTH);
		this.add(new HeaderTablePanel(), BorderLayout.CENTER);
	}
}
