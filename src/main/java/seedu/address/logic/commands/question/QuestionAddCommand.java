package seedu.address.logic.commands.question;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
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
        + "question/ [TOPIC]\n"
        + "answer/ [ANSWER]\n"
        + "type/ [TYPE: open/mcq]\n"
        + "a/ [OPTION ANSWER - Only for mcq]\n"
        + "b/ [OPTION ANSWER - Only for mcq]\n"
        + "c/ [OPTION ANSWER - Only for mcq]\n"
        + "d/ [OPTION ANSWER - Only for mcq]\n"
        + "Example: question question/ What is 1+1? answer/ 2 type/open\n"
        + "Example: question question/ What is 1+1? answer/ 2 type/ mcq a/ 1 b/ 2 c/ 3 d/ 4";

    public static final String MESSAGE_SUCCESS = "Added question:\n%1$s";

    private final String question;
    private final String answer;
    private final String type;

    private String optionA = null;
    private String optionB = null;
    private String optionC = null;
    private String optionD = null;

    /**
     * Creates a QuestionAddCommand object.
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

    /**
     * Creates a QuestionAddCommand object with MCQ options.
     *
     * @param question to set.
     * @param answer   to the question.
     * @param type     of question e.g open or mcq.
     * @param optionA  of question.
     * @param optionB  of question.
     * @param optionC  of question.
     * @param optionD  of question.
     */
    public QuestionAddCommand(String question, String answer, String type, String optionA,
        String optionB, String optionC, String optionD) {
        requireAllNonNull(question);
        this.question = question;
        this.answer = answer;
        this.type = type;

        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Question question;
        switch (type) {
        case "open":
            question = new OpenEndedQuestion(this.question, this.answer);
            break;
        case "mcq":
            question = new McqQuestion(this.question, this.answer, optionA, optionB, optionC,
                optionD);
            break;
        default:
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_TYPE);
        }

        if (model.hasQuestion(question)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_QUESTION);
        }
        model.addQuestion(question);
        return new CommandResult(String.format(MESSAGE_SUCCESS, question),
            CommandResultType.SHOW_QUESTION);
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
        return question.equals(e.question)
            && answer.equals(e.answer)
            && type.equals(e.type);
    }
}
