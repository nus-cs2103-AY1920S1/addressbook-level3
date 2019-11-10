package seedu.address.model.classroom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClassrooms.getTypicalClassroom;
import static seedu.address.testutil.TypicalStudents.ALICE;
import java.util.Arrays;
import java.util.Collection;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;

import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.testutil.StudentBuilder;

public class ClassroomTest {

    private final Classroom classroom = new Classroom();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), classroom.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> classroom.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyClassroom_replacesData() {
        Classroom newData = getTypicalClassroom().get(0);
        classroom.resetData(newData);
        assertEquals(newData, classroom);
    }


    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        ClassroomStub newData = new ClassroomStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> classroom.resetData(newData));
    }


    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> classroom.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInClassroom_returnsFalse() {
        assertFalse(classroom.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInClassroom_returnsTrue() {
        classroom.addStudent(ALICE);
        assertTrue(classroom.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInClassroom_returnsTrue() {
        classroom.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(classroom.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> classroom.getStudentList().remove(0));
    }

    /**
     * A stub ReadOnlyClassroom whose students list can violate interface constraints.
     */
    private static class ClassroomStub implements ReadOnlyClassroom {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Assignment> assignments = FXCollections.observableArrayList();
        private String classroomName = "My First Classroom";

        ClassroomStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public String getClassroomName() {
            return classroomName;
        }

        @Override
        public void setClassroomName(String classroomName) {
            this.classroomName = classroomName;
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Assignment> getAssignmentList() {
            return assignments;
        }
    }

}
