package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertListPolicyCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPolicyAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListPolicyCommand.
 */
public class ListPolicyCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertListPolicyCommandSuccess(new ListPolicyCommand(),
                model,
                ListPolicyCommand.MESSAGE_SUCCESS, expectedModel);
    }

    //    @Test
    //    public void execute_listIsFiltered_showsEverything() {
    //        showPolicyAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        assertListPolicyCommandSuccess(new ListPolicyCommand(),
    //                model,
    //                ListPolicyCommand.MESSAGE_SUCCESS, expectedModel);
    //    }
}
