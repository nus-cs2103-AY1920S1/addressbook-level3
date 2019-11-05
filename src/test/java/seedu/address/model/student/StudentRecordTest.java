package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.student.TypicalStudents.getTypicalStudentRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.testutil.student.StudentBuilder;
import seedu.address.testutil.student.TypicalStudents;

/**
 * Test for the StudentRecord model.
 */
public class StudentRecordTest {
    private final StudentRecord studentRecord = new StudentRecord();
    private final Student student = TypicalStudents.STUDENT_ONE;

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), studentRecord.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studentRecord.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyStudentRecord_replacesData() {
        StudentRecord newData = getTypicalStudentRecord();
        studentRecord.resetData(newData);
        assertEquals(newData, studentRecord);
    }

    @Test
    public void resetData_withDuplicateStudent_throwsDuplicateStudentException() {
        Student editedStudent = new StudentBuilder(student).withMark(true).build();
        List<Student> newStudents = Arrays.asList(student, editedStudent);
        StudentRecordTest.StudentRecordStub newData = new StudentRecordTest.StudentRecordStub(newStudents);
        assertThrows(DuplicateStudentException.class, () -> studentRecord.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studentRecord.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInStudentRecord_returnsFalse() {
        assertFalse(studentRecord.hasStudent(student));
    }

    @Test
    public void hasStudent_studentInStudentRecord_returnsTrue() {
        studentRecord.addStudent(student);
        assertTrue(studentRecord.hasStudent(student));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInStudentRecord_returnsTrue() {
        studentRecord.addStudent(student);
        Student editedStudent = new StudentBuilder(student).withMark(true).build();
        assertTrue(studentRecord.hasStudent(editedStudent));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> studentRecord.getStudentList().remove(0));
    }

    /**
     * A stub ReadOnlyStudentRecord whose student list can violate interface constraints.
     */
    private static class StudentRecordStub implements ReadOnlyStudentRecord {
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        StudentRecordStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }
}
