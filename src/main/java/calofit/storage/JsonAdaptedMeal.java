package calofit.storage;

import calofit.model.meal.Meal;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import calofit.commons.exceptions.IllegalValueException;
import calofit.model.meal.Name;
import calofit.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Meal}.
 */
class JsonAdaptedMeal {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meal's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMeal} with the given meal details.
     */
    @JsonCreator
    public JsonAdaptedMeal(@JsonProperty("name") String name,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Meal} into this class for Jackson use.
     */
    public JsonAdaptedMeal(Meal source) {
        name = source.getName().fullName;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted meal object into the model's {@code Meal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meal.
     */
    public Meal toModelType() throws IllegalValueException {
        final List<Tag> mealTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            mealTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Set<Tag> modelTags = new HashSet<>(mealTags);
        return new Meal(modelName, modelTags);
    }

}
