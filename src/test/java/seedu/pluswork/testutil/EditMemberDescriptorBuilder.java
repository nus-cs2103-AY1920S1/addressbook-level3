package seedu.pluswork.testutil;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.pluswork.logic.commands.member.EditMemberCommand;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberName;
import seedu.pluswork.model.tag.Tag;

/**
 * A utility class to help with building EditMemberDescriptor objects.
 */
public class EditMemberDescriptorBuilder {
    private EditMemberCommand.EditMemberDescriptor descriptor;

    public EditMemberDescriptorBuilder() {
        descriptor = new EditMemberCommand.EditMemberDescriptor();
    }

    public EditMemberDescriptorBuilder(EditMemberCommand.EditMemberDescriptor descriptor) {
        this.descriptor = new EditMemberCommand.EditMemberDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditMemberDescriptorBuilder(Member member) {
        requireNonNull(member);
        descriptor = new EditMemberCommand.EditMemberDescriptor();
        descriptor.setName(member.getName());
        descriptor.setTags(member.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditMemberDescriptorBuilder withName(String name) {
        descriptor.setName(new MemberName(name));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditMemberDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditMemberCommand.EditMemberDescriptor build() {
        return descriptor;
    }
}
