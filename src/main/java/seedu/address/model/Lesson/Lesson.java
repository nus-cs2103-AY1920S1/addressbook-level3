package seedu.address.model.lesson;

import java.util.Objects;

/**
 * Represents a Lesson in the AddressBook.
 */
public class Lesson {
    private final Time time;
    private final ClassName name;
    private boolean isRepeat;

    public Lesson(Time time, ClassName name) {
        this.time = time;
        this.name = name;
        this.isRepeat = false;
    }

    public Time getTime() {
        return time;
    }

    public ClassName getName() {
        return name;
    }

    public boolean getIsRepeat() {
        return isRepeat;
    }

    /**
     * Returns true if both lessons of the same name have both identity fields that are the same.
     */
    public boolean isSameLesson(Lesson otherLesson) {
        if (otherLesson == this) {
            return true;
        }

        return otherLesson != null
                && otherLesson.getTime().equals(this.getTime())
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
        return otherLesson.getTime().equals(this.getTime())
                && otherLesson.getName().equals(this.getName())
                && otherLesson.getIsRepeat() == this.getIsRepeat();

    }

    @Override
    public int hashCode() {
        return Objects.hash(time, name, isRepeat);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" \nTime: ")
                .append(getTime());
        return builder.toString();
    }
}
