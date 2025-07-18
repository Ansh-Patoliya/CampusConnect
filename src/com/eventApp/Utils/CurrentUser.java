package com.eventApp.Utils;

import com.eventApp.Model.User;

public class CurrentUser {
    private static User currentUser;

    public CurrentUser(User currentUser) {
        CurrentUser.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUser.currentUser = currentUser;
    }
}
