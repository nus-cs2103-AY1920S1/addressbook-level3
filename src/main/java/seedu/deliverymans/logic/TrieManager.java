package seedu.deliverymans.logic;

import java.util.LinkedList;
import java.util.stream.Stream;

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
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.Prefix;
import seedu.deliverymans.logic.parser.customer.CustomerParser;
import seedu.deliverymans.logic.parser.deliveryman.DeliverymanParser;
import seedu.deliverymans.logic.parser.restaurant.RestaurantParser;
import seedu.deliverymans.logic.parser.universal.Context;

/**
 * TO fill
 */
class TrieManager {
    private final Trie universalTrie;
    private final Trie customerTrie;
    private final Trie deliverymanTrie;
    private final Trie restaurantTrie;

    TrieManager() {
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
        insertCommandToAllTries(AddOrderCommand.COMMAND_WORD, AddOrderCommand.getPrefixesList());
        insertCommandToAllTries(CompleteOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(DeleteOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(EditOrderCommand.COMMAND_WORD, EditOrderCommand.getPrefixesList());
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
    private void insertCommandToAllTries(String command, LinkedList<Prefix> prefixes) {
        universalTrie.insertCommand(command, prefixes);
        customerTrie.insertCommand(command, prefixes);
        deliverymanTrie.insertCommand(command, prefixes);
        restaurantTrie.insertCommand(command, prefixes);
    }

    /**
     * TO fill
     */
    LinkedList<String> getAutoCompleteResults(String input, Context context) {
        /*
        int firstSpace = input.indexOf(" ");
        if (firstSpace == -1) { // no space present - user is still typing in the command word
        }
        // user is typing in prefixes
        String commandWord = input.substring(0, firstSpace);
        int lastSpace = input.lastIndexOf(" ");
        String prefixes = input.substring(lastSpace + 1);
        return autoCompletePrefix(commandWord, prefixes, context);
         */
        return autocompleteCommandWord(input, context);
    }

    /**
     * TO fill
     */
    private LinkedList<String> autocompleteCommandWord(String input, Context context) {
        switch (context) {
        case CUSTOMER:
            return customerTrie.autoCompleteCommandWord(input);
        case DELIVERYMEN:
            return deliverymanTrie.autoCompleteCommandWord(input);
        case RESTAURANT:
            return restaurantTrie.autoCompleteCommandWord(input);
        default:
            return universalTrie.autoCompleteCommandWord(input);
        }
    }

    /*
    private LinkedList<String> autoCompletePrefix(String commandWord, String prefixes, Context context) {
        switch (context) {
        case CUSTOMER:
            return customerTrie.autoCompletePrefix(commandWord, prefixes);
        case DELIVERYMEN:
            return deliverymanTrie.autoCompletePrefix(commandWord, prefixes);
        case RESTAURANT:
            return restaurantTrie.autoCompletePrefix(commandWord, prefixes);
        default:
            return universalTrie.autoCompletePrefix(commandWord, prefixes);
        }
    }
     */

    /**
     * TO fill
     */
    boolean hasPrefixes(String input) {
        LinkedList<Prefix> prefixList = universalTrie.getPrefixes(input);
        if (prefixList.isEmpty()) {
            return false;
        }
        return true;
    }
}
