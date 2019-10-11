package seedu.address.model.incident;

/**
 * Generates Incident ID for the incident in this format: MMYYYYXXXX
 * MM = Month of incident
 * YYYY = Year of incident
 * XXXX = incident number of the month
 */
public class IncidentId {
    private static int previousMonth = 0;
    private static int monthId = 0;
    private int month;
    private int year;
    private String id;

    /**
     * Generates the ID for the incident based on the following inputs
     * @param mm month of incident
     * @param yyyy year of incident
     */
    public IncidentId(int mm, int yyyy) {
        if (previousMonth != mm) {
            previousMonth = mm;
            monthId = 0;
        } else {
            monthId++;
        }

        this.month = mm;
        this.year = yyyy;
        int temp = mm * 100000000 + yyyy * 1000 + monthId;
        this.id = String.format("%10d", temp);
    }

    public String getId() {
        return this.id;
    }
}
