package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class UserQuery {

    public static ObservableList<Country> selectAll() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                Country c = new Country(id, name);
                allCountries.add(c);

//                System.out.println(id + " | " + name);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return allCountries;
    }

    public static User select(String userName) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("User_ID");
                String password = rs.getString("Password");
                LocalDateTime createDate = rs.getDate("Create_Date").toLocalDate().atTime(LocalTime.now());
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getDate("Last_Update").toLocalDate().atTime(LocalTime.now());
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                User user1 = new User(userId, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);

                System.out.println(user1);

                System.out.println("User: " + userId + " | " + userName + " | " + password + " | " + createDate + " | "
                    + createdBy + " | " + lastUpdate + " | " + lastUpdatedBy
                );

                return user1;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
