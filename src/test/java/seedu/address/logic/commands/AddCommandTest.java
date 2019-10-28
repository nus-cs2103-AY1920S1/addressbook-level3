package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.IntervieweeList;
import seedu.address.model.InterviewerList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyIntervieweeList;
import seedu.address.model.ReadOnlyInterviewerList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.Schedule;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        /* TODO: REMOVE THE FOLLOWING LINES AFTER THEIR USAGE IS REMOVED */
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        public void deletePerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        public Person getPerson(String name) throws NoSuchElementException {
            throw new AssertionError("This method should not be called.");
        }

        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        /* TODO: REMOVE ABOVE LINES */

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getIntervieweeListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIntervieweeListFilePath(Path intervieweeListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInterviewerListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInterviewerListFilePath(Path interviewerListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void emailInterviewee(Interviewee interviewee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSchedulesList(List<Schedule> schedulesList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public LinkedList<Schedule> getSchedulesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<ObservableList<ObservableList<String>>> getObservableLists() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<List<String>> getTitlesLists() {
            // TODO: Implementation
            return null;
        }

        @Override
        public List<Slot> getInterviewSlots(String intervieweeName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getInterviewerSchedule(Interviewer interviewer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addInterviewerSchedule(Interviewer interviewer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIntervieweeList(List<Interviewee> interviewees) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInterviewerList(List<Interviewer> interviewers) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Interviewee> getInterviewees() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Interviewer> getInterviewers() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Interviewee getInterviewee(String name) throws NoSuchElementException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInterviewee(Interviewee target, Interviewee editedInterviewee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Interviewer getInterviewer(String name) throws NoSuchElementException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInterviewer(Interviewer target, Interviewer editedInterviewer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInterviewee(Interviewee interviewee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInterviewer(Interviewer interviewer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteInterviewee(Interviewee target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteInterviewer(Interviewer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addInterviewee(Interviewee interviewee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addInterviewer(Interviewer interviewer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyIntervieweeList getIntervieweeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInterviewerList getInterviewerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Interviewee> getObservableIntervieweeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Interviewer> getObservableInterviewerList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyIntervieweeList getIntervieweeList() {
            return new IntervieweeList();
        }

        @Override
        public ReadOnlyInterviewerList getInterviewerList() {
            return new InterviewerList();
        }
    }

}
