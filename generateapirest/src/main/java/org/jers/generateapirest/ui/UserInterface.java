package org.jers.generateapirest.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.ui.project.ConfigInfo;
import org.jers.generateapirest.ui.project.DatabaseInfo;
import org.jers.generateapirest.ui.project.ProjectInfo;

public class UserInterface extends JFrame implements ISupportComponentUI {

	private static final long serialVersionUID = 1L;

	private ProjectInfo projectInfo;
	private ConfigInfo configInfo;
	private DatabaseInfo databaseInfo = null;
	private DatabaseInfo databaseInfoTest = null;
	
	private int x = SEPARATOR;
	private int x1 = x;
	private int y = SEPARATOR;
	private int y1 = y;

	public UserInterface() {
		initAndSetComponents();
		addComponents();
		setVisible(true);
		getConfig();
	}

	@Override
	public void initAndSetComponents() {
		setTitle("Generate API REST");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		projectInfo = new ProjectInfo(this);
		configInfo = new ConfigInfo(this);
	}

	@Override
	public void addComponents() {
		setLayout(null);
		setBoundsPanel(projectInfo);
		y1 = y1 + ((projectInfo.getHeight() - configInfo.getHeight()) / 2);
		setBoundsPanel(configInfo);
		y1 = projectInfo.getY();
		nextLine((projectInfo.getHeight() >= configInfo.getHeight()) ? projectInfo : configInfo);

		setSize(x + WIDTH_FRAME, y + HEIGTH_FRAME);
		setLocationRelativeTo(null);
		add(projectInfo);
		add(configInfo);
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

	private void setBoundsPanel(JComponent jc) {
		jc.setBounds(x1, y1, jc.getWidth(), jc.getHeight());
		x1 = x1 + jc.getWidth() + SEPARATOR;
	}

	public void setDatabaseinfo(boolean active) {
		if (active) {
			databaseInfo = new DatabaseInfo(this, false);
			setBoundsPanel(databaseInfo);
			nextLine(databaseInfo);
			add(databaseInfo);
			copyKeyText(projectInfo.getArtefactIdText(), databaseInfo.getDatabaseNameText());
			databaseInfo.getConfig();
		} else {
			y1 = databaseInfo.getY();
			y = y1;
			x1 = SEPARATOR;
			x = x1 + projectInfo.getWidth() + x1 + configInfo.getWidth() + x1;
			remove(databaseInfo);
			databaseInfo = null;
			projectInfo.getArtefactIdText().removeKeyListener(projectInfo.getArtefactIdText().getKeyListeners()[0]);
			if (databaseInfoTest != null) {
				remove(databaseInfoTest);
				databaseInfoTest = null;
				Config.DATABASE_TEST.setNullData();
			}
			Config.DATABASE.setNullData();
		}
		setSize(x + WIDTH_FRAME, y + HEIGTH_FRAME);
		setLocationRelativeTo(null);
		revalidate();
		repaint();
	}

	public void setDatabaseinfoTest(boolean active) {
		if (active) {
			databaseInfoTest = new DatabaseInfo(this, true);
			x1 = x1 + databaseInfo.getWidth() + SEPARATOR;
			y1 = databaseInfo.getY();
			setBoundsPanel(databaseInfoTest);
			nextLine(databaseInfo);
			add(databaseInfoTest);
			projectInfo.getArtefactIdText().removeKeyListener(projectInfo.getArtefactIdText().getKeyListeners()[0]);
			copyKeyText(projectInfo.getArtefactIdText(), databaseInfo.getDatabaseNameText(),
					databaseInfoTest.getDatabaseNameText());
			copyKeyText(databaseInfo.getDatabaseNameText(), databaseInfoTest.getDatabaseNameText());
			databaseInfoTest.getConfig();
		} else {
			Config.DATABASE_TEST.setNullData();
			x = SEPARATOR
					+ Math.max(projectInfo.getWidth() + SEPARATOR + configInfo.getWidth(), databaseInfo.getWidth())
					+ SEPARATOR;
			remove(databaseInfoTest);
			databaseInfoTest = null;
			databaseInfo.getDatabaseNameText()
					.removeKeyListener(databaseInfo.getDatabaseNameText().getKeyListeners()[0]);
		}
		setSize(x + WIDTH_FRAME, y + HEIGTH_FRAME);
		setLocationRelativeTo(null);
		revalidate();
		repaint();
	}

	@Override
	public void getConfig() {
		projectInfo.getConfig();
	}

	@Override
	public void setConfig() {
		projectInfo.setConfig();
		if (databaseInfo != null) {
			databaseInfo.setConfig();
		}
		if (databaseInfoTest != null) {
			databaseInfoTest.setConfig();
		}
	}

	private void copyKeyText(JTextComponent... jtc) {
		jtc[0].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				for (int i = 1; i < jtc.length; i++) {
					if (jtc[i] != null) {
						jtc[i].setText(jtc[0].getText().toLowerCase().replace("-", "_"));
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new UserInterface());
	}
}