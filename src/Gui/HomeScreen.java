/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Database.DBException;
import Logic.Employee;
import Logic.TravelAgency;

/**
 *
 * @author kiren
 */
public class HomeScreen extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public HomeScreen(Employee e) {
        initComponents();
        TravelAgency.load();
        TravelAgency.setIngelogdeEmployee(e);
    }
        public HomeScreen() {    
        initComponents();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonBooking = new javax.swing.JButton();
        ButtonCustomer = new javax.swing.JButton();
        ButtonCo2 = new javax.swing.JButton();
        ButtonSales = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        LogoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ButtonBooking.setBackground(new java.awt.Color(204, 204, 204));
        ButtonBooking.setText("New Booking");
        ButtonBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonBookingActionPerformed(evt);
            }
        });

        ButtonCustomer.setBackground(new java.awt.Color(204, 204, 204));
        ButtonCustomer.setText("Customer");
        ButtonCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCustomerActionPerformed(evt);
            }
        });

        ButtonCo2.setBackground(new java.awt.Color(204, 204, 204));
        ButtonCo2.setText("Bookings Overview");
        ButtonCo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCo2ActionPerformed(evt);
            }
        });

        ButtonSales.setBackground(new java.awt.Color(204, 204, 204));
        ButtonSales.setText("Sales Report");
        ButtonSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSalesActionPerformed(evt);
            }
        });

        jLabel3.setText("                              CLEAN FLIGHT");

        LogoutButton.setText("Log Out");
        LogoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ButtonBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonCo2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, Short.MAX_VALUE))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ButtonCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonSales, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(LogoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonSales, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonCo2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(LogoutButton)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBookingActionPerformed

         BookingFrame j = null;
        try {
            j = new BookingFrame();
        } catch (DBException ex) {
            java.util.logging.Logger.getLogger(HomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        j.setVisible(true);
// close();
        this.hide();
    }//GEN-LAST:event_ButtonBookingActionPerformed

    private void ButtonCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCustomerActionPerformed

        CustomerFrame j = new CustomerFrame();
        j.setVisible(true);
        // close();
        this.hide();
    }//GEN-LAST:event_ButtonCustomerActionPerformed

    private void ButtonCo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCo2ActionPerformed

        OverViewBooking j = new OverViewBooking();
        j.setVisible(true);
        // close();
        this.hide();
    }//GEN-LAST:event_ButtonCo2ActionPerformed

    private void ButtonSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSalesActionPerformed

        SalesFrame j = new SalesFrame();
        j.setVisible(true);
        // close();
        this.hide();
    }//GEN-LAST:event_ButtonSalesActionPerformed

    private void LogoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButtonActionPerformed
       TravelAgency.setIngelogdeEmployee(null);
        LoginFrame j = new LoginFrame();
        j.setVisible(true);
        // close();
        this.hide();
        
    }//GEN-LAST:event_LogoutButtonActionPerformed

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonBooking;
    private javax.swing.JButton ButtonCo2;
    private javax.swing.JButton ButtonCustomer;
    private javax.swing.JButton ButtonSales;
    private javax.swing.JButton LogoutButton;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}