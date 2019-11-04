package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.group.GroupCommand.GROUP_DOES_NOT_EXIST;
import static seedu.address.logic.commands.group.GroupCommand.GROUP_ID_LEFT_EMPTY;
import static seedu.address.logic.commands.group.GroupCommand.GROUP_INDEX_OUT_OF_BOUNDS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.ListOfGroups;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.group.GroupBuilder;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.student.StudentBuilder;

/**
 * Test for GroupRemoveStudentCommand.
 */
public class GroupRemoveStudentCommandTest {

    /**
     * Tests if two GroupRemoveStudentCommands are equal.
     */
    @Test
    public void equals() {
        String groupId = GroupBuilder.DEFAULT_GROUP_ID;
        String otherGroupId = "Other Group";
        GroupRemoveStudentCommand removeStudentCommand =
                new GroupRemoveStudentCommand(groupId, 1);
        GroupRemoveStudentCommand otherRemoveStudentCommand =
                new GroupRemoveStudentCommand(otherGroupId, 1);
        // same object -> returns true
        assertTrue(removeStudentCommand.equals(removeStudentCommand));

        // same values -> returns true
        GroupRemoveStudentCommand removeStudentCommandCopy = new GroupRemoveStudentCommand(groupId, 1);
        assertTrue(removeStudentCommand.equals(removeStudentCommandCopy));

        // different types -> returns false
        assertFalse(removeStudentCommand.equals(1));

        // null -> returns false
        assertFalse(removeStudentCommand.equals(null));

        // different group -> returns false
        assertFalse(removeStudentCommand.equals(otherRemoveStudentCommand));
    }

    /**
     * Test for removing a student from a group successfully.
     */
    @Test
    public void execute_removeExistingStudentFromGroup_success() throws Exception {
        GroupRemoveStudentCommand groupRemoveStudentCommand =
                new GroupRemoveStudentCommand("Remove", 1);
        Student student = new StudentBuilder().withName(new Name("RemoveTest")).build();
        ModelStub modelStub =
                new GroupRemoveStudentCommandTest.ModelStubWithGroupWithStudent("Remove", student);
        CommandResult commandResult = groupRemoveStudentCommand.execute(modelStub);
        assertEquals(String.format(GroupRemoveStudentCommand.MESSAGE_SUCCESS, 1, "Remove"),
                commandResult.getFeedbackToUser());
    }

    /**
     * Test for removing a student unsuccessfully because group index number is out of bounds.
     */
    @Test
    public void execute_removeGroupIndexNumberOutOfBounds_throwsCommandException() {
        GroupRemoveStudentCommand groupRemoveStudentCommand =
                new GroupRemoveStudentCommand("RemoveFail", 20);
        Student student = new StudentBuilder().withName(new Name("RemoveFailure")).build();
        ModelStub modelStub =
                new GroupRemoveStudentCommandTest.ModelStubWithGroupWithStudent("RemoveFail", student);
        assertThrows(CommandException.class, () -> groupRemoveStudentCommand.execute(modelStub),
                GROUP_INDEX_OUT_OF_BOUNDS);
    }

    /**
     * Test for removing a student unsuccessfully because group with group ID specified is not found.
     */
    @Test
    public void execute_removeStudentGroupNotFound_throwsCommandException() {
        GroupRemoveStudentCommand groupRemoveStudentCommand =
                new GroupRemoveStudentCommand("GroupNotFound", 1);
        Student student = new StudentBuilder().withName(new Name("NotFoundGroup")).build();
        ModelStub modelStub =
                new GroupRemoveStudentCommandTest.ModelStubWithGroupWithStudent("RemoveFail", student);
        assertThrows(CommandException.class, () -> groupRemoveStudentCommand.execute(modelStub),
                String.format(GROUP_DOES_NOT_EXIST, "GroupNotFound"));
    }

    /**
     * Test for removing from group unsuccessfully, due to group ID not present.
     */
    @Test
    public void execute_removeStudentFromGroupWithMissingGroupId_throwsCommandException() {
        GroupRemoveStudentCommand groupRemoveStudentCommand =
                new GroupRemoveStudentCommand("", 1);
        Student student = new StudentBuilder().withName(new Name("MissingGroupIDRemove")).build();
        ModelStub modelStub = new ModelStubWithGroupWithStudent("NormalTwo", student);
        assertThrows(CommandException.class, () -> groupRemoveStudentCommand.execute(modelStub), GROUP_ID_LEFT_EMPTY);
    }

    /**
     * A Model stub that contains a single group with a single student.
     */
    private class ModelStubWithGroupWithStudent extends ModelStub {

        private final ListOfGroups listOfGroups;

        /**
         * Creates a ModelStubWithGroupWithStudent instance.
         *
         * @param groupId GroupId of the group.
         * @param student Student within the group.
         */
        ModelStubWithGroupWithStudent(String groupId, Student student) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            this.listOfGroups = new ListOfGroups();
            group.addStudent(student);
            listOfGroups.addGroup(group);
        }

        /**
         * Check if a group exists.
         *
         * @param groupId GroupId of the group to check.
         * @return true if the group exists, false otherwise.
         */
        @Override
        public boolean checkGroupExists(String groupId) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            return listOfGroups.contains(group);
        }

        /**
         * Gets the size of a group.
         *
         * @param groupId GroupId of the group to be checked.
         * @return Size of the group.
         */
        @Override
        public int getGroupSize(String groupId) {
            int groupSize = 0;
            int groupIndex = listOfGroups.getGroupIndex(groupId);
            if (groupIndex != -1) {
                Group group = listOfGroups.getGroup(groupIndex);
                groupSize = group.getObservableListStudents().size();
            }
            return groupSize;
        }

        /**
         * Removes student from a group.
         *
         * @param groupId          GroupId of the group.
         * @param groupIndexNumber Group Index Number of the student to be removed.
         */
        @Override
        public void removeStudentFromGroup(String groupId, int groupIndexNumber) {
            int groupIndex = listOfGroups.getGroupIndex(groupId);
            if (groupIndex != -1) {
                Group group = listOfGroups.getGroup(groupIndex);
                group.removeStudent(groupIndexNumber);
            }
        }
    }

}
