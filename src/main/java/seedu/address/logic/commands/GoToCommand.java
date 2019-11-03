package seedu.address.logic.commands;

import seedu.address.ui.PageManager;
import seedu.address.ui.PageType;

/**
 * GoTo a specific page with user input entered by the user.
 */
public class GoToCommand<T> extends Command<T> {
    public static final String COMMAND_WORD = "goto";
    // todo: fix bug properly
    public static final String MESSAGE_CHANGE_SUCCESS = "";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Goes to the page specified.\n"
            + "Parameters: 'address_book', 'calendar', 'diary', 'financial_tracker', 'itinerary', 'main'\n"
            + "Example: " + COMMAND_WORD + " address_book";

    private PageType pageType;

    public GoToCommand(PageType pageType) {
        this.pageType = pageType;
    }

    @Override
    public CommandResult execute(T model) {
        PageManager.getPage(pageType);
        String respondMessage = String.format(MESSAGE_CHANGE_SUCCESS, pageType.toString().toLowerCase());
        return new CommandResult(respondMessage, false, false, true);
    }
}
