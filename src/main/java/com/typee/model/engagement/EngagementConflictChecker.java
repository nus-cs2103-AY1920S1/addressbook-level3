package com.typee.model.engagement;

/**
 * Helper class that helps to check if engagements are conflictingly scheduled.
 */
public class EngagementConflictChecker {

    /**
     * Checks if the given {@code Engagement firstEngagement} clashes with {@code Engagement secondEngagement}.
     *
     * @param firstEngagement first {@code Engagement}.
     * @param secondEngagement second {@code Engagement}.
     * @return true if the engagements conflict.
     */
    public static boolean areConflicting(Engagement firstEngagement, Engagement secondEngagement) {
        if (areAtDifferentLocations(firstEngagement, secondEngagement)) {
            return false;
        }

        return haveTimeOverlap(firstEngagement.getTimeSlot(), secondEngagement.getTimeSlot());
    }

    /**
     * Checks if two engagements have a time overlap.
     *
     * @param firstTimeSlot first {@code TimeSlot}.
     * @param secondTimeSlot second {@code TimeSlot}.
     * @return true if the times overlap.
     */
    private static boolean haveTimeOverlap(TimeSlot firstTimeSlot, TimeSlot secondTimeSlot) {
        return firstTimeSlot.overlapsWith(secondTimeSlot);
    }

    /**
     * Checks if the given engagements are held at different locations
     *
     * @param firstEngagement the first {@code Engagement}.
     * @param secondEngagement the second {@code Engagement}.
     * @return true if the the engagements are held at different locations.
     */
    private static boolean areAtDifferentLocations(Engagement firstEngagement, Engagement secondEngagement) {
        return !firstEngagement.getLocation().equals(secondEngagement.getLocation());
    }

}
