/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MainUI;

import DashboardUI.*;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Dashboard extends javax.swing.JFrame {

    private ProductsUI productsUI = new ProductsUI();
    private HomeUI dashboardUI = new HomeUI();
    private CategoryUI categoryUI = new CategoryUI();
    private CardLayout cardLayout;
    private JPanel mainPanel; 
    
    public Dashboard() {
        initComponents();

 
        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add the panels in correct order
        mainPanel.add(dashboardUI, "Dashboard");  // Dashboard first
        mainPanel.add(productsUI, "Products");  
        mainPanel.add(categoryUI, "Category"); // Products second

        // Add mainPanel to JFrame
        getContentPane().add(mainPanel); 

        // Force showing Dashboard at the beginning
        cardLayout.show(mainPanel, "Dashboard");

        // Refresh UI to prevent invisible panels
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Function to switch between panels
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidepanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnCategory = new javax.swing.JButton();
        btnDashboard = new javax.swing.JButton();
        btnProducts = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Arbee's Bakeshop - Dashboard");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(910, 556));

        sidepanel.setBackground(new java.awt.Color(153, 8, 8));
        sidepanel.setForeground(new java.awt.Color(120, 16, 15));
        sidepanel.setMinimumSize(new java.awt.Dimension(300, 300));
        sidepanel.setPreferredSize(new java.awt.Dimension(300, 70));
        sidepanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImageUI/arbee_logo3.png"))); // NOI18N
        jLabel2.setFocusable(false);
        jLabel2.setMaximumSize(new java.awt.Dimension(100, 100));
        jLabel2.setMinimumSize(new java.awt.Dimension(100, 100));
        jLabel2.setName(""); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 100));
        sidepanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 50));

        btnCategory.setFont(new java.awt.Font("Metropolis Light", 0, 12)); // NOI18N
        btnCategory.setText("Category");
        btnCategory.setMaximumSize(new java.awt.Dimension(180, 30));
        btnCategory.setMinimumSize(new java.awt.Dimension(180, 30));
        btnCategory.setPreferredSize(new java.awt.Dimension(180, 30));
        btnCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryActionPerformed(evt);
            }
        });
        sidepanel.add(btnCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 110, 30));

        btnDashboard.setFont(new java.awt.Font("Metropolis Light", 0, 12)); // NOI18N
        btnDashboard.setText("Dashboard");
        btnDashboard.setMaximumSize(new java.awt.Dimension(180, 30));
        btnDashboard.setMinimumSize(new java.awt.Dimension(180, 30));
        btnDashboard.setPreferredSize(new java.awt.Dimension(180, 30));
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });
        sidepanel.add(btnDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 110, 30));

        btnProducts.setFont(new java.awt.Font("Metropolis Light", 0, 12)); // NOI18N
        btnProducts.setText("Products");
        btnProducts.setMaximumSize(new java.awt.Dimension(180, 30));
        btnProducts.setMinimumSize(new java.awt.Dimension(180, 30));
        btnProducts.setPreferredSize(new java.awt.Dimension(180, 30));
        btnProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductsActionPerformed(evt);
            }
        });
        sidepanel.add(btnProducts, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 110, 30));

        getContentPane().add(sidepanel, java.awt.BorderLayout.NORTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel2.setMinimumSize(new java.awt.Dimension(710, 556));
        jPanel2.setName(""); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(710, 556));
        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryActionPerformed
        cardLayout.show(mainPanel, "Category");
    }//GEN-LAST:event_btnCategoryActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        cardLayout.show(mainPanel, "Dashboard");
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductsActionPerformed
        cardLayout.show(mainPanel, "Products");
    }//GEN-LAST:event_btnProductsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        FlatLightLaf.setup();
        try {
        javax.swing.UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCategory;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnProducts;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel sidepanel;
    // End of variables declaration//GEN-END:variables
}
