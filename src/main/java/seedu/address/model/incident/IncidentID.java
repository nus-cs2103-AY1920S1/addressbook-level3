package seedu.address.model.incident;

/**
 * Generates Incident ID for the incident in this format: MMYYYYXXXX
 * MM = Month of incident
 * YYYY = Year of incident
 * XXXX = incident number of the month
 */

public class IncidentID {
    private static int PREVIOUSM = 0;
    private int mm;
    private int yyyy;
    private static int XXXX = 0;
    private String ID;

    /**
     * Generates the ID for the incident based on the following inputs
     * @param mm month of incident
     * @param yyyy year of incident
     */
    public IncidentID(int mm, int yyyy) {
        if(PREVIOUSM != mm) {
            PREVIOUSM = mm;
            XXXX = 0;
        } else {
            XXXX++;
        }

        this.mm = mm;
        this.yyyy = yyyy;
        int temp = mm * 100000000 + yyyy * 1000 + XXXX;
        this.ID = String.format("%10d", temp);
    }

    public String getID() {
        return this.ID;
    }
}
