package mams.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import mams.logic.commands.CommandTestUtil;
import mams.model.module.Module;
import mams.model.student.Student;
import mams.model.student.exceptions.DuplicateStudentException;
import mams.testutil.Assert;
import mams.testutil.StudentBuilder;
import mams.testutil.TypicalStudents;


public class MamsTest {

    private final Mams mams = new Mams();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), mams.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> mams.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMams_replacesData() {
        Mams newData = TypicalStudents.getTypicalMams();
        mams.resetData(newData);
        assertEquals(newData, mams);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withMatricId(CommandTestUtil.VALID_MATRICID_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        List<Student> newStudents = Arrays.asList(TypicalStudents.ALICE, editedAlice);
        MamsStub newData = new MamsStub(newStudents);

        Assert.assertThrows(DuplicateStudentException.class, () -> mams.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> mams.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInMams_returnsFalse() {
        assertFalse(mams.hasStudent(TypicalStudents.ALICE));
    }

    @Test
    public void hasStudent_studentInMams_returnsTrue() {
        mams.addStudent(TypicalStudents.ALICE);
        assertTrue(mams.hasStudent(TypicalStudents.ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInMams_returnsTrue() {
        mams.addStudent(TypicalStudents.ALICE);
        Student editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withMatricId(CommandTestUtil.VALID_MATRICID_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(mams.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> mams.getStudentList().remove(0));
    }

    /**
     * A stub ReadOnlyMams whose students list can violate interface constraints.
     */
    private static class MamsStub implements ReadOnlyMams {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Module> modules = FXCollections.observableArrayList();

        MamsStub(Collection<Student> students) {
            this.students.setAll(students);
            this.modules.setAll(modules);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }
    }
}
