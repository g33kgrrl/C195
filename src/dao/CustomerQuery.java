package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerQuery {

    public static int insert(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);

        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public static int update(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);

        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);

        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");

            System.out.println(customerId + " | " + customerName + " | " + address + " | " + postalCode + " | " + phone
                    + " | " + divisionId);
        }
    }

    public static void select(int divisionId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionIdFK = rs.getInt("Division_ID");

            System.out.println(customerId + " | " + customerName + " | " + address + " | " + postalCode + " | " + phone
                    + " | " + divisionIdFK);
        }
    }
}
