package seedu.address.logic.commands.conditioncommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminders.conditions.ClassCondition;



/**
 * Creates a ClassCondition when executed.
 */
public class AddClassConditionCommand extends Command {

    public static final String COMMAND_WORD = "addClassCondition";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Class Condition to the conditions list.\n"
            + "Parameters: CLASS_TYPE (must be expense, income, wish, autoExpense or budget, case-insensitive.)"
            + "CLASS_TYPE"
            + "Example: " + COMMAND_WORD + " "
            + " expense \n";

    public static final String MESSAGE_SUCCESS = "New ClassCondition added: %1$s";

    private String classType;

    public AddClassConditionCommand(String classType) {
        this.classType = classType;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ClassCondition condition = new ClassCondition(classType);
        model.addCondition(condition);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, condition));
        commandResult.showConditionPanel();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddClassConditionCommand // instanceof handles nulls
                && classType.equals(((AddClassConditionCommand) other).classType));
    }
}
