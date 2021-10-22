package com.company;

/**
 * A room has an ID code and a capacity.
 */
public class Room {

    private String idCode;
    private int capacity;

    /**
     * The constructor consists of a room ID, and the room's capacity.
     * @param idCode Room ID.
     * @param capacity How many tests can occur in the room at any given time.
     */
    public Room(String idCode, int capacity)
    {
        this.idCode = idCode;
        this.capacity = capacity;
    }

    /**
     * Get the ID.
     * @return idCode
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * Get the capacity.
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Print the room's information
     * @return the string
     */
    public  String printInfo()
    {
        return " | " + idCode + " | " + capacity;
    }
}
