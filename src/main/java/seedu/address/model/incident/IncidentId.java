package seedu.address.model.incident;

/**
 * Generates Incident ID for the incident in this format: MMYYYYXXXX
 * MM = Month of incident
 * YYYY = Year of incident
 * XXXX = incident number of the month
 */

public class IncidentId {
    private static int previousM = 0;
    private static int xxxx = 0;
    private int mm;
    private int yyyy;
    private String id;

    /**
     * Generates the ID for the incident based on the following inputs
     * @param mm month of incident
     * @param yyyy year of incident
     */
    public IncidentId(int mm, int yyyy) {
        if (previousM != mm) {
            previousM = mm;
            xxxx = 0;
        } else {
            xxxx++;
        }

        this.mm = mm;
        this.yyyy = yyyy;
        int temp = mm * 100000000 + yyyy * 1000 + xxxx;
        this.id = String.format("%10d", temp);
    }

    public String getId() {
        return this.id;
    }
}
