package org.jers.generateapirest.ui.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.JTextComponent;

import org.jers.generateapirest.ui.UserInterface;
import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.configuration.Constants;
import org.jers.generateapirest.configuration.enums.Database;
import org.jers.generateapirest.dependency.DependencyData;
import org.jers.generateapirest.ui.ISupportPanelUI;
import org.jers.generateapirest.ui.depemdencies.DependenciesConfig;
import org.jers.generateapirest.ui.utils.ComponentUtils;

public class ConfigInfo extends JPanel implements ISupportPanelUI {

	private static final long serialVersionUID = 1L;

	private UserInterface window;
	private DependenciesConfig dependenciesConfig;
	
	private JRadioButton mavenRB;
	private JRadioButton gradleRB;
	private JButton openDependenciesBT;
	private JButton extraInfoBT;
	private JButton generateProjectBT;

	private List<DependencyData> dependencyData;
	
	private int x = SEPARATOR;
	private int x1 = x;
	private int y = SEPARATOR;
	private int y1 = y;

	public ConfigInfo(UserInterface window) {
		this.window = window;
		initAndSetComponents();
		addComponents();
	}

	@Override
	public void initAndSetComponents() {
		mavenRB = new JRadioButton("Maven");
		mavenRB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.IS_MAVEN = true;
				mavenRB.setSelected(true);
				gradleRB.setSelected(false);
				extraInfoBT.setVisible(true);
			}
		});
		mavenRB.setSelected(true);
		
		gradleRB = new JRadioButton("Gradle");
		gradleRB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.IS_MAVEN = false;
				gradleRB.setSelected(true);
				mavenRB.setSelected(false);
				extraInfoBT.setVisible(false);
			}
		});
		
		openDependenciesBT = new JButton("Dependencies");
		openDependenciesBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getConfig();
				dependenciesConfig = new DependenciesConfig(window, dependencyData);
			}
		});

		extraInfoBT = new JButton("Extra information");
		extraInfoBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		if (!Config.IS_MAVEN) {
			extraInfoBT.setVisible(false);
		}
		
		generateProjectBT = new JButton("Generate project");
		generateProjectBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setConfig();
			}
		});
		generateProjectBT.setEnabled(false);
	}

	@Override
	public void addComponents() {
		setLayout(null);
		setBoundsComponent(mavenRB);
		setBoundsComponent(gradleRB);
		nextLine(gradleRB);
		setBoundsComponent(openDependenciesBT);
		setBoundsComponent(extraInfoBT);
		nextLine(extraInfoBT);
		setBoundsComponent(generateProjectBT);
		nextLine(generateProjectBT);

		setSize(x, y);
		add(mavenRB);
		add(gradleRB);
		add(openDependenciesBT);
		add(extraInfoBT);
		add(generateProjectBT);
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
		window.setConfig();
		System.out.println("====================================================");
		dependencyData = Constants.getDependencies(Config.IS_MAVEN);
		if (Config.DATABASE.getDbLibrary().getRequired()) {
			dependencyData.add(Config.DATABASE.getDbLibrary());
		}
		if (Config.DATABASE_TEST.getDbLibrary().getRequired() && !Config.DATABASE_TEST.getDbLibrary().getName().equals(Config.DATABASE.getDbLibrary().getName())) {
			dependencyData.add(Config.DATABASE_TEST.getDbLibrary());
		}
	}

	@Override
	public void setConfig() {

	}
}