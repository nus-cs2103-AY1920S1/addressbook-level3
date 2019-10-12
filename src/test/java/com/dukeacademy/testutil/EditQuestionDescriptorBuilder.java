package com.dukeacademy.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dukeacademy.logic.commands.EditCommand;
import com.dukeacademy.model.question.Address;
import com.dukeacademy.model.question.Email;
import com.dukeacademy.model.question.Phone;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditQuestionDescriptorBuilder {

    private EditCommand.EditQuestionDescriptor descriptor;

    public EditQuestionDescriptorBuilder() {
        descriptor = new EditCommand.EditQuestionDescriptor();
    }

    public EditQuestionDescriptorBuilder(EditCommand.EditQuestionDescriptor descriptor) {
        this.descriptor = new EditCommand.EditQuestionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditQuestionDescriptor} with fields containing {@code question}'s details
     */
    public EditQuestionDescriptorBuilder(Question question) {
        descriptor = new EditCommand.EditQuestionDescriptor();
        descriptor.setTitle(question.getTitle());
        descriptor.setPhone(question.getPhone());
        descriptor.setEmail(question.getEmail());
        descriptor.setAddress(question.getAddress());
        descriptor.setTags(question.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditQuestionDescriptor} that we are building.
     */
    public EditQuestionDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditQuestionDescriptor}
     * that we are building.
     */
    public EditQuestionDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditQuestionDescriptor build() {
        return descriptor;
    }
}
