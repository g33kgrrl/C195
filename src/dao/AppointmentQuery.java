package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class AppointmentQuery {

    public static int insert(String title, String description, String location, String type, LocalDateTime start,
                             LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                             String lastUpdatedBy, int customerId, int userId, int contactId) {
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End," +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setDate(5, Date.valueOf(start.toLocalDate()));
            ps.setDate(6, Date.valueOf(end.toLocalDate()));
            ps.setDate(7, Date.valueOf(createDate.toLocalDate()));
            ps.setString(8, createdBy);
            ps.setDate(9, Date.valueOf(lastUpdate.toLocalDate()));
            ps.setString(10, lastUpdatedBy);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    /*
    Appointment_ID INT(10) (PK)
    Title VARCHAR(50)
    Description VARCHAR(50)
    Location VARCHAR(50)
    Type VARCHAR(50)
    Start DATETIME
    End DATETIME */
    /* Create_Date DATETIME
    Created_By VARCHAR(50)
    Last_Update TIMESTAMP
    Last_Updated_By VARCHAR(50)
    Customer_ID INT(10) (FK)
    User_ID INT(10) (FK)
    Contact_ID INT(10) (FK)

     */

    /*
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

    public static ObservableList<Customer> selectAll() {
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

    // TODO: Is this needed?
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
    } */
}
