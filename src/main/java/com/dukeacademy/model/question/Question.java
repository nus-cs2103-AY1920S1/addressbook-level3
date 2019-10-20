package com.dukeacademy.model.question;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;

/**
 * Represents a Question in the question bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Question {

    private final String title;
    private final Status status;
    private final Difficulty difficulty;
    private final Set<Topic> topics = new HashSet<>();
    private final List<TestCase> testCases = new ArrayList<>();
    private final UserProgram userProgram;

    public static final String TITLE_VALIDATION_REGEX = "[^\\s].*";

    /**
     * Every field must be present and not null.
     */
    public Question(String title, Status status, Difficulty difficulty, Set<Topic> topics, List<TestCase> testCases, UserProgram userProgram) {
        requireAllNonNull(title, status, difficulty, topics, testCases, userProgram);
        if (!Question.checkValidTitle(title)) {
            throw new IllegalArgumentException();
        };

        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
        this.topics.addAll(topics);
        this.testCases.addAll(testCases);
        this.userProgram = new UserProgram(userProgram.getClassName(), userProgram.getSourceCodeAsString());
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
        return new UserProgram(this.userProgram.getClassName(), this.userProgram.getSourceCodeAsString());
    }

    public List<TestCase> getTestCases() {
        return new ArrayList<>(this.testCases);
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

    public static boolean checkValidTitle(String title) {
        if (!title.matches(TITLE_VALIDATION_REGEX)) {
            return false;
        }

        return true;
    }
}
