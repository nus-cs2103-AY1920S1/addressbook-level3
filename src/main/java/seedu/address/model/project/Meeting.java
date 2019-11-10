package seedu.address.model.project;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a meeting in the app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Meeting {

    private final Time time;
    private final Description description;

    public Meeting(Time time, Description description) {
        requireAllNonNull(time, description);
        this.time = time;
        this.description = description;
    }

    public Time getTime() {
        return this.time;
    }

    public Description getDescription() {
        return this.description;
    }

    public static boolean isValidMeeting(Meeting meeting) {
        boolean timeValidity = Time.isValidTimeAndDate(meeting.getTime().toString());
        boolean descriptionValidity = Description.isValidDescription(meeting.getDescription().toString());
        boolean validityRes = timeValidity && descriptionValidity;
        return validityRes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Meeting)) {
            return false;
        }

        Meeting meeting = (Meeting) o;
        return this.time.equals(meeting.time)
                && this.description.equals(meeting.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, description);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Meeting Description: ")
                .append(getDescription()).append("\n")
                .append("Meeting Time: ")
                .append(getTime());
        return builder.toString();
    }
}
