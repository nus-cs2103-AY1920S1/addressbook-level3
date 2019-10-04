package mams.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import mams.commons.exceptions.IllegalValueException;
import mams.model.Mams;
import mams.model.ReadOnlyMams;
import mams.model.student.Person;

/**
 * An Immutable MAMS that is serializable to JSON format.
 */
@JsonRootName(value = "mams")
class JsonSerializableMams {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMams} with the given persons.
     */
    @JsonCreator
    public JsonSerializableMams(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyMams} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMams}.
     */
    public JsonSerializableMams(ReadOnlyMams source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Mams} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Mams toModelType() throws IllegalValueException {
        Mams mams = new Mams();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (mams.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            mams.addPerson(person);
        }
        return mams;
    }

}
