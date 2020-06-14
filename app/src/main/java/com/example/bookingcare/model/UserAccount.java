package com.example.bookingcare.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class UserAccount {

    private String userId;
    private String displayName;

    public UserAccount(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
