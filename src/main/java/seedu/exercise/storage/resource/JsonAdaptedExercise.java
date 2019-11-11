package seedu.exercise.storage.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.storage.JsonAdaptedMuscle;

/**
 * Jackson-friendly version of {@link Exercise}.
 */
public class JsonAdaptedExercise extends JsonAdaptedResource<Exercise> {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Exercise's %s field is missing!";

    private final String name;
    private final String date;
    private final String calories;
    private final String quantity;
    private final String unit;
    private final List<JsonAdaptedMuscle> muscles = new ArrayList<>();
    private final Map<String, String> customProperties = new TreeMap<>();

    /**
     * Constructs a {@code JsonAdaptedExercise} with the given exercise details.
     */
    @JsonCreator
    public JsonAdaptedExercise(@JsonProperty("name") String name, @JsonProperty("date") String date,
                               @JsonProperty("calories") String calories, @JsonProperty("quantity") String quantity,
                               @JsonProperty("unit") String unit,
                               @JsonProperty("muscles") List<JsonAdaptedMuscle> muscles,
                               @JsonProperty("customProperties") Map<String, String> customProperties) {
        this.name = name;
        this.date = date;
        this.calories = calories;
        this.quantity = quantity;
        this.unit = unit;
        if (muscles != null) {
            this.muscles.addAll(muscles);
        }
        if (customProperties != null) {
            this.customProperties.putAll(customProperties);
        }
    }

    /**
     * Converts a given {@code Exercise} into this class for Jackson use.
     */
    public JsonAdaptedExercise(Exercise source) {
        name = source.getName().fullName;
        date = source.getDate().toString();
        calories = source.getCalories().toString();
        quantity = source.getQuantity().toString();
        unit = source.getUnit().unit;
        muscles.addAll(source.getMuscles().stream()
            .map(JsonAdaptedMuscle::new)
            .collect(Collectors.toList()));
        customProperties.putAll(source.getCustomPropertiesMap());
    }

    /**
     * Converts this Jackson-friendly adapted exercise object into the model's {@code Exercise} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise.
     */
    public Exercise toModelType() throws IllegalValueException {
        final List<Muscle> personMuscles = new ArrayList<>();
        for (JsonAdaptedMuscle muscle : muscles) {
            personMuscles.add(muscle.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (calories == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Calories.class.getSimpleName()));
        }
        if (!Calories.isValidCalories(calories)) {
            throw new IllegalValueException(Calories.MESSAGE_CONSTRAINTS);
        }
        final Calories modelCalories = new Calories(calories);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (unit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Unit.class.getSimpleName()));
        }
        if (!Unit.isValidUnit(unit)) {
            throw new IllegalValueException(Unit.MESSAGE_CONSTRAINTS);
        }
        final Unit modelUnit = new Unit(unit);

        final Set<Muscle> modelMuscles = new HashSet<>(personMuscles);
        final Map<String, String> modelCustomProperties = new TreeMap<>(customProperties);
        return new Exercise(modelName, modelDate, modelCalories, modelQuantity, modelUnit, modelMuscles,
            modelCustomProperties);
    }

}
