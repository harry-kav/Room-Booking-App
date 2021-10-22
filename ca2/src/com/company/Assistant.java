package com.company;

/**
 * Assistants have a name and an email address- they can also print this info.
 */
public class Assistant {
    private String name;
    private String email;

    /**
     * The constructor contains a name and an email address.
     * @param name The name of the assistant.
     * @param email Their email address.
     */
    public  Assistant(String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    /**
     * Get the email address.
     * @return email - The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the name.
     * @return name - The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a string with the assistant's information.
     * @return infoString - The string of info.
     */
    public String printInfo()
    {
        return " | " + name + " | " + email;
    }
}
