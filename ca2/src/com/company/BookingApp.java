package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The central class for the program.
 * Contains the main method, initialises data and runs the menu.
 * It also features methods for listing Assistants, AssistantOnShifts, Rooms, BookableRooms and Bookings
 */
public class BookingApp {

    private ArrayList<Assistant>assistants;
    private ArrayList<AssistantOnShift>assistantsOnShift;
    private ArrayList<Room>rooms;
    private ArrayList<BookableRoom>bookableRooms;

    private ArrayList<Booking> bookings;

    private BookingSystem bookingSystem;

    /**
     * The main method initialises all data and runs the runMenu method.
     * It first initialises the BookingApp and UniversityResources, then initialises the university's data before running the menu.
     * @param args arguments for the main
     */
    public static void main(String[] args) {

        BookingApp app = new BookingApp();
        UniversityResources resources = new UniversityResources();

        app.assistants = resources.getAssistants();
        app.rooms = resources.getRooms();
        app.assistantsOnShift = app.initialiseAssistantsOnShift();
        app.bookableRooms = app.initialiseBookableRooms();
        app.bookings = app.initialiseBookings();

        app.bookingSystem = new BookingSystem(app);

	   app.runMenu();

    }

    /**
     * Update the status of each booking- this happens every time the menu is run.
     */
    private void updateBookings()
    {
        for (Booking booking: bookings)
        {
            booking.updateStatus();
        }
    }

    /**
     * The menu allows the user to select an option for managing COVID tests.
     * Options vary from managing bookable rooms, assistants on shifts, and bookings.
     */
    public void runMenu() {
        updateBookings();
        pressAnyKeyToContinue();

        System.out.println("University of Knowledge - COVID Test\n");
        System.out.println("Manage Bookings\n");
        System.out.println("Please, enter the number to select your option\n");

        System.out.println("To manage Bookable Rooms: ");
        System.out.println("  1. List");
        System.out.println("  2. Add");
        System.out.println("  3. Remove");

        System.out.println("To manage Assistants on Shift: ");
        System.out.println("  4. List");
        System.out.println("  5. Add");
        System.out.println("  6. Remove");

        System.out.println("To manage Bookings: ");
        System.out.println("  7. List");
        System.out.println("  8. Add");
        System.out.println("  9. Remove");
        System.out.println(" 10. Conclude");

        System.out.println("""
                After selecting one the options above, you will be presented other screens.
                If you press 0, you will be able to return to this main menu.
                Press -1 (or ctrl+c) to quit this application.
                """);

        Scanner userInput = new Scanner(System.in);
        String selection = userInput.nextLine();

        manageMenuInput(selection);
    }

    /**
     * Runs a method based on the option the user chose in the runMenu method
     * @param input The option the user chose in the menu
     */
    private void manageMenuInput(String input)
    {
        boolean isValidInput = false;

        while (!isValidInput)
        {
            switch (input)
            {
                case "1":
                    isValidInput = true;
                    System.out.println("University of Knowledge - COVID Test\n");
                    listBookableRooms();
                    break;
                case "2":
                    isValidInput = true;
                    System.out.println("University of Knowledge - COVID Test\n");
                    bookingSystem.addBookableRooms();
                    break;
                case "3":
                    isValidInput = true;
                    System.out.println("University of Knowledge - COVID Test\n");
                    bookingSystem.removeBookableRooms();
                    break;
                case "4":
                    isValidInput = true;
                    System.out.println("University of Knowledge - COVID Test\n");
                    listAssistantsOnShift();
                    break;
                case "5":
                    isValidInput = true;
                    System.out.println("University of Knowledge - COVID Test\n");
                    bookingSystem.addAssistantsOnShift();
                    break;
                case "6":
                    isValidInput = true;
                    System.out.println("University of Knowledge - COVID Test\n");
                    bookingSystem.removeAssistantsOnShift();
                    break;
                case "7":
                    isValidInput = true;
                    System.out.println("University of Knowledge - COVID Test\n");
                    listBookings();
                    break;
                case "8":
                    isValidInput = true;
                    System.out.println("University of Knowledge - COVID Test\n");
                    bookingSystem.addBookings();
                    break;
                case "9":
                    isValidInput = true;
                    System.out.println("University of Knowledge - COVID Test\n");
                    bookingSystem.removeBookings();
                    break;
                case "10":
                    System.out.println("University of Knowledge - COVID Test\n");
                    isValidInput = true;
                    bookingSystem.concludeBooking();
                case "-1":
                    System.exit(1);
                case "0":
                    runMenu();
                default:
                    isValidInput = false;
                    System.out.println("Invalid input\n");
                    runMenu();
                    break;
            }
        }
    }

