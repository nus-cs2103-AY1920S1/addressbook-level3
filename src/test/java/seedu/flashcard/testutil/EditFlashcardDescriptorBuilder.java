package seedu.flashcard.testutil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.flashcard.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.tag.Tag;

/**
 * A utility class to help with building EditFlashcardDescriptor objects.
 */
public class EditFlashcardDescriptorBuilder {

    private EditFlashcardDescriptor descriptor;

    public EditFlashcardDescriptorBuilder() {
        descriptor = new EditFlashcardDescriptor();
    }

    public EditFlashcardDescriptorBuilder(EditFlashcardDescriptor editFlashcardDescriptor) {
        this.descriptor = editFlashcardDescriptor;
    }

    /**
     * Returns a {@code EditFlashcardDescriptor} with fields containing {@code flashcard}'s details
     * Particular for ShortAnswerFlashcards
     */
    public EditFlashcardDescriptorBuilder(ShortAnswerFlashcard flashcard) {
        descriptor = new EditFlashcardDescriptor();
        descriptor.setQuestion(flashcard.getQuestion());
        descriptor.setAnswer(flashcard.getAnswer());
        descriptor.setDefinition(flashcard.getDefinition());
        descriptor.setTags(flashcard.getTags());
    }

    /**
     * Returns a {@code EditFlashcardDescriptor} with fields containing {@code flashcard}'s details
     * Particular for Mcq flashcards
     */
    public EditFlashcardDescriptorBuilder(McqFlashcard flashcard) {
        descriptor = new EditFlashcardDescriptor();
        descriptor.setQuestion(flashcard.getQuestion());
        descriptor.setAnswer(flashcard.getAnswer());
        descriptor.setTags(flashcard.getTags());
        descriptor.setDefinition(flashcard.getDefinition());
        descriptor.setChoices(flashcard.getChoices());
    }

    /**
     * Sets the {@code Question} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }

    /**
     * Sets the {@code Definition} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withDefinition(String definition) {
        descriptor.setDefinition(new Definition(definition));
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withAnswer(String answer) {
        descriptor.setAnswer(new Answer(answer));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditFlashcardDescriptor}
     */
    public EditFlashcardDescriptorBuilder withTag(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code choices} into a {@code Set<Choice>} and set it to the {@code EditFlashcardDescriptor}
     */
    public EditFlashcardDescriptorBuilder withChoices(String... choices) {
        List<Choice> choiceSet = Stream.of(choices).map(Choice::new).collect(Collectors.toList());
        descriptor.setChoices(choiceSet);
        return this;
    }

    public EditFlashcardDescriptor build() {
        return descriptor;
    }
}
