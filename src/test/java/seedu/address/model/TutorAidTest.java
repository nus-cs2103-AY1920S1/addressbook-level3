package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.CS2103T;
import static seedu.address.testutil.TypicalTutorAid.ALICE;
import static seedu.address.testutil.TypicalTutorAid.getTypicalTutorAid;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.note.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TaskBuilder;

public class TutorAidTest {

    private final TutorAid tutorAid = new TutorAid();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), tutorAid.getPersonList());
        assertEquals(Collections.emptyList(), tutorAid.getEarningsList());
        assertEquals(Collections.emptyList(), tutorAid.getTaskList());
        assertEquals(Collections.emptyList(), tutorAid.getCommandsList());
        assertEquals(Collections.emptyList(), tutorAid.getNotesList());
        assertEquals(Collections.emptyList(), tutorAid.getReminderList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tutorAid.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTutorAid_replacesData() {
        TutorAid newData = getTypicalTutorAid();
        tutorAid.resetData(newData);
        assertEquals(newData, tutorAid);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withClassId("Tutorial 7")
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        TutorAidStub newData = new TutorAidStub(newPersons, ALICE);

        assertThrows(DuplicatePersonException.class, () -> tutorAid.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedCS2103T = new TaskBuilder(CS2103T).build();
        List<Task> newTasks = Arrays.asList(CS2103T, editedCS2103T);
        TutorAidStub newData = new TutorAidStub(newTasks, CS2103T);

        assertThrows(DuplicateTaskException.class, () -> tutorAid.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tutorAid.hasPerson(null));
    }

    @Test
    public void hatTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tutorAid.hasTask(null));
    }

    @Test
    public void hasPerson_personNotInTutorAid_returnsFalse() {
        assertFalse(tutorAid.hasPerson(ALICE));
    }

    @Test
    public void hasTask_taskNotInTutorAid_returnsFalse() {
        assertFalse(tutorAid.hasTask(CS2103T));
    }

    @Test
    public void hasPerson_personInTutorAid_returnsTrue() {
        tutorAid.addPerson(ALICE);
        assertTrue(tutorAid.hasPerson(ALICE));
    }

    @Test
    public void hasTask_taskInTutorAid_returnsTrue() {
        tutorAid.addTask(CS2103T);
        assertTrue(tutorAid.hasTask(CS2103T));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInTutorAid_returnsTrue() {
        tutorAid.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withClassId("Tutorial 7")
                .build();
        assertTrue(tutorAid.hasPerson(editedAlice));
    }

    @Test
    public void hasTask_taskWithSameIdentityFieldsInTutorAid_returnsTrue() {
        tutorAid.addTask(CS2103T);
        Task editedCS2103T = new TaskBuilder(CS2103T).build();
        assertTrue(tutorAid.hasTask(editedCS2103T));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> tutorAid.getPersonList().remove(0));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> tutorAid.getTaskList().remove(0));
    }

    @Test
    public void getEarningsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> tutorAid.getEarningsList().remove(0));
    }

    /**
     * A stub ReadOnlyTutorAid whose persons list can violate interface constraints.
     */
    private static class TutorAidStub implements ReadOnlyTutorAid {

        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Earnings> earnings = FXCollections.observableArrayList();
        private final ObservableList<CommandObject> commands = FXCollections.observableArrayList();
        private final ObservableList<Reminder> reminder = FXCollections.observableArrayList();
        private final ObservableList<Notes> notes = FXCollections.observableArrayList();


        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TutorAidStub(Collection<Person> persons, Person person) {
            this.persons.setAll(persons);
        }

        TutorAidStub(Collection<Task> tasks, Task task) {
            this.tasks.setAll(tasks);
        }


        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Earnings> getEarningsList() {
            return earnings;
        }

        @Override
        public ObservableList<CommandObject> getCommandsList() {
            return commands;
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Reminder> getReminderList() {
            return reminder;
        }

        @Override
        public ObservableList<Notes> getNotesList() {
            return notes;
        }
    }

}
