package org.jers.generateapirest.ui.depemdencies;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.jers.generateapirest.ui.UserInterface;
import org.jers.generateapirest.ui.utils.ComponentUtils;
import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.dependency.DependencyData;
import org.jers.generateapirest.ui.ISupportComponentUI;

public class DependenciesConfig extends JDialog implements ISupportComponentUI {

	private static final long serialVersionUID = 1L;

	private UserInterface window;
	private JLabel activeLabel;
	private JLabel nameLabel;
	private JLabel groupIdLabel;
	private JLabel artefactIdLabel;
	private JLabel versionTextLabel;
	private JLabel scopeLabel;

	private List<DependencyInfo> dependencyInfos;
	private List<DependencyData> dependencyData;
	private JButton acceptBT;
	
	private int x = SEPARATOR;
	private int x1 = x;
	private int y = SEPARATOR;
	private int y1 = y;

	public DependenciesConfig(UserInterface window, List<DependencyData> dependencyData) {
		super(window, "Select project dependencies", true);
		this.window = window;
		this.dependencyData = dependencyData;
		initAndSetComponents();
		addComponents();
		setVisible(true);
	}

	@Override
	public void initAndSetComponents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getConfig();
		for (int i = 0; i < dependencyData.size(); i++) {
			dependencyInfos.add(new DependencyInfo(this, dependencyData.get(i)));
		}
		
		acceptBT = new JButton("Accept");
		acceptBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setConfig();
				dispose();
			}
		});
	}

	@Override
	public void addComponents() {
		setLayout(null);
		for (int i = 0; i < dependencyInfos.size(); i++) {
			setBoundsPanel(dependencyInfos.get(i));
			nextLine(dependencyInfos.get(i));
		}
		setBoundsComponent(acceptBT);

		setSize(x + WIDTH_FRAME, y + HEIGTH_FRAME);
		setLocationRelativeTo(null);
		for (int i = 0; i < dependencyInfos.size(); i++) {
			add(dependencyInfos.get(i));
		}
		add(acceptBT);
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
	
	public void setBoundsComponent(JComponent jc) {
		ComponentUtils.setBoundsComponent(jc, x1, y1, WIDTH_EX, WIDTH_E, HEIGHT);
		x1 = x1 + jc.getWidth() + SEPARATOR;
	}

	private void setBoundsPanel(JComponent jc) {
		jc.setBounds(x1, y1, jc.getWidth(), jc.getHeight());
		x1 = x1 + jc.getWidth() + SEPARATOR;
	}

	@Override
	public void getConfig() {
		dependencyInfos = new ArrayList<>();
	}

	@Override
	public void setConfig() {
		Config.DEPENDENCIES = new ArrayList<DependencyData>();
		for (int i = 0; i < dependencyInfos.size(); i++) {
			dependencyInfos.get(i).setConfig();
			if (dependencyInfos.get(i).getDependencyData().getRequired()) {
				Config.DEPENDENCIES.add(dependencyInfos.get(i).getDependencyData());
			}
		}
	}
}