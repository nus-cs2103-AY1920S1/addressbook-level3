package mams.logic.commands;

import static mams.logic.commands.Approve.MESAGE_NO_APPEALS_APPROVED;
import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.testutil.TypicalMams.getTypicalMams;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;


public class MassApproveTest {

    private Model model = new ModelManager(getTypicalMams(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getMams(), new UserPrefs());



    @Test
    public void equal() {

        ArrayList<String> validIDs = new ArrayList<>();
        ArrayList<String> invalidIDs = new ArrayList<>();


        // same object -> returns true
        MassApprove firstEmptyCommand = new MassApprove(validIDs, invalidIDs);
        assertTrue(firstEmptyCommand.equals(firstEmptyCommand));

        validIDs.add("C000000");
        validIDs.add("C000023");
        validIDs.add("C000007");

        MassApprove validCommand = new MassApprove(validIDs, invalidIDs);
        MassApprove validCommandCopy = new MassApprove(validIDs, invalidIDs);
        // same values -> returns true
        assertTrue(validCommand.equals(validCommandCopy));

        ArrayList<String> secondValidIDs = new ArrayList<>();
        secondValidIDs.add("C000001");
        secondValidIDs.add("C000123");
        // different values -> returns false
        assertFalse(validCommand.equals(secondValidIDs));

        invalidIDs.add("C0123");
        invalidIDs.add("C0132");

        ArrayList<String> validListWithRightOrder = new ArrayList<>();
        validIDs.add("C000000");
        validIDs.add("C000007");
        validIDs.add("C000023");

        MassApprove validCommandCopyWithRightOrder = new MassApprove(validIDs, invalidIDs);
        // same values different order and different invalid list -> returns true
        assertTrue(validCommand.equals(validCommandCopyWithRightOrder));

        // different type -> returns false
        assertFalse(validCommand.equals(1));

        // different type -> returns false
        assertFalse(validCommand.equals("1"));

        // null -> returns false
        assertFalse(validCommand.equals(null));
    }

    @Test
    public void execute_nothingApproved_success() {
        ArrayList<String> validIDs = new ArrayList<>();
        ArrayList<String> invalidIDs = new ArrayList<>();
        String expectedMessage = "";
        invalidIDs.add("C0123");
        invalidIDs.add("C0132");
        MassApprove nothingApprovedCommand = new MassApprove(validIDs, invalidIDs);
        expectedMessage += MESAGE_NO_APPEALS_APPROVED + "\nInvalid appeal IDs: " + invalidIDs.toString();
        assertCommandSuccess(nothingApprovedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noSuchAppealId_success() {
        ArrayList<String> validIDs = new ArrayList<>();
        ArrayList<String> invalidIDs = new ArrayList<>();
        String expectedMessage = "";
        validIDs.add("C120000");
        validIDs.add("C999999");
        MassApprove command = new MassApprove(validIDs, invalidIDs);
        expectedMessage += MESAGE_NO_APPEALS_APPROVED + "\nThese appeal IDs do not exist: " + validIDs.toString();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }



    /**
     * Generates feedback message
     * @param approvedSuccessfully
     * @param alreadyApproved
     * @param alreadyRejected
     * @param invalidIds
     * @param appealsWithClash
     * @param cannotFindIDList
     * @return
     */
    private String resultGenerator(List<String> approvedSuccessfully,
                                   List<String> alreadyApproved,
                                   List<String> alreadyRejected,
                                   List<String> invalidIds,
                                   List<String> appealsWithClash,
                                   List<String> cannotFindIdList) {
        String result = "";
        if (approvedSuccessfully.isEmpty()) {
            result += MESAGE_NO_APPEALS_APPROVED;
        } else {
            result += "Successfully approved: " + approvedSuccessfully.toString();
        }
        if (!alreadyApproved.isEmpty()) {
            result += "\nAlready approved: " + alreadyApproved.toString();
        }
        if (!alreadyRejected.isEmpty()) {
            result += "\nAlready rejected: " + alreadyRejected.toString();
        }
        if (!invalidIds.isEmpty()) {
            result += "\nInvalid appeal IDs: " + invalidIds.toString();
        }
        if (!appealsWithClash.isEmpty()) {
            result += "\nAppeals with module clash: " + appealsWithClash.toString();
        }
        if (!cannotFindIdList.isEmpty()) {
            result += "\nThese appeal IDs do not exist: " + cannotFindIdList.toString();
        }
        return result;
    }


}
