package org.jers.generateapirest.ui;

import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.jers.generateapirest.configuration.Constants;

public interface ISupportComponentUI {

	public final int WIDTH_FRAME = 25;
	public final int HEIGTH_FRAME = 40;
	public final int WIDTH_E = 5;
	public final int HEIGTH = 5;
	public final int SEPARATOR = 5;
	public final int WIDTH_EX = 25;

	public void initAndSetComponents();

	public void addComponents();

	public void nextLine(JComponent jc);

	public void getConfig();

	public void setConfig();

	public default void loadProperties(JComboBox<String> jcb, String property) {
		jcb.setModel(new DefaultComboBoxModel<>(Constants.getProperties(property, ",")));
	}

	public default void onlyNumber(KeyEvent evt) {
		if (!Character.isDigit(evt.getKeyChar())) {
			evt.consume();
		}
	}

	public default void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
}