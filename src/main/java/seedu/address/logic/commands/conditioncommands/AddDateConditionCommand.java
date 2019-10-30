package seedu.address.logic.commands.conditioncommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Date;
import seedu.address.model.reminders.conditions.DateCondition;

/**
 * Creates a Date Condition when executed.
 */
public class AddDateConditionCommand extends Command {
    public static final String COMMAND_WORD = "addDateCondition";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Date Condition to the conditions list. "
            + "Parameters: "
            + PREFIX_DATE + "START"
            + PREFIX_DATE + "END"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2019-10-08"
            + PREFIX_DATE + "2019-11-08 \n";

    public static final String MESSAGE_SUCCESS = "New DateCondition added: %1$s";

    private Date start;
    private Date end;


    public AddDateConditionCommand(Date date1, Date date2) {
        if (date1.getDate().isBefore(date2.getDate())) {
            start = date1;
            end = date2;
        } else {
            start = date2;
            end = date1;
        }
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        DateCondition condition = new DateCondition(start, end);
        model.addCondition(condition);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, condition));
        commandResult.showConditionPanel();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDateConditionCommand // instanceof handles nulls
                && start.equals(((AddDateConditionCommand) other).start)
                && end.equals(((AddDateConditionCommand) other).end));
    }

}
