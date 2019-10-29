package seedu.jarvis.storage.history.commands.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.finance.RemovePaidCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.finance.JsonAdaptedPurchase;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link RemovePaidCommand}.
 */
public class JsonAdaptedRemovePaidCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_INDEX = "Invalid index.";

    private final JsonAdaptedIndex targetIndex;
    private final JsonAdaptedPurchase purchase;

    /**
     * Converts a given {@code RemovePaidCommand} into this class for Jackson use.
     *
     * @param targetIndex {@code Index} of the purchase to be removed.
     * @param purchase {@code Purchase} that was deleted, which can be null.
     */
    @JsonCreator
    public JsonAdaptedRemovePaidCommand(@JsonProperty("targetIndex") JsonAdaptedIndex targetIndex,
                                        @JsonProperty("purchase") JsonAdaptedPurchase purchase) {
        this.targetIndex = targetIndex;
        this.purchase = purchase;
    }

    /**
     * Converts a given {@code RemovePaidCommand} into this class for Jackson use.
     *
     * @param removePaidCommand {@code RemovePaidCommand} to be used to construct the
     * {@code JsonAdaptedRemovePaidCommand}.
     */
    public JsonAdaptedRemovePaidCommand(RemovePaidCommand removePaidCommand) {
        targetIndex = new JsonAdaptedIndex(removePaidCommand.getTargetIndex());
        purchase = removePaidCommand.getDeletedPurchase().map(JsonAdaptedPurchase::new).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted {@code RemovePaidCommand} object into the model's
     * {@code RemovePaidCommand} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code RemovePaidCommand}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code RemovePaidCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (targetIndex == null) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return new RemovePaidCommand(
                targetIndex.toModelType(),
                purchase != null ? purchase.toModelType() : null);
    }
}
