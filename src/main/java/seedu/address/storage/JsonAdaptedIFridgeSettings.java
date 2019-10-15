package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.IFridgeSettings;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.food.Food;

/**
 * Jackson-friendly version of {@link Food}.
 */
public class JsonAdaptedIFridgeSettings {
    private final String defaultNumberOfDays;
    private final String defaultSortMethod;
    private final String defaultListDisplay;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedIFridgeSettings(
            @JsonProperty("defaultNumberOfDays") String defaultNumberOfDays,
            @JsonProperty("defaultSortMethod") String defaultSortMethod,
            @JsonProperty("defaultListDisplay") String defaultListDisplay) {
        this.defaultNumberOfDays = defaultNumberOfDays;
        this.defaultSortMethod = defaultSortMethod;
        this.defaultListDisplay = defaultListDisplay;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedIFridgeSettings(IFridgeSettings iFridgeSettings) {
        defaultNumberOfDays = iFridgeSettings.getNumberOfDays();
        defaultSortMethod = iFridgeSettings.getSortMethod();
        defaultListDisplay = iFridgeSettings.getListDisplay();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public IFridgeSettings toModelType() throws IllegalValueException {
        return new IFridgeSettings(defaultNumberOfDays, defaultSortMethod, defaultListDisplay);
    }
}
