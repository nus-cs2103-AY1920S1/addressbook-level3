package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEateries.getTypicalOpenAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ModeCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalOpenAddressBook(), getTypicalFeedList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getFeedList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_modeChanged() {
        assertCommandSuccess(new ModeCommand(), model, ModeCommand.MESSAGE_SUCCESS_TODO, expectedModel);
        assertCommandSuccess(new ModeCommand(), model, ModeCommand.MESSAGE_SUCCESS_MAIN, expectedModel);
    }


}
