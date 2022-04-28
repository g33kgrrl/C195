package dao;

import model.Customer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class CustomerQuery {
    public static model.Customer select(int Customer_ID) throws SQLException { // String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
//        String sql = "INSERT INTO customers (Customer_Name, division_ID) VALUES(?, ?)";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
//        ps.setString(1, customerName);
//        ps.setString(2, address);
//        ps.setString(3, postalCode);
//        ps.setString(4, phone);
//        ps.setInt(5, divisionId);
//        ps.setInt(2, divisionId);
//        int rowsFound = ps.executeUpdate();
        Customer customer = (Customer) ps.executeQuery();
//        System.out.println("Rows found: " + rowsFound);
//        return rowsFound;
        return customer;
    }

    public static int insert(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, division_ID) VALUES(?, ?, ?, ?, ?)";
//        String sql = "INSERT INTO customers (Customer_Name, division_ID) VALUES(?, ?)";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
//        ps.setInt(2, divisionId);
        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public static int update(int CustomerId, String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
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
}
