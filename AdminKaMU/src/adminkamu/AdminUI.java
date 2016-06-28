/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminkamu;

import static adminkamu.AdminKaMU.kaaClient;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXDatePicker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kaaproject.kaa.client.event.EventFamilyFactory;
import org.kaaproject.kaa.schema.sample.event.kamu.ChangeProfile;
import org.kaaproject.kaa.schema.sample.event.kamu.KaMUEventClassFamily;
import org.kaaproject.kaa.schema.sample.event.kamu.RestartDevice;
import org.kaaproject.kaa.schema.sample.event.kamu.UpdateDevice;

/**
 *
 * @author h9073
 */
public class AdminUI extends javax.swing.JFrame {
    
    static BaasBoxController bbc = new BaasBoxController();
    static String session = bbc.logIn();
    //static Map<String, String> devices = new HashMap<>();
    static Map<String, String> devices = getDevices(session);
    /**
     * Creates new form AdminUI
     */
    public AdminUI() {
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        initComponents();
        initDeviceList(devices);
        initVersionCombo();  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstDevices = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
        lblLocation = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        lblIDText = new javax.swing.JLabel();
        lblLocationText = new javax.swing.JLabel();
        lblVersionText = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        dateProfile = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstProfiles = new javax.swing.JList<>();
        txtTimeProfile = new javax.swing.JFormattedTextField();
        btnSetProfile = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        dateRestart = new org.jdesktop.swingx.JXDatePicker();
        txtTimeRestart = new javax.swing.JFormattedTextField();
        btnRestart = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        dateUpdate = new org.jdesktop.swingx.JXDatePicker();
        txtTimeUpdate = new javax.swing.JFormattedTextField();
        cmbVersions = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(null);

        lstDevices.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstDevices.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstDevicesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstDevices);

        jPanel4.setBorder(null);

        lblID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblID.setText("ID");

        lblLocation.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblLocation.setText("Location");

        lblVersion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblVersion.setText("Version");

        lblIDText.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lblLocationText.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblLocationText.setText("Something something");
        lblLocationText.setToolTipText("");

