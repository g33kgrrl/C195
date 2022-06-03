package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.TypeMonthAppt;
import java.sql.*;
import java.time.*;


public abstract class AppointmentQuery {
    /***
     * Search the database for an appointment, by appointment ID.
     * @param appointmentId the appointment ID
     * @return appointment with the specified appointment ID, or null if not found
     */
    public static Appointment select(int appointmentId) {
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

    /***
     * Add an appointment to the database.
     * @param title the title
     * @param description the description
     * @param location the location
     * @param type the type
     * @param start the start date and time
     * @param end the end date and time
     * @param createDate the date and time the appointment was created
     * @param createdBy the original user who created the appointment
     * @param lastUpdate the date and time the appointment was last updated
     * @param lastUpdatedBy the last user to update the appointment
     * @param customerId the associated customer ID
     * @param userId the associated user ID
     * @param contactId the associated contact ID
     * @return number of rows inserted, or -1 if insert failed
     */
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

            return ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    /***
     * Update an appointment in the database.
     * @param id the appointment ID
     * @param title the title
     * @param description the description
     * @param location the location
     * @param type the type
     * @param start the start date and time
     * @param end the end date and time
     * @param createDate the date and time the appointment was created
     * @param createdBy the original user who created the appointment
     * @param lastUpdate the date and time the appointment was last updated
     * @param lastUpdatedBy the last user to update the appointment
     * @param customerId the associated customer ID
     * @param userId the associated user ID
     * @param contactId the associated contact ID
     * @return number of rows updated, or -1 if update failed
     */
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

            return ps.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }


    /***
     * Delete an appointment from the database, by appointment ID.
     * @param appointmentId the appointment ID
     * @return number of rows deleted, or -1 if delete failed
     */
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

    /***
     * Delete all appointments associated with the given customer ID.
     * @param customerId the customer ID
     * @return the number of rows deleted
     */
    public static int deleteAllForCustomerId(int customerId) {
        ObservableList<Appointment> thisCustomerAppointments = getAllForCustomerId(customerId);

        for (Appointment appointment : thisCustomerAppointments
        ) {
            delete(appointment.getId());
        }

        return thisCustomerAppointments.size();
    }

    /***
     * Get all appointments associated with the given customer ID.
     * @param customerId the customer ID
     * @return the number of rows selected, or null
     */
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

            return customerAppointments;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***
     * Get all appointments for the current week.
     * @return this week's appointments, or null if none
     */
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

    /***
     * Get all appointments for the current month.
     * @return this month's appointments, or null if none
     */
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

    /***
     * Get all appointments in the database.
     * @return all appointments, or null if none
     */
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

    /***
     * Get all appointments in the database and group by type and month.
     * @return all appointments grouped by type and month, or null if none
     */
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

    /***
     * Get all appointments associated with the given contact ID.
     * @param contactId the contact ID
     * @return all appointments for the given contact ID, or null if none
     */
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
