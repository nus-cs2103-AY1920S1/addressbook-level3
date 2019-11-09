package seedu.address.logic.commands.sidebar;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * Enters the itinerary page of travel pal.
 */
public class EnterExpenseManagerCommand extends Command {
    public static final String COMMAND_WORD = "expense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the expense manager page of TravelPal.";

    public static final String MESSAGE_SUCCESS = "Entered the expense manager screen.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setPageStatus(model.getPageStatus()
                .withResetExpense()
                .withNewPageType(PageType.EXPENSE_MANAGER));

        return new CommandResult(MESSAGE_SUCCESS, true);
    }
}
