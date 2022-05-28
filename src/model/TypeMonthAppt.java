package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TypeMonthAppt {
    private static ObservableList<TypeMonthAppt> allTypeMonthAppts = FXCollections.observableArrayList();

    // total counts of customer appointments by type and month (regardless of year)
    private int appointmentId;
    private int count;
    private String type;
    private String monthName;

    public TypeMonthAppt(int appointmentId, int count, String type, String monthName) {
        this.appointmentId = appointmentId;
        this.count = count;
        this.type = type;
        this.monthName = monthName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
