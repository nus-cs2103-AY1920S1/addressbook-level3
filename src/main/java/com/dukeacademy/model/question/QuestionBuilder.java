package com.dukeacademy.model.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;

/**
 * Utility class for building questions.
 */
public class QuestionBuilder {
    private String title;
    private Status status;
    private Difficulty difficulty;
    private boolean isBookmarked;
    private Set<Topic> topics = new HashSet<>();
    private List<TestCase> testCases = new ArrayList<>();
    private UserProgram userProgram = new UserProgram("Main", "");

    /**
     * Returns a builder with the title added.
     * @param title the title to be added.
     * @return a new builder.
     */
    public QuestionBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Returns a builder with the status added.
     * @param status the status to be added.
     * @return a new builder.
     */
    public QuestionBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Returns a new builder with the difficulty added.
     * @param difficulty the difficulty to be added.
     * @return a new builder.
     */
    public QuestionBuilder withDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    /**
     * Returns a new builder with the isBookmarked boolean added.
     * @param isBookmarked the isBookmarked boolean to be added.
     * @return a new builder.
     */
    public QuestionBuilder withIsBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
        return this;
    }

    /**
     * Returns a new builder with the topics added.
     * @param topics the topics to be added.
     * @return a new builder.
     */
    public QuestionBuilder withTopics(Topic... topics) {
        this.topics.addAll(Arrays.asList(topics));
        return this;
    }

    /**
     * Returns a new builder with the test cases added.
     * @param testCases the test cases to be added.
     * @return a new builder.
     */
    public QuestionBuilder withTestCases(TestCase... testCases) {
        this.testCases.addAll(Arrays.asList(testCases));
        return this;
    }

    /**
     * Returns a new builder with the user program added.
     * @param userProgram the user program to be added.
     * @return a new builder.
     */
    public QuestionBuilder withUserProgram(UserProgram userProgram) {
        this.userProgram = userProgram;
        return this;
    }

    /**
     * Builds the specified attributes into a new question.
     * @return the newly built question.
     */
    public Question build() {
        if (this.title == null) {
            throw new IllegalArgumentException("Title cannot be null.");
        }

        if (this.status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }

        if (this.difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }

        return new Question(title, status, difficulty, topics, testCases, userProgram, isBookmarked);
    }
}
