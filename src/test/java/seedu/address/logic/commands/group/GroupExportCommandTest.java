package seedu.address.logic.commands.group;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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


import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.group.GroupCommand.GROUP_DOES_NOT_EXIST;
import static seedu.address.logic.commands.group.GroupCommand.GROUP_ID_LEFT_EMPTY;
import static seedu.address.logic.commands.group.GroupExportCommand.MESSAGE_SUCCESS;

public class GroupExportCommandTest {

    @Test
    public void equals() {
        String groupId = GroupBuilder.DEFAULT_GROUP_ID;
        String otherGroupId = "Other Group";
        GroupExportCommand exportCommand = new GroupExportCommand(groupId);
        GroupExportCommand otherExportCommand = new GroupExportCommand(otherGroupId);
        // same object -> returns true
        assertTrue(exportCommand.equals(exportCommand));

        // same values -> returns true
        GroupExportCommand exportCommandCopy = new GroupExportCommand(groupId);
        assertTrue(exportCommand.equals(exportCommandCopy));

        // different types -> returns false
        assertFalse(exportCommand.equals(1));

        // null -> returns false
        assertFalse(exportCommand.equals(null));

        // different group -> returns false
        assertFalse(exportCommand.equals(otherExportCommand));
    }

    /**
     * Test for exporting a group of students successfully.
     */
    @Test
    public void execute_exportGroup_Success() throws Exception {
        GroupExportCommand exportCommand = new GroupExportCommand("Export");
        Student student = new StudentBuilder().withName(new Name("ExportTest")).build();
        ModelStub modelStub = new GroupExportCommandTest.ModelStubWithGroupWithStudent("Export", student);
        CommandResult commandResult = exportCommand.execute(modelStub);
        assertEquals(String.format(MESSAGE_SUCCESS,"Export"), commandResult.getFeedbackToUser());
    }

    /**
     * Test for exporting a group of students unsuccessfully, as group with matching group id is not present.
     */
    @Test
    public void execute_exportGroupInvalidGroupId_throwsCommandException() throws Exception {
        GroupExportCommand exportCommand = new GroupExportCommand("ExportNotFound");
        Student student = new StudentBuilder().withName(new Name("ExportTestTwo")).build();
        ModelStub modelStub = new GroupExportCommandTest.ModelStubWithGroupWithStudent("GetTwo", student);
        assertThrows(CommandException.class, () -> exportCommand.execute(modelStub), String.format(GROUP_DOES_NOT_EXIST, "ExportNotFound"));
    }

    /**
     * Test for exporting a group of students unsuccessfully, as group ID is empty.
     */
    @Test
    public void execute_exportGroupEmptyGroupId_throwsCommandException() throws Exception {
        GroupExportCommand exportCommand = new GroupExportCommand("");
        Student student = new StudentBuilder().withName(new Name("ExportTestThree")).build();
        ModelStub modelStub = new GroupExportCommandTest.ModelStubWithGroupWithStudent("ExportThree", student);
        assertThrows(CommandException.class, () -> exportCommand.execute(modelStub), GROUP_ID_LEFT_EMPTY);
    }


    /**
     * A Model stub that contains a single group with a single student.
     */
    private class ModelStubWithGroupWithStudent extends ModelStub {

        private final ListOfGroups listOfGroups;
        private final StudentRecord studentRecord;
        private final FilteredList<Student> filteredStudents;


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

        @Override
        public boolean checkGroupExists(String groupId) {
            requireNonNull(groupId);
            Group group = new GroupBuilder().withGroupId(groupId).build();
            return listOfGroups.contains(group);
        }


        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return studentRecord.getStudentList();
        }

        @Override
        public void exportGroup(String groupId) {
            listOfGroups.exportGroup(groupId);
        }

    }
}
