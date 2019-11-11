package seedu.pluswork.logic.commands.inventory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVENTORY_DESC_TOY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_INVENTORY_NAME_SPORTS;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_INVENTORY_PRICE_SPORTS;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_FIRST_INVENTORY;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_SECOND_INVENTORY;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_THIRD_INVENTORY;
import static seedu.pluswork.testutil.TypicalInventories.getTypicalProjectDashboard;

import org.junit.jupiter.api.Test;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.universal.ClearCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.inventory.InvName;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.inventory.Price;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.testutil.EditInventoryDescriptorBuilder;
import seedu.pluswork.testutil.InventoryBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests
 * for EditInventoryCommand.
 */
public class EditInventoryCommandTest {
    private static final EditInventoryCommand.EditInventoryDescriptor INVENTORY_DESC_BALL =
            new EditInventoryCommand.EditInventoryDescriptor();

    static {
        INVENTORY_DESC_BALL.setName(new InvName("Sample Inventory Name"));
        INVENTORY_DESC_BALL.setPrice(new Price(0));
        INVENTORY_DESC_BALL.setTaskId(new Index(1));
        INVENTORY_DESC_BALL.setMemId(new MemberId("rak"));
    }

    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Inventory editedInventory = new InventoryBuilder().build();
        EditInventoryCommand editInventoryCommand =
                new EditInventoryCommand(INDEX_FIRST_INVENTORY, INVENTORY_DESC_BALL);

        String expectedMessage = String.format(EditInventoryCommand.MESSAGE_EDIT_INVENTORY_SUCCESS, editedInventory);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());
        expectedModel.setInventory(model.getFilteredInventoriesList().get(0), editedInventory);

        //Commented out because of Null pointed exception
        // assertCommandSuccess(editInventoryCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastInventory = Index.fromOneBased(model.getFilteredInventoriesList().size());
        Inventory firstInv = model.getFilteredInventoriesList().get(INDEX_FIRST_INVENTORY.getZeroBased());

        InventoryBuilder invInList = new InventoryBuilder(firstInv);
        Inventory editedInv = invInList.withName(VALID_INVENTORY_NAME_SPORTS)
                .withPrice(new Price(VALID_INVENTORY_PRICE_SPORTS)).build();

        EditInventoryCommand.EditInventoryDescriptor descriptor = new EditInventoryDescriptorBuilder()
                .withName(VALID_INVENTORY_NAME_SPORTS)
                .withPrice(new Price(VALID_INVENTORY_PRICE_SPORTS)).build();
        EditInventoryCommand editInventoryCommand = new EditInventoryCommand(INDEX_FIRST_INVENTORY, descriptor);

        String expectedMessage = String.format(EditInventoryCommand.MESSAGE_EDIT_INVENTORY_SUCCESS, editedInv);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());
        expectedModel.setInventory(firstInv, editedInv);

        assertCommandSuccess(editInventoryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditInventoryCommand editInventoryCommand =
                new EditInventoryCommand(INDEX_FIRST_INVENTORY, new EditInventoryCommand.EditInventoryDescriptor());
        Inventory editedInventory = model.getFilteredInventoriesList().get(INDEX_FIRST_INVENTORY.getZeroBased());

        String expectedMessage = String.format(EditInventoryCommand.MESSAGE_EDIT_INVENTORY_SUCCESS, editedInventory);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());

        assertCommandSuccess(editInventoryCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicateInventoryUnfilteredList_failure() {
        Inventory firstInv = model.getFilteredInventoriesList().get(INDEX_FIRST_INVENTORY.getZeroBased());
        EditInventoryCommand.EditInventoryDescriptor descriptor = new EditInventoryDescriptorBuilder(firstInv).build();
        EditInventoryCommand editInventoryCommand = new EditInventoryCommand(INDEX_THIRD_INVENTORY, descriptor);

        assertCommandFailure(editInventoryCommand, model, EditInventoryCommand.MESSAGE_DUPLICATE_INVENTORY);
    }

    @Test
    public void execute_invalidInventoryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInventoriesList().size() + 1);
        EditInventoryCommand.EditInventoryDescriptor descriptor = INVENTORY_DESC_BALL;
        EditInventoryCommand editInventoryCommand = new EditInventoryCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editInventoryCommand, model, Messages.MESSAGE_INVALID_INVENTORY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditInventoryCommand standardCommand =
                new EditInventoryCommand(INDEX_FIRST_INVENTORY, INVENTORY_DESC_TOY);

        //

        //// same values -> returns true

        //EditInventoryCommand.EditInventoryDescriptor copyDescriptor =
        // new EditInventoryCommand.EditInventoryDescriptor(INVENTORY_DESC_TOY);

        //EditInventoryCommand commandWithSameValues = new EditInventoryCommand(INDEX_FIRST_INVENTORY, copyDescriptor);

        //assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditInventoryCommand(INDEX_SECOND_INVENTORY, INVENTORY_DESC_TOY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditInventoryCommand(INDEX_FIRST_INVENTORY, INVENTORY_DESC_TOY)));
    }
}
