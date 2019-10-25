package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.financetracker.MonthlyLimit;

/**
 * Sets a monthly limit to the finance tracker.
 */
public class SetMonthlyLimitCommand extends Command {

    public static final String COMMAND_WORD = "set-limit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the monthly spending limit in the finance "
            + "tracker with the value input by the user. "
            + "Existing monthly limit will be overwritten by new input value.\n"
            + "Parameters: "
            + "[" + PREFIX_MONEY + "AMOUNT] "
            + "Example: " + COMMAND_WORD
            + PREFIX_MONEY + "500.0";

    public static final String MESSAGE_SUCCESS = "New limit set: %1$s";

    public static final String MESSAGE_SET_LIMIT_SUCCESS = "Edited installment: %1$s";
    public static final String MESSAGE_NOT_SET = "At least one value to set must be provided.";

    public static final String MESSAGE_INVERSE_SUCCESS_RESET = "Monthly limit has been reset";

    public static final boolean HAS_INVERSE = true;

    private MonthlyLimit originalLimit;
    private final MonthlyLimit toSet;

    /**
     * Creates a {@code SetMonthlyLimitCommand} to add the specified (@code MonthlyLimit}.
     */
    public SetMonthlyLimitCommand(MonthlyLimit monthlyLimit) {
        requireNonNull(monthlyLimit);
        toSet = monthlyLimit;
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

        model.setMonthlyLimit(toSet);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSet));
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

        if (!originalLimit.equals(null)) {
            model.setMonthlyLimit(originalLimit);
        } else {
            model.setMonthlyLimit(new MonthlyLimit("0.0"));
        }

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_RESET, originalLimit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SetMonthlyLimitCommand)
                && toSet.equals(((SetMonthlyLimitCommand) other).toSet);
    }
}
