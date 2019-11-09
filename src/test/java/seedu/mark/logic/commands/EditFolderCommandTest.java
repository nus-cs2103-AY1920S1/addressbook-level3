package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.mark.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.Messages;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.ModelStub;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.storage.StorageStub;

public class EditFolderCommandTest {
    private static final String NON_NULL_STRING = "nonNull";
    private static final Folder NON_NULL_FOLDER = new Folder(NON_NULL_STRING);
    private static final Folder NEW_FOLDER = new Folder("newFolder");

    @Test
    public void constructor_nullFolderNullNewFolder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditFolderCommand(null, null));
    }

    @Test
    public void constructor_nullFolderNonNullNewFolder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditFolderCommand(null, NON_NULL_FOLDER));
    }

    @Test
    public void constructor_nonNullFolderNullNewFolder_createsCommand() {
        assertThrows(NullPointerException.class, () -> new EditFolderCommand(NON_NULL_FOLDER, null));
    }

    @Test
    public void constructor_nonNullFolderNonNullParent_createsCommand() {
        assertDoesNotThrow(() -> new EditFolderCommand(NON_NULL_FOLDER, NON_NULL_FOLDER));
    }

    @Test
    public void execute_folderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubWithFolder modelStub = new ModelStubWithFolder(NON_NULL_FOLDER);

        CommandResult commandResult =
                new EditFolderCommand(NON_NULL_FOLDER, NEW_FOLDER).execute(modelStub, new StorageStub());

        assertEquals(String.format(EditFolderCommand.MESSAGE_SUCCESS, NEW_FOLDER.folderName),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_editingRootFolder_throwsCommandException() {
        EditFolderCommand editFolderCommand = new EditFolderCommand(Folder.ROOT_FOLDER, NEW_FOLDER);
        ModelStub modelStub = new ModelStubWithFolder(NON_NULL_FOLDER);

        assertThrows(CommandException.class, EditFolderCommand.MESSAGE_RENAMING_ROOT_FOLDER, () ->
                editFolderCommand.execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_renamingToRootFolder_throwsCommandException() {
        EditFolderCommand editFolderCommand = new EditFolderCommand(NEW_FOLDER, Folder.ROOT_FOLDER);
        ModelStub modelStub = new ModelStubWithFolder(NON_NULL_FOLDER);

        assertThrows(CommandException.class, Messages.MESSAGE_READDING_ROOT_FOLDER, () ->
                editFolderCommand.execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_duplicateFolder_throwsCommandException() {
        EditFolderCommand editFolderCommand = new EditFolderCommand(NON_NULL_FOLDER, NEW_FOLDER);
        ModelStub modelStub = new ModelStubWithFolder(NEW_FOLDER, NON_NULL_FOLDER);

        assertThrows(CommandException.class,
                String.format(EditFolderCommand.MESSAGE_DUPLICATE_FOLDER, NEW_FOLDER), () ->
                editFolderCommand.execute(modelStub, new StorageStub()));
    }

    @Test
    public void equals() {
        Folder schoolFolder = new Folder("school");
        Folder workFolder = new Folder("work");

        EditFolderCommand editFolderCommand = new EditFolderCommand(schoolFolder, workFolder);

        // same object -> returns true
        assertEquals(editFolderCommand, editFolderCommand);

        // same values -> returns true
        EditFolderCommand copy = new EditFolderCommand(schoolFolder, workFolder);
        assertEquals(editFolderCommand, copy);

        // different types -> returns false
        assertNotEquals(1, editFolderCommand);

        // null -> returns false
        assertNotEquals(null, editFolderCommand);
    }

    /**
     * A Model stub that contains a single folder.
     */
    private class ModelStubWithFolder extends ModelStub {
        private final List<Folder> folders;

        ModelStubWithFolder(Folder... folders) {
            this.folders = List.of(folders);
        }

        @Override
        public boolean hasFolder(Folder folder) {
            return folders.contains(folder);
        }

        @Override
        public void renameFolder(Folder from, Folder to) {
            // phantom method for testing
        }

        @Override
        public void saveMark(String record) {
            // phantom method for testing
        }
    }
}
