package seedu.address.model.Lesson;

import static java.util.Objects.requireNonNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time {
    private final Calendar lessonTime;

    public Time(Calendar lessonTime) {
        requireNonNull(lessonTime);
        this.lessonTime = lessonTime;
    }

    public Calendar getLessonTime() {
        return this.lessonTime;
    }
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy h:mm a");
        return formatter.format(lessonTime.getTime());
    }

    @Override
    public int hashCode() {
        return lessonTime.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && lessonTime.equals(((Time) other).lessonTime)); // state check
    }
}
