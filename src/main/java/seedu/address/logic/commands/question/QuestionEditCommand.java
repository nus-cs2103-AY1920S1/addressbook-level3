package seedu.address.logic.commands.question;

import java.util.HashMap;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
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
        + "question/ [TOPIC]\n"
        + "answer/ [ANSWER]\n"
        + "type/ [TYPE: open/mcq]\n"
        + "Example: question/ What is 1+1?\n"
        + "Example: answer/ 2\n"
        + "Example: type/ open";

    private final Index index;
    private final String question;
    private final String answer;
    private final String type;

    private String optionA = null;
    private String optionB = null;
    private String optionC = null;
    private String optionD = null;

    /**
     * Creates a QuestionEditCommand object.
     *
     * @param index  of question in the list.
     * @param fields to edit.
     */
    public QuestionEditCommand(Index index, HashMap<String, String> fields) {
        this.index = index;
        this.question = fields.get("question");
        this.answer = fields.get("answer");
        this.type = fields.get("type");
    }

    /**
     * Creates a QuestionEditCommand object with MCQ options.
     *
     * @param index   of question in the list.
     * @param fields  to edit.
     * @param options for mcq.
     */
    public QuestionEditCommand(Index index, HashMap<String, String> fields,
        HashMap<String, String> options) {
        this.index = index;
        this.question = fields.get("question");
        this.answer = fields.get("answer");
        this.type = fields.get("type");

        this.optionA = options.get("optionA");
        this.optionB = options.get("optionB");
        this.optionC = options.get("optionC");
        this.optionD = options.get("optionD");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (index.getZeroBased() >= model.getAllQuestions().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        Question questionObj = model.getQuestion(index);

        String question = (!this.question.isBlank()) ? this.question : questionObj.getQuestion();
        String answer = (!this.answer.isBlank()) ? this.answer : questionObj.getAnswer();

        if (!type.isBlank()) {
            switch (type) {
            case "open":
                questionObj = new OpenEndedQuestion(question, answer);
                break;
            case "mcq":
                formatMcqOptions(questionObj);
                questionObj = new McqQuestion(question, answer, optionA, optionB, optionC, optionD);
                break;
            default:
                throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_TYPE);
            }
        } else {
            questionObj.setQuestion(question);
            questionObj.setAnswer(answer);

            if (questionObj instanceof McqQuestion) {
                formatMcqOptions(questionObj);
                questionObj = new McqQuestion(question, answer, optionA, optionB, optionC, optionD);
            }

        }

        model.setQuestion(index, questionObj);
        return new CommandResult(generateSuccessMessage(questionObj),
            CommandResultType.SHOW_QUESTION);
    }

    /**
     * Generates a command execution success message.
     *
     * @param question that has been added.
     */
    private String generateSuccessMessage(Question question) {
        return "Edited question: " + question;
    }

    /**
     * Sets the mcq options to either the new value or the stored value.
     *
     * @param questionObj from list.
     */
    private void formatMcqOptions(Question questionObj) throws CommandException {
        try {
            optionA = (!this.optionA.isBlank()) ? this.optionA
                : ((McqQuestion) questionObj).getOptionA();
            optionB = (!this.optionB.isBlank()) ? this.optionB
                : ((McqQuestion) questionObj).getOptionB();
            optionC = (!this.optionC.isBlank()) ? this.optionC
                : ((McqQuestion) questionObj).getOptionC();
            optionD = (!this.optionD.isBlank()) ? this.optionD
                : ((McqQuestion) questionObj).getOptionD();
        } catch (ClassCastException cce) {
            // Throw error when switching type from mcq to open ended and have missing options
            throw new CommandException(Messages.MESSAGE_MISSING_QUESTION_OPTIONS);
        }
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
        return index.equals(e.index)
            && question.equals(e.question)
            && answer.equals(e.answer)
            && type.equals(e.type);
    }
}
