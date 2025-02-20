/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package DashboardUI;

import DAO.ProductDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Xandro
 */
public class ProductsUI extends javax.swing.JPanel {
    private DefaultTableModel tableModel;
    private ProductDAO productDAO;
    private Map<Integer, String> productMap;
    private Map<Integer, String> categoryMap;
    
    public ProductsUI() {   
        initComponents();
        productDAO = new ProductDAO();
        tableModel = (DefaultTableModel) jTable1.getModel();
        productMap = new HashMap<>();
        categoryMap = new HashMap<>();
        loadProductData();
        loadProductDropdown();
        loadProductTypeDropdown();
    }
    
    private void loadProductData() {
        ResultSet rs = productDAO.getProductResultSet();
        try {
            if (rs != null) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                tableModel.setColumnCount(0);
                tableModel.setRowCount(0);
                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void loadProductDropdown() {
        cmbProductSelect.removeAllItems();
        productMap.clear();
        ResultSet rs = productDAO.getProductResultSet();
        try {
            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                productMap.put(id, name);
                cmbProductSelect.addItem(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadProductTypeDropdown() {
        cmbProductType.removeAllItems(); // Clear previous items
        categoryMap.clear();

        ResultSet rs = productDAO.getCategoryResultSet();

        try {
            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("category_name");
                categoryMap.put(id, name);
                cmbProductType.addItem(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private int getSelectedCategoryId() {
        String selectedCategory = (String) cmbProductType.getSelectedItem();
        for (Map.Entry<Integer, String> entry : categoryMap.entrySet()) {
            if (entry.getValue().equals(selectedCategory)) {
                return entry.getKey(); // Return category_id
            }
        }
        return -1; // Default value if not found
    }

  
    private void addProduct() {
        String name = txtProductName.getText();
        int type = getSelectedCategoryId();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a product name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (productDAO.addProduct(name, type)) {
            JOptionPane.showMessageDialog(this, "Product added successfully");
            loadProductData();
            loadProductDropdown();
            loadProductTypeDropdown();
            txtProductName.setText("");    
        } else {
            JOptionPane.showMessageDialog(this, "Error adding product", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateProduct() {
        String selected = (String) cmbProductSelect.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a product to update", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = getKeyFromValue(productMap, selected);
        String name = txtProductName.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a valid product name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int type = getSelectedCategoryId();
        if (productDAO.updateProduct(id, name, type)) {
            JOptionPane.showMessageDialog(this, "Product updated successfully");
            loadProductData();
            loadProductDropdown();
            loadProductTypeDropdown();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating product", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteProduct() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (productDAO.deleteProduct(id)) {
                JOptionPane.showMessageDialog(this, "Product deleted successfully");
                loadProductData();
                loadProductDropdown();
                loadProductTypeDropdown();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting product", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private int getKeyFromValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnDeleteProd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbProductType = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnAddProd = new javax.swing.JButton();
        cmbProductSelect = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btnEditProd = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(600, 360));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setName(""); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setCellSelectionEnabled(true);
        jTable1.setUpdateSelectionOnSort(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Metropolis Semi Bold", 0, 24)); // NOI18N
        jLabel1.setText("List of Products");

        jButton1.setText("Add New");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnDeleteProd.setText("Delete Selected");
        btnDeleteProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteProd, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1035, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteProd, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Product List", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel5.setFont(new java.awt.Font("Metropolis Medium", 1, 24)); // NOI18N
        jLabel5.setText("Add/edit a product");

        cmbProductType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HUMUKON", "TAM-ISON", "GAHI-ON" }));

        jLabel2.setText("Product Name:");

        txtProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameActionPerformed(evt);
            }
        });

        jLabel4.setText("Product Category:");

        btnAddProd.setText("Add");
        btnAddProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProdActionPerformed(evt);
            }
        });

        cmbProductSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Product Item:");

        btnEditProd.setText("Edit");
        btnEditProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbProductSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(49, 175, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(65, 65, 65)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(cmbProductType, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAddProd, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditProd, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(285, 285, 285))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbProductType, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(4, 4, 4)
                                .addComponent(cmbProductSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProd, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditProd, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(271, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add/Edit Products", jPanel2);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAddProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProdActionPerformed
        addProduct();
    }//GEN-LAST:event_btnAddProdActionPerformed

    private void btnDeleteProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProdActionPerformed
        deleteProduct();
    }//GEN-LAST:event_btnDeleteProdActionPerformed

    private void btnEditProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProdActionPerformed
        updateProduct();
    }//GEN-LAST:event_btnEditProdActionPerformed

    private void txtProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProd;
    private javax.swing.JButton btnDeleteProd;
    private javax.swing.JButton btnEditProd;
    private javax.swing.JComboBox<String> cmbProductSelect;
    private javax.swing.JComboBox<String> cmbProductType;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtProductName;
    // End of variables declaration//GEN-END:variables
}
