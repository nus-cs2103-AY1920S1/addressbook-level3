package seedu.sugarmummy.logic.commands.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.biography.AddBioCommand.MESSAGE_BIO_ALREADY_EXISTS;
import static seedu.sugarmummy.logic.commands.biography.AddBioCommand.MESSAGE_SUCCESS;
import static seedu.sugarmummy.model.biography.BioModelStub.OTHER_VALID_USER;
import static seedu.sugarmummy.model.biography.BioModelStub.VALID_USER;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.biography.BioModelStub;
import seedu.sugarmummy.ui.DisplayPaneType;

class AddBioCommandTest {


    @Test
    public void nullUser_throwsNullPointerException() {
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> new AddBioCommand(null));
    }

    @Test
    public void executeAddbio_nullModel_throwsNullPointerException() {
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> (new AddBioCommand(VALID_USER)).execute(null));
    }

    @Test
    public void executeAddbio_existingBio_throwsCommandException() throws CommandException {
        assertThrows(CommandException.class, MESSAGE_BIO_ALREADY_EXISTS, () -> (new AddBioCommand(VALID_USER))
                .execute(new BioModelStub.ModelStubWithUserList()));
    }

    @Test
    public void executeAddbio_success() throws CommandException {
        StringBuilder addedFields = new StringBuilder();

        Model model = new BioModelStub.ModelStubWithNoUserList();

        model.addUser(VALID_USER);
        VALID_USER.getFieldMap().forEach((key, value) -> {
            if (!value.isEmpty() && !value.equals("[]")) {
                addedFields.append("- ").append(key).append(": ")
                        .append(value).append("\n");
            }
        });

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS,
                addedFields.toString().trim()), false, false);
        assertCommandSuccess(new AddBioCommand(VALID_USER), new BioModelStub.ModelStubWithNoUserList(),
                expectedCommandResult,
                model);
    }

    @Test
    public void get_displayPaneType_test() {
        assertEquals(DisplayPaneType.BIO, new AddBioCommand(VALID_USER).getDisplayPaneType());
    }

    @Test
    public void getNewPaneToBecreated_test() {
        assertTrue(new AddBioCommand(VALID_USER).isToCreateNewPane());
    }

    @Test
    public void equals_sameAddbioCommand() {
        AddBioCommand addBioCommand = new AddBioCommand(VALID_USER);
        assertEquals(addBioCommand, addBioCommand);
    }


    @Test
    public void equals_differentAddBioCommands_sameUserToAdd() {
        AddBioCommand firstAddBioCommand = new AddBioCommand(VALID_USER);
        AddBioCommand secondAddBioCommand = new AddBioCommand(VALID_USER);
        assertEquals(firstAddBioCommand, secondAddBioCommand);
    }

    @Test
    public void equals_differentObject() {
        AddBioCommand addBioCommand = new AddBioCommand(VALID_USER);
        assertNotEquals(addBioCommand, new Object());
    }

    @Test
    public void equals_differentAddbioCommands_differentUserToAdd() {
        AddBioCommand firstAddBioCommand = new AddBioCommand(VALID_USER);
        AddBioCommand secondAddBioCommand = new AddBioCommand(OTHER_VALID_USER);
        assertNotEquals(firstAddBioCommand, secondAddBioCommand);
    }

}
