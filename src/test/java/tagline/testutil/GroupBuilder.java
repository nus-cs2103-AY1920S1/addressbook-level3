//@@author e0031374
package tagline.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import tagline.model.group.Group;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.model.group.MemberId;
import tagline.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_GROUPNAME = "SHIELD";
    public static final String DEFAULT_GROUPDESCRIPTION = "Strategic Homeland Intervention "
        + "Enforcement Logistics Divison";

    private GroupName groupName;
    private GroupDescription groupDescription;
    private Set<MemberId> memberIds;

    public GroupBuilder() {
        groupName = new GroupName(DEFAULT_GROUPNAME);
        groupDescription = new GroupDescription(DEFAULT_GROUPDESCRIPTION);
        memberIds = new HashSet<>();
    }

    /**
     * Initializes the GroupBuilder with the data of {@code personToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        groupName = groupToCopy.getGroupName();
        memberIds = new HashSet<>(groupToCopy.getMemberIds());
        groupDescription = groupToCopy.getGroupDescription();
    }

    /**
     * Sets the {@code GroupName} of the {@code Group} that we are building.
     */
    public GroupBuilder withGroupName(String groupName) {
        this.groupName = new GroupName(groupName);
        return this;
    }

    /**
     * Sets the {@code GroupDescription} of the {@code Group} that we are building.
     */
    public GroupBuilder withGroupDescription(String groupDescription) {
        this.groupDescription = new GroupDescription(groupDescription);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public GroupBuilder withMemberIds(String ... memberIds) {
        this.memberIds = SampleDataUtil.getMemberIdSet(memberIds);
        return this;
    }

    /**
     * Parses the {@code memberIds} into a {@code Set<MemberIds>} and adds it to the {@code Group} that we are building.
     */
    public GroupBuilder addMemberIds(String ... memberIds) {
        this.memberIds.addAll(SampleDataUtil.getMemberIdSet(memberIds));
        return this;
    }

    /**
     * Parses the {@code memberIds} into a {@code Set<MemberIds>} and removes it to the {@code Group}
     * that we are building.
     */
    public GroupBuilder removeMemberIds(String ... memberIds) {
        this.memberIds = this.memberIds.stream()
            .filter(member -> !SampleDataUtil.getMemberIdSet(memberIds).contains(member))
            .collect(Collectors.toSet());
        return this;
    }

    public Group build() {
        return new Group(groupName, groupDescription, memberIds);
    }

}
