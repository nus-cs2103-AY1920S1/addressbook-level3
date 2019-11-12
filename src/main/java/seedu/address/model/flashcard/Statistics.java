package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a Flashcard's statistics in the StudyBuddy app.
 */
public class Statistics {

    public static final String MESSAGE_CONSTRAINTS =
            "Statistics must have valid LocalDate lastViewed and toViewNext in the format yyyy-mm-dd and valid "
                    + "ScheduleIncrement currentIncrement such as FIRST or FINAL";

    private LocalDate lastViewed;
    private LocalDate toViewNext;
    private ScheduleIncrement currentIncrement;

    /**
     * Constructs a {@code Statistics}.
     * Initializes lastViewed variable to local system time.
     * Initializes currentIncrement variable to determine next viewing time to default FIRST.
     * Initializes toViewNext variable based on lastViewed and currentIncrement.
     */
    public Statistics() {
        lastViewed = LocalDate.now();
        currentIncrement = ScheduleIncrement.FIRST;
        updateToViewNext();
    }

    /**
     * Creates a {@code Statistics} object by parsing given String parameters.
     * @param lastViewed String of LocalDate in format yyyy-mm-dd
     * @param toViewNext String of LocalDate in format yyyy-mm-dd
     * @param currentIncrement String of ScheduleIncrement
     */
    public Statistics(LocalDate lastViewed, LocalDate toViewNext, ScheduleIncrement currentIncrement) {
        requireAllNonNull(lastViewed, toViewNext, currentIncrement);
        assert(lastViewed.isBefore(toViewNext));
        this.lastViewed = lastViewed;
        this.toViewNext = toViewNext;
        this.currentIncrement = currentIncrement;
    }

    /**
     * Updates lastViewed variable to current system time.
     */
    private void updateLastViewed() {
        this.lastViewed = LocalDate.now();
    }

    /**
     * Increases current increment to the next increment as specified by the {@link ScheduleIncrement} enum class.
     */
    private void increaseIncrement() {
        this.currentIncrement = this.currentIncrement.getNextIncrement();
    }

    /**
     * Updates toViewNext variable based on this Statistics' currentIncrement and lastViewed.
     */
    private void updateToViewNext() {
        long daysToAdd = this.currentIncrement.getNumberOfDays();
        this.toViewNext = this.lastViewed.plusDays(daysToAdd);
    }

    /**
     * Updates all fields from the described methods. To be used when a {@link Flashcard} is viewed.
     * Ensures lastViewed and toViewNext will never conflict and be the same date
     */
    public void onView() {
        LocalDate currentDate = LocalDate.now();
        if (toViewNext.isEqual(currentDate) || toViewNext.isBefore(currentDate)) {
            updateLastViewed();
            increaseIncrement();
            updateToViewNext();
        } else {
            updateLastViewed();
        }
    }

    public LocalDate getLastViewed() {
        return lastViewed;
    }

    public LocalDate getToViewNext() {
        return toViewNext;
    }

    public ScheduleIncrement getCurrentIncrement() {
        return currentIncrement;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("This flashcard was last viewed on: ");
        sb.append(lastViewed.toString());
        sb.append(". This flashcard should next be viewed on ");
        sb.append(toViewNext.toString());
        sb.append(" for optimum revision!");
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Statistics)) {
            return false;
        }

        Statistics otherStatistics = (Statistics) other;
        return otherStatistics.getLastViewed().equals(getLastViewed())
                && otherStatistics.getToViewNext().equals(getToViewNext())
                && otherStatistics.getCurrentIncrement().equals(getCurrentIncrement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastViewed, toViewNext, currentIncrement);
    }
}
