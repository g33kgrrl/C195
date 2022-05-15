package model;

import dao.ContactQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Contact {
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static ObservableList<Contact> getAllContacts() {
        return ContactQuery.getAll();
    }

    @Override
    public String toString() {
//        return("#" + Integer.toString(id) + " " + name);
        return(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
