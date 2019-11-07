package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
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

class AddIntervieweeCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddIntervieweeCommand(null));
    }

    @Test
    public void execute_intervieweeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIntervieweeAdded modelStub = new ModelStubAcceptingIntervieweeAdded();
        Interviewee validInterviewee = ALICE_INTERVIEWEE;

        CommandResult commandResult = new AddIntervieweeCommand(validInterviewee).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validInterviewee), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validInterviewee), modelStub.intervieweesAdded);
    }

    @Test
    public void execute_duplicateInterviewee_throwsCommandException() {
        Interviewee validInterviewee = ALICE_INTERVIEWEE;
        AddCommand addCommand = new AddIntervieweeCommand(validInterviewee);
        ModelStubWithInterviewee modelStub = new ModelStubWithInterviewee(validInterviewee);

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
    private class ModelStubWithInterviewee extends ModelStub {
        private final Interviewee interviewee;

        ModelStubWithInterviewee(Interviewee interviewee) {
            requireNonNull(interviewee);
            this.interviewee = interviewee;
        }

        @Override
        public boolean hasInterviewee(Interviewee interviewee) {
            requireNonNull(interviewee);
            return this.interviewee.isSamePerson(interviewee);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingIntervieweeAdded extends ModelStub {
        final ArrayList<Interviewee> intervieweesAdded = new ArrayList<>();

        @Override
        public boolean hasInterviewee(Interviewee interviewee) {
            requireNonNull(interviewee);
            return intervieweesAdded.stream().anyMatch(interviewee::isSamePerson);
        }

        @Override
        public void addInterviewee(Interviewee interviewee) {
            requireNonNull(interviewee);
            intervieweesAdded.add(interviewee);
        }

        @Override
        public ReadAndWriteList<Interviewee> getMutableIntervieweeList() {
            return new IntervieweeList();
        }

        @Override
        public void updateFilteredIntervieweeList(Predicate<Interviewee> predicate) {
            requireNonNull(predicate);
        }
    }
}
