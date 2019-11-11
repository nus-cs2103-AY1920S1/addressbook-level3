package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;
import static seedu.jarvis.logic.parser.finance.FinanceParserUtil.AMOUNT_CLOSE_TO_LIMIT;
import static seedu.jarvis.logic.parser.finance.FinanceParserUtil.AMOUNT_LIMIT_REACHED;

import java.util.Objects;
import java.util.Optional;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.finance.MonthlyLimit;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedSetMonthlyLimitCommand;

/**
 * Sets a monthly limit to the finance tracker.
 */
public class SetMonthlyLimitCommand extends Command {

    public static final String COMMAND_WORD = "set-limit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Jarvis will set this as the monthly spending limit in "
            + "the finance tracker. "
            + "Existing monthly limit will be overwritten by new input value.\n"
            + "Parameters: "
            + PREFIX_MONEY + "AMOUNT" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONEY + "500.0";

    public static final String COMMAND_SYNTAX = "Command format: " + COMMAND_WORD + " "
            + PREFIX_MONEY + "MONEY";
    public static final String MESSAGE_MONEY_ERROR = COMMAND_SYNTAX + "\n"
            + MonthlyLimit.MESSAGE_CONSTRAINTS;

    public static final String MESSAGE_SUCCESS = "Jarvis has set your new limit: %1$s";

    public static final String MESSAGE_INVERSE_SUCCESS_RESET = "Monthly limit has been reset";

    public static final String MESSAGE_ALMOST_TO_LIMIT = "Note: You're almost at your spending limit for"
            + " the month!\n" + MESSAGE_SUCCESS;
    public static final String MESSAGE_OVERSPEND = "Note: You've reached your spending limit this month!"
            + " Please change your monthly limit with the command 'set-limit'.\n" + MESSAGE_SUCCESS;

    public static final boolean HAS_INVERSE = true;

    private MonthlyLimit originalLimit;
    private final MonthlyLimit updatedLimit;

    /**
     * Creates a {@code SetMonthlyLimitCommand} to update the specified (@code MonthlyLimit} and the original
     * {@code MonthlyLimit}.
     */
    public SetMonthlyLimitCommand(MonthlyLimit updatedLimit, MonthlyLimit originalLimit) {
        requireNonNull(updatedLimit);
        this.updatedLimit = updatedLimit;
        this.originalLimit = originalLimit;
    }

    /**
     * Creates a {@code SetMonthlyLimitCommand} to update the specified (@code MonthlyLimit}.
     */
    public SetMonthlyLimitCommand(MonthlyLimit updatedLimit) {
        this(updatedLimit, null);
    }

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Gets the original {@code MonthlyLimit} before the update wrapped in an {@code Optional}.
     *
     * @return original {@code MonthlyLimit} before the update wrapped in an {@code Optional}.
     */
    public Optional<MonthlyLimit> getOriginalLimit() {
        return Optional.ofNullable(originalLimit);
    }

    /**
     * Gets the updated {@code MonthlyLimit}.
     *
     * @return updated {@code MonthlyLimit}.
     */
    public MonthlyLimit getUpdatedLimit() {
        return updatedLimit;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Sets {@code MonthlyLimit} to the finance tracker.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that monthly limit was set successfully.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getMonthlyLimit().isPresent()) {
            originalLimit = model.getMonthlyLimit().get();
        }

        model.setMonthlyLimit(updatedLimit);
        model.setViewStatus(ViewType.LIST_FINANCE);

        boolean isReachingLimit = model.calculateRemainingAmount() < AMOUNT_CLOSE_TO_LIMIT
                && model.calculateRemainingAmount() > AMOUNT_LIMIT_REACHED;
        boolean hasExceededLimit = model.calculateRemainingAmount() <= AMOUNT_LIMIT_REACHED;
        if (isReachingLimit) {
            return new CommandResult(String.format(MESSAGE_ALMOST_TO_LIMIT, updatedLimit), true);
        } else if (hasExceededLimit) {
            return new CommandResult(String.format(MESSAGE_OVERSPEND, updatedLimit), true);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedLimit), true);
    }

    /**
     * Reverts the {@code MonthlyLimit} from the finance tracker that was set by this command's execution
     * if there was a monthly limit that existed before in the finance tracker
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} that MonthlyLimit was reset if it existed before,
     * else {@code CommandResult} that there was no MonthlyLimit and it would be set to null
     * @throws CommandException
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        model.setMonthlyLimit(originalLimit);

        model.setViewStatus(ViewType.LIST_FINANCE);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_RESET, originalLimit), true);
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedSetMonthlyLimitCommand(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SetMonthlyLimitCommand)) {
            return false;
        }

        SetMonthlyLimitCommand command = (SetMonthlyLimitCommand) other;
        return updatedLimit.equals(command.updatedLimit) && Objects.equals(originalLimit, command.originalLimit);
    }
}
