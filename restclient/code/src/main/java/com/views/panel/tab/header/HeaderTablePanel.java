package com.views.panel.tab.header;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.enhance.swing.table.EnhanceSelectTable;
import com.views.ComponentPool;

public class HeaderTablePanel extends JScrollPane {

	private static final long serialVersionUID = 1701232232205145262L;

	public HeaderTablePanel() {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		initView();
	}

	private void initView() {
		String[] titleName = { "Header", "Value" };
		String[][] data = {};
		DefaultTableModel model = new DefaultTableModel(data, titleName);
		EnhanceSelectTable headerTable = ComponentPool.getInstance().getHeaderTable();
		headerTable.setModel(model);

		this.getViewport().add(headerTable);
	}
}
