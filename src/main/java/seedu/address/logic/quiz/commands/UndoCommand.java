package seedu.address.logic.quiz.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.quiz.Model.PREDICATE_SHOW_ALL_QUESTIONS;

import seedu.address.logic.quiz.commands.exceptions.CommandException;
import seedu.address.model.quiz.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoQuizBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoQuizBook();
        model.updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
