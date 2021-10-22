package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Handles everything with regard to adding and removing assistants on shifts, bookable rooms, and bookings.
 * Requires a BookingApp in the constructor.
 */
public class BookingSystem {
    private BookingApp bookingApp;

    /**
     * The constructor requires a BookingApp.
     * @param bookingApp The BookingApp.
     */
    public BookingSystem(BookingApp bookingApp)
    {
        this.bookingApp = bookingApp;
    }

    /**
     * List the bookings.
     */
    private void listBookings()
    {
        int i = 11;
        for (Booking booking: bookingApp.getBookings())
        {
            System.out.println(i + booking.printInfo());
            i++;
        }
    }

    /**
     * Requests for the user to input a booking ID for a scheduled booking, and sets the corresponding booking's status to complete.
     */
    public void concludeBooking()
    {
        System.out.println("Concluding booking\n");
        ArrayList<Booking>scheduledBookings = new ArrayList<>();
        int i = 11;
        //show scheduled bookings
        for (Booking booking: bookingApp.getBookings())
        {
            if (booking.getStatus().equals("SCHEDULED"))
            {
                scheduledBookings.add(booking);
                System.out.println(i + booking.printInfo());
                i++;
            }
        }
        if (scheduledBookings.size() == 0)
        {
            //return to menu if there are no scheduled bookings
            System.out.println("No scheduled bookings found\n");
            bookingApp.runMenu();
        }
        System.out.println("Enter the sequential ID of the booking you would like to complete\n0 for menu and -1 to exit");
        Scanner userInput = new Scanner(System.in);
        int id = userInput.nextInt()-11;
        //-11 is what happens when the user enters 0, -12 for -1
        if (id == -11)
        {
            bookingApp.runMenu();
        }
        else if (id == -12)
        {
            System.exit(1);
        }
        else if (id > -1 && id < scheduledBookings.size())
        {
            for (Booking booking: bookingApp.getBookings())
            {
                if (booking.equals(scheduledBookings.get(id)))
                {
                    booking.setManualComplete(true);
                    booking.updateStatus();
                    System.out.println("Booking has been successfully completed\n");
                    concludeBooking();
                }
            }
            System.out.println("Booking could not be completed\n");
            concludeBooking();
        }
        else
        {
            System.out.println("Could not find a booking with this ID\n");
            concludeBooking();
        }

    }

    /**
     * A booking is removed if it is found in the system, and has not been completed.
     * It will also reduce the occupancy of the bookable room, and change the status of the assistant on shift to free.
     */
    public void removeBookings() {
        System.out.println("Removing booking\n");
        listBookings();

        if(true)
        {
            ArrayList<Booking> availableRemovals = new ArrayList<Booking>();
            for (Booking booking: bookingApp.getBookings())
            {
                if (booking.getStatus().equals("SCHEDULED"))
                {
                    availableRemovals.add(booking);
                }
            }

            if (availableRemovals.size()>0)
            {
                System.out.println("Bookings found: \n");
                int bookingCount = 11;
                for(Booking booking: availableRemovals)
                {
                    System.out.println(bookingCount + ") " + booking.printInfo());
                    bookingCount++;
                }
                System.out.println("Select the booking you want to remove: \nType 0 for menu and -1 for exit");
                Scanner userInput = new Scanner(System.in);
                int bookingChoice = userInput.nextInt() - 11;

                if(bookingChoice > -1 && bookingChoice < availableRemovals.size())
                {
                    availableRemovals.get(bookingChoice).getAssistant().unAssignAssistant();
                    availableRemovals.get(bookingChoice).getRoom().changeOccupancy(-1);

                    bookingApp.getBookings().remove(availableRemovals.get(bookingChoice));

                    System.out.println("Booking has been successfully removed\n");
                }
                else if (bookingChoice == -11)
                {
                    bookingApp.runMenu();
                }
                else if (bookingChoice == -12)
                {
                    System.exit(1);
                }
                else
                {
                    System.out.println("Failed to pick a valid option");
                    removeBookings();
                }
            }
            else
            {
                System.out.println("No bookings found- the booking has either been completed or does not exist\n");
            }
            removeBookings();
        }
    }

