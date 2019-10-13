package seedu.exercise.storage;

import static seedu.exercise.model.exercise.PropertyManager.getCustomProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.exercise.CustomProperty;
import seedu.exercise.model.exercise.PropertyManager;

/**
 * A PropertyManager that is serializable to JSON format.
 */
@JsonRootName(value = "propertymanager")
public class JsonSerializablePropertyManager {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "PropertyManager's %s field is missing!";

    private final List<String> shortNames = new ArrayList<>();
    private final List<String> fullNames = new ArrayList<>();
    private final List<JsonAdaptedCustomProperty> customProperties = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePropertyManager} from the given parameters.
     *
     * @param shortNames       a list containing strings that represent the short names of the exercise's properties
     * @param fullNames        a list containing strings that represent the full names of the exercise's properties
     * @param customProperties a list containing {@code JsonAdaptedCustomProperty}
     */
    @JsonCreator
    public JsonSerializablePropertyManager(@JsonProperty("shortNames") List<String> shortNames,
                                           @JsonProperty("fullNames") List<String> fullNames,
                                           @JsonProperty("customProperties") List<JsonAdaptedCustomProperty>
                                               customProperties) {
        if (shortNames != null) {
            this.shortNames.addAll(shortNames);
        }

        if (fullNames != null) {
            this.fullNames.addAll(fullNames);
        }

        if (customProperties != null) {
            this.customProperties.addAll(customProperties);
        }
    }

    /**
     * Converts a given {@code PropertyManager} object into this class for Jackson use.
     *
     * @param source the {@code PropertyManager} object that is intended to be converted
     */
    public JsonSerializablePropertyManager(PropertyManager source) {
        Set<Prefix> sourcePrefixes = source.getPrefixes();
        shortNames.addAll(sourcePrefixes.stream()
            .map(x -> x.getPrefixName())
            .collect(Collectors.toList()));

        Set<String> sourceFullNames = source.getFullNames();
        fullNames.addAll(sourceFullNames);

        List<CustomProperty> sourceCustomProperties = getCustomProperties();
        customProperties.addAll(sourceCustomProperties.stream()
            .map(JsonAdaptedCustomProperty::new)
            .collect(Collectors.toList()));
    }

    /**
     * Returns a {@code Set<Prefix>} object meant for a {@code PropertyManager}.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    private Set<Prefix> toModelPrefixes() throws IllegalValueException {
        Set<Prefix> modelPrefixes = new HashSet<>();
        for (String shortName : shortNames) {
            if (!CustomProperty.isValidShortName(shortName)) {
                throw new IllegalValueException(CustomProperty.SHORT_NAME_CONSTRAINTS);
            }
            modelPrefixes.add(new Prefix(shortName + "/"));
        }
        return modelPrefixes;
    }

    /**
     * Returns a {@code Set<String>} object meant for a {@code PropertyManager}.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    private Set<String> toModelFullNames() throws IllegalValueException {
        Set<String> modelFullNames = new HashSet<>();
        for (String fullName : fullNames) {
            if (!CustomProperty.isValidFullName(fullName)) {
                throw new IllegalValueException(CustomProperty.FULL_NAME_CONSTRAINTS);
            }
            modelFullNames.add(fullName);
        }
        return modelFullNames;
    }

    /**
     * Returns a {@code List<CustomProperty>} object meant for a {@code PropertyManager}.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    private List<CustomProperty> toModelCustomProperties() throws IllegalValueException {
        List<CustomProperty> modelCustomProperties = new ArrayList<>();
        for (JsonAdaptedCustomProperty property : customProperties) {
            modelCustomProperties.add(property.toModelType());
        }
        return modelCustomProperties;
    }

    /**
     * Converts this Jackson-friendly PropertyManager into the model's {@code PropertyManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public PropertyManager toModelManager() throws IllegalValueException {
        Set<Prefix> modelPrefixes = toModelPrefixes();
        Set<String> modelFullNames = toModelFullNames();
        List<CustomProperty> modelCustomProperties = toModelCustomProperties();
        return new PropertyManager(modelPrefixes, modelFullNames, modelCustomProperties);
    }
}
