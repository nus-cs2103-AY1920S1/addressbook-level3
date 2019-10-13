package seedu.address.model.quiz.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.quiz.tag.Tag;

/**
 * Represents a Question in modulo quiz.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Question {

    // Identity fields
    private final Name name;
    private final Answer answer;
    private final Category category;

    // Data fields
    private final Type type;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Question(Name name, Answer answer, Category category, Type type, Set<Tag> tags) {
        requireAllNonNull(name, answer, category, type, tags);
        this.name = name;
        this.answer = answer;
        this.category = category;
        this.type = type;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Category getCategory() {
        return category;
    }

    public Type getType() {
        return type;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both questions of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two questions.
     */
    public boolean isSameQuestion(Question otherQuestion) {
        if (otherQuestion == this) {
            return true;
        }

        return otherQuestion != null
                && otherQuestion.getName().equals(getName())
                && (otherQuestion.getAnswer().equals(getAnswer()) || otherQuestion.getCategory().equals(getCategory()));
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
        return otherQuestion.getName().equals(getName())
                && otherQuestion.getAnswer().equals(getAnswer())
                && otherQuestion.getCategory().equals(getCategory())
                && otherQuestion.getType().equals(getType())
                && otherQuestion.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, answer, category, type, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Answer: ")
                .append(getAnswer())
                .append(" Category: ")
                .append(getCategory())
                .append(" Type: ")
                .append(getType())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
