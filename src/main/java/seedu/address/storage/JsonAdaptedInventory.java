package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.Name;

/**
 * Jackson friendly version of {@code Inventory}.
 */
public class JsonAdaptedInventory {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Inventory's %s field is missing!";

    private final String name;
    private final boolean isDone;
    private final int eventInstances;

    /**
     * Constructs a {@code JsonAdaptedInventory} with the given JsonAdaptedInventory details.
     */
    @JsonCreator
    public JsonAdaptedInventory(@JsonProperty("name") String name, @JsonProperty("isDone") boolean isDone,
                                @JsonProperty("eventInstances") int eventInstances) {
        this.name = name;
        this.isDone = isDone;
        this.eventInstances = eventInstances;
    }

    /**
     * Converts a given {@code Inventory} into this class for Jackson use.
     */
    public JsonAdaptedInventory(Inventory source) {
        this.name = source.getName().fullName;
        this.isDone = source.getIsDone();
        this.eventInstances = source.getEventInstances();
    }

    /**
     * Converts this Jackson-friendly adapted day object into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted day.
     */
    public Inventory toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        return new Inventory(modelName, isDone, eventInstances);
    }
}
