package seedu.ifridge.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the iFridge settings.
 * Guarantees: immutable.
 */
public class IFridgeSettings implements Serializable {
    private static final String DEFAULT_NUMBER_OF_DAYS = "3";

    private final String numberOfDays;

    public IFridgeSettings() {
        numberOfDays = DEFAULT_NUMBER_OF_DAYS;
    }

    public IFridgeSettings(String numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getNumberOfDays() {
        return numberOfDays;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof IFridgeSettings)) { //this handles null as well.
            return false;
        }

        IFridgeSettings o = (IFridgeSettings) other;

        return Objects.equals(numberOfDays, o.numberOfDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfDays);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("numberOfDays : " + numberOfDays + "\n");
        return sb.toString();
    }
}
