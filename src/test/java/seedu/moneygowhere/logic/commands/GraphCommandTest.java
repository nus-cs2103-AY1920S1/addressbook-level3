package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.logic.commands.GraphCommand.SHOWING_GRAPH_MESSAGE;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BANANA;
import static seedu.moneygowhere.testutil.TypicalSpendings.GLASSES;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Spending;

public class GraphCommandTest {
    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void execute_graphMessage_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_GRAPH_MESSAGE, true, false);
        assertCommandSuccess(new GraphCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_graphMessageValidDateRange_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_GRAPH_MESSAGE, true, false);
        assertCommandSuccess(new GraphCommand(APPLE.getDate(), GLASSES.getDate()), model,
            expectedCommandResult, expectedModel);
    }

    @Test
    public void getGraphData_graphData_success() {
        Map<Date, Double> costPerDateList = new HashMap<>();
        List<Spending> lastShownList = expectedModel.getFilteredSpendingList();
        for (Spending i: lastShownList) {
            costPerDateList.put(i.getDate(), Double.parseDouble(i.getCost().toString()));
        }
        assertEquals(costPerDateList, new GraphCommand().getGraphData(model));
    }

    @Test
    public void getGraphData_graphDataValidDateRange_success() {
        Map<Date, Double> costPerDateList = new HashMap<>();
        costPerDateList.put(APPLE.getDate(), Double.parseDouble(APPLE.getCost().toString()));
        costPerDateList.put(BANANA.getDate(), Double.parseDouble(BANANA.getCost().toString()));
        assertEquals(costPerDateList, new GraphCommand(APPLE.getDate(), BANANA.getDate()).getGraphData(model));
    }
}
