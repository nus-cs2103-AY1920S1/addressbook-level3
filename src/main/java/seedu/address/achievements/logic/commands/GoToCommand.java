package seedu.address.achievements.logic.commands;

import seedu.address.achievements.model.StatisticsModel;
import seedu.address.logic.commands.CommandResult;
import seedu.address.ui.PageManager;
import seedu.address.ui.PageType;
import seedu.address.main.logic.commands.Command;

/**
 * GoTo a specific page with user input entered by the user.
 */
public class GoToCommand extends Command<StatisticsModel> {
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
    public CommandResult execute(StatisticsModel statisticsModel) {
        PageManager.getPage(pageType);
        String respondMessage = String.format(MESSAGE_CHANGE_SUCCESS, pageType.toString().toLowerCase());
        return new CommandResult(respondMessage, false, false);
    }
}
