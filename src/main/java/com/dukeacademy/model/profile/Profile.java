package com.dukeacademy.model.profile;

/**
 * The type Profile.
 */
public class Profile {
    /**
     * The Profile.
     */
    private final String profile;

    /**
     * Instantiates a new Profile.
     *
     * @param s the s
     */
    public Profile(String s) {
        profile = s;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return profile;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof Profile)) {
            return false;
        }

        // state check
        Profile other = (Profile) obj;
        return profile.equals(other.profile);
    }
}
