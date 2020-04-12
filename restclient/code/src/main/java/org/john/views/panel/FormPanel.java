package org.john.views.panel;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.john.enums.MethodEnum;
import org.john.views.ComponentPool;
import org.john.views.panel.tab.BodyTab;
import org.john.views.panel.tab.header.HeaderTab;

public class FormPanel extends JPanel {
	private static final long serialVersionUID = -5929789622274396087L;

	protected JComboBox<String> urlCombo;
	protected JComboBox<String> methodCombo;

	public FormPanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 1, false), "HTTP Request", TitledBorder.CENTER, TitledBorder.TOP));

		initComponent();
	}

	private void initComponent() {
		HeaderTab headerTab = new HeaderTab();
		BodyTab bodyTab = new BodyTab();
		urlCombo = ComponentPool.getInstance().getUrlCombo();

		JPanel headPanel = new JPanel(new BorderLayout());
		addConfig(headPanel);
		addUrlField(headPanel);
		this.add(headPanel, BorderLayout.NORTH);

		JTabbedPane tabbedPane = new JTabbedPane();// 存放选项卡的组件
		tabbedPane.addTab("Header", headerTab);
		tabbedPane.addTab("Body", bodyTab);

		this.add(tabbedPane, BorderLayout.CENTER);

		initDefaultHeaderData();
	}

	protected void addConfig(JPanel headPanel) {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel rightPanel = new JPanel(new BorderLayout());

		JLabel label = new JLabel("请求方式:");
		leftPanel.add(label, BorderLayout.WEST);
		String[] items = new String[] { MethodEnum.POST.getText(), MethodEnum.GET.getText(), MethodEnum.PUT.getText(), MethodEnum.DELETE.getText() };
		methodCombo = ComponentPool.getInstance().getMethodCombo();
		methodCombo.setModel(new DefaultComboBoxModel<>(items));
		methodCombo.setSelectedIndex(0);
		leftPanel.add(methodCombo, BorderLayout.CENTER);
		panel.add(leftPanel);

		panel.add(rightPanel);

		headPanel.add(panel, BorderLayout.NORTH);
	}

	private void addUrlField(JPanel headPanel) {
		JPanel panel = new JPanel(new BorderLayout());

		JLabel label = new JLabel("URL:");
		panel.add(label, BorderLayout.WEST);

		JPanel componentPanel = new JPanel();
		componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.X_AXIS));
		urlCombo.setEditable(true);
		JButton go = ComponentPool.getInstance().getGo();
		componentPanel.add(urlCombo);
		componentPanel.add(go);
		panel.add(componentPanel, BorderLayout.CENTER);

		headPanel.add(panel, BorderLayout.SOUTH);
	}

	protected void initDefaultHeaderData() {

	}
}
