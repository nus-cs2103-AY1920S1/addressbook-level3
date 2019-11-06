package com.typee.model.engagement;

import java.util.Arrays;
import java.util.function.Predicate;

import com.typee.commons.util.StringUtil;
import com.typee.model.person.Name;
import com.typee.model.person.Person;

/**
 * Tests that an {@code Engagement}'s description matches any of the keywords given.
 */
public class EngagementPredicate implements Predicate<Engagement> {
    private String timeSlot;
    private String attendees;
    private String location;
    private String description;
    private String priority;

    public EngagementPredicate() {
    }

    public boolean isValid() {
        return timeSlot != null || attendees != null || location != null || description != null
                || priority != null;
    }

    /**
     * Specifies the timeSlot in the engagement predicate.
     *
     * @param timeSlot of the engagement.
     */
    public EngagementPredicate setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
        return this;
    }

    /**
     * Specifies the attendees in the engagement predicate.
     *
     * @param attendees of the engagement.
     */
    public EngagementPredicate setAttendees(String attendees) {
        this.attendees = attendees;
        return this;
    }

    /**
     * Specifies the location predicate in the engagement predicate.
     *
     * @param location of the engagement.
     */
    public EngagementPredicate setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Specifies the description in the engagement predicate.
     *
     * @param description of the engagement.
     */
    public EngagementPredicate setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Specifies the priority in the engagement predicate.
     *
     * @param priority of the engagement.
     */
    public EngagementPredicate setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public boolean test(Engagement engagement) {

        if (description != null
                && !Arrays.stream(description.split("\\s+"))
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(engagement.getDescription(), keyword))) {
            return false;
        }

        if (attendees != null
                && !engagement.getAttendees().getAttendees().stream()
                .map(Person::getName).map(Name::toString).map(String::toLowerCase)
                .anyMatch(name -> name.contains(attendees.toLowerCase()))) {
            return false;
        }

        if (location != null && !engagement.getLocation().getLocation().equalsIgnoreCase(location)) {
            return false;
        }

        if (priority != null && !engagement.getPriority().toString().equalsIgnoreCase(priority)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EngagementPredicate // instanceof handles nulls
                && (timeSlot == null || timeSlot.equals(((EngagementPredicate) other).timeSlot))
                && (attendees == null || attendees.equals(((EngagementPredicate) other).attendees))
                && (location == null || location.equals(((EngagementPredicate) other).location))
                && (priority == null || priority.equals(((EngagementPredicate) other).priority))
                && (description == null || description.equals(((EngagementPredicate) other).description)));
    }
}
