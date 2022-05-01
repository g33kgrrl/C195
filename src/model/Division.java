package model;

import dao.DivisionQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Division {
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    private int id;
    private String name;
    private int countryId;

    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public static ObservableList<Division> getAllDivisions(int countryId) {
//        allCountries.add(new Country(4, "Mexico"));
//        allCountries.add(new Country(5, "Iceland"));

//        return allCountries;
        return DivisionQuery.selectAllForCountry(countryId);
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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
