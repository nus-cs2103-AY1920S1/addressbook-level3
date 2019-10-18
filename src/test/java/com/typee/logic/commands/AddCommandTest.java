package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.typee.commons.core.GuiSettings;
import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;
import com.typee.model.EngagementList;
import com.typee.model.Model;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.ReadOnlyUserPrefs;
import com.typee.model.engagement.Engagement;
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
        public ReadOnlyEngagementList getHistoryManager() {
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
            return this.engagement.isEqualEngagement(engagement);
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
            return engagementsAdded.stream().anyMatch(engagement::isEqualEngagement);
        }

        @Override
        public void addEngagement(Engagement engagement) {
            requireNonNull(engagement);
            engagementsAdded.add(engagement);
        }

        @Override
        public ReadOnlyEngagementList getHistoryManager() {
            return new EngagementList();
        }
    }

}

/*

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

 */

//}
