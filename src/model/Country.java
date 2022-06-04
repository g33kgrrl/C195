package model;

import dao.CountryQuery;
import javafx.collections.ObservableList;


public class Country {
    private int id;
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /***
     * Override toString for user-friendly display of country name.
     * @return the name
     */
    @Override
    public String toString() { return(name); }

    /***
     * Get a list of all set countries.
     * @return a list of all set countries
     */
    public static ObservableList<Country> getAllCountries() {
        return CountryQuery.selectAll();
    }

    /***
     * Get the country ID.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /***
     * Set the country ID.
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Get the country name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /***
     * Set the country name.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
