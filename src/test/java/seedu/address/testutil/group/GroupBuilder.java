package seedu.address.testutil.group;

import seedu.address.model.group.Group;
import seedu.address.model.student.UniqueStudentList;

/**
 * A utility class to help build Groups.
 */
public class GroupBuilder {
    public static final String DEFAULT_GROUP_ID = "TestG01";
    public static final UniqueStudentList DEFAULT_STUDENT_LIST = new UniqueStudentList();

    private UniqueStudentList studentList;
    private String groupId;

    public GroupBuilder() {
        this.studentList = DEFAULT_STUDENT_LIST;
        this.groupId = DEFAULT_GROUP_ID;
    }

    public GroupBuilder(Group groupToCopy) {
        studentList = groupToCopy.getStudentList();
        groupId = groupToCopy.getGroupId();
    }

    /**
     * Sets the {@code groupId} of the {@code Group} that we are building.
     */
    public GroupBuilder withGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    /**
     * Sets the {@code UniqueStudentList} of the {@code Group} that we are building.
     */
    public GroupBuilder withStudentList(UniqueStudentList studentList) {
        this.studentList = studentList;
        return this;
    }

    public Group build() {
        return new Group(groupId, studentList);
    }

}
