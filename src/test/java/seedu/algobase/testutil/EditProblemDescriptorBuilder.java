package seedu.algobase.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.algobase.logic.commands.EditCommand.EditProblemDescriptor;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;

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
     * Sets the {@code PlanName} of the {@code EditProblemDescriptor} that we are building.
     */
    public EditProblemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code EditProblemDescriptor} that we are building.
     */
    public EditProblemDescriptorBuilder withAuthor(String author) {
        descriptor.setAuthor(new Author(author));
        return this;
    }

    /**
     * Sets the {@code WebLink} of the {@code EditProblemDescriptor} that we are building.
     */
    public EditProblemDescriptorBuilder withWeblink(String weblink) {
        descriptor.setWebLink(new WebLink(weblink));
        return this;
    }

    /**
     * Sets the {@code PlanDescription} of the {@code EditProblemDescriptor} that we are building.
     */
    public EditProblemDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
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
