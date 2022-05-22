package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class UserQuery {
    private static User currentUser;

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

//                System.out.println(id + " | " + name);
            }

            return allUsers;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static User getUser(int id) {

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

                User u = new User(id, name, password, createDate, createdBy,lastUpdate, lastUpdatedBy);

                System.out.println(id + " | " + name);

                return u;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static boolean checkIfAuthorized(String enteredUsername, String enteredPassword) {
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

                System.out.println(currentUser);
//                System.out.println("User: " + userId + " | " + enteredUsername + " | " + password + " | " + createDate + " | "
//                    + createdBy + " | " + lastUpdate + " | " + lastUpdatedBy
//                );

                return true;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static int getCurrentUserId() {
        return currentUser.getUserId();
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
