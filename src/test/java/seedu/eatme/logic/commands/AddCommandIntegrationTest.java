package seedu.eatme.logic.commands;

import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.testutil.EateryBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());
    }

    @Test
    public void execute_newEatery_success() {
        Eatery validEatery = new EateryBuilder().withName("New Person").build();

        Model expectedModel = new ModelManager(model.getEateryList(), model.getFeedList(), new UserPrefs());
        expectedModel.addEatery(validEatery);

        assertCommandSuccess(new AddCommand(validEatery), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validEatery.getName().fullName), expectedModel);
    }

    @Test
    public void execute_duplicateEatery_throwsCommandException() {
        Eatery eateryInList = model.getEateryList().getEateryList().get(0);
        assertCommandFailure(new AddCommand(eateryInList), model, AddCommand.MESSAGE_DUPLICATE_EATERY);
    }

}
