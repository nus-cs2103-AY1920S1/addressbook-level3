package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Budget;

/**
 * Sets the budget for the BankAccount.
 */
public class SetCommand extends Command {
    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a budget to the bank account. "
            + "Parameters: "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DEADLINE "
            + PREFIX_TAG + "TAG "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "100 "
            + PREFIX_DATE + "2019/01/01 "
            + PREFIX_TAG + "food expenditure ";

    public static final String MESSAGE_SUCCESS = "New budget set: %1$s";

    private Budget budget;

    public SetCommand(Budget budget) {
        this.budget = budget;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, budget));
    }



}
