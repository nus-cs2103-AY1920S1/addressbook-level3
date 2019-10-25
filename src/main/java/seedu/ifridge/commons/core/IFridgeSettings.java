package seedu.ifridge.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the iFridge settings.
 * Guarantees: immutable.
 */
public class IFridgeSettings implements Serializable {
    private static final String DEFAULT_NUMBER_OF_DAYS = "3";
    private static final String DEFAULT_SORT_METHOD = "alphabetical";
    private static final String DEFAULT_LIST_DISPLAY = "merge";

    private final String numberOfDays;
    private final String sortMethod;
    private final String listDisplay;

    public IFridgeSettings() {
        numberOfDays = DEFAULT_NUMBER_OF_DAYS;
        sortMethod = DEFAULT_SORT_METHOD;
        listDisplay = DEFAULT_LIST_DISPLAY;
    }


    public IFridgeSettings(String numberOfDays, String sortMethod, String listDisplay) {
        this.numberOfDays = numberOfDays;
        this.sortMethod = sortMethod;
        this.listDisplay = listDisplay;
    }
    public String getNumberOfDays() {
        return numberOfDays;
    }

    public String getSortMethod() {
        return sortMethod;
    }

    public String getListDisplay() {
        return listDisplay;
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

        return Objects.equals(numberOfDays, o.numberOfDays)
                && Objects.equals(sortMethod, o.sortMethod)
                && Objects.equals(listDisplay, o.listDisplay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfDays, sortMethod, listDisplay);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("numberOfDays : " + numberOfDays + "\n");
        sb.append("sortMethod : " + sortMethod + "\n");
        sb.append("listDisplay : " + listDisplay);
        return sb.toString();
    }
}
