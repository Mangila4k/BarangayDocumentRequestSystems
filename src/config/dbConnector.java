package config;

import java.sql.*;

public class dbConnector {

    private Connection connect;

    public dbConnector() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdrsystem", "root", "");
        } catch (SQLException ex) {
            System.out.println("Can't connect to the database: " + ex.getMessage());
        }
    }

    public ResultSet getData(String sql) throws SQLException {
        Statement stmt = connect.createStatement();
        ResultSet rst = stmt.executeQuery(sql);
        return rst;
    }

    public Connection getConnection() {
        return this.connect;
    }

    // Function to insert data
    public boolean insertData(String sql) {
        try {
            PreparedStatement pst = connect.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("Inserted Successfully!");
            pst.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Insert Error: " + ex);
            return false;
        }
    }

    // Function to update data
    public boolean updateData(String sql) {
        try {
            PreparedStatement pst = connect.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("Updated Successfully!");
            pst.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Update Error: " + ex);
            return false;
        }
    }
    public void closeConnection() {
    if (connect != null) {
        try {
            if (!connect.isClosed()) {
                connect.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException ex) {
            System.out.println("Error closing database connection: " + ex.getMessage());
        }
    }
}
}
