package seedu.deliverymans.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.deliverymans.logic.commands.CommandTestUtil.showRestaurantAtIndex;
import static seedu.deliverymans.model.util.SampleDataUtil.getTagSet;
import static seedu.deliverymans.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.deliverymans.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.logic.commands.restaurant.DeleteRestaurantCommand;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.ModelManager;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.UserPrefs;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.OrderDatabase;
import seedu.deliverymans.model.database.RestaurantDatabase;
import seedu.deliverymans.model.location.LocationMap;
import seedu.deliverymans.model.restaurant.Restaurant;



public class DeleteRestaurantCommandTest {

    private Model model = new ModelManager(new CustomerDatabase(), new DeliverymenDatabase(),
            getRestaurantDatabase(), new OrderDatabase(), new UserPrefs());

    public static RestaurantDatabase getRestaurantDatabase() {
        Restaurant restaurant1 = new Restaurant(new Name("Test1"),
                LocationMap.getLocation("Jurong").get(),
                getTagSet());
        Restaurant restaurant2 = new Restaurant(new Name("Test2"),
                LocationMap.getLocation("Changi").get(),
                getTagSet());
        RestaurantDatabase rd = new RestaurantDatabase();
        rd.addRestaurant(restaurant1);
        rd.addRestaurant(restaurant2);
        return rd;
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Restaurant restaurantToDelete = model.getFilteredRestaurantList().get(INDEX_FIRST.getZeroBased());
        DeleteRestaurantCommand dc = new DeleteRestaurantCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteRestaurantCommand.MESSAGE_DELETE_RESTAURANT_SUCCESS,
                restaurantToDelete);

        ModelManager expectedModel = new ModelManager(model.getCustomerDatabase(), model.getDeliverymenDatabase(),
                model.getRestaurantDatabase(), model.getOrderDatabase(), new UserPrefs());
        expectedModel.deleteRestaurant(restaurantToDelete);

        assertCommandSuccess(dc, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);
        DeleteRestaurantCommand deleteCommand = new DeleteRestaurantCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRestaurantAtIndex(model, INDEX_FIRST);

        Restaurant personToDelete = model.getFilteredRestaurantList().get(INDEX_FIRST.getZeroBased());
        DeleteRestaurantCommand deleteCommand = new DeleteRestaurantCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteRestaurantCommand.MESSAGE_DELETE_RESTAURANT_SUCCESS,
                personToDelete);

        ModelManager expectedModel = new ModelManager(model.getCustomerDatabase(), model.getDeliverymenDatabase(),
                model.getRestaurantDatabase(), model.getOrderDatabase(), new UserPrefs());
        expectedModel.deleteRestaurant(personToDelete);
        showNoRestaurant(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRestaurantAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantDatabase().getRestaurantList().size());

        DeleteRestaurantCommand deleteCommand = new DeleteRestaurantCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteRestaurantCommand deleteFirstCommand = new DeleteRestaurantCommand(INDEX_FIRST);
        DeleteRestaurantCommand deleteSecondCommand = new DeleteRestaurantCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRestaurantCommand deleteFirstCommandCopy = new DeleteRestaurantCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different restaurant -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoRestaurant(Model model) {
        model.updateFilteredRestaurantList(p -> false);

        assertTrue(model.getFilteredRestaurantList().isEmpty());
    }
}
