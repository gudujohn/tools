package com.views.panel.tab.header;

import java.awt.event.ActionEvent;

import javax.swing.*;

import com.constant.PictureConst;
import com.enhance.common.util.Assertion;
import com.enhance.swing.action.MAction;
import com.enhance.swing.field.EnhanceTextField;
import com.enhance.swing.layout.EnhanceBoxLayout;
import com.views.ComponentPool;

public class AddHeaderPanel extends JPanel {

	private static final long serialVersionUID = -6726022256448792615L;

	public AddHeaderPanel() {
		this.setLayout(new EnhanceBoxLayout(EnhanceBoxLayout.BoxType.X_AXIS));
		initView();
	}

	private void initView() {
		EnhanceTextField keyTextField = new EnhanceTextField("KEY");

		EnhanceTextField valueTextField = new EnhanceTextField("VALUE");

		JButton addButton = new JButton(new MAction(PictureConst.IMAGE_ADD, "Add") {
			private static final long serialVersionUID = 619099619120430577L;

			@Override
			public void execute(ActionEvent actionEvent) {
				String key = keyTextField.getValue().trim();
				String value = valueTextField.getValue().trim();
				Assertion.notEmpty(key, "key can not be null");
				Assertion.notEmpty(value, "value can not be null");
				String[] data = { key, value };
				ComponentPool.getInstance().getHeaderTable().addValue(data);
			}
		});

		this.add(keyTextField, "1w");
		this.add(valueTextField, "1w");
		this.add(addButton, "auto");
	}

}
