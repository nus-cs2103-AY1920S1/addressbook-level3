package com.dukeacademy.storage.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jackson-friendly version of {@link Question}.
 */
public class JsonAdaptedQuestion {
    private final String title;
    private final String status;
    private final String difficulty;
    private final boolean isBookmarked;
    private final List<String> topics = new ArrayList<>();
    private final List<JsonAdaptedTestCase> testCases = new ArrayList<>();
    private final JsonAdaptedUserProgram userProgram;
    private final String description;


    /**
     * Constructs a {@code JsonAdaptedQuestion} with the given question details.
     *
     * @param title        the title
     * @param status       the status
     * @param difficulty   the difficulty
     * @param isBookmarked the bookMark indicator
     * @param topics       the topics
     * @param testCases    the test cases
     * @param userProgram  the user program
     * @param description  the description
     */
    @JsonCreator
    public JsonAdaptedQuestion(@JsonProperty("title") String title, @JsonProperty("status") String status,
                               @JsonProperty("difficulty") String difficulty,
                               @JsonProperty("isBookmarked") boolean isBookmarked,
                               @JsonProperty("topics") List<String> topics,
                               @JsonProperty("testCases") List<JsonAdaptedTestCase> testCases,
                               @JsonProperty("userProgram") JsonAdaptedUserProgram userProgram,
                               @JsonProperty("description") String description) {
        this.title = title;
        this.status = status;
        this.difficulty = difficulty;
        this.isBookmarked = isBookmarked;

        if (topics != null) {
            this.topics.addAll(topics);
        }
        if (testCases != null) {
            this.testCases.addAll(testCases);
        }
        this.userProgram = userProgram;
        this.description = description;
    }

    /**
     * Converts a given {@code Question} into this class for Jackson use.
     *
     * @param source the source
     */
    public JsonAdaptedQuestion(Question source) {
        this.title = source.getTitle();
        this.status = source.getStatus().toString();
        this.difficulty = source.getDifficulty().toString();
        this.isBookmarked = source.isBookmarked();
        this.topics.addAll(source.getTopics().stream().map(Objects::toString).collect(Collectors.toList()));
        this.testCases.addAll(source.getTestCases().stream()
                .map(JsonAdaptedTestCase::new)
                .collect(Collectors.toList()));
        this.userProgram = new JsonAdaptedUserProgram(source.getUserProgram());
        this.description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted question object into the model's {@code Question} object.
     *
     * @return the question
     * @throws IllegalValueException if there were any data constraints violated in the adapted question.
     */
    public Question toModel() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException("Title cannot be null.");
        }
        if (!Question.checkValidTitle(title)) {
            throw new IllegalValueException("Invalid title.");
        }

        if (status == null) {
            throw new IllegalValueException("Status cannot be null.");
        }

        if (difficulty == null) {
            throw new IllegalValueException("Difficulty cannot be null.");
        }

        if (description == null) {
            throw new IllegalValueException("Description cannot be null.");
        }

        final Status status = Status.valueOf(this.status);
        final Difficulty difficulty = Difficulty.valueOf(this.difficulty);
        final boolean isBookmarked = this.isBookmarked;
        final Set<Topic> newTopicsSet = this.topics.stream().map(Topic::valueOf).collect(Collectors.toSet());
        final List<TestCase> newTestCaseList = this.testCases.stream()
                .map(JsonAdaptedTestCase::toModel)
                .collect(Collectors.toList());
        final UserProgram newUserProgram = this.userProgram.toModel();

        return new Question(title, status, difficulty, newTopicsSet,
            newTestCaseList, newUserProgram, isBookmarked, description);
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Gets difficulty.
     *
     * @return the difficulty
     */
    public String getDifficulty() {
        return this.difficulty;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets topics.
     *
     * @return the topics
     */
    public List<String> getTopics() {
        return this.topics;
    }

    /**
     * Gets test cases.
     *
     * @return the test cases
     */
    public List<JsonAdaptedTestCase> getTestCases() {
        return this.testCases;
    }

    /**
     * Gets user program.
     *
     * @return the user program
     */
    public JsonAdaptedUserProgram getUserProgram() {
        return this.userProgram;
    }

}
