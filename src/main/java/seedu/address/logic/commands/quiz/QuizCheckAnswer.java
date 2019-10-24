package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResult;

/**
 * Checks the quiz answer input by users.
 */
public class QuizCheckAnswer extends Command {
    public static final String ANSWER_CORRECT = "The answer is correct!";
    public static final String ANSWER_WRONG = "The answer is wrong!";

    public static final String EMPTY_QUESTION = "You have answered all questions!";

    private final Answer answer;

    /**
     * @param answer of the question input by user.
     */
    public QuizCheckAnswer(Answer answer) {
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

        try {
            boolean result = model.checkQuizAnswer(answer);
            Question question = model.getOneQuizQuestion();
            QuestionBody questionBody = question.getQuestionBody();
            Subject subject = question.getSubject();
            Difficulty difficulty = question.getDifficulty();
            QuizResult quizResult = new QuizResult(answer, questionBody, subject, difficulty, getQuizTime(), result);

            model.addQuizResult(quizResult);
            model.removeOneQuizQuestion();

            if (result) {
                return new CommandResult(ANSWER_CORRECT, 4);
            } else {
                return new CommandResult(ANSWER_WRONG, 4);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(EMPTY_QUESTION);
        }
    }
}