    /**
     * Checks that a given email address ends in "@uok.ac.uk" and has only one "@" char.
     * @param email the email address that's validity is being checked
     * @return true if the email is valid
     */
    private Boolean isEmailValid(String email)
    {
        long count = email.chars().filter(ch -> ch == '@').count();
        if (email.endsWith("@uok.ac.uk") && count == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * A booking can be added if a valid student email is given, and there is an available room and assistant.
     * If a booking in the past is added, it will be set to completed.
     */
    public void addBookings() {

        System.out.println("Adding booking (appointment for a COVID test) to the system");

        Scanner userInput = new Scanner(System.in);
        System.out.println("Type the email of the student who is having a test (must be @uok.ac.uk): ");
        String email = userInput.nextLine();


        //basic email validity check
        if (isEmailValid(email))
        {
            System.out.println("List of available time slots:\n");
            int i = 11;
            ArrayList<String>timeSlots = new ArrayList<>();
            for (BookableRoom bookableRoom: bookingApp.getBookableRooms())
            {
                for(AssistantOnShift assistantOnShift: bookingApp.getAssistantsOnShift())
                {
                    if (bookableRoom.getTimeSlot().toLowerCase(Locale.ROOT).equals(assistantOnShift.getTimeSlot().toLowerCase(Locale.ROOT)) && assistantOnShift.getStatus().equals("FREE") && !bookableRoom.getStatus().equals("FULL"))
                    {
                        timeSlots.add(bookableRoom.getTimeSlot());
                        System.out.println(i + " | "+ bookableRoom.getTimeSlot());
                        i++;
                        break;

                    }
                }
            }
            if (i == 11)
            {
                System.out.println("No available timeslots\n");
                bookingApp.runMenu();
            }

            System.out.println("Select the index of an available booking slot: \n0 for menu and -1 for exit");
            Scanner userInput1 = new Scanner(System.in);
            int index = userInput1.nextInt()-11;
            if (index == -11)
            {
                bookingApp.runMenu();
            }
            else if(index == -12)
            {
                System.exit(1);
            }
            else if (index > -1 && index < timeSlots.size())
            {
                String timeSlot = timeSlots.get(index);

                boolean areRoomsAvailable = false;

                ArrayList<BookableRoom> availableRooms = new ArrayList<BookableRoom>();
                for (BookableRoom bookableRoom: bookingApp.getBookableRooms())
                {
                    if(bookableRoom.getTimeSlot().equals(timeSlot) && !bookableRoom.getStatus().equals("FULL"))
                    {
                        areRoomsAvailable = true;

                        availableRooms.add(bookableRoom);
                    }
                }
                if (areRoomsAvailable == false)
                {
                    System.out.println("No rooms found in this timeslot\n");
                    addBookings();
                }

                boolean areAssistantsAvailable = false;
                ArrayList<AssistantOnShift> availableAssistants = new ArrayList<AssistantOnShift>();
                for (AssistantOnShift assistantOnShift: bookingApp.getAssistantsOnShift())
                {
                    if(assistantOnShift.getTimeSlot().equals(timeSlot) && assistantOnShift.getStatus().equals("FREE"))
                    {
                        areAssistantsAvailable = true;
                        availableAssistants.add(assistantOnShift);
                    }
                }
                if (!areAssistantsAvailable)
                {
                    System.out.println("No assistants found in this timeslot\n");
                    addBookings();
                }

                System.out.println("Select an available room:\n");
                int roomCount = 1;
                for (BookableRoom bookableRoom: availableRooms)
                {
                    System.out.println(roomCount + ") " + bookableRoom.printInfo());
                    roomCount ++;
                }
                int roomNum = userInput.nextInt() - 1;
                //if the user selects a valid room
                if(roomNum > -1 && roomNum < availableRooms.size())
                {
                    System.out.println("Select an available assistant: \n");

                    int assistantCount = 1;
                    for (AssistantOnShift assistantOnShift: availableAssistants)
                    {
                        System.out.println(assistantCount + ") " + assistantOnShift.printInfo());
                        assistantCount++;
                    }
                    int assistantNum = userInput.nextInt() - 1;

                    if (assistantNum > -1 && assistantNum < availableAssistants.size())
                    {
                        //check if the booking has already been made
                        for (Booking booking: bookingApp.getBookings())
                        {
                            if (booking.getRoom().getTimeSlot().equals(timeSlot) && booking.getStudentEmail().equals(email))
                            {
                                System.out.println("Sorry, a booking has already been made for this student at this time");
                                addBookings();
                            }
                        }
                        //create the booking
                        //this booking ID is not used much in this implementation, but would be very useful upon further expansion of the program
                        String bookingID = createUniqueID();
                        bookingApp.getBookings().add(new Booking(availableRooms.get(roomNum), availableAssistants.get(assistantNum),bookingID,email));
                        availableAssistants.get(assistantNum).assignAssistant();
                        availableRooms.get(roomNum).changeOccupancy(1);
                        System.out.println("Booking successfully added\n");
                        addBookings();
                    }
                }
                else
                {
                    System.out.println("Failed to select a room\n");
                    addBookings();
                }
            }
        }
        else
        {
            System.out.println("Invalid email");
            addBookings();
        }
    }

    /**
     * Create a unique booking ID.
     * @return a unique sequential booking ID for a booking.
     */
    private String createUniqueID()
    {
        boolean isIdUnique = false;
        //the number assigned is based on the size of the bookings array
        //if bookings are removed, this could cause duplicate ids, which the following code resolves
        String bookingID = "UoK-COVID-TEST-"+bookingApp.getBookings().size();

        while (!isIdUnique)
        {
            int duplicates = 0;
            for (Booking booking: bookingApp.getBookings())
            {
                if (booking.getBookingID().equals(bookingID))
                {
                    isIdUnique = false;
                    duplicates ++;
                }
            }
            if (duplicates == 0)
            {
                isIdUnique = true;
            }
            else
            {
                //add a random alphanumeric character to the end of the id
                Random r = new Random();
                char c = (char)(r.nextInt(26) + 'a');
                bookingID = bookingID + c;
            }
        }
        return bookingID;
    }

    /**
     * Remove assistants from shifts as long as they are free.
     * The user selects the sequential ID of a free assistant on shift, which will be removed from the system.
     */
    public void removeAssistantsOnShift() {
        System.out.println("Remove assistant from shift\n");
        ArrayList<AssistantOnShift>assistantOnShifts = new ArrayList<>();
        int i = 11;
        for (AssistantOnShift assistant: bookingApp.getAssistantsOnShift())
        {
            if (assistant.getStatus().equals("FREE"))
            {
                assistantOnShifts.add(assistant);
                System.out.println(i + assistant.printInfo());
                i++;
            }
        }
        if (assistantOnShifts.size() == 0)
        {
            System.out.println("No removable assistants on shift\n");
            bookingApp.runMenu();
        }
        Scanner userInput = new Scanner(System.in);
        System.out.println("Type the sequential ID of the shift you want to remove:\n0 for menu and -1 for exit\n");
        int selection = userInput.nextInt() - 11;
        if (selection == -11)
        {
            bookingApp.runMenu();
        }
        else if (selection == -12)
        {
            System.exit(1);
        }
        else if (selection > -1 && selection < bookingApp.getAssistantsOnShift().size())
        {
            for (AssistantOnShift assistant: bookingApp.getAssistantsOnShift())
            {
                if (assistant.getName().equals(assistantOnShifts.get(selection).getName()) && assistant.getTimeSlot().equals(assistantOnShifts.get(selection).getTimeSlot()) && assistant.getEmail().equals(assistantOnShifts.get(selection).getEmail()))
                {
                    bookingApp.getAssistantsOnShift().remove(assistant);
                    System.out.println("Shift removed successfully\n");
                    removeAssistantsOnShift();
                }
            }
            System.out.println("Could not delete shift\n");
            removeAssistantsOnShift();
        }
        System.out.println("No assistants found");
        bookingApp.runMenu();
    }

    /**
     * Add a selected assistant to a day's worth of shifts (7am, 8am, 9am) as long as they are not already assigned to that shift.
     */
    public void addAssistantsOnShift() {
        Assistant assistant = bookingApp.getAssistants().get(0);
        System.out.println("Adding assistant on shift\n");
        bookingApp.listAssistants();
        Scanner userInput = new Scanner(System.in);
        System.out.println("Type the sequential ID of the assistant you want to add:\n0 for menu and -1 for exit\n");
        int selection = userInput.nextInt()-11;
        if (selection == -11) {
            bookingApp.runMenu();
        } else if (selection == -12) {
            System.exit(1);
        } else if (selection > -1 && selection < bookingApp.getAssistants().size()) {
            assistant = bookingApp.getAssistants().get(selection);
        } else {
            System.out.println("Invalid input\n");
            addAssistantsOnShift();
        }

        System.out.println("Assistant found in registry");
        System.out.println("Enter a date for their shift in the format dd/MM/yyyy: \n0 for menu and -1 for exit\n");
        Scanner userInput1 = new Scanner(System.in);
        String dateSelection = userInput1.nextLine();
        if (dateSelection.equals("0")) {
            bookingApp.runMenu();
        } else if (dateSelection.equals("-1")) {
            System.exit(1);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate timeSelection = LocalDate.now();
        try {
            timeSelection = LocalDate.parse(dateSelection, dtf);
        } catch (Exception ex) {
            System.out.println("Invalid date given");
            addAssistantsOnShift();
        }

        boolean doesShiftOneExist = false;
        boolean doesShiftTwoExist = false;
        boolean doesShiftThreeExist = false;
        for (AssistantOnShift assistantOnShift : bookingApp.getAssistantsOnShift()) {
            if (assistantOnShift.getTimeSlot().equals(timeSelection.toString() + " 07:00:00")) {
                doesShiftOneExist = true;
            }
            if (assistantOnShift.getTimeSlot().equals(timeSelection.toString() + " 08:00:00")) {
                doesShiftTwoExist = true;
            }
            if (assistantOnShift.getTimeSlot().equals(timeSelection.toString() + " 09:00:00")) {
                doesShiftThreeExist = true;
            }
        }
        if (!doesShiftOneExist) {
            bookingApp.getAssistantsOnShift().add(new AssistantOnShift(assistant.getName(), assistant.getEmail(), (timeSelection.toString() + " 07:00:00")));
        }
        if (!doesShiftTwoExist) {
            bookingApp.getAssistantsOnShift().add(new AssistantOnShift(assistant.getName(), assistant.getEmail(), (timeSelection.toString() + " 08:00:00")));
        }
        if (!doesShiftThreeExist) {
            bookingApp.getAssistantsOnShift().add(new AssistantOnShift(assistant.getName(), assistant.getEmail(), (timeSelection.toString() + " 09:00:00")));
        }

        if (doesShiftOneExist && doesShiftTwoExist && doesShiftThreeExist) {
            System.out.println("This assistant already has a full set of shifts on this day");
        }
        else {
            System.out.println("Assistant on shift successfully added");
            addAssistantsOnShift();
        }

        addAssistantsOnShift();

    }


    /**
     * Removes an empty bookable room that the user chooses, from the system.
     */
    public void removeBookableRooms() {
        System.out.println("Removing bookable room\n");
        ArrayList<BookableRoom>availableRooms = new ArrayList<>();
        int i = 11;
        for (BookableRoom room: bookingApp.getBookableRooms())
        {
            if (room.getStatus().equals("EMPTY"))
            {
                System.out.println(i + room.printInfo());
                i++;
                availableRooms.add(room);
            }
        }
        if (availableRooms.size() == 0)
        {
            System.out.println("No removable bookable rooms\n");
            bookingApp.runMenu();
        }
        System.out.println("Enter the bookable room's sequential ID:\n0 for menu and -1 for exit ");
        Scanner userInput = new Scanner(System.in);
        int inputID = userInput.nextInt() -11;
        if (inputID == -11)
        {
            bookingApp.runMenu();
        }
        else if (inputID == -12)
        {
            System.exit(1);
        }
        else if (inputID > -1 && inputID < availableRooms.size())
        {
            for (BookableRoom room: bookingApp.getBookableRooms())
            {
                if (room.getIdCode().equals(availableRooms.get(inputID).getIdCode()) && room.getTimeSlot().equals(availableRooms.get(inputID).getTimeSlot()))
                {
                    bookingApp.getBookableRooms().remove(room);
                    System.out.println("Bookable room removed successfully\n");
                    removeBookableRooms();
                }
            }
        }
        else
        {
            System.out.println("Invalid input\n");
            removeBookableRooms();
        }

    }

    /**
     * Requests user input to select a time of day (7am, 8am or 9am).
     * @return time - A string containing one of the aforementioned times.
     */
    private String selectTimeOfDay()
    {
        boolean isValidInput = false;
        String time = "07:00:00";

        while (!isValidInput)
        {
            Scanner userInput = new Scanner(System.in);
            String selection = userInput.nextLine();
            switch (selection)
            {
                case "1":
                    time = "07:00:00";
                    isValidInput = true;
                    break;
                case "2":
                    time = "08:00:00";
                    isValidInput = true;
                    break;
                case "3":
                    time = "09:00:00";
                    isValidInput = true;
                    break;
                case "0":
                    bookingApp.runMenu();
                case "-1":
                    System.exit(1);
                default:
                    //invalid selection
                    isValidInput = false;
                    System.out.println("Please enter a valid choice\n1)7am\n2)8am\n3)9am\n");
                    break;
            }
        }
        return time;
    }

    /**
     * Add a bookable room to a shift as long as it is not already assigned to that shift.
     */
    public void addBookableRooms() {
        System.out.println("Adding bookable room\n");
        bookingApp.listRooms();
        System.out.println("Enter the room's sequential ID:\n0 for menu and -1 for exit\n");
        Scanner userInput = new Scanner(System.in);
        int inputID = userInput.nextInt() - 11;
        if (inputID == -11)
        {
            bookingApp.runMenu();
        }
        else if (inputID == -12)
        {
            System.exit(1);
        }
        else if (inputID > -1 && inputID < bookingApp.getRooms().size())
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate formattedDate = LocalDate.now();

            System.out.println("Room found\n");
            System.out.println("Please enter a date for the room to be available in the format dd/MM/yyyy:\n0 for menu and -1 for exit\n");
            Scanner userInput1 = new Scanner(System.in);
            String date = userInput1.nextLine();
            if (date.equals("0"))
            {
                bookingApp.runMenu();
            }
            else if (date.equals("-1"))
            {
                System.exit(1);
            }
            try
            {
                formattedDate = LocalDate.parse(date, dtf);
            }
            catch (Exception ex)
            {
                System.out.println("Invalid date given");
                addBookableRooms();
            }
            System.out.println("Select a time of day for the room to be available: \n1) 7am\n2) 8am\n3) 9am\n0) Menu\n-1) Exit\n");
            String time = selectTimeOfDay();


            String timeSlot = formattedDate.toString() + " " + time;
            BookableRoom newRoom = new BookableRoom(bookingApp.getRooms().get(inputID).getIdCode(),bookingApp.getRooms().get(inputID).getCapacity(),timeSlot);
            boolean alreadyExists = false;
            for (BookableRoom bookableRoom: bookingApp.getBookableRooms())
            {
                if (bookableRoom.getIdCode().equals(newRoom.getIdCode()) && bookableRoom.getTimeSlot().equals(newRoom.getTimeSlot()))
                {
                    alreadyExists = true;
                    break;
                }
            }
            if (!alreadyExists)
            {
                bookingApp.getBookableRooms().add(newRoom);
                System.out.println("Room added\n");
            }
            else
            {
                System.out.println("Already exists");
            }
            addBookableRooms();
        }
        System.out.println("No room with this ID found\n");
        addBookableRooms();

    }
}
