package org.jers.generator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyGraphics extends JFrame {
    JLabel lGroupId = new JLabel();
    JTextField groupId = new JTextField();
    JLabel lArtefactId = new JLabel();
    JTextField artefactId = new JTextField();
    JLabel lDescription = new JLabel();
    JTextField description = new JTextField();
    JLabel lSpringVersion = new JLabel();
    JTextField springVersion = new JTextField();
    JLabel lJavaVersion = new JLabel();
    JComboBox javaVersion = new JComboBox();
    JLabel lMavenVersion = new JLabel();
    JTextField mavenVersion = new JTextField();
    JLabel lLombok = new JLabel();
    JRadioButton lombokTrue = new JRadioButton();
    JRadioButton lombokFalse = new JRadioButton();
    JLabel lSwagger = new JLabel();
    JRadioButton swaggerFalse = new JRadioButton();
    JRadioButton swaggerTrue = new JRadioButton();
    JLabel lSwaggerVersion = new JLabel();
    JTextField swaggerVersion = new JTextField();
    JRadioButton databaseFalse = new JRadioButton();
    JRadioButton databaseTrue = new JRadioButton();
    JLabel lDatabase = new JLabel();
    JLabel lDatabaseMotor = new JLabel();
    JComboBox databaseMotor = new JComboBox();
    JLabel lServer = new JLabel();
    JTextField server = new JTextField();
    JLabel lPort = new JLabel();
    JTextField port = new JTextField();
    JLabel lDatabaseName = new JLabel();
    JTextField databaseName = new JTextField();
    JLabel lUser = new JLabel();
    JTextField user = new JTextField();
    JLabel lPassword = new JLabel();
    JTextField password = new JTextField();
    JButton b1 = new JButton();
    JTextArea result = new JTextArea();

    public MyGraphics() {
        //setLayout(new FlowLayout());
        initComponents();


        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    @SuppressWarnings("unchecked")
    private void initComponents() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Generate Spring Boot project");
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
        pack();
    }

    private void setFields() {
        lGroupId.setText("Package:");
        groupId.setText("org.jers");
        lArtefactId.setText("Artefact id:");
        artefactId.setText("parameters");
        lSpringVersion.setText("Spring version:");
        springVersion.setText("3.3.3");
        lJavaVersion.setText("Java version:");
        javaVersion.setModel(new DefaultComboBoxModel(new String[] { "22", "21", "17", "1.8" }));
        lMavenVersion.setText("Maven Version:");
        mavenVersion.setText("3.9.9");
        lLombok.setText("Lombok:");
        lombokTrue.setSelected(true);
        lombokTrue.setText("True");
        lombokTrue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                lombokFalse.setSelected(false);
            }
        });
        lombokFalse.setText("False");
        lombokFalse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                lombokTrue.setSelected(false);
            }
        });
        lSwagger.setText("Swagger:");
        swaggerTrue.setSelected(true);
        swaggerTrue.setText("True");
        swaggerTrue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                swaggerFalse.setSelected(false);
            }
        });
        swaggerFalse.setText("False");
        swaggerFalse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                swaggerTrue.setSelected(false);
            }
        });
        lSwaggerVersion.setText("Swagger Version:");
        swaggerVersion.setText("2.6.0");
        lDatabase.setText("Database:");
        databaseTrue.setSelected(true);
        databaseTrue.setText("True");
        databaseTrue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                databaseFalse.setSelected(false);
            }
        });databaseFalse.setText("False");
        databaseFalse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                databaseTrue.setSelected(false);
            }
        });
        lDatabaseMotor.setText("Database motor:");
        databaseMotor.setModel(new DefaultComboBoxModel(new String[] { "H2", "MySQL", "Oracle DB", "PostgreSQL", "SQL Server" }));
        databaseMotor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

            }
        });
        lServer.setText("Server:");
        server.setText("localhost");
        lPort.setText("Port:");
        port.setText("90");
        lDatabaseName.setText("Database:");
        databaseName.setText("parametersDB");
        lUser.setText("User:");
        lPassword.setText("Password:");
        user.setText("h2");
        password.setText("asd");
        b1.setText("Generate project");
    }

    public static void main(String[] args) {
        new MyGraphics().setVisible(true);;
    }
}