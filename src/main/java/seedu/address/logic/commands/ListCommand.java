package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import seedu.address.model.Model;

/**
 * Lists all answerables in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all answerables";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAnswerableList(PREDICATE_SHOW_ALL_ANSWERABLE);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
