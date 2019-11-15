package dukecooks.logic.commands.health;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.GotoCommand;
import dukecooks.model.Model;

/**
 * Directs to Health in DukeCooks to the user.
 */
public class GotoHealthCommand extends GotoCommand {

    public static final String VARIANT_WORD = "health";

    public static final String MESSAGE_SUCCESS = "You are now at health records";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecordList(Model.PREDICATE_SHOW_ALL_RECORDS);
        event = Event.getInstance();
        event.set("health", "all");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
