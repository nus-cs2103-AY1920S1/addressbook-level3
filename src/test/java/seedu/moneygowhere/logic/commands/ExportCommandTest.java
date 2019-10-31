package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.path.FolderPath;

public class ExportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "SampleSpendings");
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
    }

    @Test
    public void execute_allDataIsImported_showUpdatedMessage() throws CommandException {
        FolderPath path = new FolderPath(TEST_DATA_FOLDER.toString());
        CommandResult commandResult = new ExportCommand(path).execute(model);
        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, TEST_DATA_FOLDER.toString()),
                commandResult.getFeedbackToUser());
    }

}

