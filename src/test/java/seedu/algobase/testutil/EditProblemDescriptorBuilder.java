package seedu.algobase.testutil;

import seedu.algobase.logic.commands.EditCommand.EditProblemDescriptor;
import seedu.algobase.model.Problem.*;
import seedu.algobase.model.tag.Tag;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A utility class to help with building EditProblemDescriptor objects.
 */
public class EditProblemDescriptorBuilder {

    private EditProblemDescriptor descriptor;

    public EditProblemDescriptorBuilder() {
        descriptor = new EditProblemDescriptor();
    }

    public EditProblemDescriptorBuilder(EditProblemDescriptor descriptor) {
        this.descriptor = new EditProblemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditProblemDescriptor} with fields containing {@code Problem}'s details
     */
    public EditProblemDescriptorBuilder(Problem problem) {
        descriptor = new EditProblemDescriptor();
        descriptor.setName(problem.getName());
        descriptor.setAuthor(problem.getAuthor());
        descriptor.setWebLink(problem.getWebLink());
        descriptor.setDescription(problem.getDescription());
        descriptor.setTags(problem.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditProblemDescriptor} that we are building.
     */
    public EditProblemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code EditProblemDescriptor} that we are building.
     */
    public EditProblemDescriptorBuilder withPhone(String phone) {
        descriptor.setAuthor(new Author(phone));
        return this;
    }

    /**
     * Sets the {@code WebLink} of the {@code EditProblemDescriptor} that we are building.
     */
    public EditProblemDescriptorBuilder withEmail(String email) {
        descriptor.setWebLink(new WebLink(email));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditProblemDescriptor} that we are building.
     */
    public EditProblemDescriptorBuilder withAddress(String address) {
        descriptor.setDescription(new Description(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditProblemDescriptor}
     * that we are building.
     */
    public EditProblemDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditProblemDescriptor build() {
        return descriptor;
    }
}
