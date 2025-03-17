package org.jers.generateapirest.ui;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.configuration.Constants;
import org.jers.generateapirest.configuration.enums.Database;
import org.jers.generateapirest.dependency.DependencyData;
import org.jers.generateapirest.dependency.maven.OnlineVersionDetector;

public class UserInterface extends JFrame {

    public UserInterface() {
        initComponents();
        setVisible(true);
        getConfig();
    }
    
    private void getConfig() {
        groupIdText.setText(Config.GROUP_ID);
        artefactIdText.setText(Config.ARTEFACT_ID);
        nameText.setText(Config.NAME);
        descriptionText.setText(Config.DESCRIPTION);
        getProperties(javaVersionCB, Constants.JAVA_VERSIONS);
        mavenVersionText.setText(OnlineVersionDetector.getVersion(Constants.APACHE_MAVEN));
        serverPortText.setText(Config.PORT + "");
        
    }
    
    private void getProperties(JComboBox jcb, String property) {
        jcb.setModel(new DefaultComboBoxModel<>(Constants.getProperties(property, ",")));
    }
    
    private void setConfig() {
        if (isText(groupIdText) && isText(artefactIdText) && isText(nameText) && isText(mavenVersionText)) {
            Config.GROUP_ID = groupIdText.getText();
            Config.ARTEFACT_ID = artefactIdText.getText();
            Config.NAME = nameText.getText();
            Config.DESCRIPTION = descriptionText.getText();
            Constants.APACHE_MAVEN.setVersion(mavenVersionText.getText());
            Config.LOMBOK = lombokCK.isSelected();
            Config.SWAGGER = swaggerCK.isSelected();
            if(Config.SWAGGER) {
                Constants.SPRINGDOC_OPENAPI_STARTER_WEBMVC_UI.setVersion(swaggerVersionText.getText());
            }
            Config.modifyConstants();
        } else {
            showMessage("Campos vacios");
        }
    }
    
    private boolean isText(JTextComponent jtc) {
        return !(jtc.getText().isBlank() || jtc.getText().isEmpty() || jtc.getText() == null);
    }
    
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    private void selectCheck(JCheckBox jcb, Boolean config) {
        config = jcb.isSelected();
    }
    
    private void selectCheck(JCheckBox jcb, Boolean config, DependencyData data) {
        selectCheck(jcb, config);
        data.setVersion(OnlineVersionDetector.getVersion(data));
    }
    
