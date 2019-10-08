package seedu.address.logic.commands.question;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Question;

/**
 * Deletes a question in the question list.
 */
public class QuestionDeleteCommand extends QuestionCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " [index]: Deletes a question\n"
        + "Note: index has to be greater than 0";

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
        return new CommandResult(generateSuccessMessage(model.deleteQuestion(index)));
    }

    /**
     * Generates a command execution success message.
     *
     * @param question that has been added.
     */
    private String generateSuccessMessage(Question question) {
        return "Deleted question: " + question;
    }
}
