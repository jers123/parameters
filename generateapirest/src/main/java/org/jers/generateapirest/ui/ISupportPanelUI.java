package org.jers.generateapirest.ui;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

public interface ISupportPanelUI extends ISupportComponentUI {
	public void setBoundsComponent(JComponent jc);

	public void setBoundsText(JTextComponent jtc, int width);

	public default boolean isText(JTextComponent jtc) {
		return !(jtc.getText().isBlank() || jtc.getText().isEmpty() || jtc.getText() == null);
	}
	
	public default boolean isMultiText(JTextComponent... jtc) {
		boolean result = true;
		for (int i = 0; i < jtc.length; i++) {
			result = result && isText(jtc[i]);
		}
		return result;
	}
}