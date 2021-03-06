package org.john.views;

import javax.swing.*;

import org.enhance.swing.table.EnhanceSelectTable;
import org.john.action.ExecuteAction;
import org.john.views.component.JsonTextAreaScrollPane;

import lombok.Getter;

@Getter
public class ComponentPool {
	private static ComponentPool instance;

	private ComponentPool() {
		initComponent();
	}

	public synchronized static ComponentPool getInstance() {
		if (instance == null) {
			instance = new ComponentPool();
		}
		return instance;
	}

	private JMenu bookMenu;

	private JComboBox<String> methodCombo;
	private JComboBox<String> urlCombo;
	private JButton go;
	private EnhanceSelectTable headerTable;

	private JsonTextAreaScrollPane bodyPanel;

	private JsonTextAreaScrollPane resultPanel;

	private JProgressBar requestProcessBar;

	private void initComponent() {
		methodCombo = new JComboBox<>();
		urlCombo = new JComboBox<>();
		go = new JButton(new ExecuteAction());
		headerTable = new EnhanceSelectTable();

		bodyPanel = new JsonTextAreaScrollPane();

		resultPanel = new JsonTextAreaScrollPane();

		requestProcessBar = new JProgressBar();
	}

	public void setBookMenu(JMenu bookMenu) {
		this.bookMenu = bookMenu;
	}

}
