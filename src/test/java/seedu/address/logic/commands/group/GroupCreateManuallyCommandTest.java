package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.group.GroupCommand.GROUP_ALREADY_EXISTS;
import static seedu.address.logic.commands.group.GroupCommand.GROUP_ID_LEFT_EMPTY;
import static seedu.address.logic.commands.group.GroupCreateManuallyCommand.OUT_OF_BOUNDS;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

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

/**
 * Test for GroupCreateManuallyCommand.
 */
public class GroupCreateManuallyCommandTest {

    /**
     * Checks if two GroupCreateManuallyCommands are equal.
     */
    @Test
    public void equals() {
        String groupId = GroupBuilder.DEFAULT_GROUP_ID;
        String otherGroupId = "Other Group";
        ArrayList<Integer> mockNumbers =
                new ArrayList<Integer>(Arrays.asList(1));
        GroupCreateManuallyCommand createManuallyCommand = new GroupCreateManuallyCommand(groupId, mockNumbers);
        GroupCreateManuallyCommand otherCreateManuallyCommand =
                new GroupCreateManuallyCommand(otherGroupId, mockNumbers);
        // same object -> returns true
        assertTrue(createManuallyCommand.equals(createManuallyCommand));

        // same values -> returns true
        GroupCreateManuallyCommand createManuallyCommandCopy = new GroupCreateManuallyCommand(groupId, mockNumbers);
        assertTrue(createManuallyCommand.equals(createManuallyCommandCopy));

        // different types -> returns false
        assertFalse(createManuallyCommand.equals(1));

        // null -> returns false
        assertFalse(createManuallyCommand.equals(null));

        // different group -> returns false
        assertFalse(createManuallyCommand.equals(otherCreateManuallyCommand));
    }

    /**
     * Test for creating a group with one student successfully.
     */
    @Test
    public void execute_createGroup_success() throws Exception {
        ArrayList<Integer> mockNumbers =
                new ArrayList<Integer>(Arrays.asList(1));
        GroupCreateManuallyCommand groupCreateManuallyCommand =
                new GroupCreateManuallyCommand("Create", mockNumbers);
        Student student = new StudentBuilder().withName(new Name("CreateTest")).build();
        ModelStub modelStub =
                new GroupCreateManuallyCommandTest.ModelStubWithGroupWithStudent("CreateCopy", student);
        CommandResult commandResult = groupCreateManuallyCommand.execute(modelStub);
        assertEquals(String.format(groupCreateManuallyCommand.CREATED_SUCCESSFULLY_WITH_ONE, "Create", 1),
                commandResult.getFeedbackToUser());
    }

    /**
     * Test for creating group unsuccessfully, due to duplicate group ID.
     */
    @Test
    public void execute_createDuplicateGroup_throwsCommandException() {
        ArrayList<Integer> mockNumbers =
                new ArrayList<Integer>(Arrays.asList(1));
        GroupCreateManuallyCommand groupCreateManuallyCommand =
                new GroupCreateManuallyCommand("CantCreate", mockNumbers);
        Student student = new StudentBuilder().withName(new Name("CreateFail")).build();
        ModelStub modelStub = new ModelStubWithGroupWithStudent("CantCreate", student);
        assertThrows(CommandException.class, () -> groupCreateManuallyCommand.execute(modelStub),
                String.format(GROUP_ALREADY_EXISTS, "CantCreate"));
    }

    /**
     * Test for creating group unsuccessfully, due to student number not present in student list.
     */
    @Test
    public void execute_createGroupWithInvalidStudentNumber_throwsCommandException() {
        ArrayList<Integer> mockNumbers =
                new ArrayList<Integer>(Arrays.asList(5));
        GroupCreateManuallyCommand groupCreateManuallyCommand =
                new GroupCreateManuallyCommand("OutOfBounds", mockNumbers);
        Student student = new StudentBuilder().withName(new Name("OutOfBounds")).build();
        ModelStub modelStub = new ModelStubWithGroupWithStudent("Normal", student);
        assertThrows(CommandException.class, () -> groupCreateManuallyCommand.execute(modelStub), OUT_OF_BOUNDS);
    }

    /**
     * Test for creating group unsuccessfully, due to group ID not present.
     */
    @Test
    public void execute_createGroupWithMissingGroupId_throwsCommandException() {
        ArrayList<Integer> mockNumbers =
                new ArrayList<Integer>(Arrays.asList(1));
        GroupCreateManuallyCommand groupCreateManuallyCommand =
                new GroupCreateManuallyCommand("", mockNumbers);
        Student student = new StudentBuilder().withName(new Name("MissingGroupID")).build();
        ModelStub modelStub = new ModelStubWithGroupWithStudent("NormalTwo", student);
        assertThrows(CommandException.class, () -> groupCreateManuallyCommand.execute(modelStub), GROUP_ID_LEFT_EMPTY);
    }

    /**
     * A Model stub that contains a single group with a single student.
     */
    private class ModelStubWithGroupWithStudent extends ModelStub {

        private final ListOfGroups listOfGroups;
        private final StudentRecord studentRecord;
        private final FilteredList<Student> filteredStudents;


        /**
         * Creates an instance of ModelStubWithGroupWithStudent.
         *
         * @param groupId GroupId of the group.
         * @param student Student within the group.
         */
        ModelStubWithGroupWithStudent(String groupId, Student student) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            this.listOfGroups = new ListOfGroups();
            group.addStudent(student);
            studentRecord = new StudentRecord();
            studentRecord.addStudent(student);
            listOfGroups.addGroup(group);
            this.filteredStudents = new FilteredList<>(this.studentRecord.getStudentList());
        }

        /**
         * Checks if a group exists.
         *
         * @param groupId GroupId of the group.
         * @return true if the group exists, false otherwise.
         */
        @Override
        public boolean checkGroupExists(String groupId) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            return listOfGroups.contains(group);
        }

        /**
         * Creates a group.
         *
         * @param groupId        GroupId of the group to be created.
         * @param studentNumbers Student Numbers of the students to be added to the newly created group.
         */
        @Override
        public void createGroupManually(String groupId, ArrayList<Integer> studentNumbers) {
            Group group = new Group(groupId);

            ArrayList<Student> students = new ArrayList<>();
            for (Integer i : studentNumbers) {
                students.add(filteredStudents.get(i - 1));
            }

            for (Student s : students) {
                group.addStudent(s);
            }

            listOfGroups.addGroup(group);
        }

        /**
         * Gets a filtered list of the students stored.
         *
         * @return Filtered list of the students stored.
         */
        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return studentRecord.getStudentList();
        }


    }

}
