package com.dukeacademy.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.question.Topic;
import com.dukeacademy.model.tag.Tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jackson-friendly version of {@link Question}.
 */
class JsonAdaptedQuestion {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Question's %s field is missing!";

    private final String title;
    private final String topic;
    private final String status;
    private final String difficulty;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedQuestion} with the given question details.
     */
    @JsonCreator
    public JsonAdaptedQuestion(@JsonProperty("title") String title, @JsonProperty("topic") String topic,
                               @JsonProperty("status") String status, @JsonProperty("difficulty") String difficulty,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.title = title;
        this.topic = topic;
        this.status = status;
        this.difficulty = difficulty;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Question} into this class for Jackson use.
     */
    public JsonAdaptedQuestion(Question source) {
        title = source.getTitle().fullTitle;
        topic = source.getTopic().value;
        status = source.getStatus().value;
        difficulty = source.getDifficulty().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted question object into the model's {@code Question} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted question.
     */
    public Question toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (topic == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Topic.class.getSimpleName()));
        }
        if (!Topic.isValidTopic(topic)) {
            throw new IllegalValueException(Topic.MESSAGE_CONSTRAINTS);
        }
        final Topic modelTopic = new Topic(topic);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        if (difficulty == null) {
            throw new IllegalValueException(String
                .format(MISSING_FIELD_MESSAGE_FORMAT, Difficulty.class.getSimpleName()));
        }
        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Question(modelTitle, modelTopic, modelStatus, modelDifficulty, modelTags);
    }

}
