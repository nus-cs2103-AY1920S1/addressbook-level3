package seedu.revision.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.revision.logic.commands.EditCommand;
import seedu.revision.logic.commands.EditCommand.EditAnswerableDescriptor;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.category.Category;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.Difficulty;


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
        descriptor.setCorrectAnswerSet(answerable.getCorrectAnswerSet());
        descriptor.setWrongAnswerSet(answerable.getWrongAnswerSet());
        descriptor.setDifficulty(answerable.getDifficulty());
        descriptor.setCategories(answerable.getCategories());
    }

    /**
     * Sets the {@code Question} of the {@code EditAnswerableDescriptor} that we are building.
     */
    public EditAnswerableDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }
    /**
     * Sets the Correct Answer Set of the {@code EditAnswerableDescriptor} that we are building.
     */
    public EditAnswerableDescriptorBuilder withCorrectAnswerSet(Set<Answer> correctAnswerSet) {
        descriptor.setCorrectAnswerSet(correctAnswerSet);
        return this;
    }

    /**
     * Sets the Wrong Answer Set of the {@code EditAnswerableDescriptor} that we are building.
     */
    public EditAnswerableDescriptorBuilder withWrongAnswerSet(Set<Answer> wrongAnswerSet) {
        descriptor.setWrongAnswerSet(wrongAnswerSet);
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
     * Parses the {@code tags} into a {@code Set<Category>} and set it to the {@code EditAnswerableDescriptor}
     * that we are building.
     */
    public EditAnswerableDescriptorBuilder withCategories(String... tags) {
        Set<Category> tagSet = Stream.of(tags).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(tagSet);
        return this;
    }

    public EditCommand.EditAnswerableDescriptor build() {
        return descriptor;
    }
}
