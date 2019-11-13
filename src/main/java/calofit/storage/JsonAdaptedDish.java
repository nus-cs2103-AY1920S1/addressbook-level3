package calofit.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import calofit.commons.exceptions.IllegalValueException;
import calofit.model.dish.Calorie;
import calofit.model.dish.Dish;
import calofit.model.dish.Name;
import calofit.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Dish}.
 */
class JsonAdaptedDish {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Dish's %s field is missing!";

    private final String name;
    private final int calories;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDish} with the given dish details.
     */
    @JsonCreator
    public JsonAdaptedDish(@JsonProperty("name") String name,
                           @JsonProperty("calories") int calories,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.calories = calories;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Dish} into this class for Jackson use.
     */
    public JsonAdaptedDish(Dish source) {
        name = source.getName().fullName;
        calories = source.getCalories().getValue();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted dish object into the model's {@code Dish} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted dish.
     */
    public Dish toModelType() throws IllegalValueException {
        final List<Tag> dishTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            dishTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        if (Calorie.tryParseCalorie(String.valueOf(calories)).isEmpty()) {
            throw new IllegalValueException(Calorie.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Calorie modelCalories = new Calorie(calories);

        final Set<Tag> modelTags = new HashSet<>(dishTags);
        return new Dish(modelName, modelCalories, modelTags);
    }

}
