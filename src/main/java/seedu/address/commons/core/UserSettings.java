package seedu.address.commons.core;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * A Serializable class that contains the user settings.
 * Guarantees: immutable.
 */
public class UserSettings implements Serializable {

    private final boolean suggestionsOn;
    private final int timeToLiveAmount;
    private final ChronoUnit timeToLiveUnit;

    public UserSettings() {
        suggestionsOn = true;
        timeToLiveAmount = 30;
        timeToLiveUnit = ChronoUnit.DAYS;
    }

    public UserSettings(boolean suggestionsOn, int amount, ChronoUnit unit) {
        this.suggestionsOn = suggestionsOn;
        this.timeToLiveAmount = amount;
        this.timeToLiveUnit = unit;
    }

    public boolean isSuggestionsOn() {
        return suggestionsOn;
    }

    public int getTimeToLiveAmount() {
        return timeToLiveAmount;
    }

    public ChronoUnit getTimeToLiveUnit() {
        return timeToLiveUnit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserSettings)) { //this handles null as well.
            return false;
        }

        UserSettings o = (UserSettings) other;

        return suggestionsOn == o.suggestionsOn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suggestionsOn);
    }

    @Override
    public String toString() {
        return "Suggestions on: " + suggestionsOn + "\n";
    }
}
