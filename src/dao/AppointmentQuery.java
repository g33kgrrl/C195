package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.TypeMonthAppt;
import java.sql.*;
import java.time.*;


public abstract class AppointmentQuery {
    private static ZoneId localZoneId = ZoneId.systemDefault();

    public static Appointment select(int appointmentId) throws SQLException {
        try {
            String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime(); // atZone(localZoneId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime(); // .atZone(ZoneOffset.UTC).withZoneSameInstant(localZoneId).toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime(); // .atZone(ZoneOffset.UTC).withZoneSameInstant(localZoneId).toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime(); //.atZone(ZoneOffset.UTC).withZoneSameInstant(localZoneId).toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                return a;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static int insert(String title, String description, String location, String type, LocalDateTime start,
                             LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                             String lastUpdatedBy, int customerId, int userId, int contactId) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End," +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setTimestamp(7, Timestamp.valueOf(createDate));
            ps.setString(8, createdBy);
            ps.setTimestamp(9, Timestamp.valueOf(lastUpdate));
            ps.setString(10, lastUpdatedBy);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public static int update(int id, String title, String description, String location, String type, LocalDateTime start,
                             LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                             String lastUpdatedBy, int customerId, int userId, int contactId) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?," +
                    " End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?," +
                    " User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setTimestamp(7, Timestamp.valueOf(createDate));
            ps.setString(8, createdBy);
            ps.setTimestamp(9, Timestamp.valueOf(lastUpdate));
            ps.setString(10, lastUpdatedBy);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);
            ps.setInt(14, id);


            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public static int delete(int appointmentId) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);

            return ps.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public static int deleteAllForCustomerId(int customerId) {
        ObservableList<Appointment> thisCustomerAppointments = getAllForCustomerId(customerId);

        for (Appointment appointment : thisCustomerAppointments
        ) {
            delete(appointment.getId());
        }

        System.out.println(thisCustomerAppointments.size());
        return thisCustomerAppointments.size();
    }

    public static ObservableList<Appointment> getAllForCustomerId(int customerId) {
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                customerAppointments.add(a);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return customerAppointments;
    }

    public static ObservableList<Appointment> getWeek() {
        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE yearweek(start) = yearweek(now())";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                weekAppointments.add(a);
            }

            return weekAppointments;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ObservableList<Appointment> getMonth() {
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE month(start) = month(now()) AND year(start) = year(now());";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Contact_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                monthAppointments.add(a);
            }

            return monthAppointments;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ObservableList<Appointment> getAll() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                allAppointments.add(a);
            }

            return allAppointments;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ObservableList<TypeMonthAppt> getAllByTypeMonth() {
        ObservableList<TypeMonthAppt> allTypeMonthAppts = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, COUNT(*), Type, monthname(start) FROM appointments GROUP BY Type, monthname(start) ORDER BY Type;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                int count = rs.getInt("COUNT(*)");
                String type = rs.getString("Type");
                String monthName = rs.getString("monthname(start)");

                TypeMonthAppt t = new TypeMonthAppt(id, count, type, monthName);

                allTypeMonthAppts.add(t);
            }

            return allTypeMonthAppts;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ObservableList<Appointment> getAllByContact(int contactId) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end, null,
                        null, null, null, customerId, userId, contactId);

                contactAppointments.add(a);
            }

            return contactAppointments;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
