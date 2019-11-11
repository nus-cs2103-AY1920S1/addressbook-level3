package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditFlashCardDescriptor;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;

/**
 * A utility class to help with building EditFlashCardDescriptor objects.
 */
public class EditFlashCardDescriptorBuilder {

    private EditFlashCardDescriptor descriptor;

    public EditFlashCardDescriptorBuilder() {
        descriptor = new EditFlashCardDescriptor();
    }

    public EditFlashCardDescriptorBuilder(EditFlashCardDescriptor descriptor) {
        this.descriptor = new EditFlashCardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFlashCardDescriptor} with fields containing {@code flashCard}'s details
     */
    public EditFlashCardDescriptorBuilder(FlashCard flashCard) {
        descriptor = new EditFlashCardDescriptor();
        descriptor.setQuestion(flashCard.getQuestion());
        descriptor.setAnswer(flashCard.getAnswer());
        descriptor.setRating(flashCard.getRating());
        descriptor.setCategories(flashCard.getCategories());
    }

    /**
     * Sets the {@code Question} of the {@code EditFlashCardDescriptor} that we are building.
     */
    public EditFlashCardDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code EditFlashCardDescriptor} that we are building.
     */
    public EditFlashCardDescriptorBuilder withAnswer(String answer) {
        descriptor.setAnswer(new Answer(answer));
        return this;
    }


    /**
     * Sets the {@code Rating} of the {@code EditFlashCardDescriptor} that we are building.
     */
    public EditFlashCardDescriptorBuilder withRating (String rating) {
        descriptor.setRating(new Rating(rating));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code EditFlashCardDescriptor}
     * that we are building.
     */
    public EditFlashCardDescriptorBuilder withCategories(String... categories) {
        Set<Category> categorySet = Stream.of(categories).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(categorySet);
        return this;
    }

    public EditFlashCardDescriptor build() {
        return descriptor;
    }
}
