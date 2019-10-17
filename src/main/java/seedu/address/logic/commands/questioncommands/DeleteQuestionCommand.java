package seedu.address.logic.commands.questioncommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Question;

/**
 * Deletes a question by index number.
 */
public class DeleteQuestionCommand extends Command {
    public static final String COMMAND_WORD = "deleteq";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the question with the specified index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_QUESTION_SUCCESS = "Deleted question: %1$s";

    private final Index targetIndex;

    public DeleteQuestionCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Question> lastShownList = model.getFilteredQuestionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        Question questionToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteQuestion(questionToDelete);
        model.updateFilteredQuestionList(Model.PREDICATE_SHOW_ALL_QUESTIONS);
        return new CommandResult(String.format(MESSAGE_DELETE_QUESTION_SUCCESS, questionToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteQuestionCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteQuestionCommand) other).targetIndex)); // state check
    }
}
