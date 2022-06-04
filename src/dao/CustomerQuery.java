package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Customer;


public abstract class CustomerQuery {
    /***
     * Search the database for a customer, by customer ID.
     * @param id the customer ID
     * @return customer with the specified customer ID, or null if none
     */
    public static Customer select(int id) {
        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionIdFK = rs.getInt("Division_ID");

                return new Customer(id, customerName, address, postalCode, phone, divisionIdFK);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***
     * Add a customer to the database.
     * @param customerName the name
     * @param address the address
     * @param postalCode the postal code
     * @param phone the phone
     * @param divisionId the division ID
     * @return the number of rows inserted, or -1 if insert failed
     */
    public static int insert(String customerName, String address, String postalCode, String phone, int divisionId) {
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(NULL, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);

            return ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    /***
     * Update a customer in the database.
     * @param customerId the customer ID
     * @param customerName the name
     * @param address the address
     * @param postalCode the postal code
     * @param phone the phone
     * @param divisionId the division ID
     * @return the number of rows updated, or -1 if update failed
     */
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

            return ps.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    /***
     * Delete a customer from the database, by customer ID.
     * @param customerId the customer ID
     */
    public static void delete(int customerId) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);

            ps.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

    }

    /***
     * Get all customers in the database.
     * @return all customers, or null if none
     */
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

                Customer c = new Customer(customerId, customerName, address, postalCode, phone, divisionId);

                allCustomers.add(c);
            }

            return allCustomers;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***
     * Get a count of all customers in the database.
     * @return count of all customers, or zero if none
     */
    public static int getCustomerCount() {
        int customerCount;

        try {
            String sql = "SELECT COUNT(*) FROM customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                customerCount = rs.getInt("COUNT(*)");

                return customerCount;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    }
}
