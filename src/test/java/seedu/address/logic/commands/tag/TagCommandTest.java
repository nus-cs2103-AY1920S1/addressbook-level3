package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.tag.TagCommand.MESSAGE_NO_NEW_TAGS;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.student.StudentBuilder;

/**
 * Test for TagCommand.
 */
public class TagCommandTest {

    /**
     * Adds tag unsuccessfully as the student index is out of bounds.
     */
    @Test
    public void execute_addTagOutOfBounds_throwsCommandException() throws Exception {
        TagCommand tagCommand = new TagCommand(Index.fromOneBased(2));
        Student student = new StudentBuilder().withName(new Name("OutOfBoundsTag")).build();
        ModelStub modelStub = new ModelStubWithStudent(student);
        assertThrows(CommandException.class, () -> tagCommand.execute(modelStub),
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Adds tag successfully.
     */
    @Test
    public void execute_addTag_success() throws Exception {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("TestTag"));
        TagCommand tagCommand = new TagCommand(Index.fromOneBased(1), tagSet);
        Student student = new StudentBuilder().withName(new Name("TagTest")).build();
        ModelStub modelStub = new ModelStubWithStudent(student);
        CommandResult commandResult = tagCommand.execute(modelStub);
        assertEquals(String.format("Updated student: %1$s", student), commandResult.getFeedbackToUser());
    }

    /**
     * Adds tag unsuccessfully as there is no update to the tags of the student.
     */
    @Test
    public void execute_addTagNoUpdateTag_throwsCommandException() throws Exception {
        TagCommand tagCommand = new TagCommand(Index.fromOneBased(1));
        Student student = new StudentBuilder().withName(new Name("TagTest")).build();
        ModelStub modelStub = new ModelStubWithStudent(student);
        assertThrows(CommandException.class, () -> tagCommand.execute(modelStub), MESSAGE_NO_NEW_TAGS);
    }


    @Test
    public void equals() {
        TagCommand tagCommand = new TagCommand(Index.fromOneBased(1));
        TagCommand otherTagCommand = new TagCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(tagCommand.equals(tagCommand));

        // same values -> returns true
        TagCommand tagCommandCopy = new TagCommand(Index.fromOneBased(1));
        assertTrue(tagCommand.equals(tagCommandCopy));

        // different types -> returns false
        assertFalse(tagCommand.equals(1));

        // null -> returns false
        assertFalse(tagCommand.equals(null));

        // different note -> returns false
        assertFalse(tagCommand.equals(otherTagCommand));
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;
        private final StudentRecord studentRecord;
        private final FilteredList<Student> filteredStudents;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
            this.studentRecord = new StudentRecord();
            studentRecord.addStudent(student);
            this.filteredStudents = new FilteredList<>(this.studentRecord.getStudentList());
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return studentRecord.getStudentList();
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            requireAllNonNull(target, editedStudent);
            studentRecord.setStudent(target, editedStudent);
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            requireNonNull(predicate);
            filteredStudents.setPredicate(predicate);
        }

        @Override
        public boolean getIsMarked(Student student) {
            return student.getIsMarked();
        }

        @Override
        public void setStudentWithIndex(Index index, Student student) {
            requireAllNonNull(index, student);
            studentRecord.setStudentWithIndex(index, student);
        }

        @Override
        public Optional<Index> getIndexFromStudentList(Student student) {
            return studentRecord.getIndex(student);
        }

    }


}
