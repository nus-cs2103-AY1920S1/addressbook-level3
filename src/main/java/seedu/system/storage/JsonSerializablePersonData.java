package seedu.system.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.system.commons.exceptions.IllegalValueException;
import seedu.system.model.Data;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.person.Person;

/**
 * An Immutable Person Data that is serializable to JSON format.
 */
@JsonRootName(value = "system")
class JsonSerializablePersonData implements JsonSerializableData {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePersonData} with the given persons.
     */
    @JsonCreator
    public JsonSerializablePersonData(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyData} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePersonData}.
     */
    public JsonSerializablePersonData(ReadOnlyData<Person> source) {
        persons.addAll(source.getListOfElements().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    @Override
    public Data toModelType() throws IllegalValueException {
        Data<Person> persons = new Data();
        for (JsonAdaptedPerson jsonAdaptedPerson : this.persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (persons.hasUniqueElement(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            persons.addUniqueElement(person);
        }
        return persons;
    }
}
