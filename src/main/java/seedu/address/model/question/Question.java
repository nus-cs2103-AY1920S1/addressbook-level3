package seedu.address.model.question;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a question. Its contents, answers, difficulty and subjects are guaranteed non-null.
 */
public class Question {
    private final Content content;
    private final Answer answer;
    private final Difficulty difficulty;
    private final Subject subject;

    /**
     * Constructs a new question. All fields must be present and non-null.
     *
     * @param content    The questions's content, which must be unique.
     * @param answer     The question's answer, which do not have to be unique.
     * @param difficulty The question's difficulty, which do not have to be unique.
     * @param subject    The question's subject, which do not have to be unique.
     */
    public Question(Content content, Answer answer, Difficulty difficulty, Subject subject) {
        requireAllNonNull(content, answer, difficulty, subject);
        this.content = content;
        this.answer = answer;
        this.difficulty = difficulty;
        this.subject = subject;
    }

    public Content getContent() {
        return content;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Subject getSubject() {
        return subject;
    }

    /**
     * Returns true if both questions have the same content.
     */
    public boolean isSameQuestion(Question other) {
        if (other == this) {
            return true;
        }
        return other != null && other.getContent().equals(getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, answer, difficulty, subject);
    }

    @Override
    public String toString() {
        return "Question: " + getContent() + "\n" + "Answer: " + getAnswer() + "\n"
                + "Difficulty: " + getDifficulty() + "\n" + "Subject: " + getSubject();
    }
}
