package com.dukeacademy.model.question;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;

/**
 * Represents a Question in the question bank. Each newly created question is tagged with a UUID. This UUID is not
 * saved to storage and is not exposed to external classes. However, it is used to determine the equality of questions.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Question {
    public static final String TITLE_VALIDATION_REGEX = "[^\\s].*";

    private final UUID uuid;
    private final String title;
    private final Status status;
    private final Difficulty difficulty;
    private final Set<Topic> topics = new HashSet<>();
    private final List<TestCase> testCases = new ArrayList<>();
    private final UserProgram userProgram;
    private final boolean isBookmarked;

    /**
     * Every field must be present and not null.
     */
    public Question(UUID uuid, String title, Status status, Difficulty difficulty, Set<Topic> topics,
                    List<TestCase> testCases, UserProgram userProgram, boolean isBookmarked) {
        requireAllNonNull(title, status, difficulty, topics, testCases, userProgram);
        if (!Question.checkValidTitle(title)) {
            throw new IllegalArgumentException();
        }

        this.uuid = uuid;
        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
        this.topics.addAll(topics);
        this.testCases.addAll(testCases);
        this.userProgram = new UserProgram(userProgram.getCanonicalName(), userProgram.getSourceCode());
        this.isBookmarked = isBookmarked;
    }


    /**
     * Every field must be present and not null.
     */
    public Question(String title, Status status, Difficulty difficulty, Set<Topic> topics,
                    List<TestCase> testCases, UserProgram userProgram, boolean isBookmarked) {
        requireAllNonNull(title, status, difficulty, topics, testCases, userProgram);
        if (!Question.checkValidTitle(title)) {
            throw new IllegalArgumentException();
        }

        this.uuid = UUID.randomUUID();
        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
        this.topics.addAll(topics);
        this.testCases.addAll(testCases);
        this.userProgram = new UserProgram(userProgram.getCanonicalName(), userProgram.getSourceCode());
        this.isBookmarked = isBookmarked;
    }

    public String getTitle() {
        return this.title;
    }

    public Status getStatus() {
        return status;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Set<Topic> getTopics() {
        return Collections.unmodifiableSet(this.topics);
    }

    public UserProgram getUserProgram() {
        return new UserProgram(this.userProgram.getCanonicalName(), this.userProgram.getSourceCode());
    }

    public List<TestCase> getTestCases() {
        return new ArrayList<>(this.testCases);
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    /**
     * Creates a new instance of the same question with a new status. This new instance has the same uuid as the
     * previous instance.
     *
     * @param status the status to be updated to.
     * @return a new instance of the question.
     */
    public Question withNewStatus(Status status) {
        return new Question(this.uuid, this.title, status, this.difficulty, this.topics,
                this.testCases, this.userProgram, this.isBookmarked);
    }

    /**
     * Creates a new instance of the same question with a new user program. This new instance has the same uuid as the
     * previous instance.
     *
     * @param userProgram the user program to be updated to.
     * @return a new instance of the question.
     */
    public Question withNewUserProgram(UserProgram userProgram) {
        return new Question(this.uuid, this.title, this.status, this.difficulty, this.topics,
                this.testCases, userProgram, this.isBookmarked);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Status: ")
                .append(getStatus())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Topics: ")
                .append(isBookmarked());
        this.getTopics().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Checks if the given string is a valid title for a question. Titles must be alphanumeric.
     *
     * @param title the string to be checked.
     * @return true if the string is a valid title.
     */
    public static boolean checkValidTitle(String title) {
        return title.matches(TITLE_VALIDATION_REGEX);
    }

    /**
     * Checks if the contents of the questions are equal. The UUID of each question is disregarded.
     * @param other the other question to be checked against.
     * @return true if the contents are equal.
     */
    public boolean checkContentsEqual(Question other) {
        return other.getTitle().equals(this.title)
                && other.getStatus().equals(this.status)
                && other.getDifficulty().equals(this.difficulty)
                && other.getTopics().equals(this.topics)
                && other.getTestCases().equals(this.testCases)
                && other.getUserProgram().equals(this.userProgram)
                && other.isBookmarked() == this.isBookmarked();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Question) {
            return this.uuid.equals(((Question) o).uuid);
        }

        return false;
    }
}
