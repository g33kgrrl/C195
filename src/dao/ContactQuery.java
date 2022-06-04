package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Contact;


public abstract class ContactQuery {
    /***
     * Get all contacts in the database.
     * @return all contacts, or null if none
     */
    public static ObservableList<Contact> getAll() {
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
            }

            return allContacts;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /***
     * Search the database for a contact, by contact ID.
     * @param id the contact ID
     * @return contact with the specified contact ID, or null if none
     */
    public static Contact getContact(int id) {
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                return new Contact(contactId, contactName, contactEmail);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
