package com.dukeacademy.testutil;

import java.util.HashSet;
import java.util.Set;

import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.question.Topic;
import com.dukeacademy.model.tag.Tag;
import com.dukeacademy.model.util.SampleDataUtil;

/**
 * A utility class to help with building Question objects.
 */
public class QuestionBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Title title;
    private Topic topic;
    private Status status;
    private Difficulty difficulty;
    private Set<Tag> tags;

    public QuestionBuilder() {
        title = new Title(DEFAULT_NAME);
        topic = new Topic(DEFAULT_PHONE);
        status = new Status(DEFAULT_EMAIL);
        difficulty = new Difficulty(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the QuestionBuilder with the data of {@code questionToCopy}.
     */
    public QuestionBuilder(Question questionToCopy) {
        title = questionToCopy.getTitle();
        topic = questionToCopy.getTopic();
        status = questionToCopy.getStatus();
        difficulty = questionToCopy.getDifficulty();
        tags = new HashSet<>(questionToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code Question} that we are building.
     */
    public QuestionBuilder withTitle(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Question} that we are building.
     */
    public QuestionBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code Question} that we are building.
     */
    public QuestionBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    /**
     * Sets the {@code Topic} of the {@code Question} that we are building.
     */
    public QuestionBuilder withPhone(String topic) {
        this.topic = new Topic(topic);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Question} that we are building.
     */
    public QuestionBuilder withEmail(String status) {
        this.status = new Status(status);
        return this;
    }

    public Question build() {
        return new Question(title, topic, status, difficulty, tags);
    }

}
