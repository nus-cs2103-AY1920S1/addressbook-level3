//@@author LeowWB

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCUMENT_PATH_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCUMENT_PATH_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JSON_EXPORT_PATH_1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.ExportTestUtil.deleteFileIfExists;
import static seedu.address.testutil.ExportTestUtil.isFilePresent;
import static seedu.address.testutil.TypicalFlashCards.NUS;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.category.Category;
import seedu.address.model.export.DocumentPath;
import seedu.address.model.export.JsonExportPath;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class ExportCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private DocumentPath firstDocumentPath = new DocumentPath(VALID_DOCUMENT_PATH_1);
    private DocumentPath secondDocumentPath = new DocumentPath(VALID_DOCUMENT_PATH_2);
    private DocumentPath thirdDocumentPath = new DocumentPath(VALID_DOCUMENT_PATH_2);
    private JsonExportPath firstJsonExportPath = new JsonExportPath(VALID_JSON_EXPORT_PATH_1);
    private Category firstCategory = new Category(VALID_CATEGORY_HISTORY);
    private Category secondCategory = new Category(VALID_CATEGORY_LOCATION);
    private Category thirdCategory = new Category(VALID_CATEGORY_LOCATION);

    private ExportCommand firstCommand = new ExportCommand(firstCategory, firstDocumentPath);
    private ExportCommand secondCommand = new ExportCommand(secondCategory, secondDocumentPath);
    private ExportCommand thirdCommand = new ExportCommand(thirdCategory, thirdDocumentPath);
    private ExportCommand fourthCommand = new ExportCommand(firstCategory, secondDocumentPath);
    private ExportCommand fifthCommand = new ExportCommand(secondCategory, firstJsonExportPath);

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
    public void execute_validInputDocument_fileCreated() {
        model.addFlashCard(NUS);
        String expectedMessage = String.format(
                ExportCommand.MESSAGE_EXPORT_SUCCESS,
                1,
                secondDocumentPath.toAbsolutePathString()
        );

        deleteFileIfExists(secondDocumentPath);
        assertCommandSuccess(secondCommand, model, expectedMessage, model);
        assertTrue(isFilePresent(secondDocumentPath));
        deleteFileIfExists(secondDocumentPath);
    }

    @Test
    public void execute_validInputJson_fileCreated() {
        model.addFlashCard(NUS);
        String expectedMessage = String.format(
                ExportCommand.MESSAGE_EXPORT_SUCCESS,
                1,
                firstJsonExportPath.toAbsolutePathString()
        );

        deleteFileIfExists(firstJsonExportPath);
        assertCommandSuccess(fifthCommand, model, expectedMessage, model);
        assertTrue(isFilePresent(firstJsonExportPath));
        deleteFileIfExists(firstJsonExportPath);
    }

    @Test
    public void execute_nonExistentCategory_exceptionThrown() {
        assertThrows(
            CommandException.class, () ->
                fifthCommand.execute(
                        model
                )
        );
    }
}
