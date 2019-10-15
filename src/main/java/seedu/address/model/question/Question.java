package seedu.address.model.question;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a question. Its contents, answers, difficulty and subjects are guaranteed non-null.
 */
public class Question {
    private final QuestionBody questionBody;
    private final Answer answer;
    private final Subject subject;
    private final Difficulty difficulty;

    /**
     * Constructs a new question. All fields must be present and non-null.
     *
     * @param questionBody    The questions's content, which must be unique.
     * @param answer     The question's answer, which do not have to be unique.
     * @param subject    The question's subject, which do not have to be unique.
     * @param difficulty The question's difficulty, which do not have to be unique.
     */
    public Question(QuestionBody questionBody, Answer answer, Subject subject, Difficulty difficulty) {
        requireAllNonNull(questionBody, answer, subject, difficulty);
        this.questionBody = questionBody;
        this.answer = answer;
        this.subject = subject;
        this.difficulty = difficulty;
    }

    public QuestionBody getQuestionBody() {
        return questionBody;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Subject getSubject() {
        return subject;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Returns true if both questions have the same content.
     */
    public boolean isSameQuestion(Question other) {
        if (other == this) {
            return true;
        }
        return other != null && other.getQuestionBody().equals(getQuestionBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionBody, answer, subject, difficulty);
    }

    @Override
    public String toString() {
        return "Question: " + getQuestionBody() + "\n" + "Answer: " + getAnswer() + "\n"
                + "Subject: " + getSubject() + "\n" + "Difficulty: " + getDifficulty();
    }
}
