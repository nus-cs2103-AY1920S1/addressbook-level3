package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.system.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.model.Data;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.competition.Competition;
import seedu.system.testutil.CompetitionBuilder;

public class AddCompetitionCommandTest {
    @Test
    public void constructor_nullCompetition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCompetitionCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCompetitionAdded modelStub = new ModelStubAcceptingCompetitionAdded();

        Competition validCompetition = new CompetitionBuilder().build();

        CommandResult commandResult = new AddCompetitionCommand(validCompetition).execute(modelStub);

        assertEquals(String.format(AddCompetitionCommand.MESSAGE_SUCCESS, validCompetition),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCompetition), modelStub.competitionsAdded);
    }

    @Test
    public void execute_duplicateCompetition_throwsCommandException() {
        Competition validCompetition = new CompetitionBuilder().build();
        AddCompetitionCommand addCompetitionCommand = new AddCompetitionCommand(validCompetition);
        ModelStub modelStub = new ModelStubWithCompetition(validCompetition);

        assertThrows(
                CommandException.class,
                AddCompetitionCommand.MESSAGE_DUPLICATE_COMPETITION, () -> addCompetitionCommand.execute(modelStub)
        );
    }

    @Test
    public void equals() {
        Competition nusComp = new CompetitionBuilder().withName("NUS PL").build();
        Competition usComp = new CompetitionBuilder().withName("USA PL").build();
        AddCompetitionCommand addNusCompCommand = new AddCompetitionCommand(nusComp);
        AddCompetitionCommand addUsCompCommand = new AddCompetitionCommand(usComp);

        // same object -> returns true
        assertTrue(addNusCompCommand.equals(addNusCompCommand));

        // same values -> returns true
        AddCompetitionCommand addNusCompCommandCopy = new AddCompetitionCommand(nusComp);
        assertTrue(addNusCompCommand.equals(addNusCompCommandCopy));

        // different types -> returns false
        assertFalse(addNusCompCommand.equals(1));

        // null -> returns false
        assertFalse(addNusCompCommand.equals(null));

        // different competition -> returns false
        assertFalse(addNusCompCommand.equals(addUsCompCommand));
    }


    /**
     * A Model stub that contains a single competition.
     */
    private class ModelStubWithCompetition extends ModelStub {
        private final Competition competition;

        ModelStubWithCompetition(Competition competition) {
            requireNonNull(competition);
            this.competition = competition;
        }

        @Override
        public boolean hasCompetition(Competition competition) {
            requireNonNull(competition);
            return this.competition.isSameElement(competition);
        }
    }

    /**
     * A Model stub that always accept the competition being added.
     */
    private class ModelStubAcceptingCompetitionAdded extends ModelStub {
        final ArrayList<Competition> competitionsAdded = new ArrayList<>();

        @Override
        public boolean hasCompetition(Competition competition) {
            requireNonNull(competition);
            return competitionsAdded.stream().anyMatch(competition::isSameElement);
        }

        @Override
        public void addCompetition(Competition competition) {
            requireNonNull(competition);
            competitionsAdded.add(competition);
        }

        @Override
        public ReadOnlyData<Competition> getCompetitions() {
            return new Data();
        }

    }

}
