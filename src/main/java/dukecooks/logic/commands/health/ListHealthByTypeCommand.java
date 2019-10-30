package dukecooks.logic.commands.health;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ListCommand;
import dukecooks.model.Model;
import dukecooks.model.health.components.Record;

/**
 * Adds a record to Duke Cooks.
 */
public class ListHealthByTypeCommand extends ListCommand {

    public static final String VARIANT_WORD = "health";

    public static final String MESSAGE_SUCCESS = "Listed records";

    private static Event event;

    private static Predicate<Record> predicate;

    public ListHealthByTypeCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecordList(predicate);

        event = Event.getInstance();
        event.set("health", "type");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
