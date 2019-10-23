package seedu.travezy.financialtracker.commands;

import seedu.travezy.financialtracker.model.Model;
import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.ui.PageManager;
import seedu.travezy.ui.PageType;

/**
 * Switch to another page.
 */
public class GoToCommand extends Command {

    public static final String COMMAND_WORD = "goto";
    public static final String MESSAGE_CHANGE_SUCCESS = "Switching to %s page";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Goes to the page specified.\n"
            + "Parameters: 'address_book', 'calendar', 'diary', 'financial_tracker', 'itinerary', 'main'\n"
            + "Example: " + COMMAND_WORD + " address_book";

    private PageType pageType;

    public GoToCommand(PageType pageType) {
        this.pageType = pageType;
    }

    @Override
    public CommandResult execute(Model model) {
        PageManager.getPage(pageType);
        String respondMessage = String.format(MESSAGE_CHANGE_SUCCESS, pageType.toString().toLowerCase());
        return new CommandResult(respondMessage, false, false);
    }
}
