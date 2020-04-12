package com;

import com.enhance.swing.EnhanceUIManager;
import com.enhance.swing.message.MessageUtil;
import com.util.SettingLoad;
import com.views.frame.MainFrame;

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