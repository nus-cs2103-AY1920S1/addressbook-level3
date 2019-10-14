package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.GenReportCommand.MESSAGE_GENREPORT_SUCCESS;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_BODY_ID_NUM;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class GenReportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_validBodyId_success() {
        CommandResult expectedCommandResult =
                new CommandResult(String.format(MESSAGE_GENREPORT_SUCCESS, FIRST_BODY_ID_NUM));
        assertCommandSuccess(new GenReportCommand(FIRST_BODY_ID_NUM), model, expectedCommandResult, expectedModel);
    }
}
