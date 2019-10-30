package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.XpireItem;
import io.xpire.testutil.TypicalItems;
import io.xpire.testutil.XpireItemBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalItems.getTypicalLists(), new UserPrefs());
    }

    @Test
    public void execute_newItem_success() {
        XpireItem validXpireItem = new XpireItemBuilder().build();

        Model expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.addItem(validXpireItem);

        assertCommandSuccess(new AddCommand(validXpireItem), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validXpireItem), expectedModel);
    }

    @Test
    public void execute_duplicateItem_throwsCommandException() {
        XpireItem xpireItemInList = (XpireItem) model.getLists()[0].getItemList().get(0);
        assertCommandFailure(new AddCommand(xpireItemInList), model, AddCommand.MESSAGE_DUPLICATE_ITEM);
    }

}
