package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_QUESTION;
import static seedu.address.testutil.TypicalPersons.ALICE_RESULT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.note.Note;
import seedu.address.model.note.exceptions.DuplicateTitleException;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskForNote;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getNoteList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two notes with the same identity fields
        Note editedAlice = new PersonBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        List<Note> newNotes = Arrays.asList(ALICE, editedAlice);

        LocalDate date = LocalDate.parse("06/07/2019", Task.FORMAT_USER_INPUT_DATE);
        LocalTime time = LocalTime.parse("1500", Task.FORMAT_USER_INPUT_TIME);
        Task task = new TaskForNote(editedAlice, date, time);
        List<Task> newTasks = Arrays.asList(task);

        List<Question> newQuestions = Arrays.asList(ALICE_QUESTION);
        List<QuizResult> newQuizResults = Arrays.asList(ALICE_RESULT);
        AddressBookStub newData = new AddressBookStub(newNotes, newTasks, newQuestions, newQuizResults);

        assertThrows(DuplicateTitleException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasNote(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasNote(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addNote(ALICE);
        assertTrue(addressBook.hasNote(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addNote(ALICE);
        Note editedAlice = new PersonBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        assertTrue(addressBook.hasNote(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getNoteList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose notes list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Note> notes = FXCollections.observableArrayList();
        private final ObservableList<Question> questions = FXCollections.observableArrayList();
        private final ObservableList<Question> quizQuestions = FXCollections.observableArrayList();
        private final ObservableList<QuizResult> quizResults = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        AddressBookStub(Collection<Note> notes, Collection<Task> tasks, Collection<Question> questions,
                        Collection<QuizResult> quizResults) {
            this.notes.setAll(notes);
            this.tasks.setAll(tasks);
            this.questions.setAll(questions);
            this.quizResults.setAll(quizResults);
        }

        @Override
        public ObservableList<Note> getNoteList() {
            return notes;
        }

        @Override
        public ObservableList<Question> getQuestionList() {
            return questions;
        }

        @Override
        public ObservableList<Question> getQuizQuestionList() {
            return quizQuestions;
        }

        @Override
        public ObservableList<QuizResult> getQuizResultList() {
            return quizResults;
        }

        /**
         * Returns an unmodifiable view of the task list.
         */
        @Override
        public List<Task> getTaskList() {
            return tasks;
        }
    }
}
