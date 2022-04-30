package model;

public class Country {
    private int id;
    private String name;

    public Country(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
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
