package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Country {
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    private int id;
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ObservableList<Country> getAllCountries() {
        allCountries.add(new Country(4, "Mexico"));
        allCountries.add(new Country(5, "Iceland"));

        return allCountries;
    }

    @Override
    public String toString() {
        return("#" + Integer.toString(id) + " " + name);
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
