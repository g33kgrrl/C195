package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryQuery {
    /***
     * Get all countries in the database.
     * @return all countries, or null if none
     */
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
            }

            return allCountries;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /***
     * Search the database for a country, by country ID.
     * @param id the country ID
     * @return country with the specified country ID, or null if not found
     */
    public static Country getCountry(int id) {
        try {
            String sql = "SELECT * FROM countries WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                return new Country(countryId, countryName);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***
     * Get country associated with a given division ID.
     * @param divisionId the division ID
     * @return the country for the given division ID, or null if none
     */
    public static Country getCountryByDivId(int divisionId) {
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                int countryId = rs.getInt("Country_ID");

                return CountryQuery.getCountry(countryId);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
