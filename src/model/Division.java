package model;

public class Division {
    private int id;
    private String name;
    private int countryId;

    public Division(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    /***
     * Override toString for user-friendly display of division name.
     * @return the division name
     */
    @Override
    public String toString() {
        return(name);
    }

    /***
     * Get the division ID.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /***
     * Set the division ID.
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Get the division name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /***
     * Set the division name.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Get the country ID.
     * @return the country id
     */
    public int getCountryId() {
        return countryId;
    }

    /***
     * Set the country ID.
     * @param countryId the country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
