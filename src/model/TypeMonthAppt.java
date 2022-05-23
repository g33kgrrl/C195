package model;

import dao.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class TypeMonthAppt {
    private static ObservableList<TypeMonthAppt> allTypeMonthAppts = FXCollections.observableArrayList();

    // total COUNT of customer appointments by TYPE and MONTH
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

    public static ObservableList<TypeMonthAppt> getAllTypeMonthAppts() {
        return AppointmentQuery.getAllByTypeMonth();
    }

//    @Override
//    public String toString() {
////        return("#" + Integer.toString(id) + " " + name);
//        return(name);
//    }

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
