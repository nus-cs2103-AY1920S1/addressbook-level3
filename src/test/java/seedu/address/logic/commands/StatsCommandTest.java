package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSpendings.ALICE;
import static seedu.address.testutil.TypicalSpendings.BENSON;
import static seedu.address.testutil.TypicalSpendings.CARL;
import static seedu.address.testutil.TypicalSpendings.DANIEL;
import static seedu.address.testutil.TypicalSpendings.ELLE;
import static seedu.address.testutil.TypicalSpendings.FIONA;
import static seedu.address.testutil.TypicalSpendings.GEORGE;
import static seedu.address.testutil.TypicalSpendings.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for StatsCommand.
 */
class StatsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_displayStatsForAll_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        double totalCost = Double.parseDouble(ALICE.getPhone().toString())
            + Double.parseDouble(BENSON.getPhone().toString()) + Double.parseDouble(CARL.getPhone().toString())
            + Double.parseDouble(DANIEL.getPhone().toString()) + Double.parseDouble(ELLE.getPhone().toString())
            + Double.parseDouble(FIONA.getPhone().toString()) + Double.parseDouble(GEORGE.getPhone().toString());

        String expectedMessage = StatsCommand.MESSAGE_SUCCESS + "\nTotal Cost: $" + String.format("%.2f", totalCost);
        System.out.println(expectedMessage);
        assertCommandSuccess(new StatsCommand(), model , expectedMessage, expectedModel);
    }

}
