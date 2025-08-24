package com.eventApp.Utils;

import com.eventApp.Model.User;

/**
 * Utility class to manage the currently logged-in user in Event App.
 * Uses a static field to store and provide global access to the current user.
 */
public class CurrentUser {

    private static User currentUser;  // Holds the reference to the currently logged-in user

    /**
     * Constructor to initialize the current user.
     *
     * @param currentUser the user to set as currently logged-in
     */
    public CurrentUser(User currentUser) {
        CurrentUser.currentUser = currentUser;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return the current User object
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param currentUser the User object to set as current
     */
    public static void setCurrentUser(User currentUser) {
        CurrentUser.currentUser = currentUser;
    }
}
