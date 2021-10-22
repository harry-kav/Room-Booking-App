package com.company;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A booking is a test that is booked for a student, and requires a bookable room, assistantOnShift, booking ID and student email.
 */
public class Booking {

    private BookableRoom room;
    private AssistantOnShift assistant;
    private String bookingID;
    private String studentEmail;

    private String status;

    private boolean manualComplete;

    /**
     * The constructor consists of the room, assistant on shift, bookingID and student email.
     * @param room The room.
     * @param assistant The AssistantOnShift.
     * @param bookingID The unique ID.
     * @param studentEmail The student's email address.
     */
    public Booking(BookableRoom room, AssistantOnShift assistant, String bookingID, String studentEmail)
    {
        this.room = room;
        this.assistant = assistant;
        this.bookingID = bookingID;
        this.studentEmail = studentEmail;

        //update the status when the booking is created, to check if it has already happened
        manualComplete = false;
        status = "SCHEDULED";
        updateStatus();
    }

    /** Get the status
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * When a booking is manually completed, the booking acknowledges this fact.
     * @param manualComplete A boolean indicating whether the booking has manually been completed or not.
     */
    public void setManualComplete(boolean manualComplete) {
        this.manualComplete = manualComplete;
    }

    /**
     * Get the assistant.
     * @return assistant
     */
    public AssistantOnShift getAssistant() {
        return assistant;
    }

    /**
     * Get the student email.
     * @return studentEmail
     */
    public String getStudentEmail() {
        return studentEmail;
    }

    /**
     * Get the room.
     * @return room
     */
    public BookableRoom getRoom() {
        return room;
    }

    /**
     * Update the status of the booking- if it has been manually completed, its status is "completed".
     * Otherwise, if it is in the past, change the status to "completed".
     * If neither of the above are true, the status is set to "scheduled".
     */
    public void updateStatus()
    {
        if (!manualComplete)
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime currentDateTime = LocalDateTime.now();
            currentDateTime.format(dtf);

            LocalDateTime timeSlot = LocalDateTime.parse(room.getTimeSlot(),dtf);

            if (currentDateTime.isBefore(timeSlot))
            {
                status = "SCHEDULED";
            }
            else
            {
                status = "COMPLETED";
            }
        }
        else
        {
            status = "COMPLETED";
        }
    }

    /**
     * Get the booking ID.
     * @return bookingID
     */
    public String getBookingID() {
        return bookingID;
    }

    /**
     * Create a string with the booking information.
     * @return the string
     */
    public String printInfo()
    {
        return "| "+ room.getTimeSlot() + " | " + assistant.getEmail() + " | " + room.getIdCode() + " | " + studentEmail + " | " + status;
    }
}
