package seedu.address.logic.quiz.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.quiz.commands.exceptions.CommandException;

import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.person.Question;

/**
 * Show the details of a particular a question identified using it's displayed index from modulo.
 */
public class DetailCommand extends Command {

    public static final String COMMAND_WORD = "detail";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show the details of a particular question"
            + "as a list from the given positive index number.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHOW_QUESTION_SUCCESS = "Showed Question: %1$s";

    private final Index targetIndex;

    public DetailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Question> lastShownList = model.getFilteredQuestionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        Question questionToShow = lastShownList.get(targetIndex.getZeroBased());
        model.setQuestionNumber(targetIndex.getOneBased());
        model.setShowQuestion(questionToShow);

        return new CommandResult(String.format(MESSAGE_SHOW_QUESTION_SUCCESS, questionToShow), "detail");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailCommand // instanceof handles nulls
                && targetIndex.equals(((DetailCommand) other).targetIndex)); // state check
    }
}
