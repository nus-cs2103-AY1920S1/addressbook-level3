package dukecooks.logic.commands.health;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.ClearCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.model.Model;
import dukecooks.model.health.HealthRecords;

/**
 * Clears Duke Cooks.
 */
public class ClearRecordCommand extends ClearCommand {

    public static final String VARIANT_WORD = "health";
    public static final String MESSAGE_SUCCESS = "Health Records has been cleared!";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setHealthRecords(new HealthRecords());

        event = Event.getInstance();
        event.set("health", "all");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
