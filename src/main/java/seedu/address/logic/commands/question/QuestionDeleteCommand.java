package seedu.address.logic.commands.question;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a question in the question list.
 */
public class QuestionDeleteCommand extends QuestionCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " [index]: Deletes a question\n"
        + "Note: index has to be greater than 0";

    public static final String MESSAGE_SUCCESS = "Deleted question:\n%1$s";

    private final Index index;

    /**
     * Creates a QuestionDeleteCommand object.
     *
     * @param index of question to delete from the list.
     */
    public QuestionDeleteCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (index.getZeroBased() >= model.getAllQuestions().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.deleteQuestion(index)),
            CommandResultType.SHOW_QUESTION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof QuestionDeleteCommand // instanceof handles nulls
            && index.equals(((QuestionDeleteCommand) other).index)); // state check
    }
}
