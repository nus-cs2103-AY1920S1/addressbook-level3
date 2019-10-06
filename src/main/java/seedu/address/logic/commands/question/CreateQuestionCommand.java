package seedu.address.logic.commands.question;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionType;

public class CreateQuestionCommand extends Command {

    public static final String COMMAND_WORD = "question";

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
    private final QuestionType type;

    /**
     * Creates a CreateQuestionCommand object.
     *
     * @param question to set.
     * @param answer   to the question.
     * @param type     of question e.g open or mcq.
     */
    public CreateQuestionCommand(String question, String answer, QuestionType type) {
        requireAllNonNull(question);
        this.question = question;
        this.answer = answer;
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Question question = new Question(this.question, this.answer, this.type);
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
        if (!(other instanceof CreateQuestionCommand)) {
            return false;
        }

        // state check
        CreateQuestionCommand e = (CreateQuestionCommand) other;
        return question.equals(e.question);
    }
}
