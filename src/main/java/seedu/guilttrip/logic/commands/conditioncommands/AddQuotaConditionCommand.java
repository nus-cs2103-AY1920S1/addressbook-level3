package seedu.guilttrip.logic.commands.conditioncommands;

import static java.util.Objects.requireNonNull;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;

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
