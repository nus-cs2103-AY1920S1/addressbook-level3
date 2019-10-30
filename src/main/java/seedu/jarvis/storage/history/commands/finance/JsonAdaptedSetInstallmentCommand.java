package seedu.jarvis.storage.history.commands.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.finance.SetInstallmentCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.finance.JsonAdaptedInstallment;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link SetInstallmentCommand}.
 */
public class JsonAdaptedSetInstallmentCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_INSTALLMENT = "Invalid installment.";

    private final JsonAdaptedInstallment installment;

    /**
     * Constructs a {@code JsonAdaptedSetInstallmentCommand} with the given {@code Installment} to add.
     *
     * @param installment {@code Installment} in Json format.
     */
    @JsonCreator
    public JsonAdaptedSetInstallmentCommand(@JsonProperty("installment") JsonAdaptedInstallment installment) {
        this.installment = installment;
    }

    /**
     * Converts a given {@code SetInstallmentCommand} into this class for Jackson use.
     *
     * @param setInstallmentCommand {@code SetInstallmentCommand} to be used to construct the
     * {@code JsonAdaptedSetInstallmentCommand}.
     */
    public JsonAdaptedSetInstallmentCommand(SetInstallmentCommand setInstallmentCommand) {
        installment = new JsonAdaptedInstallment(setInstallmentCommand.getAddedInstallment());
    }

    /**
     * Converts this Jackson-friendly adapted {@code SetInstallmentCommand} object into the model's {@code Command}
     * object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code SetInstallmentCommand}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code SetInstallmentCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (installment == null) {
            throw new IllegalValueException(MESSAGE_INVALID_INSTALLMENT);
        }
        return new SetInstallmentCommand(installment.toModelType());
    }
}