        lblVersionText.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblVersionText.setText("0.0.1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblID)
                    .addComponent(lblLocation)
                    .addComponent(lblVersion))
                .addGap(67, 67, 67)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVersionText)
                    .addComponent(lblLocationText)
                    .addComponent(lblIDText))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(lblIDText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLocation)
                    .addComponent(lblLocationText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVersion)
                    .addComponent(lblVersionText))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(lstProfiles);

        txtTimeProfile.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("HH:mm:ss"))));

        btnSetProfile.setText("Set profile");
        btnSetProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimeProfile)
                    .addComponent(dateProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSetProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(dateProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimeProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSetProfile)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Profile", jPanel6);

        txtTimeRestart.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("HH:mm:ss"))));

        btnRestart.setText("Restart");
        btnRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimeRestart)
                    .addComponent(dateRestart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnRestart, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateRestart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRestart))
                .addGap(18, 18, 18)
                .addComponent(txtTimeRestart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Restart", jPanel7);

        txtTimeUpdate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("HH:mm:ss"))));

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimeUpdate)
                    .addComponent(dateUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbVersions, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate))
                .addGap(18, 18, 18)
                .addComponent(txtTimeUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbVersions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Update", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane1)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstDevicesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstDevicesValueChanged
        // TODO add your handling code here:
        lblIDText.setText(lstDevices.getSelectedValue());
        List<String> profiles = getProfiles(session);
        initProfileList(profiles);
    }//GEN-LAST:event_lstDevicesValueChanged

    private void btnSetProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetProfileActionPerformed
        // TODO add your handling code here:
        Date date = dateProfile.getDate();
        String timeStr = txtTimeProfile.getText();
        long seconds = 0;
        try {
            seconds = formatDate(date, timeStr);
        } catch (ParseException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        int time = (int)calculateDateDifference(seconds);
        int profile = lstProfiles.getSelectedIndex() + 1;
        String deviceID = lstDevices.getSelectedValue();
        String hash = devices.get(deviceID);
        sendProfileToSingleTarget(hash, profile, time);
    }//GEN-LAST:event_btnSetProfileActionPerformed

    private void btnRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestartActionPerformed
        // TODO add your handling code here:
        Date date = dateRestart.getDate();
        String timeStr = txtTimeRestart.getText();
        long seconds = 0;
        try {
            seconds = formatDate(date, timeStr);
        } catch (ParseException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        int time = (int)calculateDateDifference(seconds);
        String deviceID = lstDevices.getSelectedValue();
        String hash = devices.get(deviceID);
        sendRestartCommandToSingleTarget(hash, time);
    }//GEN-LAST:event_btnRestartActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        Date date = dateUpdate.getDate();
        String timeStr = txtTimeUpdate.getText();
        long seconds = 0;
        try {
            seconds = formatDate(date, timeStr);
        } catch (ParseException ex) {
            Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        int time = (int)calculateDateDifference(seconds);
        String version = cmbVersions.getSelectedItem().toString();
        String deviceID = lstDevices.getSelectedValue();
        String hash = devices.get(deviceID);
        sendUpdateCommandToSingleTarget(hash, version, time);
    }//GEN-LAST:event_btnUpdateActionPerformed

    /**
     * @param args the command line arguments
     */
    public void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);
            }
        });
    }
    
    public static Map<String, String> getDevices(String session) {
        JSONArray data = bbc.getDeviceInfo(session);
        Map<String, String> devices = new HashMap();
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            devices.put(object.getString("mac"), object.getString("hash"));
        }
        return devices;
    }
    
    public List<String> getProfiles(String session) {
        JSONArray data = bbc.getProfileInfo(session);
        List<String> profiles = new ArrayList();
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            profiles.add(object.getString("name"));
        }
        return profiles;
    }
    
    public void initDeviceList(Map<String, String> devices) {
        DefaultListModel listModel = new DefaultListModel();
        for (Map.Entry<String, String> entry : devices.entrySet()){
            listModel.addElement(entry.getKey());
        }
        lstDevices.setModel(listModel);
    }
    
    public void initProfileList(List<String> profiles) {
        DefaultListModel listModel = new DefaultListModel();
        for (String profile : profiles) {
            listModel.addElement(profile);
        }
        lstProfiles.setModel(listModel);
    }
    
    public void initVersionCombo() {
        JSONArray data = bbc.getVersionInfo(session);
        List<String> versions = new ArrayList();
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            versions.add(object.getString("number"));
        }
        
        for (String profile : versions) {
            cmbVersions.addItem(profile);
        }
    }
    
    public long formatDate(Date date, String time) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder builder = new StringBuilder();
        builder.append(dateFormat.format(date));
        builder.append(" " + time);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date newDate = dateFormat.parse(builder.toString());
        long seconds = newDate.getTime();
        return seconds;
    }
    
    public long calculateDateDifference(long seconds) {
        Date date = new Date();
        long currentSeconds = date.getTime();
        long time = seconds - currentSeconds;
        return time;
    }
    
    public void sendProfileToSingleTarget(String target, int profile, int time) {
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily kecf = eventFamilyFactory.getKaMUEventClassFamily();
        ChangeProfile cpc = new ChangeProfile(profile, time);
        kecf.sendEvent(cpc, target);
    }
    
    public void sendRestartCommandToSingleTarget(String target, int time) {
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily kecf = eventFamilyFactory.getKaMUEventClassFamily();
        RestartDevice rdc = new RestartDevice(time);
        kecf.sendEvent(rdc, target);
    }
    
    public void sendUpdateCommandToSingleTarget(String target, String version, int time) {
        final EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        final KaMUEventClassFamily kecf = eventFamilyFactory.getKaMUEventClassFamily();
        UpdateDevice udc = new UpdateDevice(version, time);
        kecf.sendEvent(udc, target);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRestart;
    private javax.swing.JButton btnSetProfile;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbVersions;
    private org.jdesktop.swingx.JXDatePicker dateProfile;
    private org.jdesktop.swingx.JXDatePicker dateRestart;
    private org.jdesktop.swingx.JXDatePicker dateUpdate;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIDText;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JLabel lblLocationText;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JLabel lblVersionText;
    private javax.swing.JList<String> lstDevices;
    private javax.swing.JList<String> lstProfiles;
    private javax.swing.JFormattedTextField txtTimeProfile;
    private javax.swing.JFormattedTextField txtTimeRestart;
    private javax.swing.JFormattedTextField txtTimeUpdate;
    // End of variables declaration//GEN-END:variables
}
