package seedu.ezwatchlist.logic.commands;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.testutil.TypicalShows.getDatabase;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.ShowBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalWatchList(), getDatabase(), new UserPrefs());
    }

    @Test
    public void execute_newShow_success() {
        Show validShow = new ShowBuilder().build();

        Model expectedModel = new ModelManager(model.getWatchList(), getDatabase(), new UserPrefs());
        expectedModel.addShow(validShow);

        assertCommandSuccess(new AddCommand(validShow), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validShow), expectedModel);
    }

    @Test
    public void execute_duplicateShow_throwsCommandException() {
        Show showInList = model.getWatchList().getShowList().get(0);
        assertCommandFailure(new AddCommand(showInList), model, AddCommand.MESSAGE_DUPLICATE_SHOW);
    }

}
