package model;

import dao.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class Appointment {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

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

    /***
     * Override toString for user-friendly display of appointment title.
     * @return the title
     */
    @Override
    public String toString() {
        return(title);
    }

    /***
     *  Find upcoming appointments.
     *  Fetches a list of all appointments from the database, then loops through them and compares their start times to
     *  current time. Any appointments with a start time in the next 15 minutes are listed.
     *
     * <p><b>LAMBDA EXPRESSION JUSTIFICATION</b>: Originally, upcomingApptsList was initialized as a string, and string
     * concatenation '+=' was used in a for loop. This is not good practice, as it degrades performance by creating a
     * new String object for each concatenation and takes up unnecessary resources until garbage collection. Using a
     * StringBuilder inside of a lambda expression made more efficient use of resources, while also keeping the code
     * concise by condensing the task into one self-contained line.</p>
     *
     * @return string representing list of appointments, or "no upcoming" if none are found
     */
    public static String getUpcoming() {
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentQuery.selectAll();
        LocalDateTime nowLdt = LocalDateTime.now();

        if(allAppointments == null) { return "No upcoming appointments."; }

        for (Appointment a:allAppointments) {
            LocalDateTime apptStart = a.start;

            if(apptStart.isAfter(nowLdt) && apptStart.isBefore(nowLdt.plusMinutes(15))) {
                upcomingAppointments.add(a);
            }
        }

        int upcomingApptsCount = upcomingAppointments.size();

        if(upcomingApptsCount == 0) {
            return "No upcoming appointments.";
        } else {
            StringBuilder upcomingApptsList = new StringBuilder();
            upcomingApptsList.append(upcomingApptsCount).append(" upcoming appointment(s):\n\n");

            // Lambda expression
            // For each upcoming appointment, append to StringBuilder to be displayed in report
            upcomingAppointments.forEach(a -> upcomingApptsList.append(a.id).append("\t").append(
                    dtf.format(a.start)).append("\t").append(a.title).append("\t").append(a.description).append("\n"));

            return upcomingApptsList.toString();
        }
    }

    /***
     * Convert time at headquarters to user's local time.
     * Take a LocalDateTime as expressed for Eastern time zone, and convert to an equivalent LocalDateTime for user's
     * local time. Used by getValidApptsHours to find start and end of business hours, each converted to user's time
     * zone, which are in turn used to restrict appointments to valid appointment times.
     * @param ldt a LocalDateTime as expressed in Eastern time zone
     * @return the LocalDateTime equivalent for user's time zone
     */
    public static LocalDateTime getConvertedLtd(LocalDateTime ldt) {
        final ZoneId hqZoneId = ZoneId.of("US/Eastern");
        ZoneId systemZoneId = ZoneId.systemDefault();

        return ldt.atZone(hqZoneId).withZoneSameInstant(systemZoneId).toLocalDateTime();
    }

    /***
     * Get a list of valid appointment hours for user to select.
     * Uses getConvertedLtd to find start and end of business hours as they would be expressed in the user's time zone,
     * and use them to create a list of valid hours the current user may select for appointments.
     * @param openLdt business open LocalDatetime, as expressed in Eastern time zone
     * @param closeLdt business close LocalDateTime, as expressed in Eastern time zone
     * @return a list of valid appointment times the current user may select, according to business hours
     */
    public static ObservableList<String> getValidApptHours(LocalDateTime openLdt, LocalDateTime closeLdt) {
        LocalDateTime localOpenLdt = getConvertedLtd(openLdt);
        LocalDateTime localCloseLdt = getConvertedLtd(closeLdt);

        ObservableList<String> validApptHours = FXCollections.observableArrayList();

        for (int i = localOpenLdt.getHour(); i <= localCloseLdt.getHour(); i++) {
            validApptHours.add(String.format("%02d", i) + ":00");
        }

        return validApptHours;
    }

    // Use data from the form, but still pass Appointment object as well because it's needed in order to eliminate self-reference
    // in the resulting ObservableList... and form data may differ from that in the object!

    /***
     * Check for appointment overlap when adding or modifying an appointment.
     * Takes the proposed customer, and proposed start and end LocalDateTimes, that the user has selected in the
     * appointment add/modify form. Gets a list of all appointments for the proposed customer, to be checked against the
     * proposed start and end times selected by the user. If the user is modifying an existing appointment, the appt
     * param will contain the original appointment object, or if adding new it will be null. A lambda expression then
     * uses the appt param to remove the current appointment from the list before the check, so that it won't create a
     * time conflict with itself when modifying.
     *
     * <p><b>LAMBDA EXPRESSION JUSTIFICATION</b>: A "for" loop could have been used here to filter the modified appointment from
     * the list, but a lambda expression is both more readable and more efficient.</p>
     *
     * @param proposedCustomerId the user-selected customer ID
     * @param proposedStart the user-selected start LocalDateTime
     * @param proposedEnd the user-selected end LocalDateTime
     * @param appt (optional) the appointment the user is attempting to modify, if any, or null if adding new
     * @return true if there is an overlap with another appointment for this customer, false if not
     */
    public static boolean checkOverlap(int proposedCustomerId, LocalDateTime proposedStart, LocalDateTime proposedEnd, Appointment appt) {
        // Get all appointments for the selected customer
        ObservableList<Appointment> custAppointments = AppointmentQuery.selectByCustomerId(proposedCustomerId);

        // If there are no appointments for this customer, there cannot be a conflict; return false
        if(custAppointments == null) { return false; }

        // If an appointment is being modified, remove it from the list so it doesn't cause a time conflict with itself
        // Lambda expression
        if(appt != null) {
            custAppointments = custAppointments.filtered(appointment -> appointment.id != appt.id);

            // If there are no other appointments left in the list, there cannot be a conflict; return false
            if(custAppointments == null) { return false; }
        }

        // Check the remaining appointments in the list against the proposed start and end LocalDateTimes
        for(Appointment a:custAppointments) {
            LocalDateTime start = a.start;
            LocalDateTime end = a.end;

            // Complete three overlap checks for different kinds of overlap
            // 1. Overlap when Start is in an appointment window
            if((proposedStart.isAfter(start) || proposedStart.isEqual(start)) && proposedStart.isBefore(end)) {
                return true;
            }
            // 2. Overlap when End is in an appointment window
            else if(proposedEnd.isAfter(start) && ((proposedEnd.isBefore(end)) || proposedEnd.isEqual(end))) {
                return true;
            }
            // 3. Overlap when Start and End encompass an entire appointment
            else if((proposedStart.isBefore(start) || proposedStart.isEqual(start)) && (proposedEnd.isAfter(end) || proposedEnd.isEqual(end))) {
                return true;
            }
        }

        return false;
    }

    /***
     * Returns start LocalDateTime as a formatted string for tables displaying appointments.
     * Note that this method is not called directly, but is used indirectly by MainController and
     * ReportByContactController to format the start date and time for user-friendly display in the table.
     * @return formatted start date and time
     */
    public String getFormattedStart() { return dtf.format(start); }

    /***
     * Returns end LocalDateTime as a formatted string for tables displaying appointments.
     * Note that this method is not called directly, but is used indirectly by MainController and
     * ReportByContactController to format the end date and time for user-friendly display in the table.
     * @return formatted end date and time
     */
    public String getFormattedEnd() { return dtf.format(end); }

    /***
     * Get the appointment id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /***
     * Set the appointment id.
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     * Get the appointment title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /***
     * Set the appointment title.
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /***
     * Get the appointment description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /***
     * Set the appointment description.
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /***
     * Get the appointment location.
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /***
     * Set the appointment location.
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /***
     * Get the appointment type.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /***
     * Set the appointment type.
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /***
     * Get the appointment start.
     * @return the start LocalDateTime
     */
    public LocalDateTime getStart() {
        return start;
    }

    /***
     * Set the appointment start.
     * @param start the start LocalDateTime
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /***
     * Get the appointment end.
     * @return the end LocalDateTime
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /***
     * Set the appointment end.
     * @param end the end LocalDateTime
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /***
     * Get the appointment created date.
     * @return the created date LocalDateTime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /***
     * Set the appointment created date.
     * @param createDate the created date LocalDateTime
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /***
     * Get the username of the appointment creator.
     * @return the creator's username
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /***
     * Set the username of the appointment creator.
     * @param createdBy the creator's username
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /***
     * Get the date and time the appointment was last updated.
     * @return the last updated LocalDateTime
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /***
     * Set the date and time the appointment was last updated.
     * @param lastUpdate the last updated LocalDateTime
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /***
     * Get the username of the user who last updated the appointment.
     * @return the last updater's username
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /***
     * Set the username of the user who last updated the appointment.
     * @param lastUpdatedBy the last updater's username
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /***
     * Get the appointment customer ID.
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /***
     * Set the appointment customer ID.
     * @param customerId the customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /***
     * Get the appointment user ID.
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /***
     * Set the appointment user ID.
     * @param userId the user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /***
     * Get the appointment contact ID.
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /***
     * Set the appointment contact ID.
     * @param contactId the contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
