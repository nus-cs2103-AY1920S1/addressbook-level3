package seedu.address.logic.commands.sidebar;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * Placeholder.
 */
public class EnterDayPageCommand extends Command {
    public static final String COMMAND_WORD = "days";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the days page of TravelPal.";

    public static final String MESSAGE_SUCCESS = "Entered the days screen.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setPageStatus(model.getPageStatus()
                .withResetEvent()
                .withResetDay()
                .withNewPageType(PageType.OVERALL_VIEW));

        return new CommandResult(MESSAGE_SUCCESS, true);
    }

}
