package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static seedu.mark.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.ModelStub;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.folderstructure.FolderStructure;

public class AddFolderCommandTest {
    private static final String NON_NULL_STRING = "nonNull";
    private static final Folder NON_NULL_FOLDER = new Folder(NON_NULL_STRING);

    @Test
    public void constructor_nullFolderNullParent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFolderCommand(null, null));
    }

    @Test
    public void constructor_nullFolderNonNullParent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFolderCommand(null, NON_NULL_FOLDER));
    }

    @Test
    public void constructor_nonNullFolderNullParent_createsCommand() {
        assertDoesNotThrow(() -> new AddFolderCommand(NON_NULL_FOLDER, null));
    }

    @Test
    public void constructor_nonNullFolderNonNullParent_createsCommand() {
        assertDoesNotThrow(() -> new AddFolderCommand(NON_NULL_FOLDER, NON_NULL_FOLDER));
    }

    @Test
    public void execute_folderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFolderAdded modelStub = new ModelStubAcceptingFolderAdded();

        CommandResult commandResult = new AddFolderCommand(NON_NULL_FOLDER, null).execute(modelStub);

        assertEquals(String.format(AddFolderCommand.MESSAGE_SUCCESS, NON_NULL_FOLDER.folderName),
                commandResult.getFeedbackToUser());

        FolderStructure expected = new FolderStructure(
                Folder.ROOT_FOLDER, List.of(new FolderStructure(NON_NULL_FOLDER, new ArrayList<>())));
        assertEquals(expected, modelStub.folderStructure);
    }

    @Test
    public void execute_duplicateFolder_throwsCommandException() {
        AddFolderCommand addFolderCommand = new AddFolderCommand(NON_NULL_FOLDER, null);
        ModelStub modelStub = new ModelStubWithFolder(NON_NULL_FOLDER);

        assertThrows(CommandException.class, AddFolderCommand.MESSAGE_DUPLICATE_FOLDER, () ->
                addFolderCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Folder schoolFolder = new Folder("school");
        Folder workFolder = new Folder("work");

        AddFolderCommand nullParentCommand = new AddFolderCommand(schoolFolder, null);
        AddFolderCommand nonNullParentCommand = new AddFolderCommand(schoolFolder, workFolder);

        // same object -> returns true
        assertEquals(nonNullParentCommand, nonNullParentCommand);
        assertEquals(nullParentCommand, nullParentCommand);

        // same values -> returns true
        AddFolderCommand nullParentCommandCopy = new AddFolderCommand(schoolFolder, null);
        AddFolderCommand nonNullParentCommandCopy = new AddFolderCommand(schoolFolder, workFolder);
        assertEquals(nonNullParentCommand, nonNullParentCommandCopy);
        assertEquals(nullParentCommand, nullParentCommandCopy);

        // different types -> returns false
        assertNotEquals(1, nullParentCommand);
        assertNotEquals(1, nonNullParentCommand);

        // null -> returns false
        assertNotEquals(null, nullParentCommand);
        assertNotEquals(null, nonNullParentCommand);

        // different bookmark -> returns false
        assertNotEquals(nullParentCommand, nonNullParentCommand);
    }

    /**
     * A Model stub that contains a single folder.
     */
    private class ModelStubWithFolder extends ModelStub {
        private final Folder folder;

        ModelStubWithFolder(Folder folder) {
            requireNonNull(folder);
            this.folder = folder;
        }

        @Override
        public boolean hasFolder(Folder folder) {
            requireNonNull(folder);
            return this.folder.equals(folder);
        }
    }

    /**
     * A Model stub that always accepts the folder being added.
     */
    private class ModelStubAcceptingFolderAdded extends ModelStub {
        public final FolderStructure folderStructure =
                new FolderStructure(Folder.ROOT_FOLDER, new ArrayList<>());

        @Override
        public boolean hasFolder(Folder folder) {
            requireNonNull(folder);
            return folderStructure.hasFolder(folder);
        }

        @Override
        public void addFolder(Folder folder, Folder parentFolder) {
            requireNonNull(folder);
            folderStructure.find(parentFolder == null ? Folder.ROOT_FOLDER : parentFolder)
                    .getSubfolders().add(new FolderStructure(folder, new ArrayList<>()));
        }
    }
}
