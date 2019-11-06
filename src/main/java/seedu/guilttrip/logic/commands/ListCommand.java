package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.Model;

/**
 * Lists all persons in the guilttrip book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all entries";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateAllLists(PREDICATE_SHOW_ALL_ENTRIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
