package dukecooks.logic.commands.diary;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.CreateCommand;
import dukecooks.model.Model;


/**
 * Navigates user to the create page screen.
 */
public class CreatePageCommand extends CreateCommand {

    public static final String VARIANT_WORD = "page";

    public static final String MESSAGE_SUCCESS = "Get started with creating your new page in the diary!";

    private static Event event;


    @Override
    public CommandResult execute(Model model) {

        event = Event.getInstance();
        event.set("diary", "pageInput");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
