package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditFlashCardDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditFlashCardDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditFlashCardDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code flashCard}'s details
     */
    public EditFlashCardDescriptorBuilder(FlashCard flashCard) {
        descriptor = new EditPersonDescriptor();
        descriptor.setQuestion(flashCard.getQuestion());
        descriptor.setAnswer(flashCard.getAnswer());
        descriptor.setRating(flashCard.getRating());
        descriptor.setCategories(flashCard.getCategories());
    }

    /**
     * Sets the {@code Question} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditFlashCardDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditFlashCardDescriptorBuilder withAnswer(String answer) {
        descriptor.setAnswer(new Answer(answer));
        return this;
    }


    /**
     * Sets the {@code Rating} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditFlashCardDescriptorBuilder withRating (String rating) {
        descriptor.setRating(new Rating(rating));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditFlashCardDescriptorBuilder withCategories(String... categories) {
        Set<Category> categorySet = Stream.of(categories).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(categorySet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
