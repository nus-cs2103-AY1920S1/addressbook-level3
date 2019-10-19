package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.PurchaseHistory;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.sorter.CustomSorter;

public class HistoryCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMenu(), new UserPrefs(), new UserRecommendations(), new PurchaseHistory(),
                new CustomSorter());
    }

    @Test
    public void execute_noCommands_history() throws CommandException {
        assertEquals(new HistoryCommand().execute(model).getFeedbackToUser(),
                HistoryCommand.MESSAGE_NO_HISTORY);
    }
}
