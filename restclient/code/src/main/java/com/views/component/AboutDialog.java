package com.views.component;

import java.awt.*;

import javax.swing.*;

import com.enhance.swing.dialog.EnhanceBasicDialog;

public class AboutDialog extends EnhanceBasicDialog {

	private static final long serialVersionUID = 292464322598700098L;

	private static final String aboutText = "<html>\n" + "<h1>RestClient</h1></br>\n" + "<p style=\"color:green;font-weight:400;\">2、提供多线程压力测试</p></br>\n"
			+ "<p style=\"color:green;font-weight:400;\">3、提供通用的rest接口请求功能</p></br>\n"
			+ "<p style=\"color:grey;font-weight:400;\">3、提供通用历史记录功能--TODO</p></br><p/><p/><p/><p/><p/><p/><p/><p/><p/><p/>\n"
			+ "<p style=\"color:#fff;font-weight:400;text-align:center;\">Copyright© 信辉-蒋耿超</p></br>\n" + "<p style=\"color:#fff;font-weight:400;text-align:center;\">All Rights Reserved.</p>\n"
			+ "</html>";

	public AboutDialog() {
		super(Frame.getFrames()[0], "关于", new Dimension(310, 350));
		this.setLayout(new BorderLayout());
		initView();
		this.setVisible(true);
	}

	private void initView() {
		JLabel label = new JLabel();
		label.setText(aboutText);
		this.add(label, BorderLayout.NORTH);
	}
}
