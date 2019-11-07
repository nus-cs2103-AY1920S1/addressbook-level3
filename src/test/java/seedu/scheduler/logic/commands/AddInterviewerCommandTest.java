package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.BOB_INTERVIEWEE_MANUAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.IntervieweeList;
import seedu.scheduler.model.ModelStub;
import seedu.scheduler.model.ReadAndWriteList;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;

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

        @Override
        public void updateFilteredInterviewerList(Predicate<Interviewer> predicate) {
            requireNonNull(predicate);
        }
    }
}
