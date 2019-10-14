package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.recipe.Recipe;

/**
 * An Immutable Exercise Catalogue that is serializable to JSON format.
 */
@JsonRootName(value = "exercisecatalogue")
class JsonSerializableExerciseCatalogue {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate recipe(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableExerciseCatalogue} with the given persons.
     */
    @JsonCreator
    public JsonSerializableExerciseCatalogue(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
    * Converts a given {@code ReadOnlyDukeCooks} into this class for Jackson use.
    *
    * @param source future changes to this will not affect the created {@code JsonSerializableExerciseCatalogue}.
    */
    public JsonSerializableExerciseCatalogue(ReadOnlyDukeCooks source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
    *  Converts this Exercise Catalogue into the model's {@code DukeCooks} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public DukeCooks toModelType() throws IllegalValueException {
        DukeCooks dukeCooks = new DukeCooks();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Recipe recipe = jsonAdaptedPerson.toModelType();
            if (dukeCooks.hasPerson(recipe)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            dukeCooks.addPerson(recipe);
        }
        return dukeCooks;
    }
}
