package com.company;

import java.util.ArrayList;

/**
 * Contains and initialises the university's assistants and rooms
 */
public class UniversityResources {

    private ArrayList<Room> rooms;
    private ArrayList<Assistant>assistants;

    /**
     * The constructor runs initialiseRooms and initialiseAssistants.
     */
    public UniversityResources()
    {
        rooms = initialiseRooms();
        assistants = initialiseAssistants();
    }

    /**
     * Get the assistants
     * @return assistants - The ArrayList of assistants
     */
    public ArrayList<Assistant> getAssistants() {
        return assistants;
    }

    /**
     * Get the rooms
     * @return rooms - The ArrayList of rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Initialise the rooms at the university.
     * @return rooms - The list of rooms.
     */
    private ArrayList<Room> initialiseRooms()
    {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Uok5A",3));
        rooms.add(new Room("Uok3B",2));
        rooms.add(new Room("Uok2D",4));

        return rooms;
    }

    /**
     * Initialise the assistants at the university.
     * @return assistants - The list of assistants.
     */
    private ArrayList<Assistant> initialiseAssistants()
    {
        ArrayList<Assistant> assistants = new ArrayList<>();
        assistants.add(new Assistant("Jared Ainsworth", "ja@uok.ac.uk"));
        assistants.add(new Assistant("Bo Jackson", "bj@uok.ac.uk"));
        assistants.add(new Assistant("Susan Davidson", "sd@uok.ac.uk"));

        return assistants;
    }


}
