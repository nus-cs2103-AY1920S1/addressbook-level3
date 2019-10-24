package seedu.address.model.flashcard;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a Flashcard's statistics in the StudyBuddy app.
 */
public class Statistics {

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
     *
     * @param lastViewed
     * @param toViewNext
     * @param currentIncrement
     */
    public Statistics(String lastViewed, String toViewNext, String currentIncrement) {

    }

    /**
     * Updates lastViewed variable to current system time.
     */
    private void updateLastViewed() {
        lastViewed = LocalDate.now();
    }

    /**
     * Increases current increment to the next increment as specified by the {@link ScheduleIncrement} enum class.
     */
    private void increaseIncrement() {
        this.currentIncrement.getNextIncrement();
    }

    /**
     * Updates toViewNext variable based on this Statistics' currentIncrement and lastViewed.
     */
    private void updateToViewNext() {
        long daysToAdd = this.currentIncrement.getNumberOfDays();
        toViewNext = toViewNext.plusDays(daysToAdd);
    }

    /**
     * Updates all fields from the described methods. To be used when a {@link Flashcard} is viewed.
     * Ensures lastViewed and toViewNext will never conflict and be the same date
     */
    public void onView() {
        updateLastViewed();
        increaseIncrement();
        updateToViewNext();
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
