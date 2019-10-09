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
    private static Folder nonNull = new Folder("nonNull");

    @Test
    public void constructor_nullFolderNullParent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFolderCommand(null, null));
    }

    @Test
    public void constructor_nullFolderNonNullParent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFolderCommand(null, nonNull));
    }

    @Test
    public void constructor_nonNullFolderNullParent_createsCommand() {
        assertDoesNotThrow(() -> new AddFolderCommand(nonNull, null));
    }

    @Test
    public void constructor_nonNullFolderNonNullParent_createsCommand() {
        assertDoesNotThrow(() -> new AddFolderCommand(nonNull, nonNull));
    }

    @Test
    public void execute_folderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFolderAdded modelStub = new ModelStubAcceptingFolderAdded();

        CommandResult commandResult = new AddFolderCommand(nonNull, null).execute(modelStub);

        assertEquals(String.format(AddFolderCommand.MESSAGE_SUCCESS, nonNull.folderName),
                commandResult.getFeedbackToUser());

        FolderStructure expected = new FolderStructure(
                Folder.DEFAULT_FOLDER_NAME, List.of(new FolderStructure(nonNull.folderName, new ArrayList<>())));
        assertEquals(expected, modelStub.folderStructure);
    }

    @Test
    public void execute_duplicateBookmark_throwsCommandException() {
        AddFolderCommand addFolderCommand = new AddFolderCommand(nonNull, null);
        ModelStub modelStub = new ModelStubWithFolder(nonNull);

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
        public boolean hasFolder(String folderName) {
            requireNonNull(folderName);
            return folder.folderName.equals(folderName);
        }
    }

    /**
     * A Model stub that always accepts the folder being added.
     */
    private class ModelStubAcceptingFolderAdded extends ModelStub {
        public final FolderStructure folderStructure =
                new FolderStructure(Folder.DEFAULT_FOLDER_NAME, new ArrayList<>());

        @Override
        public boolean hasFolder(String folderName) {
            requireNonNull(folderName);
            return folderStructure.hasFolder(folderName);
        }

        @Override
        public void addFolder(String folderName, String parentFolderName) {
            requireNonNull(folderName);
            folderStructure.find(parentFolderName == null ? Folder.DEFAULT_FOLDER_NAME : parentFolderName)
                    .getSubfolders().add(new FolderStructure(folderName, new ArrayList<>()));
        }
    }
}
