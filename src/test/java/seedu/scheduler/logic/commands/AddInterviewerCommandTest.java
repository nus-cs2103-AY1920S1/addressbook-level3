package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.BOB_INTERVIEWEE_MANUAL;

import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.scheduler.commons.core.GuiSettings;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.IntervieweeList;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ReadAndWriteList;
import seedu.scheduler.model.ReadOnlyUserPrefs;
import seedu.scheduler.model.Schedule;
import seedu.scheduler.model.person.InterviewSlot;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;
import seedu.scheduler.ui.RefreshListener;

class AddInterviewerCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddInterviewerCommand(null));
    }

    @Test
    public void execute_interviewerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingInterviewerAdded modelStub = new ModelStubAcceptingInterviewerAdded();
        Interviewer validInterviewer = ALICE_INTERVIEWER;

        CommandResult commandResult = new AddInterviewerCommand(validInterviewer).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validInterviewer), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validInterviewer), modelStub.interviewersAdded);
    }

    @Test
    public void execute_duplicateInterviewer_throwsCommandException() {
        Interviewer validInterviewer = ALICE_INTERVIEWER;
        AddCommand addCommand = new AddInterviewerCommand(validInterviewer);
        ModelStubWithInterviewer modelStub = new ModelStubWithInterviewer(validInterviewer);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Interviewee alice = ALICE_INTERVIEWEE;
        Interviewee bob = BOB_INTERVIEWEE_MANUAL;

        AddCommand addAliceCommand = new AddIntervieweeCommand(alice);
        AddCommand addBobCommand = new AddIntervieweeCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddIntervieweeCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different interviewee -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithInterviewer extends ModelStub {
        private final Interviewer interviewer;

        ModelStubWithInterviewer(Interviewer interviewer) {
            requireNonNull(interviewer);
            this.interviewer = interviewer;
        }

        @Override
        public boolean hasInterviewer(Interviewer interviewer) {
            requireNonNull(interviewer);
            return this.interviewer.isSamePerson(interviewer);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingInterviewerAdded extends ModelStub {
        final ArrayList<Interviewer> interviewersAdded = new ArrayList<>();

        @Override
        public boolean hasInterviewer(Interviewer interviewer) {
            requireNonNull(interviewer);
            return interviewersAdded.stream().anyMatch(interviewer::isSamePerson);
        }

        @Override
        public void addInterviewer(Interviewer interviewer) {
            requireNonNull(interviewer);
            interviewersAdded.add(interviewer);
        }

        @Override
        public ReadAndWriteList<Interviewee> getMutableIntervieweeList() {
            return new IntervieweeList();
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setScheduled(boolean scheduled) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isScheduled() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEmptyScheduleList() throws ParseException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Schedule> getEmptyScheduleList() {
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
        public void setIntervieweeListFilePath(Path intervieweeListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInterviewerListFilePath(Path interviewerListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getIntervieweeListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInterviewerListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadAndWriteList<Interviewee> getMutableIntervieweeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadAndWriteList<Interviewer> getMutableInterviewerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Interviewee> getFilteredIntervieweeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Interviewer> getFilteredInterviewerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Interviewee> getUnfilteredIntervieweeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Interviewer> getUnfilteredInterviewerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredIntervieweeList(Predicate<Interviewee> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredInterviewerList(Predicate<Interviewer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Interviewee getInterviewee(String name) throws NoSuchElementException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Interviewer getInterviewer(String name) throws NoSuchElementException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteInterviewee(Interviewee target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteInterviewer(Interviewer target) throws PersonNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInterviewee(Interviewee target, Interviewee editedTarget) throws PersonNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInterviewer(Interviewer target, Interviewer editedTarget) throws PersonNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

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
        public void addRefreshListener(RefreshListener listener) {
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void emailInterviewee(Interviewee interviewee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<InterviewSlot> getInterviewSlot(String intervieweeName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInterviewer(Interviewer interviewer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInterviewer(Name toFind) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInterviewee(Interviewee interviewee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInterviewee(Name toFind) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addInterviewerToSchedule(Interviewer interviewer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String scheduleHasInterviewer(Interviewer interviewer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addInterviewer(Interviewer interviewer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addInterviewee(Interviewee interviewee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearAllAllocatedSlot() {
            throw new AssertionError("This method should not be called.");
        }
    }
}
