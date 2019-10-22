package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.IncidentManager;
import seedu.address.model.ReadOnlyIncidentManager;
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.Vehicle;

/**
 * An Immutable IncidentManager that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_INCIDENT = "Incidents list contains duplicate incident(s)";
    public static final String MESSAGE_DUPLICATE_VEHICLE = "Vehicles list contains duplicate vehicle(s)";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedIncident> incidents = new ArrayList<>();
    private final List<JsonAdaptedVehicle> vehicles = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and vehicles.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("incidents") List<JsonAdaptedIncident> incidents,
                                       @JsonProperty("vehicles") List<JsonAdaptedVehicle> vehicles) {
        this.persons.addAll(persons);
        this.incidents.addAll(incidents);
        this.vehicles.addAll(vehicles);
    }

    /**
     * Converts a given {@code ReadOnlyIncidentManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyIncidentManager source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        incidents.addAll(source.getIncidentList().stream().map(JsonAdaptedIncident::new).collect(Collectors.toList()));
        vehicles.addAll(source.getVehicleList().stream().map(JsonAdaptedVehicle::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code IncidentManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public IncidentManager toModelType() throws IllegalValueException {
        IncidentManager incidentManager = new IncidentManager();
        // for persons
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (incidentManager.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            incidentManager.addPerson(person);
        }

        // for incidents
        for (JsonAdaptedIncident jsonAdaptedIncident : incidents) {
            Incident incident = jsonAdaptedIncident.toModelType();
            if (incidentManager.hasIncident(incident)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INCIDENT);
            }
            incidentManager.addIncident(incident);
        }

        // for vehicles
        for (JsonAdaptedVehicle jsonAdaptedVehicle : vehicles) {
            Vehicle vehicle = jsonAdaptedVehicle.toModelType();
            if (incidentManager.hasVehicle(vehicle)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VEHICLE);
            }
            incidentManager.addVehicle(vehicle);
        }

        return incidentManager;
    }

}
