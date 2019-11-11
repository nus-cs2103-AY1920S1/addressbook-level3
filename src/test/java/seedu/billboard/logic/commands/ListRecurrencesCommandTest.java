package seedu.billboard.logic.commands;

import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;
import static seedu.billboard.testutil.TypicalRecurrences.getTypicalBillboardWithRecurrenceExpenses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;

public class ListRecurrencesCommandTest {
    private Model withRecurrencesModel;
    private Model noRecurrencesModel;
    private Model withRecurrencesExpectedModel;
    private Model noRecurrencesExpectedModel;

    @BeforeEach
    public void setUp() {
        withRecurrencesModel = new ModelManager(getTypicalBillboardWithRecurrenceExpenses(), new UserPrefs());
        noRecurrencesModel = new ModelManager(getTypicalBillboard(), new UserPrefs());
        withRecurrencesExpectedModel = new ModelManager(withRecurrencesModel.getCombinedBillboard(), new UserPrefs());
        noRecurrencesExpectedModel = new ModelManager(noRecurrencesModel.getCombinedBillboard(), new UserPrefs());
    }

    @Test
    public void execute_listArchiveNames_success() {
        String expectedFeedback = "Here are the existing recurrence(s):\n"
                + "[Name: bill Description: pay bill Amount: 10.00 Created: 01 Jan 2019, "
                + "12:00 AM Tags: [] Interval: WEEK Iterations: WEEK]";
        CommandResult expectedCommandResult = new CommandResult(expectedFeedback);
        assertCommandSuccess(new ListRecurrenceCommand(),
                withRecurrencesModel, expectedCommandResult, withRecurrencesExpectedModel);
    }

    @Test
    public void execute_noArchivesToList_success() {
        String expectedFeedback = "There are no existing archives";
        CommandResult expectedCommandResult = new CommandResult(expectedFeedback);
        CommandResult result = new ListRecurrenceCommand().execute(noRecurrencesModel);
        System.out.println(result.getFeedbackToUser());
        assertCommandSuccess(new ListArchiveNamesCommand(),
                noRecurrencesModel, expectedCommandResult, noRecurrencesExpectedModel);
    }
}
