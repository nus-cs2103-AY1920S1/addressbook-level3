package seedu.exercise.storage;

import static seedu.exercise.model.property.PropertyBook.getCustomProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.PropertyBook;

/**
 * A PropertyBook that is serializable to JSON format.
 */
@JsonRootName(value = "propertybook")
public class JsonSerializablePropertyBook {

    private final List<JsonAdaptedCustomProperty> customProperties = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePropertyBook} from the given parameters.
     *
     * @param customProperties a list containing {@code JsonAdaptedCustomProperty}
     */
    @JsonCreator
    public JsonSerializablePropertyBook(@JsonProperty("customProperties") List<JsonAdaptedCustomProperty>
                                            customProperties) {
        if (customProperties != null) {
            this.customProperties.addAll(customProperties);
        }
    }

    /**
     * Converts a given {@code PropertyBook} object into this class for Jackson use.
     *
     * @param source the {@code PropertyBook} object that is intended to be converted
     */
    public JsonSerializablePropertyBook(PropertyBook source) {
        Set<CustomProperty> sourceCustomProperties = getCustomProperties();
        customProperties.addAll(sourceCustomProperties.stream()
            .map(JsonAdaptedCustomProperty::new)
            .collect(Collectors.toList()));
    }

    /**
     * Returns a {@code Set<CustomProperty>} object meant for a {@code PropertyBook}.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    private Set<CustomProperty> toModelCustomProperties() throws IllegalValueException {
        Set<CustomProperty> modelCustomProperties = new HashSet<>();
        for (JsonAdaptedCustomProperty property : customProperties) {
            modelCustomProperties.add(property.toModelType());
        }
        return modelCustomProperties;
    }

    /**
     * Converts this Jackson-friendly PropertyBook into the model's {@code PropertyBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public PropertyBook toModelManager() throws IllegalValueException {
        Set<CustomProperty> modelCustomProperties = toModelCustomProperties();
        return new PropertyBook(modelCustomProperties);
    }
}
