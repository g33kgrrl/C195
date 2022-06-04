package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class TypeMonthAppt {
    private static ObservableList<TypeMonthAppt> allTypeMonthAppts = FXCollections.observableArrayList();

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

    /***
     * Get the appointment ID.
     * @return the id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /***
     * Get count of appointments grouped by type and month (regardless of year).
     * Note that this is not called directly, but used by ReportsByTypeMonthController to initialize the
     * TypeMonthApptsTable.
     * @return the count of appointments grouped by type and month
     */
    public int getCount() {
        return count;
    }

    /***
     * Get appointment type.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /***
     * Get appointment month name.
     * @return the month name
     */
    public String getMonthName() {
        return monthName;
    }
}
