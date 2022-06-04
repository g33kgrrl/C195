package model;

import dao.CountryQuery;


public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private int divisionId;

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /***
     * Override toString for user-friendly display of customer name.
     * @return the name
     */
    @Override
    public String toString() { return(name); }

    /***
     * Get the customer ID.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /***
     * Set the customer ID.
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Get the customer name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /***
     * Set the customer name.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Get the customer address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /***
     * Set the customer address.
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /***
     * Get the customer postal code.
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /***
     * Set the customer postal code.
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /***
     * Get the customer phone number.
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /***
     * Set the customer phone number.
     * @param phone the phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /***
     * Get the customer's division ID.
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /***
     * Set the customer's division ID.
     * @param divisionId the division ID
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /***
     * Get the customer's country.
     * @return the country
     */
    public String getCountry() {
        return String.valueOf(CountryQuery.getCountryByDivId(divisionId));
    }

    /***
     * Set the customer's country.
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
