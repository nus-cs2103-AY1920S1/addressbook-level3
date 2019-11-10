package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.typee.commons.core.GuiSettings;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.commands.exceptions.DeleteDocumentException;
import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;
import com.typee.model.EngagementList;
import com.typee.model.Model;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.ReadOnlyUserPrefs;
import com.typee.model.engagement.Engagement;
import com.typee.model.report.Report;
import com.typee.testutil.EngagementBuilder;

import javafx.collections.ObservableList;

public class AddCommandTest {

    @Test
    public void constructor_nullEngagement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_appointmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEngagementAdded modelStub = new ModelStubAcceptingEngagementAdded();
        Engagement validAppointment = new EngagementBuilder().buildAsAppointment();
        CommandResult commandResult = new AddCommand(validAppointment).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validAppointment),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAppointment), modelStub.engagementsAdded);
    }

    @Test
    public void execute_interviewAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEngagementAdded modelStub = new ModelStubAcceptingEngagementAdded();
        Engagement validInterview = new EngagementBuilder().buildAsInterview();
        CommandResult commandResult = new AddCommand(validInterview).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validInterview),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validInterview), modelStub.engagementsAdded);
    }

    @Test
    public void execute_meetingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEngagementAdded modelStub = new ModelStubAcceptingEngagementAdded();
        Engagement validMeeting = new EngagementBuilder().buildAsMeeting();
        CommandResult commandResult = new AddCommand(validMeeting).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validMeeting),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeeting), modelStub.engagementsAdded);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() {
        Engagement validAppointment = new EngagementBuilder().buildAsAppointment();
        AddCommand addCommand = new AddCommand(validAppointment);
        ModelStub modelStub = new ModelStubWithEngagement(validAppointment);

        assertThrows(CommandException.class, () -> addCommand.execute(modelStub),
                AddCommand.MESSAGE_DUPLICATE_ENGAGEMENT);
    }

    @Test
    public void execute_duplicateInterview_throwsCommandException() {
        Engagement validInterview = new EngagementBuilder().buildAsInterview();
        AddCommand addCommand = new AddCommand(validInterview);
        ModelStub modelStub = new ModelStubWithEngagement(validInterview);

        assertThrows(CommandException.class, () -> addCommand.execute(modelStub),
                AddCommand.MESSAGE_DUPLICATE_ENGAGEMENT);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() {
        Engagement validMeeting = new EngagementBuilder().buildAsMeeting();
        AddCommand addCommand = new AddCommand(validMeeting);
        ModelStub modelStub = new ModelStubWithEngagement(validMeeting);

        assertThrows(CommandException.class, () -> addCommand.execute(modelStub),
                AddCommand.MESSAGE_DUPLICATE_ENGAGEMENT);
    }

    @Test
    public void appointmentEquals() {
        Engagement appointment = new EngagementBuilder().buildAsAppointment();
        Engagement differentAppointment = new EngagementBuilder()
                .withDescription("date").buildAsAppointment();
        AddCommand addAppointmentCommand = new AddCommand(appointment);
        AddCommand addDifferentAppointmentCommand = new AddCommand(differentAppointment);

        // same object -> returns true
        assertTrue(addAppointmentCommand.equals(addAppointmentCommand));

        // same values -> returns true
        AddCommand addAppointmentCommandCopy = new AddCommand(appointment);

        assertTrue(addAppointmentCommand.equals(addAppointmentCommandCopy));

        // different types -> returns false
        assertFalse(addAppointmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAppointmentCommand.equals(null));

        // different appointment -> returns false
        assertFalse(addAppointmentCommand.equals(addDifferentAppointmentCommand));

    }

    @Test
    public void interviewEquals() {
        Engagement interview = new EngagementBuilder().buildAsInterview();
        Engagement differentInterview = new EngagementBuilder()
                .withDescription("date").buildAsInterview();
        AddCommand addInterviewCommand = new AddCommand(interview);
        AddCommand addDifferentInterviewCommand = new AddCommand(differentInterview);

        // same object -> returns true
        assertTrue(addInterviewCommand.equals(addInterviewCommand));

        // same values -> returns true
        AddCommand addInterviewCommandCopy = new AddCommand(interview);

        assertTrue(addInterviewCommand.equals(addInterviewCommandCopy));

        // different types -> returns false
        assertFalse(addInterviewCommand.equals(1));

        // null -> returns false
        assertFalse(addInterviewCommand.equals(null));

        // different appointment -> returns false
        assertFalse(addInterviewCommand.equals(addDifferentInterviewCommand));

    }

    @Test
    public void meetingEquals() {
        Engagement meeting = new EngagementBuilder().buildAsMeeting();
        Engagement differentMeeting = new EngagementBuilder()
                .withDescription("date").buildAsMeeting();
        AddCommand addMeetingCommand = new AddCommand(meeting);
        AddCommand addDifferentMeetingCommand = new AddCommand(differentMeeting);

        // same object -> returns true
        assertTrue(addMeetingCommand.equals(addMeetingCommand));

        // same values -> returns true
        AddCommand addMeetingCommandCopy = new AddCommand(meeting);

        assertTrue(addMeetingCommand.equals(addMeetingCommandCopy));

        // different types -> returns false
        assertFalse(addMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetingCommand.equals(null));

        // different appointment -> returns false
        assertFalse(addMeetingCommand.equals(addDifferentMeetingCommand));

    }

    @Test
    public void differentEngagementTypesEquals() {
        Engagement appointment = new EngagementBuilder().buildAsAppointment();
        Engagement interview = new EngagementBuilder().buildAsInterview();
        Engagement meeting = new EngagementBuilder().buildAsMeeting();
        AddCommand addAppointmentCommand = new AddCommand(appointment);
        AddCommand addInterviewCommand = new AddCommand(interview);
        AddCommand addMeetingCommand = new AddCommand(meeting);

        // different engagement types -> returns false
        assertFalse(addAppointmentCommand.equals(addInterviewCommand));
        assertFalse(addAppointmentCommand.equals(addMeetingCommand));
        assertFalse(addInterviewCommand.equals(addMeetingCommand));

    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public Path getEngagementListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEngagementListFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEngagement(Engagement engagement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHistoryManager(ReadOnlyEngagementList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEngagementList getEngagementList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEngagement(Engagement engagement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEngagement(Engagement target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEngagement(Engagement target, Engagement editedEngagement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Engagement> getFilteredEngagementList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEngagementList(Predicate<Engagement> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNoUndoableCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoEngagementList() throws NullUndoableActionException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNoRedoableCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoEngagementList() throws NullRedoableActionException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveEngagementList() {
            // Used in the execution of AddCommand
        }

        @Override
        public void updateSortedEngagementList() {
            // Used in the execution of AddCommand
        }

        @Override
        public ObservableList<Engagement> getSortedEngagementList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setComparator(Comparator<Engagement> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path saveReport(Path fileDir, Report report) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean deleteReport(Path fileDir, Report report) throws DeleteDocumentException {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single engagement.
     */
    private class ModelStubWithEngagement extends ModelStub {
        private final Engagement engagement;

        ModelStubWithEngagement(Engagement engagement) {
            requireNonNull(engagement);
            this.engagement = engagement;
        }

        @Override
        public boolean hasEngagement(Engagement engagement) {
            requireNonNull(engagement);
            return this.engagement.isSameEngagement(engagement);
        }
    }

    /**
     * A Model stub that always accepts the engagement being added.
     */
    private class ModelStubAcceptingEngagementAdded extends ModelStub {
        final ArrayList<Engagement> engagementsAdded = new ArrayList<>();

        @Override
        public boolean hasEngagement(Engagement engagement) {
            requireNonNull(engagement);
            return engagementsAdded.stream().anyMatch(engagement::isSameEngagement);
        }

        @Override
        public void addEngagement(Engagement engagement) {
            requireNonNull(engagement);
            engagementsAdded.add(engagement);
        }

        @Override
        public ReadOnlyEngagementList getEngagementList() {
            return new EngagementList();
        }
    }

}
