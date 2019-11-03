package seedu.exercise.storage;

import static seedu.exercise.model.property.PropertyBook.MESSAGE_DUPLICATE_NAME_OR_PREFIX;

import java.util.ArrayList;
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
     */
    public JsonSerializablePropertyBook() {
        Set<CustomProperty> sourceCustomProperties = PropertyBook.getInstance().getCustomProperties();
        customProperties.addAll(sourceCustomProperties.stream()
            .map(JsonAdaptedCustomProperty::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly PropertyBook into the model's {@code PropertyBook} object.
     * If any clashes in property name or prefix are found, a default PropertyBook will be loaded instead.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public PropertyBook toModelBook() throws IllegalValueException {
        PropertyBook propertyBook = PropertyBook.getInstance();
        for (JsonAdaptedCustomProperty jsonCustomProperty : customProperties) {
            CustomProperty modelProperty = jsonCustomProperty.toModelType();
            if (propertyBook.hasClashingPrefixOrName(modelProperty)) {
                propertyBook.clearCustomProperties();
                throw new IllegalValueException(MESSAGE_DUPLICATE_NAME_OR_PREFIX);
            }
            propertyBook.addCustomProperty(modelProperty);
        }
        return propertyBook;
    }
}
