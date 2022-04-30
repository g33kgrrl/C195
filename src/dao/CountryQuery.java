package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryQuery {

    public static int insert(String name) throws SQLException {
        String sql = "INSERT INTO countries (Country) VALUES(?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);

        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public static int update(int id, String name) throws SQLException {
        String sql = "UPDATE countries SET Country = ? WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, id);

        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            String name = rs.getString("Country");

            System.out.println(id + " | " + name);
        }
    }

    public static void select(int id) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int countryId = rs.getInt("Customer_ID");
            String countryName = rs.getString("Customer_Name");

            System.out.println(countryId + " | " + countryName);
        }
    }

    public static void select(String countryName) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, countryName);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            String name = rs.getString("Country_Name");

            System.out.println(id + " | " + name);
        }
    }
}
