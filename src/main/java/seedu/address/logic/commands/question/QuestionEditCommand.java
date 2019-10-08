package seedu.address.logic.commands.question;

import java.util.HashMap;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;

/**
 * Edits the question details in the question list.
 */
public class QuestionEditCommand extends QuestionCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " [index]: Edits a question\n"
        + "Parameters(All are optional):\n"
        + "topic/ [TOPIC]\n"
        + "Example: topic/ What is the year that Singapore gained independence?\n\n"
        + "answer/ [ANSWER]\n"
        + "Example: answer/ 1965\n\n"
        + "type/ [TYPE: open/mcq]\n"
        + "Example: type/ open";

    private final Index index;
    private final String question;
    private final String answer;
    private final String type;

    /**
     * Creates a QuestionEditCommand object.
     *
     * @param fields to edit.
     */
    public QuestionEditCommand(Index index, HashMap<String, String> fields) {
        this.index = index;
        this.question = fields.get("question");
        this.answer = fields.get("answer");
        this.type = fields.get("type");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Question questionObj = model.getQuestion(index);

        String question = (!this.question.isBlank()) ? this.question : questionObj.getQuestion();
        String answer = (!this.answer.isBlank()) ? this.answer : questionObj.getAnswer();

        if (!type.isBlank()) {
            switch (type) {
            case "open":
                questionObj = new OpenEndedQuestion(question, answer);
                break;
            case "mcq":
                questionObj = new McqQuestion(question, answer);
                break;
            default:
                questionObj = new OpenEndedQuestion(question, answer);
                break;
            }
        } else {
            questionObj.setQuestion(question);
            questionObj.setAnswer(answer);
        }

        model.setQuestion(index, questionObj);
        return new CommandResult(generateSuccessMessage(questionObj));
    }

    /**
     * Generates a command execution success message.
     *
     * @param question that has been added.
     */
    private String generateSuccessMessage(Question question) {
        return "Edited question: " + question;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuestionEditCommand)) {
            return false;
        }

        // state check
        QuestionEditCommand e = (QuestionEditCommand) other;
        return question.equals(e.question);
    }
}
