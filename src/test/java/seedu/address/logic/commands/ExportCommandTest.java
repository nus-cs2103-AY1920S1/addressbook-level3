package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_PATH_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_PATH_2;

import org.junit.jupiter.api.Test;

import seedu.address.model.category.Category;
import seedu.address.model.util.FilePath;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class ExportCommandTest {
    private FilePath firstFilePath = new FilePath(VALID_FILE_PATH_1);
    private FilePath secondFilePath = new FilePath(VALID_FILE_PATH_2);
    private FilePath thirdFilePath = new FilePath(VALID_FILE_PATH_2);
    private Category firstCategory = new Category(VALID_CATEGORY_HISTORY);
    private Category secondCategory = new Category(VALID_CATEGORY_LOCATION);
    private Category thirdCategory = new Category(VALID_CATEGORY_LOCATION);

    private ExportCommand firstCommand = new ExportCommand(firstCategory, firstFilePath);
    private ExportCommand secondCommand = new ExportCommand(secondCategory, secondFilePath);
    private ExportCommand thirdCommand = new ExportCommand(thirdCategory, thirdFilePath);
    private ExportCommand fourthCommand = new ExportCommand(firstCategory, secondFilePath);

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
}
