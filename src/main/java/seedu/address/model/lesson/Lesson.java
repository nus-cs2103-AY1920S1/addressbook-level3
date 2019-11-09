package seedu.address.model.lesson;

import java.util.Calendar;
import java.util.Objects;

/**
 * Represents a Lesson in the Classroom.
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

    public String getRepeatString() {
        if (isRepeat) {
            return "Repeated weekly";
        } else {
            return "Not repeated";
        }
    }

    public void setRepeat() {
        this.isRepeat = true;
    }

    public int getDayIndex() {
        Calendar date = startTime.getTime();
        int index = (date.get(Calendar.DAY_OF_WEEK) + 5) % 7;
        return index;
    }

    /**
     * Returns true if both lessons are on the same day of week.
     * @param otherLesson Lesson object to compare to.
     * @return boolean.
     */
    public boolean isSameDay(Lesson otherLesson) {
        return this.getDayIndex() == otherLesson.getDayIndex();
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

    /**
     * checks if Lesson time occurs during another Lesson time.
     * @param toCheck Lesson object.
     * @return boolean
     */
    public boolean checkTimingExist(Lesson toCheck) {
        Calendar toCheckStart = toCheck.getStartTime().getTime();
        Calendar toCheckEnd = toCheck.getEndTime().getTime();
        Calendar thisStart = this.getStartTime().getTime();
        Calendar thisEnd = this.getEndTime().getTime();
        boolean equalStartTime = toCheckStart.compareTo(thisStart) == 0;
        boolean equalEndTime = toCheckEnd.compareTo(thisEnd) == 0;
        boolean startBetweenLesson = toCheckStart.after(thisStart) && toCheckStart.before(thisEnd);
        boolean endBetweenLesson = toCheckEnd.after(thisStart) && toCheckEnd.before(thisEnd);
        return equalStartTime || equalEndTime || startBetweenLesson || endBetweenLesson;
    }

    /**
     * Returns true if the end time is after the start time.
     * @return
     */
    public boolean endTimeIsAfterStartTime() {
        return endTime.getTime().after(startTime.getTime());
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
        builder.append(getName() + "\n")
                .append(getStartTime().getStringDay())
                .append(" \nFrom: ")
                .append(getStartTime())
                .append(" To: ")
                .append(getEndTime())
                .append(" " + getRepeatString());
        return builder.toString();
    }
}
