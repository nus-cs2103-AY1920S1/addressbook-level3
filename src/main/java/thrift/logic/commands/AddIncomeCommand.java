package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
import thrift.model.transaction.Income;

/**
 * Adds an expense transaction to the THRIFT.
 */
public class AddIncomeCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "add_income";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an income transaction to THRIFT. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME DESCRIPTION "
            + CliSyntax.PREFIX_COST + "COST "
            + "[" + CliSyntax.PREFIX_REMARK + "REMARK] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Bursary "
            + CliSyntax.PREFIX_COST + "500 "
            + CliSyntax.PREFIX_REMARK + "For studying well "
            + CliSyntax.PREFIX_TAG + "Award ";

    public static final String MESSAGE_SUCCESS = "New income added: %1$s";

    private final Income toAdd;

    /**
     * Creates an AddIncomeCommand to add the specified {@code Income}
     */
    public AddIncomeCommand(Income income) {
        requireNonNull(income);
        toAdd = income;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.addIncome(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIncomeCommand // instanceof handles nulls
                && toAdd.equals(((AddIncomeCommand) other).toAdd));
    }

    @Override
    public void undo(Model model) {
        requireNonNull(model);
        model.deleteLastTransaction();
    }

    @Override
    public void redo(Model model) {
        requireNonNull(model);
        model.addIncome(toAdd);
    }
}
