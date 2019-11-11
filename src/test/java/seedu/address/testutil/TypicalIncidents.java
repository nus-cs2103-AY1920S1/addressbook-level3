package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.TypicalVehicles.getTypicalVehicles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.IncidentManager;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.IncidentDateTime;
import seedu.address.model.incident.IncidentId;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalIncidents {

    // typical operators list
    private static final List<Person> typicalOperators = getTypicalPersons();

    // typical date time list
    private static final List<IncidentDateTime> typicalIncidentDateTimes = getTypicalIncidentDateTimes();

    // typical incident id list
    private static final List<IncidentId> typicalIncidentIds = getTypicalIncidentIds();

    // typical vehicles list
    private static final List<Vehicle> typicalVehicles = getTypicalVehicles();

    // typical descriptions list
    private static final List<Description> typicalDescriptions = getTypicalDescriptions();

    // typical caller numbers list
    private static final List<CallerNumber> typicalCallerNumbers = getTypicalCallerNumbers();

    // typical incidents

    // two identical incidents (by id)
    public static final Incident FIRST_INCIDENT = new Incident(typicalOperators.get(0), new District(20),
            typicalIncidentDateTimes.get(0), typicalIncidentIds.get(0), typicalCallerNumbers.get(0),
            typicalDescriptions.get(0), Incident.Status.SUBMITTED_REPORT,
            typicalVehicles.get(2));

    public static final Incident FIRST_INCIDENT_COPY = new Incident(typicalOperators.get(0), new District(20),
            typicalIncidentDateTimes.get(0), typicalIncidentIds.get(0), typicalCallerNumbers.get(0),
            typicalDescriptions.get(0), Incident.Status.SUBMITTED_REPORT,
            typicalVehicles.get(2));

    // two complete drafts filled by different operators

    public static final Incident SECOND_INCIDENT = new Incident(typicalOperators.get(0), new District(1),
            typicalIncidentDateTimes.get(1), typicalIncidentIds.get(1), typicalCallerNumbers.get(1),
            typicalDescriptions.get(1), Incident.Status.COMPLETE_DRAFT,
            typicalVehicles.get(0));

    public static final Incident THIRD_INCIDENT = new Incident(typicalOperators.get(1), new District(1),
            typicalIncidentDateTimes.get(2), typicalIncidentIds.get(2), typicalCallerNumbers.get(2),
            typicalDescriptions.get(2), Incident.Status.COMPLETE_DRAFT,
            typicalVehicles.get(0));

    // two incomplete drafts filled by different operators

    public static final Incident FOURTH_INCIDENT = new Incident(typicalOperators.get(0), new District(5));

    public static final Incident FIFTH_INCIDENT = new Incident(typicalOperators.get(2), new District(5));

    // two submitted reports filled by different operators

    public static final Incident SIXTH_INCIDENT = new Incident(typicalOperators.get(0), new District(1),
            typicalIncidentDateTimes.get(3), typicalIncidentIds.get(3), typicalCallerNumbers.get(3),
            typicalDescriptions.get(3), Incident.Status.SUBMITTED_REPORT,
            typicalVehicles.get(0));

    public static final Incident SEVENTH_INCIDENT = new Incident(typicalOperators.get(1), new District(1),
            typicalIncidentDateTimes.get(4), typicalIncidentIds.get(4), typicalCallerNumbers.get(3),
            typicalDescriptions.get(4), Incident.Status.SUBMITTED_REPORT,
            typicalVehicles.get(0));

    private TypicalIncidents() {} // prevents instantiation

    /**
     * Returns an {@code IncidentManager} with all the typical incidents.
     */
    public static IncidentManager getTypicalIncidentManager() {
        IncidentManager ab = new IncidentManager();
        ab.addIncident(FIRST_INCIDENT);
        ab.addIncident(SECOND_INCIDENT);
        ab.addIncident(THIRD_INCIDENT);
        ab.addIncident(FOURTH_INCIDENT);
        ab.addIncident(FIFTH_INCIDENT);
        ab.addIncident(SIXTH_INCIDENT);
        ab.addIncident(SEVENTH_INCIDENT);
        return ab;
    }

    /**
     * Returns an {@code IncidentManager} with all two incident duplicate.
     */
    public static IncidentManager getDuplicateIncidentIncidentManager() {
        IncidentManager ab = new IncidentManager();
        ab.addIncident(FIRST_INCIDENT);
        ab.addIncident(FIRST_INCIDENT_COPY);
        return ab;
    }

    /**
     * Returns an {@code IncidentManager} with all the incomplete drafts.
     */
    public static IncidentManager getIncompleteDraftIncidentManager() {
        IncidentManager ab = new IncidentManager();
        ab.addIncident(FOURTH_INCIDENT);
        ab.addIncident(FIFTH_INCIDENT);
        return ab;
    }

    /**
     * Returns an {@code IncidentManager} with all the complete drafts.
     */
    public static IncidentManager getCompleteDraftIncidentManager() {
        IncidentManager ab = new IncidentManager();
        ab.addIncident(SECOND_INCIDENT);
        ab.addIncident(THIRD_INCIDENT);
        return ab;
    }

    /**
     * Returns an {@code IncidentManager} with all the drafts.
     */
    public static IncidentManager getDraftIncidentManager() {
        IncidentManager ab = new IncidentManager();
        ab.addIncident(SECOND_INCIDENT);
        ab.addIncident(THIRD_INCIDENT);
        ab.addIncident(FOURTH_INCIDENT);
        ab.addIncident(FIFTH_INCIDENT);
        return ab;
    }

    /**
     * Returns an {@code IncidentManager} with all the typical incidents.
     */
    public static IncidentManager getSubmittedReportIncidentManager() {
        IncidentManager ab = new IncidentManager();
        ab.addIncident(FIRST_INCIDENT);
        ab.addIncident(SIXTH_INCIDENT);
        ab.addIncident(SEVENTH_INCIDENT);
        return ab;
    }

    /**
     * Returns an {@code IncidentManager} with no incidents present.
     */
    public static IncidentManager getEmptyIncidentManager() {
        IncidentManager ab = new IncidentManager();
        return ab;
    }

    private static List<Incident> getTypicalIncidents() {
        return new ArrayList<>(Arrays.asList(FIRST_INCIDENT, FIRST_INCIDENT_COPY, SECOND_INCIDENT, THIRD_INCIDENT,
                FOURTH_INCIDENT, FIFTH_INCIDENT, SIXTH_INCIDENT, SEVENTH_INCIDENT));
    }

    private static List<IncidentDateTime> getTypicalIncidentDateTimes() {
        return new ArrayList<> (Arrays.asList(new IncidentDateTime("2016-12-02T14:30:40"),
                new IncidentDateTime("2013-01-11T00:00:00"),
                new IncidentDateTime("2014-05-01T01:00:00"),
                new IncidentDateTime("2015-06-16T14:00:00"),
                new IncidentDateTime("2016-09-01T21:00:00"),
                new IncidentDateTime("2017-11-02T22:30:00"),
                new IncidentDateTime("2016-03-22T16:30:00")));
    }

    private static List<IncidentId> getTypicalIncidentIds() {
        return new ArrayList<> (Arrays.asList(new IncidentId("1220160001"),
                new IncidentId("0120130001"),
                new IncidentId("0520140001"),
                new IncidentId("0620150001"),
                new IncidentId("0920160001"),
                new IncidentId("1120170001"),
                new IncidentId("0320160001")));
    }

    public static List<Description> getTypicalDescriptions() {
        return new ArrayList<> (Arrays.asList(new Description("Pickpocket reported along the walkway in District 20"),
                new Description("Fight reported at District intersection"),
                new Description("PMD accident in District park"),
                new Description("Tiger spotted in District courthouse."),
                new Description("Chicken caught at District walkway.")));
    }

    public static List<CallerNumber> getTypicalCallerNumbers() {
        return new ArrayList<> (Arrays.asList(new CallerNumber("84738293"),
                new CallerNumber("87595849"),
                new CallerNumber("98098765"),
                new CallerNumber("89090908")));
    }
}
