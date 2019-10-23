package seedu.travezy.logic.commands;

import seedu.travezy.address.logic.commands.Command;
import seedu.travezy.address.model.AddressBookModel;
import seedu.travezy.ui.PageManager;
import seedu.travezy.ui.PageType;

/**
 * GoTo a specific page with user input entered by the user.
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
    public CommandResult execute(AddressBookModel addressBookModel) {
        PageManager.getPage(pageType);
        String respondMessage = String.format(MESSAGE_CHANGE_SUCCESS, pageType.toString().toLowerCase());
        return new CommandResult(respondMessage, false, false);
    }
}
