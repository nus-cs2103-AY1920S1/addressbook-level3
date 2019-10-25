package dukecooks.logic.commands.stats;

import static java.util.Objects.requireNonNull;

import dukecooks.logic.commands.Command;
import dukecooks.logic.commands.CommandResult;
import dukecooks.model.Model;

/**
 * Adds a person to Duke Cooks.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "stat";

    public static final String MESSAGE_SUCCESS = "Show statistics ";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
