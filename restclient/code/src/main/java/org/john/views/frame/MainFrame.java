package org.john.views.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.john.constant.PictureConst;
import org.enhance.swing.action.MAction;
import org.enhance.swing.message.MessageUtil;
import org.enhance.swing.progress.ProgressBarCallback;
import org.enhance.swing.statusbar.EnhanceStatusBar;
import org.enhance.swing.statusbar.EnhanceStatusItem;
import org.john.support.MenuInitSupport;
import org.john.support.config.SystemConfig;
import org.john.views.ComponentPool;
import org.john.views.component.AboutDialog;
import org.john.views.panel.MainPanel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private LoginFrame loginForm;
	private JFrame mainFrame = this;

	public MainFrame() {
		setDefaultLookAndFeelDecorated(false);
		this.setIconImage(PictureConst.IMAGE_LOGO.getImage());
		this.setTitle("IRM2CSF-RestClient");
		this.setSize(500, 650);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(false);

		try {
			List<ProgressBarCallback> progressBarCallbacks = new ArrayList<>();
			progressBarCallbacks.add(new ProgressBarCallback("主界面初始化", 90, 100, 1, 100) {
				@Override
				public boolean execute() throws Exception {
					MenuInitSupport.getInstance();
					initView();
					loginForm.closeLoginForm();
					mainFrame.setVisible(true);
					return true;
				}
			});
			loginForm = new LoginFrame(progressBarCallbacks);
			loginForm.setVisible(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			MessageUtil.showMessageDialog_Information_Safe("初始化界面出错!" + e.getMessage());
		}
	}

	private void initView() {
		initMenuBar();
		initStatueBar();

		this.add(new MainPanel(), BorderLayout.CENTER);
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu beginMenu = new JMenu("开始");
		beginMenu.add(new JSeparator());
		beginMenu.add(new JMenuItem(new MAction("推出", PictureConst.IMAGE_EXIT) {
			private static final long serialVersionUID = -5193296470887353534L;

			@Override
			public void execute(ActionEvent e) {
				int result = MessageUtil.showConfirmDialog_YesOrNo("是否推出系统？");
				if (JOptionPane.OK_OPTION == result) {
					System.exit(0);
				}
			}
		}));
		menuBar.add(beginMenu);

		MenuInitSupport.initMenu(menuBar, null, null, false);

		JMenu helpMenu = new JMenu("帮助");
		helpMenu.add(new JSeparator());
		helpMenu.add(new JMenuItem(new MAction("关于", PictureConst.IMAGE_ABOUT) {
			private static final long serialVersionUID = 9111066486519609402L;

			@Override
			public void execute(ActionEvent e) {
				new AboutDialog();
			}
		}));
		menuBar.add(helpMenu);

		this.add(menuBar, BorderLayout.NORTH);
	}

	private void initStatueBar() {
		EnhanceStatusBar statusBar = new EnhanceStatusBar();
		EnhanceStatusItem leftItem = new EnhanceStatusItem(new JLabel("Rest工具版本号:" + SystemConfig.getVersion()));
		statusBar.addItem(leftItem);

		EnhanceStatusItem rightItem = new EnhanceStatusItem();
		JProgressBar requestProcessBar = ComponentPool.getInstance().getRequestProcessBar();
		requestProcessBar.setIndeterminate(true);// 不确定进度条
		requestProcessBar.setVisible(false);
//		new RunningProgressBarThread(requestProcessBar);

		rightItem.add(requestProcessBar);
		statusBar.addItem(rightItem, EnhanceStatusBar.RIGHT);
		this.add(statusBar, BorderLayout.SOUTH);
	}

}
