package seedu.sugarmummy.logic.commands.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.biography.BioCommand.SHOWING_BIO_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.biography.BioModelStub;
import seedu.sugarmummy.ui.DisplayPaneType;

class BioCommandTest {

    @Test
    public void executeBioSuccess_noUserList() {

        Model model = new BioModelStub.ModelStubWithNoUserList();
        Model expectedModel = new BioModelStub.ModelStubWithNoUserList();

        CommandResult expectedCommandResult = new CommandResult(SHOWING_BIO_MESSAGE, false, true,
                false, false);
        assertCommandSuccess(new BioCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void executeBioSucess_withUserList() {

        Model model = new BioModelStub.ModelStubWithUserList();
        Model expectedModel = new BioModelStub.ModelStubWithUserList();

        CommandResult expectedCommandResult = new CommandResult(SHOWING_BIO_MESSAGE, false, true,
                false, false);
        assertCommandSuccess(new BioCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void getDisplayPaneType_test() {
        assertEquals(DisplayPaneType.BIO, new BioCommand().getDisplayPaneType());
    }

    @Test
    public void getNewPaneIsToBeCreated_test() {
        assertFalse(new BioCommand().isToCreateNewPane());
    }

}
