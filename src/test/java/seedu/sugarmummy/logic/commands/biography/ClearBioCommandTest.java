package seedu.sugarmummy.logic.commands.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.biography.ClearBioCommand.MESSAGE_ALREADY_EMPTY;
import static seedu.sugarmummy.logic.commands.biography.ClearBioCommand.MESSAGE_SUCCESS;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.biography.BioModelStub.ModelStubWithNoUserListForEditing;
import seedu.sugarmummy.model.biography.BioModelStub.ModelStubWithUserListForEditing;
import seedu.sugarmummy.model.biography.UserList;
import seedu.sugarmummy.ui.DisplayPaneType;

class ClearBioCommandTest {

    @Test
    public void executeClrBio_nullModel_throwsNullPointerException() {
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> (new ClearBioCommand()).execute(null));
    }

    @Test
    public void executeClrBio_emptyUserList_throwsCommandException() {
        assertThrows(CommandException.class,
                MESSAGE_ALREADY_EMPTY, () -> (new ClearBioCommand()).execute(new ModelStubWithNoUserListForEditing()));
    }

    @Test
    public void executeClrBioSuccess() {
        Model model = new ModelStubWithUserListForEditing();
        Model expectedModel = new ModelStubWithUserListForEditing();
        expectedModel.setUserList(new UserList());

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, false);
        assertCommandSuccess(new ClearBioCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void getDisplayPaneType_test() {
        assertEquals(DisplayPaneType.BIO, new ClearBioCommand().getDisplayPaneType());
    }

    @Test
    public void getNewPaneIsToBeCreated_test() {
        assertTrue(new ClearBioCommand().isToCreateNewPane());
    }

}
