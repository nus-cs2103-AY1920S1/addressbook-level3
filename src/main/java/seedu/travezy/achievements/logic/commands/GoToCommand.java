package seedu.travezy.achievements.logic.commands;

import seedu.travezy.achievements.model.StatisticsModel;
import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.ui.PageManager;
import seedu.travezy.ui.PageType;
import seedu.travezy.logic.commands.Command;

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
