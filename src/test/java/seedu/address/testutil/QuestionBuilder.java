package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.quiz.person.Answer;
import seedu.address.model.quiz.person.Category;
import seedu.address.model.quiz.person.Comment;
import seedu.address.model.quiz.person.Name;
import seedu.address.model.quiz.person.Question;
import seedu.address.model.quiz.person.Type;
import seedu.address.model.quiz.tag.Tag;
import seedu.address.model.quiz.util.SampleDataUtil;

/**
 * A utility class to help with building Question objects.
 */
public class QuestionBuilder {

    public static final String DEFAULT_NAME = "What is always coming, but never arrives?";
    public static final String DEFAULT_ANSWER = "Tomorrow";
    public static final String DEFAULT_CATEGORY = "CS2131";
    public static final String DEFAULT_TYPE = "high";
    public static final String DEFAULT_COMMENT = "The explanation is on lecture 20";

    private Name name;
    private Answer answer;
    private Category category;
    private Type type;
    private Comment comment;
    private Set<Tag> tags;

    public QuestionBuilder() {
        name = new Name(DEFAULT_NAME);
        answer = new Answer(DEFAULT_ANSWER);
        category = new Category(DEFAULT_CATEGORY);
        type = new Type(DEFAULT_TYPE);
        comment = null;
        tags = new HashSet<>();
    }

    /**
     * Initializes the QuestionBuilder with the data of {@code personToCopy}.
     */
    public QuestionBuilder(Question personToCopy) {
        name = personToCopy.getName();
        answer = personToCopy.getAnswer();
        category = personToCopy.getCategory();
        type = personToCopy.getType();
        comment = null;
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Question} that we are building.
     */
    public QuestionBuilder withName(String name) {
        this.name = new Name(name);
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
     * Sets the {@code Type} of the {@code Question} that we are building.
     */
    public QuestionBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Question} that we are building.
     */
    public QuestionBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Question} that we are building.
     */
    public QuestionBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    public Question build() {
        return new Question(name, comment, answer, category, type, tags);
    }

}
