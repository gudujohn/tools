package org.john;

import org.enhance.swing.EnhanceUIManager;
import org.enhance.swing.message.MessageUtil;
import org.john.util.SettingLoad;
import org.john.views.frame.MainFrame;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

	public static void main(String[] args) {
		try {
			SettingLoad.getInstance();
			EnhanceUIManager.changeFlavor(EnhanceUIManager.LOOKANDFEEL_NIMBUS);
			new MainFrame();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			MessageUtil.showExceptionDialog_Safe(e.getMessage());
		}
	}
}