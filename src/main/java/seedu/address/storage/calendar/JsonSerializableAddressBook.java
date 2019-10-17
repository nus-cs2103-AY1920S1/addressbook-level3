package seedu.address.storage.calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendar.CalendarAddressBook;
import seedu.address.model.calendar.ReadOnlyCalendarAddressBook;
import seedu.address.model.calendar.person.Task;

/**
 * An Immutable CalendarAddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate task(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyCalendarAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyCalendarAddressBook source) {
        persons.addAll(source.getPersonList()
                .stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the calendarModel's {@code CalendarAddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CalendarAddressBook toModelType() throws IllegalValueException {
        CalendarAddressBook calendarAddressBook = new CalendarAddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Task task = jsonAdaptedPerson.toModelType();
            if (calendarAddressBook.hasPerson(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            calendarAddressBook.addPerson(task);
        }
        return calendarAddressBook;
    }

}