    /**
     * Initialises BookableRooms
     * @return rooms the bookable rooms that have been initialised
     */
    private ArrayList<BookableRoom> initialiseBookableRooms()
    {
        ArrayList<BookableRoom> rooms = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        rooms.add(new BookableRoom("Uok5A",3,LocalDate.parse("05/05/2021",dtf).toString() + " " + "07:00:00"));
        rooms.add(new BookableRoom("Uok5A",3,LocalDate.parse("05/05/2021",dtf).toString() + " " + "08:00:00"));
        rooms.add(new BookableRoom("Uok5A",3,LocalDate.parse("25/04/2021",dtf).toString() + " " + "09:00:00"));

        rooms.add(new BookableRoom("Uok3B",2,LocalDate.parse("17/04/2021",dtf).toString() + " " + "07:00:00"));
        rooms.add(new BookableRoom("Uok3B",2,LocalDate.parse("17/04/2021",dtf).toString() + " " + "08:00:00"));
        rooms.add(new BookableRoom("Uok3B",2,LocalDate.parse("17/04/2021",dtf).toString() + " " + "09:00:00"));

        rooms.add(new BookableRoom("Uok2D",4,LocalDate.parse("29/04/2021",dtf).toString() + " " + "07:00:00"));
        rooms.add(new BookableRoom("Uok2D",4,LocalDate.parse("01/01/2021",dtf).toString() + " " + "08:00:00"));
        rooms.add(new BookableRoom("Uok2D",4,LocalDate.parse("26/04/2021",dtf).toString() + " " + "09:00:00"));

        return rooms;
    }

    /**
     * Initialises the AssistantOnShifts.
     * @return assistants -a list of the assistants on shifts
     */
    private ArrayList<AssistantOnShift> initialiseAssistantsOnShift()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ArrayList<AssistantOnShift> assistants = new ArrayList<>();

        assistants.add(new AssistantOnShift("Jared Ainsworth", "jd@uok.ac.uk",LocalDate.parse("05/05/2021",dtf).toString() + " " + "07:00:00"));
        assistants.add(new AssistantOnShift("Jared Ainsworth", "jd@uok.ac.uk",LocalDate.parse("05/05/2021",dtf).toString() + " " + "08:00:00"));

        assistants.add(new AssistantOnShift("Bo Jackson", "bj@uok.ac.uk",LocalDate.parse("17/04/2021",dtf).toString() + " " + "09:00:00"));
        assistants.add(new AssistantOnShift("Bo Jackson", "bj@uok.ac.uk",LocalDate.parse("29/04/2021",dtf).toString() + " " + "07:00:00"));

        assistants.add(new AssistantOnShift("Susan Davidson", "sd@uok.ac.uk",LocalDate.parse("01/01/2021",dtf).toString() + " " + "08:00:00"));
        assistants.add(new AssistantOnShift("Susan Davidson", "sd@uok.ac.uk",LocalDate.parse("26/04/2021",dtf).toString() + " " + "09:00:00"));

        assistants.add(new AssistantOnShift("Susan Davidson", "sd@uok.ac.uk",LocalDate.parse("17/04/2021",dtf).toString() + " " + "07:00:00"));

