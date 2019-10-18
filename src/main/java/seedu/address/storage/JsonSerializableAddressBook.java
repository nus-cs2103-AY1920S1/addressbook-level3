package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.util.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final int ongoingVisitPatientIndex;
    private final int ongoingVisitIndex;
    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("ongoingVisitPatientIndex") Integer ongoingVisitPatientIndex,
            @JsonProperty("ongoingVisitIndex") Integer ongoingVisitIndex) {
        this.persons.addAll(persons);
        if (ongoingVisitPatientIndex == null) {
            ongoingVisitPatientIndex = -1;
        }
        if (ongoingVisitIndex == null) {
            ongoingVisitIndex = -1;
        }
        this.ongoingVisitPatientIndex = ongoingVisitPatientIndex;
        this.ongoingVisitIndex = ongoingVisitIndex;
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        Pair<Integer, Integer> indexPairOfCurrentPersonAndVisit = source.getIndexPairOfOngoingPatientAndVisit();
        this.ongoingVisitPatientIndex = indexPairOfCurrentPersonAndVisit.getKey();
        this.ongoingVisitIndex = indexPairOfCurrentPersonAndVisit.getValue();
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (int i = 0; i < persons.size(); i++) {
            JsonAdaptedPerson jsonAdaptedPerson = persons.get(i);
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
            if (ongoingVisitPatientIndex == i) {
                Optional<Visit> optionalVisit = person.getVisitByIndex(ongoingVisitIndex);
                optionalVisit.ifPresent(addressBook::setOngoingVisit);
            }
        }
        return addressBook;
    }

}
