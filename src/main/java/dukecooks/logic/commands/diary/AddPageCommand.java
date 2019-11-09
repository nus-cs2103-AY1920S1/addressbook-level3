package dukecooks.logic.commands.diary;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.model.Model;


/**
 * Navigates user to the page creation screen.
 */
public class AddPageCommand extends AddCommand {

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
