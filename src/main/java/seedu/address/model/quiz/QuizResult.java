package seedu.address.model.quiz;

import java.util.Date;
import java.util.Objects;

import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;

/**
 * Represents a question. Its answer, questionBody, quizTime and result are guaranteed non-null.
 */
public class QuizResult {
    private final Answer answer;
    private final QuestionBody questionBody;
    private final Subject subject;
    private final Difficulty difficulty;
    private final String quizTime; // need to convert to date object
    private final boolean result;

    public QuizResult(Answer answer, QuestionBody questionBody, Subject subject, Difficulty difficulty,
                      String quizTime, boolean result) {
        this.answer = answer;
        this.questionBody = questionBody;
        this.subject = subject;
        this.difficulty = difficulty;
        this.quizTime = quizTime;
        this.result = result;
    }

    public Answer getAnswer() {
        return answer;
    }

    public QuestionBody getQuestionBody() {
        return questionBody;
    }

    public Subject getSubject() {
        return subject;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getQuizTime() {
        return quizTime;
    }

    public boolean getResult() {
        return result;
    }

    public boolean isWithinDate(Date start, Date end) { //can either pass it in as a string or as a date
        //return !(quizTime.before(start) || quizTime.after(end));
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof QuizResult)) {
            return false;
        }

        QuizResult otherQuizResult = (QuizResult) other;
        return otherQuizResult.getQuizTime().equals(getQuizTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer, questionBody, subject, difficulty, quizTime, result);
    }

    @Override
    public String toString() {
        return (questionBody.toString()); //temporary method for debugging
    }
}
