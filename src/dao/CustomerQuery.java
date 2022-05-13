package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerQuery {

    public static int insert(String customerName, String address, String postalCode, String phone, int divisionId) {
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(NULL, ?, ?, ?, ?, ?)";
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
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public static int update(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            ps.setInt(6, customerId);

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public static int delete(int customerId) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public static ObservableList<Customer> getAll() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");

//                System.out.println(customerId + " | " + customerName + " | " + address + " | " + postalCode + " | " + phone
//                        + " | " + divisionId);

                Customer c = new Customer(customerId, customerName, address, postalCode, phone, divisionId);
                allCustomers.add(c);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allCustomers;
    }

    public static Customer getCustomer(int divisionId) {
        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionIdFK = rs.getInt("Division_ID");

//                System.out.println(customerId + " | " + customerName + " | " + address + " | " + postalCode + " | " + phone
//                        + " | " + divisionIdFK);

                Customer c = new Customer(customerId, customerName, address, postalCode, phone, divisionId);

                return c;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
