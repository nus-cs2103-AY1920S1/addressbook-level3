package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.inventory.InvName;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.Price;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link Inventory}.
 */
public class JsonAdaptedInventory {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Inventory's %s field is missing!";

    private final Task task;
    private final InvName name;
    /*private final memIndex memID;*/
    private final Price price;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedInventory(@JsonProperty("name") InvName name, @JsonProperty("task") Task task,
                           @JsonProperty("price") Price price) {
        this.name = name;
        this.task = task;
        this.price = price;
    }

    /**
     * Converts a given {@code Inventory} into this class for Jackson use.
     */
    public JsonAdaptedInventory(Inventory source) {
        name = source.getName();
        task = source.getTask();
        price = source.getPrice();
    }

    /**
     * Converts this Jackson-friendly adapted inventory object into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted inventory.
     */
    public Inventory toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "InvName"));
        }
        if (!InvName.isValidName(name.toString())) {
            throw new IllegalValueException(InvName.MESSAGE_CONSTRAINTS);
        }
        if (task == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "task"));
        }
        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "price"));
        }
        if (!Price.isValidName(price.getPrice())) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }

        final InvName modelName = new InvName(name.toString());
        final Task modelTask = new Task(task.getName(), task.getTaskStatus(), task.getTags());
        final Price modelPrice = new Price(price.getPrice());
        return new Inventory(modelTask, modelName, modelPrice);
    }
}
