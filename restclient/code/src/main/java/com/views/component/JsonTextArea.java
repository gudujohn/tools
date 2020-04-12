package com.views.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;

import com.enhance.common.util.Detect;
import com.enhance.common.util.JsonFormatUtil;

public class JsonTextArea extends JTextArea implements MouseListener {

	private static final long serialVersionUID = -2968523403100649451L;

	private JMenuItem copy = null, paste = null, format = null;
	private JPopupMenu pop;

	private String history = StringUtils.EMPTY;

	public JsonTextArea() {
		Border outsideBorder = new EmptyBorder(2, 2, 2, 2);
		Border insideBorder = BorderFactory.createLineBorder(Color.gray);
		Border border = new CompoundBorder(outsideBorder, insideBorder);
		this.setBorder(border);
		init();
	}

	private void init() {
		this.addMouseListener(this);
		format = new JMenuItem("格式化");
		copy = new JMenuItem("复制");
		paste = new JMenuItem("粘贴");
		pop = new JPopupMenu();
		pop.add(format);
		pop.add(new JSeparator());
		pop.add(copy);
		pop.add(paste);
		copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		format.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		copy.addActionListener(this::action);
		paste.addActionListener(this::action);
		format.addActionListener(this::action);
		this.add(pop);
	}

	private void action(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals(copy.getText())) { // 复制
			this.copy();
		} else if (str.equals(paste.getText())) { // 粘贴
			this.paste();
		} else if (str.equals(format.getText())) { // 格式化
			if (Detect.notEmpty(history)) {
				this.setText(history);
				history = StringUtils.EMPTY;
			} else {
				String jsonText = this.getText();
				history = jsonText;
				this.setText(JsonFormatUtil.formatJson(jsonText));
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			copy.setEnabled(true);
			paste.setEnabled(true);
			format.setEnabled(true);
			pop.show(this, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
