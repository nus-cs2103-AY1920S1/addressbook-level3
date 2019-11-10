package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.installment.InstallmentDescription;
import seedu.jarvis.model.finance.installment.InstallmentMoneyPaid;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;
import seedu.jarvis.storage.history.commands.finance.JsonAdaptedSetInstallmentCommand;

/**
 * Adds an installment to the finance tracker.
 */
public class SetInstallmentCommand extends Command {

    public static final String COMMAND_WORD = "add-install";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Jarvis will add an installment to the finance tracker."
            + " \nParameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MONEY + "MONEY " + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Netflix subscription "
            + PREFIX_MONEY + "13.50";

    public static final String MESSAGE_COMMAND_SYNTAX = "Command format: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MONEY + "MONEY";
    public static final String MESSAGE_DESCRIPTION_ERROR = MESSAGE_COMMAND_SYNTAX + "\n"
            + InstallmentDescription.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_MONEY_ERROR = MESSAGE_COMMAND_SYNTAX + "\n"
            + InstallmentMoneyPaid.MESSAGE_CONSTRAINTS;

    public static final String MESSAGE_SUCCESS = "Jarvis has added your installment! \n%1$s";
    public static final String MESSAGE_SUCCESS_WITH_WARNING = "Jarvis has added this installment, but please note that "
            + "there already exists a similar installment in the finance tracker!\nInstallment added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_INSTALLMENT = "This installment already exists!";

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Jarvis has removed this installment: %1$s";
    public static final String MESSAGE_INVERSE_INSTALLMENT_NOT_FOUND = "Installment was already deleted: %1$s";

    public static final String MESSAGE_ALMOST_TO_LIMIT = "Note: You're almost at your spending limit for"
            + " the month!\n" + MESSAGE_SUCCESS;
    public static final String MESSAGE_OVERSPEND = "Note: You've reached your spending limit this month!"
            + " Please change your monthly limit with the command 'set-limit'.\n" + MESSAGE_SUCCESS;

    public static final String MESSAGE_ALMOST_TO_LIMIT_SIMILAR = "Note: You're almost at your spending limit for"
            + " the month!\n" + MESSAGE_SUCCESS_WITH_WARNING;
    public static final String MESSAGE_OVERSPEND_SIMILAR = "Note: You've reached your spending limit this month!"
            + " Please change your monthly limit with the command 'set-limit'.\n" + MESSAGE_SUCCESS_WITH_WARNING;

    public static final boolean HAS_INVERSE = true;

    private final Installment toAdd;

    /**
     * Creates a {@code SetInstallmentCommand} to add the specified (@code Installment}.
     */
    public SetInstallmentCommand(Installment installment) {
        requireNonNull(installment);
        toAdd = installment;
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
     * Gets the {@code Installment} to be added.
     *
     * @return {@code Installment} to be added.
     */
    public Installment getAddedInstallment() {
        return toAdd;
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
     * Adds {@code Installment} to the finance tracker, if the installment does not already exist in the
     * finance tracker.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that installment was added successfully.
     * @exception CommandException if there is already an {@code Installment} matching the installment to be
     * added to the finance tracker.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasInstallment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INSTALLMENT);
        }

        boolean doesSimilarInstallmentExist = model.hasSimilarInstallment(toAdd);
        model.addInstallment(toAdd);
        model.setViewStatus(ViewType.LIST_FINANCE);

        boolean hasSpendingLimit = model.getMonthlyLimit().isPresent();

        if (doesSimilarInstallmentExist) {
            boolean isReachingLimit = model.calculateRemainingAmount() < 50.0 && model.calculateRemainingAmount() > 0;
            boolean hasExceededLimit = model.calculateRemainingAmount() <= 0;

            if (hasSpendingLimit && isReachingLimit) {
                return new CommandResult(String.format(MESSAGE_ALMOST_TO_LIMIT_SIMILAR, toAdd), true);
            } else if (hasSpendingLimit && hasExceededLimit) {
                return new CommandResult(String.format(MESSAGE_OVERSPEND_SIMILAR, toAdd), true);
            } else {
                return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_WARNING, toAdd), true);
            }
        }

        if (hasSpendingLimit) {
            boolean isReachingLimit = model.calculateRemainingAmount() < 50.0 && model.calculateRemainingAmount() > 0;
            boolean hasExceededLimit = model.calculateRemainingAmount() <= 0;
            if (isReachingLimit) {
                return new CommandResult(String.format(MESSAGE_ALMOST_TO_LIMIT, toAdd), true);
            } else if (hasExceededLimit) {
                return new CommandResult(String.format(MESSAGE_OVERSPEND, toAdd), true);
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), true);
    }

    /**
     * Deletes {@code Installment} from the finance tracker that was added by this command's execution
     * if the installment is still in the finance tracker
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} that installment was removed if installment was in the finance tracker,
     * else {@code CommandResult} that the installment was already not in the finance tracker
     * @throws CommandException If installment to be removed is not found in the finance tracker
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasInstallment(toAdd)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_INSTALLMENT_NOT_FOUND, toAdd));
        }

        model.deleteInstallment(toAdd);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_DELETE, toAdd));
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedSetInstallmentCommand(this);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetInstallmentCommand // instanceof handles nulls
                && toAdd.equals(((SetInstallmentCommand) other).toAdd));
    }
}
