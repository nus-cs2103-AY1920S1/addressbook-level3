package seedu.deliverymans.model.trie;

import java.util.LinkedList;

import seedu.deliverymans.logic.commands.customer.AddCommand;
import seedu.deliverymans.logic.commands.customer.DeleteCommand;
import seedu.deliverymans.logic.commands.customer.EditCommand;
import seedu.deliverymans.logic.commands.customer.HistoryCommand;
import seedu.deliverymans.logic.commands.customer.ListCommand;
import seedu.deliverymans.logic.commands.customer.SortCommand;

import seedu.deliverymans.logic.commands.deliveryman.AssignCommand;
import seedu.deliverymans.logic.commands.deliveryman.ListAvailCommand;
import seedu.deliverymans.logic.commands.deliveryman.ListStatusCommand;
import seedu.deliverymans.logic.commands.deliveryman.StatusSwitchCommand;

import seedu.deliverymans.logic.commands.restaurant.AddFoodCommand;
import seedu.deliverymans.logic.commands.restaurant.AddRatingCommand;
import seedu.deliverymans.logic.commands.restaurant.AddRestaurantCommand;
import seedu.deliverymans.logic.commands.restaurant.DeleteFoodCommand;
import seedu.deliverymans.logic.commands.restaurant.DeleteRestaurantCommand;
import seedu.deliverymans.logic.commands.restaurant.EditDetailsCommand;
import seedu.deliverymans.logic.commands.restaurant.EditModeCommand;
import seedu.deliverymans.logic.commands.restaurant.ExitEditCommand;
import seedu.deliverymans.logic.commands.universal.AddOrderCommand;
import seedu.deliverymans.logic.commands.universal.DeleteOrderCommand;
import seedu.deliverymans.logic.commands.universal.EditOrderCommand;
import seedu.deliverymans.logic.commands.universal.ExitCommand;
import seedu.deliverymans.logic.commands.universal.HelpCommand;
import seedu.deliverymans.logic.commands.universal.RedoCommand;
import seedu.deliverymans.logic.commands.universal.SummaryCommand;
import seedu.deliverymans.logic.commands.universal.UndoCommand;
import seedu.deliverymans.logic.parser.universal.Context;

/**
 * TO fill
 */
public class TrieManager {
    private final Trie universalTrie;
    private final Trie customerTrie;
    private final Trie deliverymanTrie;
    private final Trie restaurantTrie;

    public TrieManager() {
        universalTrie = new Trie("");
        customerTrie = new Trie("");
        deliverymanTrie = new Trie("");
        restaurantTrie = new Trie("");
        addUniversalCommands();
        addCustomerCommands();
        addDeliverymanCommands();
        addRestaurantCommands();
    }

    /**
     * TO fill
     */
    private void addCustomerCommands() {
        customerTrie.insert(AddCommand.COMMAND_WORD);
        customerTrie.insert(DeleteCommand.COMMAND_WORD);
        customerTrie.insert(EditCommand.COMMAND_WORD);
        customerTrie.insert(HistoryCommand.COMMAND_WORD);
        customerTrie.insert(ListCommand.COMMAND_WORD);
        customerTrie.insert(SortCommand.COMMAND_WORD);
    }

    /**
     * TO fill
     */
    private void addDeliverymanCommands() {
        deliverymanTrie.insert(AssignCommand.COMMAND_WORD);
        deliverymanTrie.insert(ListAvailCommand.COMMAND_WORD);
        deliverymanTrie.insert(ListStatusCommand.COMMAND_WORD);
        deliverymanTrie.insert(StatusSwitchCommand.COMMAND_WORD);
    }

    /**
     * TO fill
     */
    private void addRestaurantCommands() {
        restaurantTrie.insert(AddFoodCommand.COMMAND_WORD);
        restaurantTrie.insert(AddRatingCommand.COMMAND_WORD);
        restaurantTrie.insert(AddRestaurantCommand.COMMAND_WORD);
        restaurantTrie.insert(DeleteFoodCommand.COMMAND_WORD);
        restaurantTrie.insert(DeleteRestaurantCommand.COMMAND_WORD);
        restaurantTrie.insert(EditDetailsCommand.COMMAND_WORD);
        restaurantTrie.insert(EditModeCommand.COMMAND_WORD);
        restaurantTrie.insert(ExitEditCommand.COMMAND_WORD);
    }

    /**
     * TO fill
     */
    private void addUniversalCommands() {
        insertCommandToAllTries(AddOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(DeleteOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(EditOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(ExitCommand.COMMAND_WORD);
        insertCommandToAllTries(HelpCommand.COMMAND_WORD);
        insertCommandToAllTries(RedoCommand.COMMAND_WORD);
        insertCommandToAllTries(SummaryCommand.COMMAND_WORD);
        insertCommandToAllTries(UndoCommand.COMMAND_WORD);
    }

    /**
     * TO fill
     */
    private void insertCommandToAllTries(String command) {
        universalTrie.insert(command);
        customerTrie.insert(command);
        deliverymanTrie.insert(command);
        restaurantTrie.insert(command);
    }

    /**
     * TO fill
     */
    public LinkedList<String> getAutoCompleteResults(String input, Context context) {
        switch (context) {
        case CUSTOMER:
            return customerTrie.autoComplete(input);
        case DELIVERYMEN:
            return deliverymanTrie.autoComplete(input);
        case RESTAURANT:
            return restaurantTrie.autoComplete(input);
        default:
            return universalTrie.autoComplete(input);
        }
    }
}
