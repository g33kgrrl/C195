package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Division;


public abstract class DivisionQuery {
    /***
     * Search the database for a division, by division ID.
     * @param id the division id
     * @return division with the specified division ID, or null if none
     */
    public static Division select(int id) {
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                return new Division(divisionId, division, countryId);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***
     * Get all divisions associated with the given country ID.
     * @param countryId the country ID
     * @return all divisions for the given country ID, or null if none
     */
    public static ObservableList<Division> selectAllForCountry(int countryId) {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryIdFK = rs.getInt("Country_ID");

                Division d = new Division(id, name, countryId);
                allDivisions.add(d);
            }

            return allDivisions;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***
     * Get the country ID associated with the given division ID.
     * @param divisionId the division ID
     * @return the country ID for the given division ID, or -1 if none found
     */
    public static int getCountryId(int divisionId) {
        try {
            String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("Country_ID");
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }
}
