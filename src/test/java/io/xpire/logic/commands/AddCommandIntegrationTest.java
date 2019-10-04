package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.Item;
import io.xpire.testutil.ItemBuilder;
import io.xpire.testutil.TypicalItems;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalItems.getTypicalExpiryDateTracker(), new UserPrefs());
    }

    @Test
    public void execute_newItem_success() {
        Item validItem = new ItemBuilder().build();

        Model expectedModel = new ModelManager(model.getXpire(), new UserPrefs());
        expectedModel.addItem(validItem);

        assertCommandSuccess(new AddCommand(validItem), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validItem), expectedModel);
    }

    @Test
    public void execute_duplicateItem_throwsCommandException() {
        Item personInList = model.getXpire().getItemList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_ITEM);
    }

}
