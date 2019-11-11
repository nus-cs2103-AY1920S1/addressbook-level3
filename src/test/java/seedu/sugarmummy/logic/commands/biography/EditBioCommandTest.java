package seedu.sugarmummy.logic.commands.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.biography.EditBioCommand.MESSAGE_BIOGRAPHY_DOES_NOT_EXIST;
import static seedu.sugarmummy.logic.commands.biography.EditBioCommand.MESSAGE_CHANGES_MADE;
import static seedu.sugarmummy.logic.commands.biography.EditBioCommand.MESSAGE_EDIT_USER_SUCCESS;
import static seedu.sugarmummy.logic.commands.biography.EditBioCommand.MESSAGE_NO_CHANGE;
import static seedu.sugarmummy.model.biography.BioModelStub.OTHER_VALID_USER;
import static seedu.sugarmummy.model.biography.BioModelStub.VALID_USER;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.biography.EditBioCommand.EditUserDescriptor;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.biography.BioModelStub;
import seedu.sugarmummy.model.biography.BioModelStub.ModelStubWithNoUserListForEditing;
import seedu.sugarmummy.model.biography.BioModelStub.ModelStubWithUserListForEditing;
import seedu.sugarmummy.model.biography.Name;
import seedu.sugarmummy.ui.DisplayPaneType;

class EditBioCommandTest {

    private EditUserDescriptor getUnModifiedUserDescriptor() {
        return new EditUserDescriptor();
    }

    private EditUserDescriptor getModifiedEditUserDescriptor() {
        EditUserDescriptor editUserDescriptor = new EditUserDescriptor();
        editUserDescriptor.setName(new Name("secondTestName"));
        return editUserDescriptor;
    }

    @Test
    public void null_editUserDescriptor_throwsNullPointerException() {
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> new EditBioCommand(null));
    }

    @Test
    public void executeEditBio_nullModel_throwsNullPointerException() {
        assertThrows(RuntimeException.class, (new NullPointerException())
                .getMessage(), () -> (new EditBioCommand(getUnModifiedUserDescriptor())).execute(null));
    }

    @Test
    public void executeEditBio_noExistingBio_throwsCommandException() throws CommandException {
        assertThrows(CommandException.class,
                MESSAGE_BIOGRAPHY_DOES_NOT_EXIST, () -> (new EditBioCommand(getUnModifiedUserDescriptor()))
                        .execute(new ModelStubWithNoUserListForEditing()));
    }

    @Test
    public void executeEditBio_noChange() throws CommandException {
        Model model = new BioModelStub.ModelStubWithUserListForEditing();

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_NO_CHANGE, false, false);
        assertCommandSuccess(new EditBioCommand(getUnModifiedUserDescriptor()), new ModelStubWithUserListForEditing(),
                expectedCommandResult, model);
    }

    @Test
    public void executeEditBioSuccess() throws CommandException {
        Model model = new BioModelStub.ModelStubWithUserListForEditing();
        model.setUser(VALID_USER, OTHER_VALID_USER);

        StringBuilder editedFields = new StringBuilder();
        HashMap<String, List<String>> changedDifferences = VALID_USER.getDifferencesTo(OTHER_VALID_USER);

        changedDifferences.forEach((key, beforeAndAfter) -> {
            String before = beforeAndAfter.get(0);
            String after = beforeAndAfter.get(1);
            editedFields.append("- ");

            if (before.isEmpty()) {
                editedFields.append("Added to ").append(key).append(": ").append(after);
            } else if (after.isEmpty() || after.equals("[]")) {
                editedFields.append("Deleted from ").append(key).append(": ").append(before);
            } else {
                editedFields.append("Modified ").append(key)
                        .append(": from ").append(before).append(" to ").append(after);
            }
            editedFields.append("\n");
        });

        CommandResult expectedCommandResult = new CommandResult(String.format(String.format(MESSAGE_EDIT_USER_SUCCESS,
                String.format(MESSAGE_CHANGES_MADE, editedFields.toString().trim())), false, false));
        assertCommandSuccess(new EditBioCommand(getModifiedEditUserDescriptor()), new ModelStubWithUserListForEditing(),
                expectedCommandResult, model);
    }

    @Test
    public void getDisplayPaneType_test() {
        assertEquals(DisplayPaneType.BIO, new EditBioCommand(getUnModifiedUserDescriptor()).getDisplayPaneType());
    }

    @Test
    public void getNewPaneIsToBeCreated_test() {
        assertTrue((new EditBioCommand(getUnModifiedUserDescriptor())).isToCreateNewPane());
    }

    @Test
    public void equals_sameEditBioCommand() {
        EditBioCommand editBioCommand = new EditBioCommand(getUnModifiedUserDescriptor());
        assertEquals(editBioCommand, editBioCommand);
    }


    @Test
    public void equals_differentEditBioCommands_sameEditUserDescriptions() {
        EditBioCommand firstEditBioCommand = new EditBioCommand(getUnModifiedUserDescriptor());
        EditBioCommand secondEditBioCommand = new EditBioCommand(getUnModifiedUserDescriptor());
        assertEquals(firstEditBioCommand, secondEditBioCommand);
    }

    @Test
    public void equals_differentObject() {
        EditBioCommand editBioCommand = new EditBioCommand(getUnModifiedUserDescriptor());
        assertNotEquals(editBioCommand, new Object());
    }

    @Test
    public void equals_differentEditBioCommands_differentEditUserDescriptions() {
        EditBioCommand firstEditBioCommand = new EditBioCommand(getUnModifiedUserDescriptor());
        EditBioCommand secondEditBioCommand = new EditBioCommand(getModifiedEditUserDescriptor());
        assertNotEquals(firstEditBioCommand, secondEditBioCommand);
    }

}
