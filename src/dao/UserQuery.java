package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import model.User;


public abstract class UserQuery {
    private static User currentUser;

    /***
     * Get all users in the database.
     * @return all users, or null if none
     */
    public static ObservableList<User> getAll() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                User u = new User(id, name, password, createDate, createdBy,lastUpdate, lastUpdatedBy);

                allUsers.add(u);
            }

            return allUsers;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***
     * Search the database for a user, by user ID.
     * @param id the user ID
     * @return user with the specified user ID, or null if not found
     */
    public static User select(int id) {

        try {
            String sql = "SELECT * FROM users WHERE User_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                return new User(id, name, password, createDate, createdBy,lastUpdate, lastUpdatedBy);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***
     * Check entered username and password against users in the database. If a match is found, fetch that user and set
     * as the current user, meaning the user has been successfully authenticated, and return true. Otherwise, return
     * false.
     * @param enteredUsername the username entered by the user
     * @param enteredPassword the password entered by the user
     * @return true or false indicating whether the user was successfully authenticated
     */
    public static boolean validateUser(String enteredUsername, String enteredPassword) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, enteredUsername);
            ps.setString(2, enteredPassword);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("User_ID");
                String password = rs.getString("Password");
                LocalDateTime createDate = rs.getDate("Create_Date").toLocalDate().atTime(LocalTime.now());
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getDate("Last_Update").toLocalDate().atTime(LocalTime.now());
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                currentUser = new User(userId, enteredUsername, password, createDate, createdBy, lastUpdate, lastUpdatedBy);

                return true;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /***
     * Get the current authenticated user.
     * @return the user object associated with the current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /***
     * Set the current authenticated user to null.
     * Following this action, the user will no longer have access to the program and will need to log in again.
     */
    public static void resetUser() { currentUser = null;
    }
}
