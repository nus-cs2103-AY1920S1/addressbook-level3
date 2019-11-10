package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.moneygowhere.logic.Logic;
import seedu.moneygowhere.logic.LogicManager;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.storage.JsonSpendingBookStorage;
import seedu.moneygowhere.storage.JsonUserPrefsStorage;
import seedu.moneygowhere.storage.StorageManager;

public class ExportCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonSpendingBookStorage spendingBookStorage =
                new JsonSpendingBookStorage(temporaryFolder.resolve("moneygowhere.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(spendingBookStorage, userPrefsStorage);
        Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        model.setSpendingBookFilePath(temporaryFolder.resolve("moneygowhere.json"));
        logic = new LogicManager(model, storage);

    }

    @Test
    public void execute_allDataIsImported_showUpdatedMessage() throws CommandException, ParseException {
        CommandResult result = logic.execute("export " + PREFIX_PATH + temporaryFolder.toString());
        File file = new File(temporaryFolder + "/moneygowhere.csv");
        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, file.getAbsolutePath()),
                result.getFeedbackToUser());
    }

}

