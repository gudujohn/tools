package org.john.support;

import java.awt.*;

import org.enhance.common.util.Detect;
import org.enhance.common.util.ExceptionUtil;
import org.enhance.swing.dialog.ExceptionDialog;
import org.enhance.swing.message.MessageUtil;

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
