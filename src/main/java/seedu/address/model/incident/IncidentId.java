package seedu.address.model.incident;

/**
 * Generates Incident ID for the incident in this format: MMYYYYXXXX
 * Where MM = Month of incident, YYYY = Year of incident, and XXXX = incident number of the month
 */
public class IncidentId {
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
    }


    public IncidentId(String id) {
        this.id = id;
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
