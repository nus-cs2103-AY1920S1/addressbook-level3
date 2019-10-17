package com.typee.model.engagement;

/**
 * Helper class that helps to check if engagements are conflictingly scheduled.
 */
public class EngagementConflictChecker {

    public static boolean areConflicting(Engagement firstEngagement, Engagement secondEngagement) {
        if (areAtDifferentLocations(firstEngagement, secondEngagement)) {
            return false;
        }

        return haveTimeOverlap(firstEngagement, secondEngagement);
    }

    private static boolean haveTimeOverlap(Engagement firstEngagement, Engagement secondEngagement) {
        if (firstEngagement.getStartTime().isBefore(secondEngagement.getStartTime())) {
            return firstEngagement.getEndTime().isAfter(secondEngagement.getStartTime());
        } else if (secondEngagement.getStartTime().isBefore(firstEngagement.getStartTime())) {
            return secondEngagement.getEndTime().isAfter(firstEngagement.getStartTime());
        } else {
            return true;
        }
    }

    private static boolean areAtDifferentLocations(Engagement firstEngagement, Engagement secondEngagement) {
        return !firstEngagement.getLocation().equals(secondEngagement.getLocation());
    }

}
