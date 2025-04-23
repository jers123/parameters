package org.jers.generateapirest.ui.depemdencies;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.dependency.DependencyData;
import org.jers.generateapirest.dependency.ScopeGradle;
import org.jers.generateapirest.dependency.ScopeMaven;
import org.jers.generateapirest.ui.ISupportPanelUI;
import org.jers.generateapirest.ui.utils.ComponentUtils;

import lombok.Getter;

public class DependencyInfo extends JPanel implements ISupportPanelUI {

	private static final long serialVersionUID = 1L;

	private DependenciesConfig window;
	private JCheckBox activeCK;
	private JLabel nameLabel;
	private JTextField groupIdText;
	private JTextField artefactIdText;
	private JTextField versionText;
	private JComboBox<String> scopeCB;

	@Getter
	private DependencyData dependencyData;

	private int x = SEPARATOR;
	private int x1 = x;
	private int y = SEPARATOR;
	private int y1 = y;

	public DependencyInfo(DependenciesConfig window, DependencyData dependencyData) {
		this.window = window;
		this.dependencyData = dependencyData;
		initAndSetComponents();
		addComponents();
	}

	@Override
	public void initAndSetComponents() {
		activeCK = new JCheckBox(" ");
		nameLabel = new JLabel(dependencyData.getName());
		groupIdText = new JTextField(dependencyData.getGroupId());
		artefactIdText = new JTextField(dependencyData.getArtefactId());
		versionText = new JTextField(dependencyData.getVersion());

		scopeCB = new JComboBox<String>();
		if (Config.IS_MAVEN) {
			scopeCB.setModel(new DefaultComboBoxModel<>(ScopeMaven.valuesString()));
			scopeCB.setSelectedItem(dependencyData.getScopeMaven().getScope());
		} else {
			scopeCB.setModel(new DefaultComboBoxModel<>(ScopeGradle.valuesString()));
			scopeCB.setSelectedItem(dependencyData.getScopeGradle().getScope());
		}
		scopeCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (Config.IS_MAVEN) {
					dependencyData.setScopeMaven(ScopeMaven.compareDB(scopeCB.getSelectedItem().toString()));
				} else {
					dependencyData.setScopeGradle(ScopeGradle.compareDB(scopeCB.getSelectedItem().toString()));
				}
			}
		});
		
		getConfig();
	}

	@Override
	public void addComponents() {
		setLayout(null);
		setBoundsComponent(activeCK);
		setBoundsComponent(nameLabel);
		setBoundsComponent(groupIdText);
		setBoundsComponent(artefactIdText);
		setBoundsComponent(versionText);
		setBoundsComponent(scopeCB);
		nextLine(scopeCB);

		setSize(x, y);
		add(activeCK);
		add(nameLabel);
		add(groupIdText);
		add(artefactIdText);
		add(versionText);
		add(scopeCB);
	}

	@Override
	public void nextLine(JComponent jc) {
		if (x < x1) {
			x = x1;
		}
		x1 = SEPARATOR;
		y1 = y1 + jc.getHeight() + SEPARATOR;
		if (y < y1) {
			y = y1;
		}
	}

	@Override
	public void setBoundsComponent(JComponent jc) {
		ComponentUtils.setBoundsComponent(jc, x1, y1, WIDTH_EX, WIDTH_E, HEIGHT);
		x1 = x1 + jc.getWidth() + SEPARATOR;
	}

	@Override
	public void setBoundsText(JTextComponent jtc, int width) {
		jtc.setBounds(x1, y1, width, 20);
		x1 = x1 + jtc.getWidth() + SEPARATOR;
	}

	@Override
	public void getConfig() {
		if(dependencyData.getRequired()) {
			activeCK.setSelected(true);
			activeCK.setEnabled(false);
			nameLabel.setEnabled(false);
			groupIdText.setEnabled(false);
			artefactIdText.setEnabled(false);
			scopeCB.setEnabled(false);
		}
	}

	@Override
	public void setConfig() {
		if (isText(versionText)) {
			dependencyData.setVersion(versionText.getText());
		}
		if (activeCK.isSelected()) {
			dependencyData.setRequired(true);
		}
	}
}