        return assistants;
    }

    /**
     * initialise the bookings
     * @return bookings -the list of bookings
     */
    public ArrayList<Booking> initialiseBookings()
    {
        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(getBookableRooms().get(0),getAssistantsOnShift().get(0),"Uok-COVID-TEST-0","jc@uok.ac.uk"));
        getBookableRooms().get(0).changeOccupancy(1);
        getAssistantsOnShift().get(0).assignAssistant();

        bookings.add(new Booking(getBookableRooms().get(1),getAssistantsOnShift().get(1),"Uok-COVID-TEST-1","da@uok.ac.uk"));
        getBookableRooms().get(1).changeOccupancy(1);
        getAssistantsOnShift().get(1).assignAssistant();

        bookings.add(new Booking(getBookableRooms().get(3),getAssistantsOnShift().get(3),"Uok-COVID-TEST-2","jc@uok.ac.uk"));
        bookings.add(new Booking(getBookableRooms().get(3),getAssistantsOnShift().get(6),"Uok-COVID-TEST-3","gh@uok.ac.uk"));
        getBookableRooms().get(3).changeOccupancy(2);
        getAssistantsOnShift().get(3).assignAssistant();
        getAssistantsOnShift().get(6).assignAssistant();

        bookings.add(new Booking(getBookableRooms().get(7),getAssistantsOnShift().get(4),"Uok-COVID-TEST-4","iw@uok.ac.uk"));
        getBookableRooms().get(7).changeOccupancy(1);
        getAssistantsOnShift().get(4).assignAssistant();

        return bookings;
    }

    /**
     * Get the list of Rooms.
     * @return rooms - the ArrayList of Rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Get the list of Bookings.
     * @return bookings - the ArrayList of Bookings
     */
    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    /**
     * Get the list of Assistants
     * @return assistants - the ArrayList of assistants
     */
    public ArrayList<Assistant> getAssistants() {
        return assistants;
    }

    /**
     * Get the list of AssistantOnShifts
     * @return assistantsOnShift - the Arraylist of AssistantOnShifts
     */
    public ArrayList<AssistantOnShift> getAssistantsOnShift() {
        return assistantsOnShift;
    }

    /**
     * Get the list of bookable rooms
     * @return bookableRooms - the ArrayList of BookableRooms
     */
    public ArrayList<BookableRoom> getBookableRooms() {
        return bookableRooms;
    }

    /**
     * Clears the screen in the terminal.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Allows for pauses in the program by requiring the user to press enter to continue.
     * It also uses the clearScreen method to clear the screen when called
     */
    private void pressAnyKeyToContinue()
    {
        System.out.println("Press Enter key to continue...\n");
        try
        {
            System.in.read();

            clearScreen();
        }
        catch(Exception e)
        {}
    }

    /**
     * List the bookings- the user is given several options of how they would like to view the bookings as well.
     * They can see all the bookings, just scheduled bookings, or just completed bookings.
     */
    private void listBookings() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("\n1) All bookings\n2) Scheduled bookings\n3) Completed Bookings\n0) Menu\n-1) Exit");
        String selection = userInput.nextLine();
        int i = 1;

        switch (selection) {
            case "1":
                for (Booking booking : bookings) {
                    System.out.println(i + booking.printInfo());
                    i++;
                }
                break;
            case "2":
                for (Booking booking : bookings) {
                    if (booking.getStatus().equals("SCHEDULED")) {
                        System.out.println(i + booking.printInfo());
                        i++;
                    }
                }
                break;
            case "3":
                for (Booking booking : bookings) {
                    if (booking.getStatus().equals("COMPLETED")) {
                        System.out.println(i + booking.printInfo());
                        i++;
                    }
                }
                break;
            case "0":
                runMenu();
                break;
            case "-1":
                System.exit(1);
            default:
                System.out.println("Failed to give a valid input");
                for(Booking booking:bookings)
                {
                    System.out.println(i + booking.printInfo());
                    i++;
                }
                break;
        }
        runMenu();
    }

    /**
     * List the assistants on shifts.
     */
    private void listAssistantsOnShift() {
        int i = 11;
        for (AssistantOnShift assistant:assistantsOnShift)
        {
            System.out.println(i + assistant.printInfo());
            i++;
        }
        runMenu();
    }

    /**
     * List all bookable rooms, or just available ones if the user would prefer.
     */
    private void listBookableRooms() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("See all bookable rooms or just available ones?\n1) All\n2) Available\n0) Menu\n-1) Exit");
        String selection = userInput.nextLine();
        int i = 11;
        switch (selection) {
            case "1":
                for (BookableRoom room : bookableRooms) {
                    System.out.println(i + room.printInfo());
                    i++;
                }
                break;
            case "2":
                for (BookableRoom room : bookableRooms) {
                    if (!room.getStatus().equals("FULL")) {
                        System.out.println(i + room.printInfo());
                        i++;
                    }
                }
                break;
            case "0":
                runMenu();
                break;
            case "-1":
                System.exit(1);
            default:
                System.out.println("Failed to give a valid input\n");
                runMenu();
                break;
        }

        runMenu();
    }

    /**
     * List the assistants in the system.
     */
    public void listAssistants()
    {
        int i = 11;
        for (Assistant assistant: assistants)
        {
            System.out.println(i+ assistant.printInfo());
            i++;
        }
    }

    /**
     * Lists the rooms in the system.
     */
    public void listRooms()
    {
        int i = 11;
        for (Room room: rooms)
        {
            System.out.println(i + room.printInfo());
            i++;
        }
    }
}
