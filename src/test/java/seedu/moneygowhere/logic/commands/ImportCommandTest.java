package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.exceptions.DataConversionException;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.path.FilePath;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ImportCommand.
 */
public class ImportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "SampleSpendings");
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
    }

    @Test
    public void execute_allDataIsImported_showUpdatedMessage() throws DataConversionException, CommandException {
        FilePath path = new FilePath(read("validSpending.csv").toString());
        CommandResult commandResult = new ImportCommand(path).execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 3), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidDate_showMessage() throws DataConversionException, CommandException {
        FilePath path = new FilePath(read("invalidDateSpending.csv").toString());
        CommandResult commandResult = new ImportCommand(path).execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS_WITH_ERRORS, 1, "Row 1: "
                + Date.MESSAGE_CONSTRAINTS + "\n"), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidName_throwCommandException() throws DataConversionException, CommandException {
        FilePath path = new FilePath(read("invalidNameSpending.csv").toString());
        CommandResult commandResult = new ImportCommand(path).execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS_WITH_ERRORS, 1, "Row 1: "
                + Name.MESSAGE_CONSTRAINTS + "\n"), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPath_throwCommandException() throws DataConversionException {
        FilePath path = new FilePath(read("nonexistent.csv").toString());
        assertThrows(CommandException.class, () -> new ImportCommand(path).execute(model));
    }

    private Path read(String configFileInTestDataFolder) throws DataConversionException {
        Path configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        return configFilePath;
    }

    private Path addToTestDataPathIfNotNull(String configFileInTestDataFolder) {
        return configFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(configFileInTestDataFolder)
                : null;
    }
}
