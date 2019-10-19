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
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_BODY = "Persons list contains duplicate body(s).";
    public static final String MESSAGE_DUPLICATE_WORKER = "Persons list contains duplicate worker(s).";
    public static final String MESSAGE_DUPLICATE_FRIDGE = "Persons list contains duplicate fridge(s).";


    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedBody> bodies = new ArrayList<>();
    private final List<JsonAdaptedWorker> workers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("bodies") List<JsonAdaptedBody> bodies,
                                       @JsonProperty("workers") List<JsonAdaptedWorker> workers) {
        this.persons.addAll(persons);
        this.bodies.addAll(bodies);
        this.workers.addAll(workers);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        bodies.addAll(source.getBodyList().stream().map(JsonAdaptedBody::new).collect(Collectors.toList()));
        workers.addAll(source.getWorkerList().stream().map(JsonAdaptedWorker::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasEntity(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addEntity(person);
        }

        //@@author ambervoong
        for (JsonAdaptedBody jsonAdaptedBody : bodies) {
            Body body = jsonAdaptedBody.toModelType();
            if (addressBook.hasEntity(body)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BODY);
            }
            addressBook.addEntity(body);
        }

        for (JsonAdaptedWorker jsonAdaptedWorker : workers) {
            Worker worker = jsonAdaptedWorker.toModelType();
            if (addressBook.hasEntity(worker)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WORKER);
            }
            addressBook.addEntity(worker);
        }
        //@@author

        return addressBook;
    }

}
