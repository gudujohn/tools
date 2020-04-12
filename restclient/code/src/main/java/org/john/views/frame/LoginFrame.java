package org.john.views.frame;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.john.constant.PictureConst;
import com.enhance.swing.progress.ProgressBarCallback;
import com.enhance.swing.progress.ProgressBarThread;
import org.john.support.config.SystemConfig;
import org.john.util.SpringUtil;
import org.john.views.ComponentPool;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel = new JPanel(false);
	private JProgressBar progressBar;

	public LoginFrame(List<ProgressBarCallback> progressBarCallbacks) {
		initWindow();
		this.setIconImage(PictureConst.IMAGE_LOGO.getImage());
		Dimension windowSize = this.getPreferredSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setUndecorated(true);
		this.setLocation(screenSize.width / 2 - windowSize.width / 2, screenSize.height / 2 - windowSize.height / 2);
		this.pack();
		this.setResizable(false);

		progressBarCallbacks.add(0, new ProgressBarCallback("延迟等待", 1, 90, 1, 100) {
			@Override
			public boolean execute() throws Exception {
				ComponentPool.getInstance();
				SpringUtil.getInstance();
				return true;
			}
		});
		Thread stepper = new ProgressBarThread(progressBar, progressBarCallbacks);
		stepper.start();
	}

	private void initWindow() {
		// backgroud
		JLabel backgroud = new JLabel(PictureConst.IMAGE_BACKGROUND);

		JLabel versionLabel = new JLabel("@Copyright JiangGengchao 版本信息" + SystemConfig.getVersion(), SwingConstants.LEFT);
		versionLabel.setForeground(new Color(75, 75, 75));
		versionLabel.setBackground(new Color(216, 240, 246));
		versionLabel.setOpaque(true);
		versionLabel.setVisible(true);

		progressBar = new JProgressBar(0, 100);
		progressBar.setAlignmentX(0);
		progressBar.setStringPainted(true);
		progressBar.setPreferredSize(new Dimension(480, 20));
		progressBar.setStringPainted(true); // 显示百分比字符
		progressBar.setIndeterminate(false); // 不确定的进度条
		JPanel progressPanel = new JPanel(false);
		progressPanel.setLayout(new GridBagLayout());
		JPanel versionPanel = new JPanel();
		versionPanel.setLayout(new GridBagLayout());
		versionPanel.add(versionLabel, new GridBagConstraints(0, 0, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
		progressPanel.add(versionPanel, new GridBagConstraints(0, 0, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
		progressPanel.add(progressBar, new GridBagConstraints(0, 1, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		Border outsideBorder = new EmptyBorder(3, 3, 3, 3);
		Border insideBorder = BorderFactory.createLineBorder(Color.gray);
		Border border = new CompoundBorder(outsideBorder, insideBorder);

		mainPanel.setLayout(new GridBagLayout());
		mainPanel.add(backgroud, new GridBagConstraints(0, 0, 4, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
		mainPanel.add(progressPanel, new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
		mainPanel.setBorder(border);

		this.getContentPane().add(mainPanel);
	}

	public void closeLoginForm() {
		this.setVisible(false);
		this.dispose();
	}

}
