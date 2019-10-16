package seedu.address.logic.commands;
import org.apache.commons.math3.stat.StatUtils;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Adds a person to Duke Cooks.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "stat";

    public static final String MESSAGE_SUCCESS = "Show statistics ";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
