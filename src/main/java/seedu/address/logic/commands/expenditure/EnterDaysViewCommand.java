package seedu.address.logic.commands.expenditure;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * Enters the itinerary page of travel pal.
 */
public class EnterDaysViewCommand extends Command {
    public static final String COMMAND_WORD = "showdays";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the days view of expense manager.";

    public static final String MESSAGE_SUCCESS = "Entered the days view of expense manager.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setPageStatus(model.getPageStatus()
                .withResetExpenditure()
                .withNewPageType(PageType.EXPENSE_MANAGER_DAYS));

        return new CommandResult(MESSAGE_SUCCESS, true);
    }
}
