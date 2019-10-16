package com.typee.model.engagement;

/**
 * Represents the different types of {@code Engagements}.
 */
public enum EngagementType {
    MEETING,
    INTERVIEW,
    APPOINTMENT;

    /**
     * Returns the possible values an {@code EngagementType} can assume.
     * @return a {@code String} containing the permitted conformations.
     */
    public static String getMessageConstraints() {
        return "An engagement has to be one of meeting, interview or appointment.";
    }

    /**
     * Checks if a {@code String} represents a valid {@code EngagementType}.
     *
     * @param string {@code String} to be checked for validity.
     * @return true if {@String string} is a valid {@code EngagementType}.
     */
    public static boolean isValid(String string) {
        return string.equalsIgnoreCase("meeting")
                || string.equalsIgnoreCase("interview")
                || string.equalsIgnoreCase("appointment");
    }

    /**
     * Returns an {@code EngagementType} constructed from the input string.
     *
     * @param engagementType {@code String} representing an {@code EngagementType}.
     * @return the corresponding {@code EngagementType}.
     * @throws IllegalArgumentException if {@code String engagementType} is not a valid {@code EngagementType}.
     */
    public static EngagementType of(String engagementType) throws IllegalArgumentException {
        if (engagementType.equalsIgnoreCase(EngagementType.MEETING.name())) {
            return EngagementType.MEETING;
        } else if (engagementType.equalsIgnoreCase(EngagementType.INTERVIEW.name())) {
            return EngagementType.INTERVIEW;
        } else if (engagementType.equalsIgnoreCase(EngagementType.APPOINTMENT.name())) {
            return EngagementType.APPOINTMENT;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
