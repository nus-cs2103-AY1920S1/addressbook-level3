package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.QuizResult;

/**
 * Skips current quiz question.
 */
public class QuizSkipQuestion extends Command {
    public static final String COMMAND_WORD = "skip";
    public static final String MESSAGE_SUCCESS = "Next question is displayed";

    public static final String EMPTY_QUESTION = "There is no more question. Please quit the mode.";
    public static final String LAST_QUESTION = "All questions have been answered";
    public static final String NO_ANSWER = "This question is not answered.";

    public String getQuizTime() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return simpleDateFormat.format(now);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getSize() < 1) {
            throw new CommandException(EMPTY_QUESTION);
        }

        Question question = model.getOneQuizQuestion();
        Answer answer = new Answer(NO_ANSWER);
        QuizResult quizResult = new QuizResult(answer, question.getQuestionBody(), question.getSubject(),
                question.getDifficulty(), getQuizTime(), false);
        model.addQuizResult(quizResult);
        model.removeOneQuizQuestion();

        if (model.getSize() == 0) {
            return new CommandResult(LAST_QUESTION, 4);
        } else {
            return new CommandResult(MESSAGE_SUCCESS, 4);
        }
    }
}
