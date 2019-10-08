package seedu.address.logic.commands.question;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;

/**
 * Creates a new question to be added to the question list.
 */
public class QuestionAddCommand extends QuestionCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new question\n"
        + "Parameters:\n"
        + "topic/ [TOPIC]\n"
        + "Example: topic/ What is the year that Singapore gained independence?\n\n"
        + "answer/ [ANSWER]\n"
        + "Example: answer/ 1965\n\n"
        + "type/ [TYPE: open/mcq]\n"
        + "Example: type/ open";

    private final String question;
    private final String answer;
    private final String type;

    /**
     * Creates a CreateQuestionCommand object.
     *
     * @param question to set.
     * @param answer   to the question.
     * @param type     of question e.g open or mcq.
     */
    public QuestionAddCommand(String question, String answer, String type) {
        requireAllNonNull(question);
        this.question = question;
        this.answer = answer;
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Question question;
        switch (type) {
        case "open":
            question = new OpenEndedQuestion(this.question, this.answer);
            break;
        case "mcq":
            question = new McqQuestion(this.question, this.answer);
            break;
        default:
            question = new OpenEndedQuestion(this.question, this.answer);
            break;
        }

        model.addQuestion(question);
        return new CommandResult(generateSuccessMessage(question));
    }

    /**
     * Generates a command execution success message.
     *
     * @param question that has been added.
     */
    private String generateSuccessMessage(Question question) {
        return "Added question: " + question;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuestionAddCommand)) {
            return false;
        }

        // state check
        QuestionAddCommand e = (QuestionAddCommand) other;
        return question.equals(e.question);
    }
}
