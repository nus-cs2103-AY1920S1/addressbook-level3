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
 * Represents a Question in the question bank.
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

    /**
     * Every field must be present and not null.
     */
    public Question(String title, Status status, Difficulty difficulty, Set<Topic> topics,
                    List<TestCase> testCases, UserProgram userProgram) {
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
        this.userProgram = new UserProgram(userProgram.getClassName(), userProgram.getSourceCode());
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
        return new UserProgram(this.userProgram.getClassName(), this.userProgram.getSourceCode());
    }

    public List<TestCase> getTestCases() {
        return new ArrayList<>(this.testCases);
    }

    public Question withNewStatus(Status status) {
        return new Question(this.title, status, this.difficulty, this.topics, this.testCases, this.userProgram);
    }

    public Question withNewUserProgram(UserProgram userProgram) {
        return new Question(this.title, this.status, this.difficulty, this.topics, this.testCases, userProgram);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Status: ")
                .append(getStatus())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Topics: ");
        this.getTopics().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Checks if the given string is a valid title for a question. Titles must be alphanumeric.
     * @param title the string to be checked.
     * @return true if the string is a valid title.
     */
    public static boolean checkValidTitle(String title) {
        return title.matches(TITLE_VALIDATION_REGEX);
    }

    public boolean checkContentsEqual(Question other) {
            return other.getTitle().equals(this.title)
                    && other.getStatus().equals(this.status)
                    && other.getDifficulty().equals(this.difficulty)
                    && other.getTopics().equals(this.topics)
                    && other.getTestCases().equals(this.testCases)
                    && other.getUserProgram().equals(this.userProgram);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Question) {
            return this.uuid.equals(((Question) o).uuid);
        }

        return false;
    }
}
