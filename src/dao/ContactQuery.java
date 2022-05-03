package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactQuery {

    public static ObservableList<Contact> selectAll() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact c = new Contact(id, name, email);
                allContacts.add(c);

//                System.out.println(id + " | " + name);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return allContacts;
    }

    public static Contact select(int id) {
        ObservableList<Contact> selectedContact = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contact c = new Contact(contactId, contactName, contactEmail);
                selectedContact.add(c);

//                System.out.println(contactId + " | " + contactName);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return selectedContact.get(0);
    }
}
