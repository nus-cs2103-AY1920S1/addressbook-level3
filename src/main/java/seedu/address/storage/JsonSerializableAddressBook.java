package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.Vehicle;

/**
 * An Immutable AddressBook that is serializable to JSON format.
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
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        incidents.addAll(source.getIncidentList().stream().map(JsonAdaptedIncident::new).collect(Collectors.toList()));
        vehicles.addAll(source.getVehicleList().stream().map(JsonAdaptedVehicle::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        // for persons
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        // for incidents
        for (JsonAdaptedIncident jsonAdaptedIncident : incidents) {
            Incident incident = jsonAdaptedIncident.toModelType();
            if (addressBook.hasIncident(incident)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INCIDENT);
            }
            addressBook.addIncident(incident);
        }

        // for vehicles
        for (JsonAdaptedVehicle jsonAdaptedVehicle : vehicles) {
            Vehicle vehicle = jsonAdaptedVehicle.toModelType();
            if (addressBook.hasVehicle(vehicle)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VEHICLE);
            }
            addressBook.addVehicle(vehicle);
        }

        return addressBook;
    }

}
