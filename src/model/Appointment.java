package model;

import dao.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class Appointment {
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;


    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                       String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return(title);
    }

    // find and list Appointments with start time within 15m
    public static String getUpcoming() {
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentQuery.getAll();
        LocalDateTime nowLdt = LocalDateTime.now();

        for (Appointment a:allAppointments) {
            LocalDateTime apptStart = a.getStart();

            if(apptStart.isAfter(nowLdt) && apptStart.isBefore(nowLdt.plusMinutes(15))) {
                upcomingAppointments.add(a);
            }
        }

        int upcomingApptsCount = upcomingAppointments.size();

        if(upcomingApptsCount == 0) {
            return "No upcoming appointments.";
        } else {
            String upcomingApptsList = upcomingApptsCount + " upcoming appointment(s):\n\n";

            for(Appointment a:upcomingAppointments) {
                upcomingApptsList += a.start.format(DateTimeFormatter.ofPattern("HH:mm")) + "\t" + a.getTitle() + "\t" +
                a.getDescription() + "\n";
            }

            return upcomingApptsList;
        }
    }

    public static LocalDateTime getConvertedLtd(LocalDateTime ldt) {
        final ZoneId hqZoneId = ZoneId.of("US/Eastern");

        ZoneId systemZoneId = ZoneId.systemDefault();
        LocalDateTime systemLdt = ldt.atZone(hqZoneId).withZoneSameInstant(systemZoneId).toLocalDateTime();

        return systemLdt;
    }

    public static ObservableList<String> getValidApptHours(LocalDateTime openLdt, LocalDateTime closeLdt) {
        LocalDateTime localOpenLdt = getConvertedLtd(openLdt);
        LocalDateTime localCloseLdt = getConvertedLtd(closeLdt);

        ObservableList<String> validApptHours = FXCollections.observableArrayList();

        for (int i = localOpenLdt.getHour(); i <= localCloseLdt.getHour(); i++) {
            validApptHours.add(String.format("%02d", i) + ":00");
        }

        return validApptHours;
    }

    // Enables formatted start date and time in Appointments table
    public String getFormattedStart() { return dtf.format(start); }

    // Enables formatted end date and time in Appointments table
    public String getFormattedEnd() { return dtf.format(end); }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
