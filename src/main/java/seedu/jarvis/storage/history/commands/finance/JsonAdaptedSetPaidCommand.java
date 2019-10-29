package seedu.jarvis.storage.history.commands.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.finance.SetPaidCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.finance.JsonAdaptedPurchase;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link SetPaidCommand}.
 */
public class JsonAdaptedSetPaidCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_PURCHASE = "Invalid purchase.";

    private final JsonAdaptedPurchase toAdd;

    /**
     * Constructs a {@code JsonAdaptedSetPaidCommand} with the given {@code Purchase} objects to add.
     *
     * @param toAdd {@code Purchase} object in Json format.
     */
    @JsonCreator
    public JsonAdaptedSetPaidCommand(@JsonProperty("toAdd") JsonAdaptedPurchase toAdd) {
        this.toAdd = toAdd;
    }

    /**
     * Converts a given {@code SetPaidCommand} into this class for Jackson use.
     *
     * @param setPaidCommand {@code SetPaidCommand} to be used to construct the {@code JsonAdaptedSetPaidCommand}.
     */
    public JsonAdaptedSetPaidCommand(SetPaidCommand setPaidCommand) {
        this.toAdd = new JsonAdaptedPurchase(setPaidCommand.getAddedPurchase());
    }

    /**
     * Converts this Jackson-friendly adapted {@code SetPaidCommand} object into the model's {@code SetPaidCommand}
     * object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code SetPaidCommand}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted {@code SetPaidCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (toAdd == null) {
            throw new IllegalValueException(MESSAGE_INVALID_PURCHASE);
        }
        return new SetPaidCommand(toAdd.toModelType());
    }
}
