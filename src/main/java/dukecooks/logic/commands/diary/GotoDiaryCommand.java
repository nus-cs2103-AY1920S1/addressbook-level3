package dukecooks.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.GotoCommand;
import dukecooks.model.Model;

/**
 * Directs to Diary in DukeCooks to the user.
 */
public class GotoDiaryCommand extends GotoCommand {

    public static final String VARIANT_WORD = "diary";

    public static final String MESSAGE_SUCCESS = "You are now at the diary!";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDiaryList(Model.PREDICATE_SHOW_ALL_DIARIES);
        event = Event.getInstance();
        event.set("diary", "all");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
