package seedu.address.logic.commands.global;
/* Unused test, kept as reference
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StudyBuddyPro;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        GlobalCommandResult expectedCommandResult = new GlobalCommandResult(
                ClearCommand.MESSAGE_SUCCESS + "the entire StudyBuddy book!");

        assertCommandSuccess(new ClearCommand(true), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setStudyBuddyPro(new StudyBuddyPro());

        GlobalCommandResult expectedCommandResult = new GlobalCommandResult(
                ClearCommand.MESSAGE_SUCCESS + "the entire StudyBuddy book!");

        assertCommandSuccess(new ClearCommand(true), model, expectedCommandResult, expectedModel);
    }

}
*/
