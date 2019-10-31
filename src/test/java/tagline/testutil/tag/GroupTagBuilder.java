package tagline.testutil.tag;

import tagline.model.group.GroupName;
import tagline.model.tag.GroupTag;

/**
 * A utility class to help with building Tag objects.
 */
public class GroupTagBuilder {

    public static final GroupName DEFAULT_GROUP_NAME = new GroupName("group-name");

    private GroupName groupName;

    public GroupTagBuilder() {
        this.groupName = DEFAULT_GROUP_NAME;
    }

    /**
     * Sets the {@code GroupName} of {@code GroupTag} to be that we are building.
     */
    public GroupTagBuilder withGroupName(String groupName) {
        this.groupName = new GroupName(groupName);
        return this;
    }

    public GroupTag build() {
        return new GroupTag(groupName);
    }
}
