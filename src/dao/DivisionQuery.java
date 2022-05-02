package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DivisionQuery {

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
                Division division1 = new Division(divisionId, division, countryId);
                return division1;
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

//                System.out.println(id + " | " + name + "|" + countryIdFK);
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

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");

//                System.out.println(countryId);

                return countryId;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

}
