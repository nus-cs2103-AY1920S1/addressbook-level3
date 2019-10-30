package seedu.address.testutil;

import seedu.address.logic.commands.questioncommands.EditQuestionCommand.EditQuestionDescriptor;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;

/**
 * A utility class to help with building EditQuestionDescriptor objects.
 */
public class EditQuestionDescriptorBuilder {
    private EditQuestionDescriptor descriptor;

    public EditQuestionDescriptorBuilder() {
        descriptor = new EditQuestionDescriptor();
    }

    public EditQuestionDescriptorBuilder(EditQuestionDescriptor descriptor) {
        this.descriptor = new EditQuestionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditQuestionDescriptor} with fields containing {@code question}'s details
     */
    public EditQuestionDescriptorBuilder(Question question) {
        descriptor = new EditQuestionDescriptor();
        descriptor.setQuestionBody(question.getQuestionBody());
        descriptor.setAnswer(question.getAnswer());
        descriptor.setSubject(question.getSubject());
        descriptor.setDifficulty(question.getDifficulty());
    }

    /**
     * Sets the {@code QuestionBody} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withQuestionBody(String name) {
        descriptor.setQuestionBody(new QuestionBody(name));
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withAnswer(String address) {
        descriptor.setAnswer(new Answer(address));
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withSubject(String address) {
        descriptor.setSubject(new Subject(address));
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withDifficulty(String address) {
        descriptor.setDifficulty(new Difficulty(address));
        return this;
    }

    public EditQuestionDescriptor build() {
        return descriptor;
    }

}
