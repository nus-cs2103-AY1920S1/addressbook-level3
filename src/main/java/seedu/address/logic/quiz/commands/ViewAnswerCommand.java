package seedu.address.logic.quiz.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.quiz.commands.exceptions.CommandException;
import seedu.address.model.quiz.Model;

/**
 * Enable or disable the visibility of the answer from the quiz question list.
 */
public class ViewAnswerCommand extends Command {

    public static final String COMMAND_WORD = "showAnswer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show or hide the answer of the question "
            + "from the given string.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " yes";

    public static final String MESSAGE_ERROR_PARAMETER = "The value can only be (yes/no)";
    public static final String MESSAGE_SHOW_QUESTION_SUCCESS = "Answer visibility is updated";

    private final String answerVisibility;

    public ViewAnswerCommand(String answerVisibility) {
        this.answerVisibility = answerVisibility.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (answerVisibility.toLowerCase().equals("yes")) {
            model.setShowAnswer(true);
        } else if (answerVisibility.toLowerCase().equals("no")) {
            model.setShowAnswer(false);
        } else {
            throw new CommandException(MESSAGE_ERROR_PARAMETER);
        }

        return new CommandResult(String.format(MESSAGE_SHOW_QUESTION_SUCCESS, answerVisibility), "show");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewAnswerCommand // instanceof handles nulls
                && answerVisibility.equals(((ViewAnswerCommand) other).answerVisibility)); // state check
    }
}
