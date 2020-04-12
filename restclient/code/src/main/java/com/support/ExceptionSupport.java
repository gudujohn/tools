package com.support;

import java.awt.*;

import com.enhance.common.util.Detect;
import com.enhance.common.util.ExceptionUtil;
import com.enhance.swing.dialog.ExceptionDialog;
import com.enhance.swing.message.MessageUtil;

public class ExceptionSupport {
	private ExceptionSupport() {
		throw new IllegalStateException("Constant class");
	}

	public static void handler(Exception e, String... defaultMsg) {
		if (ExceptionUtil.isBusinessException(e)) {
			MessageUtil.showMessageDialog_Warnning_Safe(e.getMessage());
		} else {
			if (Detect.notEmpty(defaultMsg)) {
				ExceptionDialog.traceException(Frame.getFrames()[0], "异常", defaultMsg[0], e);
			} else {
				ExceptionDialog.traceException(Frame.getFrames()[0], "异常", e.getMessage(), e);
			}
		}
	}

}
