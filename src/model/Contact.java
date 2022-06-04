package model;

public class Contact {
    private int id;
    private String name;
    private String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /***
     * Override toString for user-friendly display of contact name.
     * @return the name
     */
    @Override
    public String toString() { return(name); }

    /**
     * Get the contact ID.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /***
     * Set the contact ID.
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Get the contact name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /***
     * Set the contact name.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the contact email.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /***
     * Set the contact email.
     * @param email the email
     */
    public void setId(String email) {
        this.email = email;
    }
}
