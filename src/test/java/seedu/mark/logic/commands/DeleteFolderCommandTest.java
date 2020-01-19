package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.Messages;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.ModelStub;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.storage.StorageStub;

public class DeleteFolderCommandTest {
    private static final String NON_NULL_STRING = "non Null";
    private static final Folder NON_NULL_FOLDER = new Folder(NON_NULL_STRING);

    @Test
    public void constructor_nullFolder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteFolderCommand(null));
    }

    @Test
    public void constructor_nonNullFolderNullParent_createsCommand() {
        assertDoesNotThrow(() -> new DeleteFolderCommand(NON_NULL_FOLDER));
    }

    @Test
    public void execute_folderInModel_deleteSuccessful() throws Exception {
        ModelStub modelStub = new ModelStubWithFolder(NON_NULL_FOLDER);

        CommandResult commandResult = new DeleteFolderCommand(NON_NULL_FOLDER).execute(modelStub, new StorageStub());

        assertEquals(String.format(DeleteFolderCommand.MESSAGE_SUCCESS, NON_NULL_FOLDER.folderName),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deletingRootFolder_throwsCommandException() {
        DeleteFolderCommand deleteFolderCommand = new DeleteFolderCommand(Folder.ROOT_FOLDER);
        ModelStub modelStub = new ModelStubWithFolder(NON_NULL_FOLDER);

        assertThrows(CommandException.class, DeleteFolderCommand.MESSAGE_DELETING_ROOT_FOLDER, () ->
                deleteFolderCommand.execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_folderNotInModel_throwsCommandException() {
        Folder notFound = new Folder("not in model");
        DeleteFolderCommand deleteFolderCommand = new DeleteFolderCommand(notFound);
        ModelStub modelStub = new ModelStubWithFolder(NON_NULL_FOLDER);

        assertThrows(CommandException.class, String.format(Messages.MESSAGE_FOLDER_NOT_FOUND, notFound), () ->
                deleteFolderCommand.execute(modelStub, new StorageStub()));
    }

    @Test
    public void equals() {
        Folder schoolFolder = new Folder("school");
        Folder workFolder = new Folder("work");

        DeleteFolderCommand schoolCommand = new DeleteFolderCommand(schoolFolder);

        // same object -> returns true
        assertEquals(schoolCommand, schoolCommand);

        // same values -> returns true
        DeleteFolderCommand schoolCommandCopy = new DeleteFolderCommand(schoolFolder);
        assertEquals(schoolCommand, schoolCommandCopy);

        // different types -> returns false
        assertNotEquals(1, schoolCommand);

        // null -> returns false
        assertNotEquals(null, schoolCommand);

        // different command -> returns false
        assertNotEquals(schoolCommand, new DeleteFolderCommand(workFolder));
    }

    /**
     * A Model stub that contains a single folder.
     */
    private class ModelStubWithFolder extends ModelStub {
        private Folder folder;

        ModelStubWithFolder(Folder folder) {
            requireNonNull(folder);
            this.folder = folder;
        }

        @Override
        public boolean hasFolder(Folder folder) {
            requireNonNull(folder);
            return this.folder.equals(folder);
        }

        @Override
        public boolean canDeleteFolder(Folder folder) {
            return folder.equals(this.folder);
        }

        @Override
        public void deleteFolder(Folder folder) {
            this.folder = null;
        }

        @Override
        public void saveMark(String record) {
            // phantom method for testing
        }
    }

}
