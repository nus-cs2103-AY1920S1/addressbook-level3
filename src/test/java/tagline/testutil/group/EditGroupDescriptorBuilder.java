//@@author e0031374
package tagline.testutil.group;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tagline.logic.commands.group.EditGroupCommand.EditGroupDescriptor;
import tagline.model.group.Group;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.model.group.MemberId;


/**
 * A utility class to help with building EditGroupDescriptor objects.
 */
public class EditGroupDescriptorBuilder {

    private EditGroupDescriptor descriptor;

    public EditGroupDescriptorBuilder() {
        descriptor = new EditGroupDescriptor();
    }

    public EditGroupDescriptorBuilder(EditGroupDescriptor descriptor) {
        this.descriptor = new EditGroupDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGroupDescriptor} with fields containing {@code group}'s details
     */
    public EditGroupDescriptorBuilder(Group group) {
        descriptor = new EditGroupDescriptor();
        descriptor.setGroupName(group.getGroupName());
        descriptor.setGroupDescription(group.getGroupDescription());
        descriptor.setMemberIds(group.getMemberIds());
    }

    /**
     * Sets the {@code GroupName} of the {@code EditGroupDescriptor} that we are building.
     */
    public EditGroupDescriptorBuilder withGroupName(String name) {
        descriptor.setGroupName(new GroupName(name));
        return this;
    }

    /**
     * Sets the {@code GroupDescription} of the {@code EditGroupDescriptor} that we are building.
     */
    public EditGroupDescriptorBuilder withGroupDescription(String description) {
        descriptor.setGroupDescription(new GroupDescription(description));
        return this;
    }

    /**
     * Sets the {@code MemberIds} of the {@code EditGroupDescriptor} that we are building.
     */
    public EditGroupDescriptorBuilder withMemberIds(String... ids) {
        Set<MemberId> tagSet = Stream.of(ids).map(MemberId::new).collect(Collectors.toSet());
        descriptor.setMemberIds(tagSet);
        return this;
    }

    public EditGroupDescriptor build() {
        return descriptor;
    }
}
