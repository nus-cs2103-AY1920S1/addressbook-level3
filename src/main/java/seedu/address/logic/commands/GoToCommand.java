package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.PageType;

public class GoToCommand extends Command {
    public static final String COMMAND_WORD = "goto";
    public static final String MESSAGE_CHANGE_SUCCESS = "Switching to %s page";

    private PageType pageType;

    public GoToCommand(PageType pageType) {
        this.pageType = pageType;
    }

    @Override
    public CommandResult execute(Model model) {
        String respondMessage = String.format(MESSAGE_CHANGE_SUCCESS, pageType.toString().toLowerCase());
        return new CommandResult(respondMessage, false, false, true);
    }
}
