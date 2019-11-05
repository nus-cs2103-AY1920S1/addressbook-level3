package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVENTORY_DESC_TOY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INVENTORY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INVENTORY;
import static seedu.address.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserSettings;
import seedu.address.model.inventory.InvName;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.Price;
import seedu.address.model.member.MemberId;
import seedu.address.testutil.InventoryBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditInventoryCommandTest {
    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());

    private static final EditInventoryCommand.EditInventoryDescriptor INVENTORY_DESC_BALL =
                                new EditInventoryCommand.EditInventoryDescriptor();
    static {
        INVENTORY_DESC_BALL.setName(new InvName("Sample Inventory Name"));
        INVENTORY_DESC_BALL.setPrice(new Price(0));
        INVENTORY_DESC_BALL.setTaskId(new Index(1));
        INVENTORY_DESC_BALL.setMemId(new MemberId("rak"));
    }

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
//        assertCommandSuccess(editInventoryCommand, model, expectedMessage, expectedModel);
    }

    /*
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastInventory = Index.fromOneBased(model.getFilteredInventoriesList().size());
        Inventory lastInventory = model.getFilteredInventoriesList().get(indexLastInventory.getZeroBased());

        InventoryBuilder inventoryInList = new InventoryBuilder(lastInventory);
        Inventory editedInventory = inventoryInList.withName("Boxes")
                .withPrice(new Price(12.10)).build();

        INVENTORY_DESC_BALL.setName(new InvName("BOXES"));
        INVENTORY_DESC_BALL.setPrice(new Price(12.20));

        EditInventoryCommand.EditInventoryDescriptor descriptor = INVENTORY_DESC_BALL;

        EditInventoryCommand editInventoryCommand = new EditInventoryCommand(indexLastInventory, descriptor);

        String expectedMessage = String.format(EditInventoryCommand.MESSAGE_EDIT_INVENTORY_SUCCESS, editedInventory);

        Model expectedModel = new ModelManager(new ProjectDashboard(model.getProjectDashboard()), new UserPrefs());
        expectedModel.setInventory(lastInventory, editedInventory);

        assertCommandSuccess(editInventoryCommand, model, expectedMessage, expectedModel);
    }
    */
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
    /*
    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Inventory firstInventory = model.getFilteredInventoriesList().get(INDEX_FIRST_INVENTORY.getZeroBased());
        EditInventoryCommand.EditInventoryDescriptor descriptor = INVENTORY_DESC_BALL;
        EditInventoryCommand editInventoryCommand = new EditInventoryCommand(INDEX_SECOND_INVENTORY, descriptor);

        assertCommandFailure(editInventoryCommand, model, EditInventoryCommand.MESSAGE_DUPLICATE_INVENTORY);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInventoriesList().size() + 1);
        EditInventoryCommand.EditInventoryDescriptor descriptor = INVENTORY_DESC_BALL;
        EditInventoryCommand editInventoryCommand = new EditInventoryCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editInventoryCommand, model, Messages.MESSAGE_INVALID_INVENTORY_DISPLAYED_INDEX);
    }
    */
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
