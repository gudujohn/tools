package org.john.views.component;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import lombok.Getter;

@Getter
public class JsonTextAreaScrollPane extends JScrollPane {

	private static final long serialVersionUID = 6920602567956417023L;

	private JsonTextArea jsonTextArea;

	public JsonTextAreaScrollPane() {
		this.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 1, false), "HTTP Response", TitledBorder.CENTER, TitledBorder.TOP));
		jsonTextArea = new JsonTextArea();
		jsonTextArea.setLineWrap(true); // 启用自动换行
		jsonTextArea.setWrapStyleWord(true); // 启用断行不断字
		this.getViewport().add(jsonTextArea);
	}
}
