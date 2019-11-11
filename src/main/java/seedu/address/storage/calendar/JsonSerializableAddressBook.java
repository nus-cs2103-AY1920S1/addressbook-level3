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
import seedu.address.model.calendar.task.Task;

/**
 * An Immutable CalendarAddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate task(s).";

    private final List<JsonAdaptedTask> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedTask> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyCalendarAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyCalendarAddressBook source) {
        persons.addAll(source.getPersonList()
                .stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the calendarModel's {@code CalendarAddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CalendarAddressBook toModelType() throws IllegalValueException {
        CalendarAddressBook calendarAddressBook = new CalendarAddressBook();
        for (JsonAdaptedTask jsonAdaptedTask : persons) {
            Task task = jsonAdaptedTask.toModelType();
            if (calendarAddressBook.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            calendarAddressBook.addTask(task);
        }
        return calendarAddressBook;
    }

}
