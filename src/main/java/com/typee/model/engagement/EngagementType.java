package com.typee.model.engagement;

public enum EngagementType {
    MEETING,
    INTERVIEW,
    APPOINTMENT;

    public static String getMessageConstraints() {
        return "An engagement has to be one of meeting, interview or appointment.";
    }

    public static EngagementType of(String engagementType) throws IllegalArgumentException {
        final String type = engagementType;
        if (type.equalsIgnoreCase(EngagementType.MEETING.name())) {
            return EngagementType.MEETING;
        } else if (type.equalsIgnoreCase(EngagementType.INTERVIEW.name())) {
            return EngagementType.INTERVIEW;
        } else if (type.equalsIgnoreCase(EngagementType.APPOINTMENT.name())) {
            return EngagementType.APPOINTMENT;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
