package seedu.jarvis.storage.history.commands.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.finance.RemoveInstallmentCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.finance.JsonAdaptedInstallment;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link RemoveInstallmentCommand}.
 */
public class JsonAdaptedRemoveInstallmentCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_INDEX = "Invalid index.";

    private final JsonAdaptedIndex index;
    private final JsonAdaptedInstallment installment;

    /**
     * Constructs a {@code JsonAdaptedRemoveInstallmentCommand} with the given {@code Index} of the installment to
     * delete, and {@code JsonAdaptedInstallment} that was deleted.
     * @param index {@code Index} of the {@code Installment} to be deleted.
     * @param installment {@code Installment} that was deleted, which may be null.
     */
    @JsonCreator
    public JsonAdaptedRemoveInstallmentCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                               @JsonProperty("installment") JsonAdaptedInstallment installment) {
        this.index = index;
        this.installment = installment;
    }

    /**
     * Converts a given {@code RemoveInstallmentCommand} into this class for Jackson use.
     *
     * @param removeInstallmentCommand {@code RemoveInstallmentCommand} to be used to construct the
     * {@code JsonAdaptedRemoveInstallmentCommand}.
     */
    public JsonAdaptedRemoveInstallmentCommand(RemoveInstallmentCommand removeInstallmentCommand) {
        index = new JsonAdaptedIndex(removeInstallmentCommand.getTargetIndex());
        installment = removeInstallmentCommand.getDeletedInstallment().map(JsonAdaptedInstallment::new).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedRemoveInstallmentCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (index == null) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return new RemoveInstallmentCommand(
                index.toModelType(),
                installment != null ? installment.toModelType() : null);
    }
}
