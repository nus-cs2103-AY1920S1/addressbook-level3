//@@author LeowWB

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.FileUtil.isFileExists;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCUMENT_PATH_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCUMENT_PATH_2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import java.io.File;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.category.Category;
import seedu.address.model.export.DocumentPath;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class ExportCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private DocumentPath firstDocumentPath = new DocumentPath(VALID_DOCUMENT_PATH_1);
    private DocumentPath secondDocumentPath = new DocumentPath(VALID_DOCUMENT_PATH_2);
    private DocumentPath thirdDocumentPath = new DocumentPath(VALID_DOCUMENT_PATH_2);
    private Category firstCategory = new Category(VALID_CATEGORY_HISTORY);
    private Category secondCategory = new Category(VALID_CATEGORY_LOCATION);
    private Category thirdCategory = new Category(VALID_CATEGORY_LOCATION);

    private ExportCommand firstCommand = new ExportCommand(firstCategory, firstDocumentPath);
    private ExportCommand secondCommand = new ExportCommand(secondCategory, secondDocumentPath);
    private ExportCommand thirdCommand = new ExportCommand(thirdCategory, thirdDocumentPath);
    private ExportCommand fourthCommand = new ExportCommand(firstCategory, secondDocumentPath);

    @Test
    public void equals() {
        // returns true if same instance
        assertTrue(firstCommand.equals(firstCommand));

        // returns true if all fields identical (even if different instance)
        assertTrue(secondCommand.equals(thirdCommand));

        // returns false if even one field not the same
        assertFalse(firstCommand.equals(fourthCommand));
        assertFalse(secondCommand.equals(fourthCommand));

        // returns false if argument is of a different type
        assertFalse(firstCommand.equals(new String()));

        // returns false if argument is null
        assertFalse(firstCommand.equals(null));

        // returns false if both fields not the same
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_validInput_fileCreated() {
        String expectedMessage = String.format(
                ExportCommand.MESSAGE_EXPORT_SUCCESS,
                firstDocumentPath.toAbsolutePathString()
        );

        deleteFileIfExists(firstDocumentPath);
        assertCommandSuccess(firstCommand, model, expectedMessage, model);
        assertTrue(isFilePresent(firstDocumentPath));
        deleteFileIfExists(firstDocumentPath);
    }

    private boolean isFilePresent(DocumentPath documentPath) {
        return isFileExists(
                documentPath.getPath()
        );
    }

    private void deleteFile(DocumentPath documentPath) {
        File file = new File(documentPath.toString());
        file.delete();
    }

    private void deleteFileIfExists(DocumentPath documentPath) {
        if (isFilePresent(documentPath)) {
            deleteFile(documentPath);
        }
    }


}
