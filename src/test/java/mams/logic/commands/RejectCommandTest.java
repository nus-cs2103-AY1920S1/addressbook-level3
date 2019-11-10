package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandFailure;
import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalIndexes.INDEX_MAX_INT;
import static mams.testutil.TypicalIndexes.INDEX_SECOND;
import static mams.testutil.TypicalIndexes.INDEX_THIRD;
import static mams.testutil.TypicalMams.getTypicalMams;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;
import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;
import mams.model.appeal.Appeal;
import org.junit.jupiter.api.Test;

public class RejectCommandTest {

    private Model model = new ModelManager(getTypicalMams(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getMams(), new UserPrefs());

    @Test
    public void equal() {
        Index index = INDEX_FIRST;

        // same object -> returns true
        RejectCommand firstCommand = new RejectCommand(index, "");
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        RejectCommand firstCommandCopy = new RejectCommand(index, "");
        assertTrue(firstCommand.equals(firstCommandCopy));

        index = INDEX_SECOND;

        // different object -> returns false
        RejectCommand secondCommand = new RejectCommand(index, "");
        assertFalse(firstCommand.equals(secondCommand));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));
        assertFalse(secondCommand.equals("1"));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        index = INDEX_FIRST;
        RejectCommand firstCommandWithDifferentRemark = new RejectCommand(index, "good");
        assertFalse(firstCommand.equals(firstCommandWithDifferentRemark));

    }

    @Test
    public void execute_invalidAppealIndex_throwsCommandException() {

        RejectCommand command = new RejectCommand(INDEX_MAX_INT, "");
        // index > size of appeal list
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);

    }

    @Test
    public void execute_validAppealIndex_success() {
        List<Appeal> lastShownList = expectedModel.getFilteredAppealList();
        Index index = INDEX_FIRST;
        RejectCommand firstCommand = new RejectCommand(index, "");
        Appeal firstRejectedAppeal;
        Appeal firstAppealToReject = lastShownList.get(index.getZeroBased());
        String expectedMessage = "";

        if (firstAppealToReject.isResolved() == false) {
            firstRejectedAppeal = new Appeal(firstAppealToReject.getAppealId(),
                    firstAppealToReject.getAppealType(),
                    firstAppealToReject.getStudentId(),
                    firstAppealToReject.getAcademicYear(),
                    firstAppealToReject.getStudentWorkload(),
                    firstAppealToReject.getAppealDescription(),
                    firstAppealToReject.getPreviousModule(),
                    firstAppealToReject.getNewModule(),
                    firstAppealToReject.getModuleToAdd(),
                    firstAppealToReject.getModuleToDrop(),
                    true,
                    "REJECTED",
                    "");
            expectedModel.setAppeal(firstAppealToReject, firstRejectedAppeal);
            expectedModel.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
        }
        expectedMessage += generateSuccessMessage(firstAppealToReject);
        assertCommandSuccess(firstCommand, model, expectedMessage, expectedModel);


        index = INDEX_THIRD;
        Appeal secondAppealToReject = lastShownList.get(index.getZeroBased());
        Appeal secondRejectedAppeal;
        RejectCommand secondCommand = new RejectCommand(index, "");
        String secondExpectedMessage = "";

        if (secondAppealToReject.isResolved() == false) {
            secondRejectedAppeal = new Appeal(secondAppealToReject.getAppealId(),
                    secondAppealToReject.getAppealType(),
                    secondAppealToReject.getStudentId(),
                    secondAppealToReject.getAcademicYear(),
                    secondAppealToReject.getStudentWorkload(),
                    secondAppealToReject.getAppealDescription(),
                    secondAppealToReject.getPreviousModule(),
                    secondAppealToReject.getNewModule(),
                    secondAppealToReject.getModuleToAdd(),
                    secondAppealToReject.getModuleToDrop(),
                    true,
                    "REJECTED",
                    "");
            expectedModel.setAppeal(secondAppealToReject, secondRejectedAppeal);
            expectedModel.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
        }
        secondExpectedMessage += generateSuccessMessage(secondAppealToReject);
        assertCommandSuccess(secondCommand, model, secondExpectedMessage, expectedModel);


    }

    private String generateSuccessMessage(Appeal appealToReject) {
        return "Rejected " + appealToReject.getAppealId();
    }

}
