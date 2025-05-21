/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requesteddocuments;

import admin.admindashboard;
import config.dbConnector;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Macky Server
 */
public class docsrequested extends javax.swing.JFrame {

    private String userId;

    public docsrequested(String userId) {
        this.userId = userId;
        initComponents();

        // Setup table model with editable status column
        reqdocs.setModel(new DefaultTableModel(
           new Object[][]{},
           new String[]{"Request ID", "User Name", "Document Type", "Request Date", "Status"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the Status column (index 4) is editable
                return column == 4;
            }
        });

        // Set custom renderer for Status column to color the cells
        reqdocs.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        // Set JComboBox editor for Status column
        String[] statuses = {"Pending", "Approved", "Rejected"};
        JComboBox<String> comboBox = new JComboBox<>(statuses);
        reqdocs.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBox));

        reqdocs.setAutoCreateRowSorter(true);
        reqdocs.setRowHeight(25);

        // Load data into table
        loadRequestedDocuments();

        // Add listener to update DB when status changes
        reqdocs.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 4) {  // Status column
                    DefaultTableModel model = (DefaultTableModel) reqdocs.getModel();
                    int requestId = (int) model.getValueAt(row, 0);
                    String newStatus = (String) model.getValueAt(row, column);
                    updateStatusInDB(requestId, newStatus);
                }
            }
        });

        setLocationRelativeTo(null);  // Center the frame
    }

    private void loadRequestedDocuments() {
        try {
            dbConnector dbc = new dbConnector();
            String query = "SELECT r.request_id, u.first_name, u.last_name, d.d_type, r.request_date, r.status "
                         + "FROM tbl_requested_documents r "
                         + "JOIN users u ON r.user_id = u.id "
                         + "JOIN tbl_documents d ON r.d_id = d.d_id";
            PreparedStatement pst = dbc.getConnection().prepareStatement(query);

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) reqdocs.getModel();
            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                int requestId = rs.getInt("request_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String docType = rs.getString("d_type");
                Date requestDate = rs.getDate("request_date");
                String status = rs.getString("status");

                // Add a row with user full name and document type instead of IDs
                model.addRow(new Object[]{requestId, firstName + " " + lastName, docType, requestDate, status});
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading requested documents: " + e.getMessage());
        }
    }

    private void updateStatusInDB(int requestId, String newStatus) {
        try {
            dbConnector dbc = new dbConnector();
            String updateQuery = "UPDATE tbl_requested_documents SET status = ? WHERE request_id = ?";
            PreparedStatement pst = dbc.getConnection().prepareStatement(updateQuery);
            pst.setString(1, newStatus);
            pst.setInt(2, requestId);
            int updated = pst.executeUpdate();
            pst.close();

            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Status updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update status.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database update error: " + ex.getMessage());
        }
    }

    // Custom cell renderer to color status cells
    public class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String status = value != null ? value.toString() : "";

            switch (status) {
                case "Approved":
                    c.setBackground(new Color(144, 238, 144)); // light green
                    break;
                case "Rejected":
                    c.setBackground(new Color(255, 102, 102)); // light red
                    break;
                case "Pending":
                    c.setBackground(new Color(255, 255, 153)); // light yellow
                    break;
                default:
                    c.setBackground(Color.WHITE);
            }

            if (isSelected) {
                c.setBackground(c.getBackground().darker());
            }

            return c;
        }
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        reqdocs = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(600, 340));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        jButton2.setText("BACK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(266, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 90, 300));

        jScrollPane1.setViewportView(reqdocs);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 470, 260));

        jButton1.setText("BACK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        admindashboard ds = new admindashboard();
        ds.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Assuming we are passing a user ID to the constructor here
                new docsrequested("").setVisible(true); // userId ignored
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable reqdocs;
    // End of variables declaration//GEN-END:variables
}
