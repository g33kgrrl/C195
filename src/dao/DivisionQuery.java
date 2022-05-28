package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Division;


public abstract class DivisionQuery {

    public static Division getDivision(int id) {
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
