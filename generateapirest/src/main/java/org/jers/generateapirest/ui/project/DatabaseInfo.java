package org.jers.generateapirest.ui.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.configuration.enums.Database;
import org.jers.generateapirest.ui.UserInterface;
import org.jers.generateapirest.ui.ISupportPanelUI;
import org.jers.generateapirest.ui.utils.ComponentUtils;

import lombok.Getter;

public class DatabaseInfo extends JPanel implements ISupportPanelUI {

	private static final long serialVersionUID = 1L;

	private UserInterface window;
	private JLabel driverLabel;
	private JComboBox<String> driverCB;
	private JLabel serverLabel;
	private JTextField serverText;
	private JLabel serverPortLabel;
	private JTextField serverPortText;
	private JLabel databaseNameLabel;

	@Getter
	private JTextField databaseNameText;

	private JLabel userLabel;
	private JTextField userText;
	private JLabel passwordLabel;
	private JPasswordField passwordText;
	private JCheckBox showPassCK;
	private JCheckBox testCK;

	private boolean test;

	private int x = SEPARATOR;
	private int x1 = x;
	private int y = SEPARATOR;
	private int y1 = y;

	public DatabaseInfo(UserInterface window, boolean test) {
		this.window = window;
		this.test = test;
		initAndSetComponents();
		addComponents();
	}

	@Override
	public void initAndSetComponents() {
		driverLabel = new JLabel("Driver:");

		driverCB = new JComboBox<String>();
		driverCB.setModel(new DefaultComboBoxModel<>(Database.valuesString()));
		driverCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				getConfig();
			}
		});

		serverLabel = new JLabel("Server:");
		serverText = new JTextField();
		serverPortLabel = new JLabel("Port:");

		serverPortText = new JTextField();
		serverPortText.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				onlyNumber(e);
			}
		});

		databaseNameLabel = new JLabel("Database name:");
		databaseNameText = new JTextField();
		userLabel = new JLabel("User:");
		userText = new JTextField();
		passwordLabel = new JLabel("Password:");
		passwordText = new JPasswordField();

		showPassCK = new JCheckBox("Show password");
		showPassCK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showPassCK.isSelected()) {
					passwordText.setEchoChar((char) 0);
				} else {
					passwordText.setEchoChar('•');
				}
			}
		});

		if (!test) {
			testCK = new JCheckBox("Test database");
			testCK.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					window.setDatabaseinfoTest(testCK.isSelected());
				}
			});
		} else {
			databaseNameText.setEnabled(false);
		}
	}

	@Override
	public void addComponents() {
		setLayout(null);
		setBoundsComponent(driverLabel);
		setBoundsComponent(driverCB);
		setBoundsComponent(serverLabel);
		setBoundsText(serverText, 100);
		nextLine(serverText);
		setBoundsComponent(serverPortLabel);
		setBoundsText(serverPortText, 100);
		setBoundsComponent(databaseNameLabel);
		setBoundsText(databaseNameText, 100);
		nextLine(databaseNameText);
		setBoundsComponent(userLabel);
		setBoundsText(userText, 100);
		setBoundsComponent(passwordLabel);
		setBoundsText(passwordText, 100);
		nextLine(passwordText);
		setBoundsComponent(showPassCK);
		if (!test) {
			setBoundsComponent(testCK);
		}
		nextLine(showPassCK);

		setSize(x, y);
		add(driverLabel);
		add(driverCB);
		add(serverLabel);
		add(serverText);
		add(serverPortLabel);
		add(serverPortText);
		add(databaseNameLabel);
		add(databaseNameText);
		add(userLabel);
		add(userText);
		add(passwordLabel);
		add(passwordText);
		add(showPassCK);
		if (!test) {
			add(testCK);
		}
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
		// Config.DATABASE = artefactIdText.getText().replace("-", "") + "_db";
		Database database = Database.compareDB((String) driverCB.getSelectedItem());
		serverText.setText(database.getServer());
		serverPortText.setText(database.getPort().toString());
		// databaseNameText.setText(Config.DATABASE);
		userText.setText(database.getUser());
		passwordText.setText(database.getPass());
	}

	@Override
	public void setConfig() {
		if (isMultiText(serverText, databaseNameText, serverPortText, userText)) {
			Database database = Database.compareDB((String) driverCB.getSelectedItem());
			if(test) {
				Config.DATABASE_TEST.setData(database.getDbLibrary(), serverText.getText().trim(), databaseNameText.getText().trim(), Integer.parseInt(serverPortText.getText().trim()), database.getSchema(), userText.getText().trim(), passwordText.getText().trim());
				Config.DATABASE_TEST.getDbLibrary().setRequired(true);
			} else {
				Config.DATABASE.setData(database.getDbLibrary(), serverText.getText().trim(), databaseNameText.getText().trim(), Integer.parseInt(serverPortText.getText().trim()), database.getSchema(), userText.getText().trim(), passwordText.getText().trim());
				Config.DATABASE.getDbLibrary().setRequired(true);
			}
		} else {
			showMessage("Empty fields in " + ((test) ? "Test" : "") + " Database Information");
		}
	}
}