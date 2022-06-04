package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class User {
    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public User(int userId, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /***
     * Override toString for user-friendly display of username.
     * @return the username
     */
    @Override
    public String toString() {
        return(userName);
    }

    /***
     * Write specified user activity to log.
     * @param activityToLog the user activity to log
     */
    public static void writeToActivityLog(String activityToLog) {
        try (
            FileWriter fw = new FileWriter("src/login_activity.txt", true);
            PrintWriter pw = new PrintWriter(fw);
        ) {
            pw.println(activityToLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Get user ID.
     * @return the id
     */
    public int getUserId() { return userId; }

    /***
     * Get username.
     * @return the username
     */
    public String getUserName() { return userName; }

    /***
     * Set username.
     * @param userName the username
     */
    public void setUserName(String userName) { this.userName = userName; }

    /***
     * Get user's password.
     * @return the password
     */
    public String getPassword() { return password; }

    /***
     * Set user's password.
     * @param password the password
     */
    public void setPassword(String password) { this.password = password; }

    /***
     * Get date user was created.
     * @return the created date
     */
    public LocalDateTime getCreateDate() { return createDate; }

    /***
     * Set date user was created.
     * @param createDate the created date
     */
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    /***
     * Get the username that created this user.
     * @return the created by username
     */
    public String getCreatedBy() { return createdBy; }

    /***
     * Set the username that created this user.
     * @param createdBy the created by username
     */
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /***
     * Get the date and time the user was last updated.
     * @return the last updated LocalDateTime
     */
    public LocalDateTime getLastUpdate() { return lastUpdate; }

    /***
     * Set the date and time the user was last updated.
     * @param lastUpdate the last updated LocalDateTime
     */
    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }

    /***
     * Get the username that last updated this user.
     * @return the last updated by username
     */
    public String getLastUpdatedBy() { return lastUpdatedBy; }

    /***
     * Set the username that last updated this user.
     * @param lastUpdatedBy the last updated by username
     */
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
}
