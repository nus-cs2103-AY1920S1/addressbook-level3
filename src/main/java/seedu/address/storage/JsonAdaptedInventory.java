package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.inventory.Inventory;

/**
 * Jackson friendly version of {@code Inventory}.
 */
public class JsonAdaptedInventory {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Inventory's %s field is missing!";

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedInventory} with the given JsonAdaptedInventory details.
     */
    @JsonCreator
    public JsonAdaptedInventory(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Inventory} into this class for Jackson use.
     */
    public JsonAdaptedInventory(Inventory source) {
        this.name = source.getName();
    }

    /**
     * Converts this Jackson-friendly adapted day object into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted day.
     */
    public Inventory toModelType() throws IllegalValueException {

        /*
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }*/

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        return new Inventory(name);
    }
}