    private void setInfoDatabase() {
        Config.DATABASE = artefactIdText.getText().replace("-", "") + "_db";
        Database database = Database.compareDB((String) driverCB.getSelectedItem());
        serverText.setText(database.getServer());
        portText.setText(database.getPort().toString());
        databaseNameText.setText(Config.DATABASE);
        userText.setText(database.getUser());
        passwordText.setText(database.getPass());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupIdLabel = new javax.swing.JLabel();
        groupIdText = new javax.swing.JTextField();
        artefactIdLabel = new javax.swing.JLabel();
        artefactIdText = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        descriptionText = new javax.swing.JTextField();
        javaVersionLabel = new javax.swing.JLabel();
        mavenVersionText = new javax.swing.JTextField();
        javaVersionCB = new javax.swing.JComboBox<>();
        packagingLabel = new javax.swing.JLabel();
        packagingCB = new javax.swing.JComboBox<>();
        mavenVersionLabel = new javax.swing.JLabel();
        lombokCK = new javax.swing.JCheckBox();
        swaggerCK = new javax.swing.JCheckBox();
        databaseCK = new javax.swing.JCheckBox();
        databasePanel = new javax.swing.JPanel();
        serverLabel = new javax.swing.JLabel();
        databaseNameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        databaseNameText = new javax.swing.JTextField();
        serverText = new javax.swing.JTextField();
        passwordText = new javax.swing.JPasswordField();
        portLabel = new javax.swing.JLabel();
        portText = new javax.swing.JTextField();
        userLabel = new javax.swing.JLabel();
        userText = new javax.swing.JTextField();
        showPassCK = new javax.swing.JCheckBox();
        driverLabel = new javax.swing.JLabel();
        driverCB = new javax.swing.JComboBox<>();
        serverPortLabel = new javax.swing.JLabel();
        serverPortText = new javax.swing.JTextField();
        swaggerVersionLabel = new javax.swing.JLabel();
        swaggerVersionText = new javax.swing.JTextField();
        springVersionLabel = new javax.swing.JLabel();
        springVersionText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Generate API REST");

        groupIdLabel.setText("Group:");

        artefactIdLabel.setText("Artefact");

        nameLabel.setText("Name:");

        descriptionLabel.setText("Description:");

        javaVersionLabel.setText("Java version:");

        packagingLabel.setText("Packaging:");

        packagingCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "jar", "war" }));

        mavenVersionLabel.setText("Maven version:");

        lombokCK.setText("Lombok");

        swaggerCK.setText("Swagger");
        swaggerCK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                swaggerCKActionPerformed(evt);
            }
        });

        databaseCK.setText("Database");
        databaseCK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                databaseCKActionPerformed(evt);
            }
        });

        databasePanel.setEnabled(false);

        serverLabel.setText("Server:");
        serverLabel.setEnabled(false);

        databaseNameLabel.setText("Database name:");
        databaseNameLabel.setEnabled(false);

        passwordLabel.setText("Password:");
        passwordLabel.setEnabled(false);

        databaseNameText.setEnabled(false);

        serverText.setEnabled(false);

        passwordText.setEnabled(false);

        portLabel.setText("Port:");
        portLabel.setEnabled(false);

        portText.setEnabled(false);
        portText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                portTextKeyTyped(evt);
            }
        });

        userLabel.setText("User:");
        userLabel.setEnabled(false);

        userText.setEnabled(false);

        showPassCK.setText("Show password");
        showPassCK.setEnabled(false);
        showPassCK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPassCKActionPerformed(evt);
            }
        });

        driverLabel.setText("Driver:");
        driverLabel.setEnabled(false);

        driverCB.setModel(new DefaultComboBoxModel<>(Database.valuesString()));
        driverCB.setEnabled(false);
        driverCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                driverCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout databasePanelLayout = new javax.swing.GroupLayout(databasePanel);
        databasePanel.setLayout(databasePanelLayout);
        databasePanelLayout.setHorizontalGroup(
            databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(databasePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(databasePanelLayout.createSequentialGroup()
                        .addComponent(showPassCK)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(databasePanelLayout.createSequentialGroup()
                        .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userLabel)
                            .addComponent(portLabel)
                            .addComponent(driverLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(driverCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(portText)
                            .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(databasePanelLayout.createSequentialGroup()
                                .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(databaseNameLabel)
                                    .addComponent(passwordLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(passwordText)
                                    .addComponent(databaseNameText)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, databasePanelLayout.createSequentialGroup()
                                .addComponent(serverLabel)
                                .addGap(55, 55, 55)
                                .addComponent(serverText, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(56, 56, 56))))
        );
        databasePanelLayout.setVerticalGroup(
            databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(databasePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverLabel)
                    .addComponent(serverText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(driverLabel)
                    .addComponent(driverCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(databaseNameLabel)
                    .addComponent(databaseNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(portLabel)
                        .addComponent(portText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel)
                    .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showPassCK)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        serverPortLabel.setText("Server port:");

        serverPortText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                serverPortTextKeyTyped(evt);
            }
        });

        swaggerVersionLabel.setText("Swagger version:");
        swaggerVersionLabel.setEnabled(false);

        swaggerVersionText.setEnabled(false);

        springVersionLabel.setText("Spring version:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(databasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(groupIdLabel))
                            .addComponent(descriptionLabel)
                            .addComponent(javaVersionLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(springVersionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(groupIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(artefactIdLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(artefactIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(descriptionText)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(springVersionText)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(swaggerVersionLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(swaggerVersionText, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(serverPortLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(serverPortText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(javaVersionCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(packagingLabel)
                            .addGap(18, 18, 18)
                            .addComponent(packagingCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mavenVersionLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mavenVersionText, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lombokCK)
                    .addComponent(databaseCK)
                    .addComponent(swaggerCK))
                .addGap(90, 90, 90))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(groupIdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(artefactIdLabel)
                        .addComponent(artefactIdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nameLabel)
                        .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lombokCK))
                    .addComponent(groupIdLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionLabel)
                    .addComponent(descriptionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(swaggerCK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(javaVersionLabel)
                    .addComponent(javaVersionCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(packagingLabel)
                    .addComponent(packagingCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mavenVersionLabel)
                    .addComponent(mavenVersionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(databaseCK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(serverPortLabel)
                            .addComponent(serverPortText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(swaggerVersionLabel)
                            .addComponent(swaggerVersionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(springVersionLabel)
                            .addComponent(springVersionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addComponent(databasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(205, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void databaseCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_databaseCKActionPerformed
        databasePanel.setEnabled(databaseCK.isSelected());
        if(!databasePanel.isEnabled()) {
            serverText.setText("");
            portText.setText("");
            databaseNameText.setText("");
            userText.setText("");
            passwordText.setText("");
            showPassCK.setSelected(false);
            
            driverLabel.setEnabled(false);
            serverLabel.setEnabled(false);
            portLabel.setEnabled(false);
            databaseNameLabel.setEnabled(false);
            userLabel.setEnabled(false);
            passwordLabel.setEnabled(false);
            
            driverCB.setEnabled(false);
            serverText.setEnabled(false);
            portText.setEnabled(false);
            databaseNameText.setEnabled(false);
            userText.setEnabled(false);
            passwordText.setEnabled(false);
            showPassCK.setEnabled(false);
        } else {
            driverCB.setModel(new DefaultComboBoxModel<>(Database.valuesString()));
            driverLabel.setEnabled(true);
            serverLabel.setEnabled(true);
            portLabel.setEnabled(true);
            databaseNameLabel.setEnabled(true);
            userLabel.setEnabled(true);
            passwordLabel.setEnabled(true);
            
            driverCB.setEnabled(true);
            serverText.setEnabled(true);
            portText.setEnabled(true);
            databaseNameText.setEnabled(true);
            userText.setEnabled(true);
            passwordText.setEnabled(true);
            showPassCK.setEnabled(true);
        }
    }//GEN-LAST:event_databaseCKActionPerformed

    private void serverPortTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_serverPortTextKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_serverPortTextKeyTyped

    private void portTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_portTextKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_portTextKeyTyped

    private void driverCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_driverCBActionPerformed
        setInfoDatabase();
    }//GEN-LAST:event_driverCBActionPerformed

    private void showPassCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPassCKActionPerformed
        if (showPassCK.isSelected()) {
            passwordText.setEchoChar((char) 0);
        } else {
            passwordText.setEchoChar('•');
        }
    }//GEN-LAST:event_showPassCKActionPerformed

    private void swaggerCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swaggerCKActionPerformed
        if(swaggerCK.isSelected()) {
            swaggerVersionLabel.setEnabled(true);
            swaggerVersionText.setEnabled(true);
            swaggerVersionText.setText(OnlineVersionDetector.getVersion(Constants.SPRINGDOC_OPENAPI_STARTER_WEBMVC_UI));
        } else {
            swaggerVersionText.setText("");
            swaggerVersionLabel.setEnabled(false);
            swaggerVersionText.setEnabled(false);
        }
    }//GEN-LAST:event_swaggerCKActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserInterface().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel artefactIdLabel;
    private javax.swing.JTextField artefactIdText;
    private javax.swing.JCheckBox databaseCK;
    private javax.swing.JLabel databaseNameLabel;
    private javax.swing.JTextField databaseNameText;
    private javax.swing.JPanel databasePanel;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionText;
    private javax.swing.JComboBox<String> driverCB;
    private javax.swing.JLabel driverLabel;
    private javax.swing.JLabel groupIdLabel;
    private javax.swing.JTextField groupIdText;
    private javax.swing.JComboBox<String> javaVersionCB;
    private javax.swing.JLabel javaVersionLabel;
    private javax.swing.JCheckBox lombokCK;
    private javax.swing.JLabel mavenVersionLabel;
    private javax.swing.JTextField mavenVersionText;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameText;
    private javax.swing.JComboBox<String> packagingCB;
    private javax.swing.JLabel packagingLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordText;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portText;
    private javax.swing.JLabel serverLabel;
    private javax.swing.JLabel serverPortLabel;
    private javax.swing.JTextField serverPortText;
    private javax.swing.JTextField serverText;
    private javax.swing.JCheckBox showPassCK;
    private javax.swing.JLabel springVersionLabel;
    private javax.swing.JTextField springVersionText;
    private javax.swing.JCheckBox swaggerCK;
    private javax.swing.JLabel swaggerVersionLabel;
    private javax.swing.JTextField swaggerVersionText;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField userText;
    // End of variables declaration//GEN-END:variables
}
