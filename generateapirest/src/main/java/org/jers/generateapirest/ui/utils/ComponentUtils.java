package org.jers.generateapirest.ui.utils;

import java.awt.FontMetrics;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

public abstract class ComponentUtils {
	public static void setBoundsComponent(JComponent jc, int x1, int y1, int widthEx, int width, int height) {
		int widthExtra = 0;
		FontMetrics fm = jc.getFontMetrics(jc.getFont());
		String text = getTextFromComponent(jc);
		widthExtra = (jc instanceof AbstractButton) ? 30 : (jc instanceof JComboBox) ? widthEx : 0;
		jc.setBounds(x1, y1, fm.stringWidth(text) + widthExtra + width, fm.getHeight() + height);
	}

	private static String getTextFromComponent(JComponent jc) {
		if (jc instanceof JTextComponent) {
			return ((JTextComponent) jc).getText();
		} else if (jc instanceof JLabel) {
			return ((JLabel) jc).getText();
		} else if (jc instanceof AbstractButton) {
			return ((AbstractButton) jc).getText();
		} else if (jc instanceof JComboBox) {
			return getLongestItem((JComboBox<String>) jc);
		}
		return "";
	}

	private static String getLongestItem(JComboBox<String> comboBox) {
		int maxWidth = 0;
		String longestItem = "";
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			Object item = comboBox.getItemAt(i);
			if (item != null) {
				int itemWidth = comboBox.getFontMetrics(comboBox.getFont()).stringWidth(item.toString());
				if (itemWidth > maxWidth) {
					maxWidth = itemWidth;
					longestItem = item.toString();
				}
			}
		}
		return longestItem;
	}
}
