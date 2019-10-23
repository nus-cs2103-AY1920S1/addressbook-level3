package seedu.address.model.lesson;

import java.util.Objects;

/**
 * Represents a lesson in the AddressBook.
 */
public class Lesson {
    private final Time startTime;
    private final Time endTime;
    private final ClassName name;
    private boolean isRepeat;

    public Lesson(Time startTime, Time endTime, ClassName name) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.isRepeat = false;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public ClassName getName() {
        return name;
    }

    public boolean getIsRepeat() {
        return isRepeat;
    }

    public void setRepeat() {
        this.isRepeat = true;
    }

    /**
     * Returns true if both lessons of the same name have both identity fields that are the same.
     */
    public boolean isSameLesson(Lesson otherLesson) {
        if (otherLesson == this) {
            return true;
        }

        return otherLesson != null
                && otherLesson.getStartTime().equals(this.getStartTime())
                && otherLesson.getEndTime().equals(this.getEndTime())
                && otherLesson.getName().equals(this.getName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return otherLesson.getStartTime().equals(this.getStartTime())
                && otherLesson.getEndTime().equals(this.getEndTime())
                && otherLesson.getName().equals(this.getName())
                && otherLesson.getIsRepeat() == this.getIsRepeat();

    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, name, isRepeat);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" \nFrom: ")
                .append(getStartTime())
                .append(" To: ")
                .append(getEndTime())
                .append(" " + getIsRepeat());
        return builder.toString();
    }
}
