package seedu.address.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.util.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.notif.Notif;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_BODY = "Bodies list contains duplicate body(s).";
    public static final String MESSAGE_DUPLICATE_WORKER = "Workers list contains duplicate worker(s).";
    public static final String MESSAGE_DUPLICATE_FRIDGE = "Fridges list contains duplicate fridge(s).";
    public static final String MESSAGE_MISSING_BODY = "The body in this fridge couldn't be found.";
    public static final String MESSAGE_DUPLICATE_NOTIF = "Notifs list contains duplicate notif(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedBody> bodies = new ArrayList<>();
    private final List<JsonAdaptedWorker> workers = new ArrayList<>();
    private final List<JsonAdaptedFridge> fridges = new ArrayList<>();
    private final List<JsonAdaptedNotif> notifs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("bodies") List<JsonAdaptedBody> bodies,
                                       @JsonProperty("workers") List<JsonAdaptedWorker> workers,
                                       @JsonProperty("fridges") List<JsonAdaptedFridge> fridges,
                                       @JsonProperty("notifs") List<JsonAdaptedNotif> notifs) {
        this.persons.addAll(persons);
        this.bodies.addAll(bodies);
        this.workers.addAll(workers);
        this.fridges.addAll(fridges);
        this.notifs.addAll(notifs);
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
        fridges.addAll(source.getFridgeList().stream().map(JsonAdaptedFridge::new).collect(Collectors.toList()));
        notifs.addAll(source.getNotifList().stream().map(JsonAdaptedNotif::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        HashMap<Integer, Body> bodyIdMap = new HashMap<>();

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
            bodyIdMap.put(body.getIdNum().getIdNum(), body);
            addressBook.addEntity(body);
        }

        for (JsonAdaptedWorker jsonAdaptedWorker : workers) {
            Worker worker = jsonAdaptedWorker.toModelType();
            if (addressBook.hasEntity(worker)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WORKER);
            }
            addressBook.addEntity(worker);
        }

        for (JsonAdaptedFridge jsonAdaptedFridge : fridges) {
            Fridge fridge = jsonAdaptedFridge.toModelType();

            if (addressBook.hasEntity(fridge)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FRIDGE);
            } else if (!(bodyIdMap.containsKey(fridge.getBodyId()))) {
                if (fridge.getBodyId() != 0) {
                    throw new IllegalValueException(MESSAGE_MISSING_BODY);
                }
            }
            Body fridgeBody = bodyIdMap.get(fridge.getBodyId());
            fridge.setBody(fridgeBody);
            addressBook.addEntity(fridge);
        }
        //@@author

        //@@author arjavibahety
        for (JsonAdaptedNotif jsonAdaptedNotif : notifs) {
            Pair<Integer, Long> notifAttributes = jsonAdaptedNotif.toModelType();
            int bodyId = notifAttributes.getKey();
            long dateTime = notifAttributes.getValue();

            List<Body> bodyList = addressBook.getBodyList();
            Notif notif;
            for (Body body : bodyList) {
                if (body.getIdNum().getIdNum() == bodyId) {
                    notif = new Notif(body);
                    notif.setNotifCreationTime(new Date(dateTime));
                    if (addressBook.hasNotif(notif)) {
                        throw new IllegalValueException(MESSAGE_DUPLICATE_NOTIF);
                    }
                    addressBook.addNotif(notif);
                }
            }
        }
        //@@author
        return addressBook;
    }
}
