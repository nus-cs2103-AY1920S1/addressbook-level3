package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ImportCommand.
 */
public class ImportCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
    }

    @Test
    public void execute_dataIsNotImported_showSameData() {
        CommandResult commandResult = new ImportCommand().execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS), commandResult.getFeedbackToUser());
    }
}
