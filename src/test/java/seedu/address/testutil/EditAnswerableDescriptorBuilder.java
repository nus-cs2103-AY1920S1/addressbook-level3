package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditAnswerableDescriptor;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.answerable.Category;
import seedu.address.model.answerable.AnswerSet;
import seedu.address.model.answerable.Question;
import seedu.address.model.answerable.Difficulty;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditAnswerableDescriptor objects.
 */
public class EditAnswerableDescriptorBuilder {

    private EditAnswerableDescriptor descriptor;

    public EditAnswerableDescriptorBuilder() {
        descriptor = new EditCommand.EditAnswerableDescriptor();
    }

    public EditAnswerableDescriptorBuilder(EditCommand.EditAnswerableDescriptor descriptor) {
        this.descriptor = new EditAnswerableDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAnswerableDescriptor} with fields containing {@code answerable}'s details
     */
    public EditAnswerableDescriptorBuilder(Answerable answerable) {
        descriptor = new EditCommand.EditAnswerableDescriptor();
        descriptor.setQuestion(answerable.getQuestion());
        descriptor.setAnswerSet(answerable.getAnswerSet());
        descriptor.setDifficulty(answerable.getDifficulty());
        descriptor.setCategory(answerable.getCategory());
        descriptor.setTags(answerable.getTags());
    }

    /**
     * Sets the {@code Question} of the {@code EditAnswerableDescriptor} that we are building.
     */
    public EditAnswerableDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }

    /**
     * Sets the {@code Question} of the {@code EditAnswerableDescriptor} that we are building.
     */
    public EditAnswerableDescriptorBuilder withAnswer(String answer) {
        //TODO: Implement Answerable
        descriptor.setAnswerSet(new AnswerSet(answer));
        return this;
    }
    /**
     * Sets the {@code Difficulty} of the {@code EditAnswerableDescriptor} that we are building.
     */
    public EditAnswerableDescriptorBuilder withDifficulty(String difficulty) {
        descriptor.setDifficulty(new Difficulty(difficulty));
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code EditAnswerableDescriptor} that we are building.
     */
    public EditAnswerableDescriptorBuilder withAddress(String address) {
        descriptor.setCategory(new Category(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditAnswerableDescriptor}
     * that we are building.
     */
    public EditAnswerableDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditAnswerableDescriptor build() {
        return descriptor;
    }
}
