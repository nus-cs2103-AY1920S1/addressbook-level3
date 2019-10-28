package dukecooks.logic.commands.health;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ListCommand;
import dukecooks.model.Model;

/**
 * Adds a record to Duke Cooks.
 */
public class ListHealthCommand extends ListCommand {

    public static final String VARIANT_WORD = "health";

    public static final String MESSAGE_SUCCESS = "Listed all records";

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
