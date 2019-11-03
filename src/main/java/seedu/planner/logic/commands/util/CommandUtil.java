package seedu.planner.logic.commands.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import seedu.planner.commons.core.index.Index;
import seedu.planner.model.Model;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.Duration;
import seedu.planner.model.contact.Contact;

/**
 * Helper functions for commands.
 */
public class CommandUtil {
    /**
     * Returns the index of activity in the model.
     * Precondition: the {@code activity} must have been added before this.
     */
    public static Index findIndexOfActivity(Model model, Activity activity) {
        Optional<Index> indexOfActivity = model.getActivityIndex(activity);
        if (indexOfActivity.isEmpty()) {
            throw new AssertionError("Activity should have been added.");
        }
        return indexOfActivity.get();
    }

    /**
     * Returns the index of accommodation in the model.
     * Precondition: the {@code accommodation} must have been added before this.
     */
    public static Index findIndexOfAccommodation(Model model, Accommodation accommodation) {
        Optional<Index> indexOfAccommodation = model.getAccommodationIndex(accommodation);
        if (indexOfAccommodation.isEmpty()) {
            throw new AssertionError("Accommodation should have been added.");
        }
        return indexOfAccommodation.get();
    }

    /**
     * Returns the index of contact in the model.
     * Precondition: the {@code contact} must have been added before this.
     */
    public static Index findIndexOfContact(Model model, Contact contact) {
        Optional<Index> indexOfContact = model.getContactIndex(contact);
        if (indexOfContact.isEmpty()) {
            throw new AssertionError("Contact should have been added.");
        }
        return indexOfContact.get();
    }

    /**
     * Calculates and returns the endDateTime of an activity.
     */
    public static LocalDateTime calculateEndDateTime(LocalDate startDate,
                                                     Index dayIndex, LocalTime startTime, Duration duration) {
        return LocalDateTime.of(startDate.plusDays(dayIndex.getZeroBased()), startTime)
                .plusMinutes(duration.value);
    }
}
