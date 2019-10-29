package seedu.jarvis.storage.history.commands.finance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.finance.SetMonthlyLimitCommand;
import seedu.jarvis.model.finance.MonthlyLimit;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link SetMonthlyLimitCommand}.
 */
public class JsonAdaptedSetMonthlyLimitCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_LIMIT = "Invalid monthly limit.";

    private final String originalLimit;
    private final String updatedLimit;

    /**
     * Constructs the {@code JsonAdaptedSetMonthlyLimitCommand} with the original and updated limits.
     *
     * @param originalLimit Original limit before the update.
     * @param updatedLimit Updated limit.
     */
    @JsonCreator
    public JsonAdaptedSetMonthlyLimitCommand(@JsonProperty("originalLimit") String originalLimit,
                                             @JsonProperty("updatedLimit") String updatedLimit) {
        this.originalLimit = originalLimit;
        this.updatedLimit = updatedLimit;
    }

    /**
     * Converts a given {@code SetMonthlyLimitCommand} into this class for Jackson use.
     *
     * @param setMonthlyLimitCommand {@code SetMonthlyLimitCommand} to be used to construct the
     * {@code JsonAdaptedSetMonthlyLimitCommand}.
     */
    public JsonAdaptedSetMonthlyLimitCommand(SetMonthlyLimitCommand setMonthlyLimitCommand) {
        originalLimit = setMonthlyLimitCommand.getOriginalLimit().map(MonthlyLimit::toString).orElse(null);
        updatedLimit = setMonthlyLimitCommand.getUpdatedLimit().toString();
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedSetMonthlyLimitCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        boolean isValidUpdatedLimit = updatedLimit != null && MonthlyLimit.isValidAmount(updatedLimit);
        boolean isValidOriginalLimit = originalLimit == null || MonthlyLimit.isValidAmount(originalLimit);

        if (!isValidUpdatedLimit || !isValidOriginalLimit) {
            throw new IllegalValueException(MESSAGE_INVALID_LIMIT);
        }

        return new SetMonthlyLimitCommand(
                new MonthlyLimit(updatedLimit),
                originalLimit != null ? new MonthlyLimit(originalLimit) : null);
    }
}
