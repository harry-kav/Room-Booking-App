package com.company;

/**
 * A child class of Room.
 * It requires an ID code, a capacity, and a timeslot.
 */
public class BookableRoom extends Room {

    private String timeSlot;
    private int occupancy;
    private String status;
    private String idCode;
    private int capacity;

    /**
     * The constructor has a room code, capacity, and a timeslot for bookings.
     * @param idCode The room code.
     * @param capacity The room capacity.
     * @param timeSlot The timeslot for the bookable room.
     */
    public BookableRoom(String idCode, int capacity, String timeSlot) {
        super(idCode, capacity);
        this.idCode = idCode;
        this.capacity = capacity;
        this.timeSlot = timeSlot;

        occupancy = 0;

        updateStatus();
    }

    /**
     * Get the timeslot.
     * @return timeSlot
     */
    public String getTimeSlot() {
        return timeSlot;
    }

    /**
     * Update the room's status.
     * If the room has nobody occupying it, it is empty.
     * If it is at full capacity, it is full.
     * Otherwise, it is available.
     */
    public void updateStatus()
    {
        if (occupancy <= 0)
        {
            status = "EMPTY";
        }
        else if (occupancy < capacity)
        {
            status = "AVAILABLE";
        }
        else
        {

            status = "FULL";
        }
    }

    /**
     * Get the status
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * print the room's info
     * @return infoString
     */
    public String printInfo()
    {
        return " | " + timeSlot.toString() + " | " + status + " | ID Code: " + idCode + " | Occupancy: " + occupancy + " | Capacity: " + capacity;
    }

    /**
     * Changes the occupancy when a booking occurs.
     * @param occupancyChange The number to change the occupancy by.
     */
    public void changeOccupancy(int occupancyChange) {
        this.occupancy += occupancyChange;

        updateStatus();
    }
}
