/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Logic.Booking;
import Logic.SendEmail;
import Logic.TravelAgency;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author kiren
 */
public class impactFrame extends javax.swing.JFrame {

    private Booking booking;

    public impactFrame(Booking booking) {
        initComponents();
        this.booking = booking;
    }

    private impactFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        C02 = new javax.swing.JButton();
        SendEmailButton = new javax.swing.JButton();
        GoToHomescreenButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        C02.setText("Donate to MyClimate");
        C02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C02ActionPerformed(evt);
            }
        });

        SendEmailButton.setText("Send A confirmation E-mail to client");
        SendEmailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendEmailButtonActionPerformed(evt);
            }
        });

        GoToHomescreenButton.setText("Go Back To HomeScreen!");
        GoToHomescreenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoToHomescreenButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GoToHomescreenButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(C02, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SendEmailButton, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(C02, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SendEmailButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(GoToHomescreenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void C02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C02ActionPerformed

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://co2.myclimate.org/en/contribution_calculators/new?localized_currency=EUR"));
            } catch (URISyntaxException ex) {
                Logger.getLogger(impactFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(impactFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_C02ActionPerformed

    private void GoToHomescreenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoToHomescreenButtonActionPerformed
        HomeScreen j = new HomeScreen();
        j.setVisible(true);
        this.hide();
    }//GEN-LAST:event_GoToHomescreenButtonActionPerformed

    private void SendEmailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendEmailButtonActionPerformed
        String email = JOptionPane.showInputDialog("Enter enter the email address");
        if (email != null && email.equals("") == false) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to send the confirmation to " + email);
            if (dialogResult == JOptionPane.YES_OPTION) {

                String boodschap = "Dear Sir/Madam\n"
                        + "\n"
                        + "This is the confirmation of booking #" + booking.getBookingNumber() + " for " + booking.getRoute().toString() + "\n"
                        + "\n"
                        + "Within a few minutes we will send your ticket(s) with the full flight schedule."
                        + "Furthermore, we want to reccomend you to arrive at airport at least 2 hours before departure.\n"
                        + "\n"
                        + "To compensate for this emission, please consider donating to MyClimate by clicking on the following link: \n https://co2.myclimate.org/en/contribution_calculators/new?localized_currency=EUR\n"
                        + "\n" + "Thank you for booking with CleanFlight!\n"
                        + TravelAgency.getIngelogdeEmployee().getLastName() + " " + TravelAgency.getIngelogdeEmployee().getFirstName();

                SendEmail.send(email, boodschap, "Confirmation of your booking");
            }
        }

    }//GEN-LAST:event_SendEmailButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(impactFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(impactFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(impactFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(impactFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new impactFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton C02;
    private javax.swing.JButton GoToHomescreenButton;
    private javax.swing.JButton SendEmailButton;
    // End of variables declaration//GEN-END:variables
}
