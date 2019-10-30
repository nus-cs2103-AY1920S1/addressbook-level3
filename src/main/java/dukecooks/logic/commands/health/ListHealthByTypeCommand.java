package dukecooks.logic.commands.health;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ListCommand;
import dukecooks.model.Model;
import dukecooks.model.health.components.Record;

/**
 * Adds a record to Duke Cooks.
 */
public class ListHealthByTypeCommand extends ListCommand {

    public static final String VARIANT_WORD = "health";

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

        return new CommandResult(
                String.format(Messages.MESSAGE_RECORDS_LISTED_OVERVIEW, model.getFilteredRecordList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListHealthByTypeCommand // instanceof handles nulls
                && predicate.equals(((ListHealthByTypeCommand) other).predicate)); // state check
    }
}
