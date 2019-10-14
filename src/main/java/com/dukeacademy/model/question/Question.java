package com.dukeacademy.model.question;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.dukeacademy.model.tag.Tag;

/**
 * Represents a Question in the question bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Question {

    // Identity fields
    private final Title title;
    private final Topic topic;
    private final Status status;

    // Data fields
    private final Difficulty difficulty;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Question(Title title, Topic topic, Status status, Difficulty difficulty, Set<Tag> tags) {
        requireAllNonNull(title, topic, status, difficulty, tags);
        this.title = title;
        this.topic = topic;
        this.status = status;
        this.difficulty = difficulty;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return title;
    }

    public Topic getTopic() {
        return topic;
    }

    public Status getStatus() {
        return status;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both questions of the same title have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two questions.
     */
    public boolean isSameQuestion(Question otherQuestion) {
        if (otherQuestion == this) {
            return true;
        }

        return otherQuestion != null
                && otherQuestion.getTitle().equals(getTitle())
                && (otherQuestion.getTopic().equals(getTopic()) || otherQuestion
            .getStatus().equals(getStatus()));
    }

    /**
     * Returns true if both questions have the same identity and data fields.
     * This defines a stronger notion of equality between two questions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Question)) {
            return false;
        }

        Question otherQuestion = (Question) other;
        return otherQuestion.getTitle().equals(getTitle())
                && otherQuestion.getTopic().equals(getTopic())
                && otherQuestion.getStatus().equals(getStatus())
                && otherQuestion.getDifficulty().equals(getDifficulty())
                && otherQuestion.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, topic, status, difficulty, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Topic: ")
                .append(getTopic())
                .append(" Status: ")
                .append(getStatus())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
