package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEateries.getTypicalAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.eatery.Eatery;
import seedu.address.testutil.EateryBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalFeedList(), new UserPrefs());
    }

    @Test
    public void execute_newEatery_success() {
        Eatery validEatery = new EateryBuilder().withName("New Person").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getFeedList(), new UserPrefs());
        expectedModel.addEatery(validEatery);

        assertCommandSuccess(new AddCommand(validEatery), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validEatery), expectedModel);
    }

    @Test
    public void execute_duplicateEatery_throwsCommandException() {
        Eatery eateryInList = model.getAddressBook().getEateryList().get(0);
        assertCommandFailure(new AddCommand(eateryInList), model, AddCommand.MESSAGE_DUPLICATE_EATERY);
    }

}
