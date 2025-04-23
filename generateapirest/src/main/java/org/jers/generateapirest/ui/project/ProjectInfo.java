package org.jers.generateapirest.ui.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.configuration.Constants;
import org.jers.generateapirest.configuration.enums.Packaging;
import org.jers.generateapirest.ui.UserInterface;
import org.jers.generateapirest.ui.ISupportPanelUI;
import org.jers.generateapirest.ui.utils.ComponentUtils;

import lombok.Getter;

public class ProjectInfo extends JPanel implements ISupportPanelUI {

	private static final long serialVersionUID = 1L;

	private UserInterface window;
	private JLabel groupIdLabel;
	private JTextField groupIdText;
	private JLabel artefactIdLabel;

	@Getter
	private JTextField artefactIdText;

	private JLabel nameLabel;
	private JTextField nameText;
	private JLabel descriptionLabel;
	private JTextField descriptionText;
	private JLabel javaVersionLabel;
	private JComboBox<String> javaVersionCB;
	private JLabel packagingLabel;
	private JComboBox<String> packagingCB;
	private JLabel portLabel;
	private JTextField portText;
	private JCheckBox databaseCK;

	private int x = SEPARATOR;
	private int x1 = x;
	private int y = SEPARATOR;
	private int y1 = y;

	public ProjectInfo(UserInterface window) {
		this.window = window;
		initAndSetComponents();
		addComponents();
	}

	@Override
	public void initAndSetComponents() {
		groupIdLabel = new JLabel("Group id:");
		groupIdText = new JTextField();
		artefactIdLabel = new JLabel("Artefact id:");
		artefactIdText = new JTextField();
		nameLabel = new JLabel("Name:");
		nameText = new JTextField();
		descriptionLabel = new JLabel("Description:");
		descriptionText = new JTextField();
		javaVersionLabel = new JLabel("Java:");
		javaVersionCB = new JComboBox<String>();
		loadProperties(javaVersionCB, Constants.JAVA_VERSIONS);
		packagingLabel = new JLabel("Packaging:");

		packagingCB = new JComboBox<String>();
		packagingCB.setModel(new DefaultComboBoxModel<>(Packaging.valuesString()));

		portLabel = new JLabel("Server port:");

		portText = new JTextField();
		portText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				onlyNumber(e);
			}
		});

		databaseCK = new JCheckBox("Database");
		databaseCK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setDatabaseinfo(databaseCK.isSelected());
			}
		});
	}

	@Override
	public void addComponents() {
		setLayout(null);
		setBoundsComponent(groupIdLabel);
		setBoundsText(groupIdText, 100);
		setBoundsComponent(artefactIdLabel);
		setBoundsText(artefactIdText, 100);
		setBoundsComponent(nameLabel);
		setBoundsText(nameText, 100);
		nextLine(nameText);
		setBoundsComponent(descriptionLabel);
		setBoundsText(descriptionText, x - (3 * SEPARATOR) - descriptionLabel.getWidth());
		nextLine(descriptionText);
		setBoundsComponent(javaVersionLabel);
		setBoundsComponent(javaVersionCB);
		setBoundsComponent(packagingLabel);
		setBoundsComponent(packagingCB);
		setBoundsComponent(portLabel);
		setBoundsText(portText, 100);
		setBoundsComponent(databaseCK);
		nextLine(databaseCK);

		setSize(x, y);
		add(groupIdLabel);
		add(groupIdText);
		add(artefactIdLabel);
		add(artefactIdText);
		add(nameLabel);
		add(nameText);
		add(descriptionLabel);
		add(descriptionText);
		add(javaVersionLabel);
		add(javaVersionCB);
		add(packagingLabel);
		add(packagingCB);
		add(portLabel);
		add(portText);
		add(databaseCK);
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
		groupIdText.setText(Config.GROUP_ID);
		artefactIdText.setText(Config.ARTEFACT_ID);
		nameText.setText(Config.NAME);
		descriptionText.setText(Config.DESCRIPTION);
		portText.setText(Config.PORT + "");
	}

	@Override
	public void setConfig() {
		if (isMultiText(groupIdText, artefactIdText, portText)) {
			Config.GROUP_ID = groupIdText.getText();
			Config.ARTEFACT_ID = artefactIdText.getText();
			Config.NAME = nameText.getText();
			Config.DESCRIPTION = descriptionText.getText();
			Config.PORT = Integer.parseInt(portText.getText());
			Config.modifyConstants();
		} else {
			showMessage("Empty fields in Project Information");
		}
	}
}