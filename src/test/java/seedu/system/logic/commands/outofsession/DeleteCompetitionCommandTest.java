package seedu.system.logic.commands.outofsession;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.system.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.system.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.system.testutil.TypicalCompetitions.getTypicalCompetitionData;
import static seedu.system.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.system.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.system.testutil.TypicalPersons.getTypicalPersonData;

import org.junit.jupiter.api.Test;

import seedu.system.commons.core.Messages;

import seedu.system.commons.core.index.Index;
import seedu.system.model.Data;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;
import seedu.system.model.UserPrefs;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;
import seedu.system.model.util.SampleDataUtil;

public class DeleteCompetitionCommandTest {

    private Data<Person> samplePersonData = getTypicalPersonData();
    private Data<Competition> sampleCompetitionData = getTypicalCompetitionData();
    private Data<Participation> sampleParticipationData =
            new Data(SampleDataUtil.getSampleParticipationData(samplePersonData, sampleCompetitionData));

    private Model model =
            new ModelManager(
                    getTypicalPersonData(),
                    getTypicalCompetitionData(),
                    sampleParticipationData,
                    new UserPrefs()
            );

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Competition compToDelete = model.getFilteredCompetitionList().get(INDEX_FIRST.getZeroBased());
        DeleteCompetitionCommand deleteCompCommand = new DeleteCompetitionCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCompetitionCommand.MESSAGE_DELETE_COMPETITION_SUCCESS,
                compToDelete);

        ModelManager expectedModel = new ModelManager(model.getPersons(), model.getCompetitions(),
                model.getParticipations(), new UserPrefs());
        expectedModel.deleteCompetition(compToDelete);

        assertCommandSuccess(deleteCompCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompetitionList().size() + 1);
        DeleteCompetitionCommand deleteCompCommand = new DeleteCompetitionCommand(outOfBoundIndex);

        assertCommandFailure(deleteCompCommand, model, Messages.MESSAGE_INVALID_COMPETITION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCompetitionCommand deleteFirstCommand = new DeleteCompetitionCommand(INDEX_FIRST);
        DeleteCompetitionCommand deleteSecondCommand = new DeleteCompetitionCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCompetitionCommand deleteFirstCommandCopy = new DeleteCompetitionCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
