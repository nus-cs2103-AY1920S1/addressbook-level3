package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the user settings.
 * Guarantees: immutable.
 */
public class UserSettings implements Serializable {

    private final boolean suggestionsOn;

    public UserSettings() {
        suggestionsOn = true;
    }

    public UserSettings(boolean suggestionsOn) {
        this.suggestionsOn = suggestionsOn;
    }

    public boolean isSuggestionsOn() {
        return suggestionsOn;
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
