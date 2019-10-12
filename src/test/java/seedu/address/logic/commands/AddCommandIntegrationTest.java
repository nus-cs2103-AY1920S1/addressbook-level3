package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.item.Item;
import seedu.address.model.*;
import seedu.address.testutil.TestItemBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {
    /*
    private ItemModel model;

    @BeforeEach
    public void setUp() {
        model = new ItemModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Item validItem = new TestItemBuilder().build();

        ItemModel expectedModel = new ItemModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addItem(validItem);

        assertCommandSuccess(new AddCommand(validItem), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validItem), expectedModel);
    }

    // Can't test for this yet.
    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

     */

}
