package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertExpandPolicySuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPolicyAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_POLICY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.Policy;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ExpandPolicyCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_expandsFirstPolicyInNotFilteredList() {
        Policy policy = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        assertExpandPolicySuccess(new ExpandPolicyCommand(INDEX_FIRST_POLICY),
            model,
            String.format(ExpandPolicyCommand.MESSAGE_SUCCESS, policy), expectedModel, policy);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);
        showPolicyAtIndex(expectedModel, INDEX_FIRST_POLICY);
        Policy policy = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        assertExpandPolicySuccess(new ExpandPolicyCommand(INDEX_FIRST_PERSON),
            model,
            String.format(ExpandPolicyCommand.MESSAGE_SUCCESS, policy), expectedModel, policy);
    }
}
