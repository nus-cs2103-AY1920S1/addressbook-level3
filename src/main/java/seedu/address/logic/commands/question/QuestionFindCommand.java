package seedu.address.logic.commands.question;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Creates a new question to be added to the question list.
 */
public class QuestionFindCommand extends QuestionCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " find/[QUESTION TEXT]";

    private final String textToFind;

    public QuestionFindCommand(String textToFind) {
        this.textToFind = textToFind;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(model.searchQuestions(textToFind),
            CommandResultType.SHOW_QUESTION_SEARCH);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof QuestionFindCommand // instanceof handles nulls
            && textToFind.equals(((QuestionFindCommand) other).textToFind)); // state check
    }
}
