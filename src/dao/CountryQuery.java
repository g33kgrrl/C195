package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryQuery {
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
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return allCountries;
    }

    public static Country getCountry(int id) {
        ObservableList<Country> selectedCountry = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country c = new Country(countryId, countryName);
                selectedCountry.add(c);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return selectedCountry.get(0);
    }

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
