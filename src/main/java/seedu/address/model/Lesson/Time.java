package seedu.address.model.Lesson;

import static java.util.Objects.requireNonNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time {
    private final Calendar time;

    public Time(Calendar time) {
        requireNonNull(time);
        this.time = time;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy h:mm a");
        return formatter.format(time.getTime());
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).time)); // state check
    }
}
