package seedu.address.logic.commands.question;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Creates a new question to be added to the question list.
 */
public class QuestionListCommand extends QuestionCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " list: List summary of questions";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(model.getQuestionsSummary(), CommandResultType.SHOW_QUESTION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof QuestionListCommand); // instanceof handles nulls
    }
}
