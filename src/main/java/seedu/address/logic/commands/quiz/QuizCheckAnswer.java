package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
<<<<<<< HEAD

=======
import java.util.List;

import seedu.address.commons.core.Messages;
>>>>>>> 9b56a138d1200f66ac914863790197ce79798407
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Answer;
<<<<<<< HEAD
=======
import seedu.address.model.question.Question;
>>>>>>> 9b56a138d1200f66ac914863790197ce79798407
import seedu.address.model.question.QuestionBody;
import seedu.address.model.quiz.QuizResult;

/**
 * Checks the quiz answer input by users.
 */
public class QuizCheckAnswer extends Command {
    public static final String ANSWER_CORRECT = "The answer is correct!";
    public static final String ANSWER_WRONG = "The answer is wrong!";

    private final int index;
    private final Answer answer;

    /**
     * @param index  of the question the answer is mapped to.
     * @param answer of the question input by user.
     */
    public QuizCheckAnswer(int index, Answer answer) {
        this.index = index;
        this.answer = answer;
    }

    public String getQuizTime() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return simpleDateFormat.format(now);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

<<<<<<< HEAD
=======
        List<Question> listShownList = model.getFilteredQuizQuestionList();
        if (index >= listShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUIZ_QUESTION_DISPLAYED_INDEX);
        }

>>>>>>> 9b56a138d1200f66ac914863790197ce79798407
        boolean result = model.checkQuizAnswer(index, answer);
        QuestionBody questionBody = model.getFilteredQuizQuestionList().get(index).getQuestionBody();
        QuizResult quizResult = new QuizResult(answer, questionBody, getQuizTime(), result);
        model.addQuizResult(quizResult);

        if (result) {
            return new CommandResult(ANSWER_CORRECT);
        } else {
            return new CommandResult(ANSWER_WRONG);
        }
    }
}