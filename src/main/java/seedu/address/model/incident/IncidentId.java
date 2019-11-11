package seedu.address.model.incident;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Generates Incident ID for the incident in this format: MMYYYYXXXX
 * Where MM = Month of incident, YYYY = Year of incident, and XXXX = incident number of the month
 */
public class IncidentId {
    public static final String MESSAGE_CONSTRAINTS =
            "Incident ID should only contain numerical digits and should not be blank.";

    private static int previousMonth = 1;
    private static int monthId = 1;
    private String id;

    /**
     * Generates the ID for the incident based on the following inputs
     * @param mm month of incident
     * @param yyyy year of incident
     */
    public IncidentId(int mm, int yyyy) {
        this.id = String.format("%02d", mm) + yyyy + String.format("%04d", monthId);

        if (previousMonth != mm) {
            previousMonth = mm;
            monthId = 1;
        } else {
            monthId++;
        }
        requireNonNull(getId());
        checkArgument(isValidIncidentId(getId()), MESSAGE_CONSTRAINTS);
    }

    public IncidentId(String id) {
        this.id = id;
    }

    /**
     * Checks if {@code caller} is a valid {@code CallerNumber}.
     */
    public static boolean isValidIncidentId(String incidentId) {
        if (incidentId.equals("")) {
            return false;
        }
        boolean isNumber;
        try {
            Integer.parseInt(incidentId);
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return isNumber;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IncidentId)) {
            return false;
        }

        return ((IncidentId) other).id.equals(this.id);
    }
}
