package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building FlashCard objects.
 */
public class FlashCardBuilder {

    public static final String DEFAULT_QUESTION = "1 + 1";
    public static final String DEFAULT_ANSWER = "2";
    public static final String DEFAULT_RATING = "good";

    private Question question;
    private Answer answer;
    private Rating rating;
    private Set<Category> categories;

    public FlashCardBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answer = new Answer(DEFAULT_ANSWER);
        rating = new Rating(DEFAULT_RATING);
        categories = new HashSet<>();
    }

    /**
     * Initializes the FlashCardBuilder with the data of {@code flashCardToCopy}.
     */
    public FlashCardBuilder(FlashCard flashCardToCopy) {
        question = flashCardToCopy.getQuestion();
        answer = flashCardToCopy.getAnswer();
        rating = flashCardToCopy.getRating();
        categories = new HashSet<>(flashCardToCopy.getCategories());
    }

    /**
     * Sets the {@code Question} of the {@code FlashCard} that we are building.
     */
    public FlashCardBuilder withQuestion(String name) {
        this.question = new Question(name);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>}
     * and set it to the {@code FlashCard} that we are building.
     */
    public FlashCardBuilder withCatgeories(String ... categories) {
        this.categories = SampleDataUtil.getTagSet(categories);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code FlashCard} that we are building.
     */
    public FlashCardBuilder withRating (String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code FlashCard} that we are building.
     */
    public FlashCardBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }


    public FlashCard build() {
        return new FlashCard(question, answer, rating, categories);
    }

}
