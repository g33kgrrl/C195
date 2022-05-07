package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public abstract class AppointmentQuery {

    private static ZoneId localZoneId = ZoneId.of("America/Los_Angeles");
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

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
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public static int update(String title, String description, String location, String type, LocalDateTime start,
                             LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                             String lastUpdatedBy, int customerId, int userId, int contactId) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?," +
                    " End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?," +
                    " User_ID = ?, Contact_ID =?";
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
            System.out.println("Rows affected: " + rowsAffected);
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

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public static ObservableList<Appointment> selectAll() {
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

                // LocalDateTime ldt = LocalDateTime.parse(<string>, dtf);

                System.out.println(appointmentId + " | " + title + " | " + description + " | " + location + " | " + type
                        + " | " + dtf.format(start) + " | " + dtf.format(end) + " | " + dtf.format(createDate) + " | "
                        + createdBy + " | " + dtf.format(lastUpdate) + " | " + lastUpdatedBy + " | " + customerId
                        + " | " + userId + " | " + contactId
                );

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                allAppointments.add(a);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allAppointments;
    }

    public static void select(int appointmentId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointmentId);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");

            /*
            LocalDateTime start = LocalDateTime.now(); //Local date time
            ZoneId zoneId = ZoneId.of( "Asia/Kolkata" );  //Zone information
            ZonedDateTime zdtAtAsia = start.atZone( zoneId );	//Local time in Asia timezone
            ZonedDateTime zdtAtET = zdtAtAsia
                    .withZoneSameInstant( ZoneId.of( "America/New_York" ) ); //Same time in ET timezone
             */

            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime(); // atZone(localZoneId).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime(); // .atZone(ZoneOffset.UTC).withZoneSameInstant(localZoneId).toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime(); // .atZone(ZoneOffset.UTC).withZoneSameInstant(localZoneId).toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime(); //.atZone(ZoneOffset.UTC).withZoneSameInstant(localZoneId).toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            System.out.println(appointmentId + " | " + title + " | " + description + " | " + location + " | " + type
                    + " | " + dtf.format(start) + " | " + dtf.format(end) + " | " + dtf.format(createDate) + " | "
                    + createdBy + " | " + dtf.format(lastUpdate) + " | " + lastUpdatedBy + " | " + customerId
                    + " | " + userId + " | " + contactId
            );
        }
    }
}
