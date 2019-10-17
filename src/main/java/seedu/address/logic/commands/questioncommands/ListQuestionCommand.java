package seedu.address.logic.commands.questioncommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_QUESTIONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all questions.
 */
public class ListQuestionCommand extends Command {
    public static final String COMMAND_WORD = "listq";

    public static final String MESSAGE_SUCCESS = "Listed all questions.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
