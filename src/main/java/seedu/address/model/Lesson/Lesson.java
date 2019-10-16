package seedu.address.model.Lesson;

import java.util.Objects;

public class Lesson {
    private final Time time;
    private final ClassName name;
    private boolean isRepeat;

    public Lesson(Time time, ClassName name, boolean isRepeat) {
        this.time = time;
        this.name = name;
        this.isRepeat = isRepeat;
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

//    public boolean isSameLesson(Lesson otherLesson) {
//        if (otherLesson == this) {
//            return true;
//        }
//
//        return otherLesson != null
//                && otherLesson.getTime().equals(this.getTime())
//                && otherLesson.getName().equals(this.getName())
//                && otherLesson.getIsRepeat().equals(this.getIsRepeat());
//    }

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
                .append(" Time: ")
                .append(getTime());
        return builder.toString();
    }
}
