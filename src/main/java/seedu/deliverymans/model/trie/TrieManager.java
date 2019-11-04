package seedu.deliverymans.model.trie;

import java.util.LinkedList;

import seedu.deliverymans.logic.commands.customer.CustomerAddCommand;
import seedu.deliverymans.logic.commands.customer.CustomerDeleteCommand;
import seedu.deliverymans.logic.commands.customer.CustomerEditCommand;
import seedu.deliverymans.logic.commands.customer.CustomerHistoryCommand;
import seedu.deliverymans.logic.commands.customer.CustomerListCommand;
import seedu.deliverymans.logic.commands.customer.CustomerSortCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanAddCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanAssignCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanDeleteCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanEditCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanEnterRecordCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanGetStatisticsCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanListStatusCommand;
import seedu.deliverymans.logic.commands.deliveryman.DeliverymanStatusSwitchCommand;
import seedu.deliverymans.logic.commands.restaurant.AddFoodCommand;
import seedu.deliverymans.logic.commands.restaurant.AddRatingCommand;
import seedu.deliverymans.logic.commands.restaurant.AddRestaurantCommand;
import seedu.deliverymans.logic.commands.restaurant.DeleteFoodCommand;
import seedu.deliverymans.logic.commands.restaurant.DeleteRestaurantCommand;
import seedu.deliverymans.logic.commands.restaurant.EditDetailsCommand;
import seedu.deliverymans.logic.commands.restaurant.EditModeCommand;
import seedu.deliverymans.logic.commands.restaurant.ExitEditCommand;
import seedu.deliverymans.logic.commands.universal.AddOrderCommand;
import seedu.deliverymans.logic.commands.universal.CompleteOrderCommand;
import seedu.deliverymans.logic.commands.universal.DeleteOrderCommand;
import seedu.deliverymans.logic.commands.universal.EditOrderCommand;
import seedu.deliverymans.logic.commands.universal.ExitCommand;
import seedu.deliverymans.logic.commands.universal.HelpCommand;
import seedu.deliverymans.logic.commands.universal.ListOrderCommand;
import seedu.deliverymans.logic.commands.universal.RedoCommand;
import seedu.deliverymans.logic.commands.universal.UndoCommand;
import seedu.deliverymans.logic.parser.customer.CustomerParser;
import seedu.deliverymans.logic.parser.deliveryman.DeliverymanParser;
import seedu.deliverymans.logic.parser.restaurant.RestaurantParser;
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
        universalTrie = new Trie();
        customerTrie = new Trie();
        deliverymanTrie = new Trie();
        restaurantTrie = new Trie();
        addUniversalCommands();
        addCustomerCommands();
        addDeliverymanCommands();
        addRestaurantCommands();
    }

    /**
     * TO fill
     */
    private void addCustomerCommands() {
        customerTrie.insertCommand(CustomerAddCommand.COMMAND_WORD);
        customerTrie.insertCommand(CustomerDeleteCommand.COMMAND_WORD);
        customerTrie.insertCommand(CustomerEditCommand.COMMAND_WORD);
        customerTrie.insertCommand(CustomerHistoryCommand.COMMAND_WORD);
        customerTrie.insertCommand(CustomerListCommand.COMMAND_WORD);
        customerTrie.insertCommand(CustomerSortCommand.COMMAND_WORD);
    }

    /**
     * TO fill
     */
    private void addDeliverymanCommands() {
        deliverymanTrie.insertCommand(DeliverymanAddCommand.COMMAND_WORD);
        deliverymanTrie.insertCommand(DeliverymanAssignCommand.COMMAND_WORD);
        deliverymanTrie.insertCommand(DeliverymanDeleteCommand.COMMAND_WORD);
        deliverymanTrie.insertCommand(DeliverymanEditCommand.COMMAND_WORD);
        deliverymanTrie.insertCommand(DeliverymanEnterRecordCommand.COMMAND_WORD);
        deliverymanTrie.insertCommand(DeliverymanGetStatisticsCommand.COMMAND_WORD);
        deliverymanTrie.insertCommand(DeliverymanListStatusCommand.COMMAND_WORD);
        deliverymanTrie.insertCommand(DeliverymanStatusSwitchCommand.COMMAND_WORD);
    }

    /**
     * TO fill
     */
    private void addRestaurantCommands() {
        restaurantTrie.insertCommand(AddFoodCommand.COMMAND_WORD);
        restaurantTrie.insertCommand(AddRatingCommand.COMMAND_WORD);
        restaurantTrie.insertCommand(AddRestaurantCommand.COMMAND_WORD);
        restaurantTrie.insertCommand(DeleteFoodCommand.COMMAND_WORD);
        restaurantTrie.insertCommand(DeleteRestaurantCommand.COMMAND_WORD);
        restaurantTrie.insertCommand(EditDetailsCommand.COMMAND_WORD);
        restaurantTrie.insertCommand(EditModeCommand.COMMAND_WORD);
        restaurantTrie.insertCommand(ExitEditCommand.COMMAND_WORD);
    }

    /**
     * TO fill
     */
    private void addUniversalCommands() {
        insertCommandToAllTries(AddOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(CompleteOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(DeleteOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(EditOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(ExitCommand.COMMAND_WORD);
        insertCommandToAllTries(HelpCommand.COMMAND_WORD);
        insertCommandToAllTries(ListOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(RedoCommand.COMMAND_WORD);
        // insertCommandToAllTries(SummaryCommand.COMMAND_WORD);
        insertCommandToAllTries(UndoCommand.COMMAND_WORD);
        insertCommandToAllTries(CustomerParser.COMMAND_WORD);
        insertCommandToAllTries(DeliverymanParser.COMMAND_WORD);
        insertCommandToAllTries(RestaurantParser.COMMAND_WORD);
    }

    /**
     * TO fill
     */
    private void insertCommandToAllTries(String command) {
        universalTrie.insertCommand(command);
        customerTrie.insertCommand(command);
        deliverymanTrie.insertCommand(command);
        restaurantTrie.insertCommand(command);
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
