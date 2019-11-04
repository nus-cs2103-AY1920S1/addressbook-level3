package seedu.address.logic.commands.group;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.ListOfGroups;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.testutil.group.GroupBuilder;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.student.StudentBuilder;
import seedu.address.testutil.student.TypicalStudents;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.group.GroupCommand.*;

public class GroupAddStudentCommandTest {

    @Test
    public void equals() {
        String groupId = GroupBuilder.DEFAULT_GROUP_ID;
        String otherGroupId = "Other Group";
        GroupAddStudentCommand addStudentCommand = new GroupAddStudentCommand(groupId, 1, 1);
        GroupAddStudentCommand otherAddStudentCommand = new GroupAddStudentCommand(otherGroupId, 1, 1);
        // same object -> returns true
        assertTrue(addStudentCommand.equals(addStudentCommand));

        // same values -> returns true
        GroupAddStudentCommand addStudentCommandCopy = new GroupAddStudentCommand(groupId, 1, 1);
        assertTrue(addStudentCommand.equals(addStudentCommandCopy));

        // different types -> returns false
        assertFalse(addStudentCommand.equals(1));

        // null -> returns false
        assertFalse(addStudentCommand.equals(null));

        // different group -> returns false
        assertFalse(addStudentCommand.equals(otherAddStudentCommand));
    }

    /**
     * Test for adding to group unsuccessfully, due to duplicate student present in group.
     */
    @Test
    public void execute_duplicateStudentToGroup_throwsCommandException() {
        GroupAddStudentCommand groupAddStudentCommand = new GroupAddStudentCommand("groupID", 4, 1);
        ModelStub modelStub = new ModelStubWithGroupWithStudent("groupID", 4, 1);
        Student student = modelStub.getStudent(3);
        assertThrows(CommandException.class, () -> groupAddStudentCommand.execute(modelStub), String.format(STUDENT_EXISTS_IN_GROUP,student));
    }

    /**
     * Test for adding to group successfully.
     */
    @Test
    public void execute_addUniqueStudentToGroup_Success() throws Exception{
        GroupAddStudentCommand groupAddStudentCommand = new GroupAddStudentCommand("Success", 4, 1);
        ModelStub modelStub = new ModelStubWithGroupWithStudent("Success", 3, 1);
        Student student = modelStub.getStudent(4);
        CommandResult commandResult = groupAddStudentCommand.execute(modelStub);
        assertEquals(String.format(GroupAddStudentCommand.MESSAGE_SUCCESS,4,"Success"), commandResult.getFeedbackToUser());
    }

    /**
     * Test for adding to group unsuccessfully, due to student number out of bounds.
     */
    @Test
    public void execute_addStudentNumberOutOfBounds_throwsCommandException(){
        GroupAddStudentCommand groupAddStudentCommand = new GroupAddStudentCommand("groupID", 20, 1);
        ModelStub modelStub = new ModelStubWithGroupWithStudent("groupID", 2, 1);
        assertThrows(CommandException.class, () -> groupAddStudentCommand.execute(modelStub), STUDENT_NUMBER_OUT_OF_BOUNDS);
    }

    /**
     * Test for adding to group unsuccessfully, due to group index number out of bounds.
     */
    @Test
    public void execute_addGroupIndexNumberOutOfBounds_throwsCommandException(){
        GroupAddStudentCommand groupAddStudentCommand = new GroupAddStudentCommand("groupID", 3, 20);
        ModelStub modelStub = new ModelStubWithGroupWithStudent("groupID", 1, 1);
        assertThrows(CommandException.class, () -> groupAddStudentCommand.execute(modelStub), GROUP_INDEX_OUT_OF_BOUNDS);
    }

    /**
     * Test for adding to group unsuccessfully, due to group ID not present.
     */
    @Test
    public void execute_addStudentToGroupWithMissingGroupId_throwsCommandException(){
        GroupAddStudentCommand groupAddStudentCommand = new GroupAddStudentCommand("",1,1);
        Student student = new StudentBuilder().withName(new Name("MissingGroupIDAdd")).build();
        ModelStub modelStub = new ModelStubWithGroupWithStudent("NormalThree", student);
        assertThrows(CommandException.class, () -> groupAddStudentCommand.execute(modelStub),GROUP_ID_LEFT_EMPTY);
    }



    /**
     * A Model stub that contains a single group.
     */
    private class ModelStubWithGroupWithStudent extends ModelStub {
        private final ListOfGroups listOfGroups;
        private final StudentRecord studentRecord;

        ModelStubWithGroupWithStudent(String groupId, int studentNumber, int groupIndexNumber) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            this.listOfGroups = new ListOfGroups();
            this.studentRecord = new StudentRecord(TypicalStudents.getTypicalStudentRecord());
            Student student = studentRecord.getStudent(studentNumber-1);
            group.addStudent(groupIndexNumber, student);
            listOfGroups.addGroup(group);
        }

        ModelStubWithGroupWithStudent(String groupId, Student student) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            this.listOfGroups = new ListOfGroups();
            this.studentRecord = new StudentRecord();
            studentRecord.addStudent(student);
            group.addStudent(student);
            listOfGroups.addGroup(group);
        }

        @Override
        public Student getStudent(int index) {
            return studentRecord.getStudent(index);
        }

        @Override
        public boolean checkGroupExists(String groupId) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            return listOfGroups.contains(group);
        }

        @Override
        public boolean checkStudentExistInGroup(String groupId, Student student) {
            ArrayList<Group> groupArrayList = listOfGroups.getGroupList();
            Group queriedGroup = null;
            for (Group group : groupArrayList) {
                if (group.getGroupId().equals(groupId)) {
                    queriedGroup = group;
                    break;
                }
            }
            return queriedGroup.checkStudentExist(student);
        }

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

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return studentRecord.getStudentList();
        }


        @Override
        public boolean addStudentToGroup(String groupId, int studentNumber, int groupIndexNumber) {
            int studentIndex = studentNumber;
            Student student = studentRecord.getStudent(studentIndex);

            int groupIndex = listOfGroups.getGroupIndex(groupId);
            if (groupIndex != -1) {
                Group group = listOfGroups.getGroup(groupIndex);
                return group.addStudent(groupIndexNumber, student);
            }
            return false;
        }
    }
}
