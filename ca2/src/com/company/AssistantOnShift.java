package com.company;

/**
 * A child class of the base class assistant, for assistants on a specific shift.
 */
public class AssistantOnShift extends Assistant
{
    private String name;
    private String email;

    private String timeSlot;

    private String status;

    /**
     * The constructor includes a name, email address and a timeslot.
     * @param name The assistant's name.
     * @param email Their email address.
     * @param timeSlot The timeslot of the shift.
     */
    public AssistantOnShift(String name, String email, String timeSlot) {
        super(name, email);
        this.name = name;
        this.email = email;
        this.timeSlot = timeSlot;

        //an assistant is initially free
        status = "FREE";
    }

    /**
     * Get the assistant's status.
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get the timeslot.
     * @return timeSlot
     */
    public String getTimeSlot() {
        return timeSlot;
    }

    /**
     * The assistant's status is changed to busy when they have been assigned to a booking.
     */
    public void assignAssistant() {

        status = "BUSY";
    }

    /**
     * When an assistant is unassigned from a booking, their status is set to free.
     */
    public void unAssignAssistant()
    {
        status = "FREE";
    }

    /**
     * Creates a string with the assistant's information.
     * @return infoString
     */
    public String printInfo()
    {
        return " | " + timeSlot + " | " + status + " | " + email;
    }
}
