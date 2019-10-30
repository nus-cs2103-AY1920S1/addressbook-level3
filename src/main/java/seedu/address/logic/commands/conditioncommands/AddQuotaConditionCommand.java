package seedu.address.logic.commands.conditioncommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminders.conditions.QuotaCondition;

/**
 * Creates a QuotaCondition when executed.
 */
public class AddQuotaConditionCommand extends Command {

    public static final String COMMAND_WORD = "addQuotaCondition";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a QuotaCondition to the conditions list. \n"
            + "Parameters: "
            + "QUOTA (In Dollars)\n"
            + "Example: "
            + COMMAND_WORD
            + "100\n";

    public static final String MESSAGE_SUCCESS = "New QuotaCondition added: %1$s";

    private double quota;

    public AddQuotaConditionCommand(double quota) {
        this.quota = quota;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        QuotaCondition condition = new QuotaCondition(quota);
        model.addCondition(condition);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, condition));
        commandResult.showConditionPanel();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddQuotaConditionCommand // instanceof handles nulls
                && quota == (((AddQuotaConditionCommand) other).quota));
    }
}
