package seedu.deliverymans.logic;

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
import seedu.deliverymans.logic.commands.universal.AssignOrderCommand;
import seedu.deliverymans.logic.commands.universal.CompleteOrderCommand;
import seedu.deliverymans.logic.commands.universal.DeleteOrderCommand;
import seedu.deliverymans.logic.commands.universal.EditOrderCommand;
import seedu.deliverymans.logic.commands.universal.ExitCommand;
import seedu.deliverymans.logic.commands.universal.HelpCommand;
import seedu.deliverymans.logic.commands.universal.ListOrderCommand;
import seedu.deliverymans.logic.commands.universal.RedoCommand;
import seedu.deliverymans.logic.commands.universal.UndoCommand;
import seedu.deliverymans.logic.commands.universal.UndoListCommand;
import seedu.deliverymans.logic.commands.universal.UndoTillCommand;
import seedu.deliverymans.logic.parser.Prefix;
import seedu.deliverymans.logic.parser.customer.CustomerParser;
import seedu.deliverymans.logic.parser.deliveryman.DeliverymanParser;
import seedu.deliverymans.logic.parser.restaurant.RestaurantParser;

/**
 * TrieManager to handle all searching using the relevant Trie.
 */
class TrieManager {
    private final Trie universalTrie;
    private final Trie customerTrie;
    private final Trie deliverymanTrie;
    private final Trie restaurantTrie;
    private final Trie editingTrie;

    TrieManager() {
        universalTrie = new Trie();
        customerTrie = new Trie();
        deliverymanTrie = new Trie();
        restaurantTrie = new Trie();
        editingTrie = new Trie();
        addUniversalCommands();
        addCustomerCommands();
        addDeliverymanCommands();
        addRestaurantCommands();
        addEditingCommands();
    }

    /**
     * Customer commandWords to add to the customerTrie;
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
     * Deliveryman commandWords to add to the deliverymanTrie;
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
     * Restaurant commandWords to add to the restaurantTrie;
     */
    private void addRestaurantCommands() {
        restaurantTrie.insertCommand(AddRestaurantCommand.COMMAND_WORD);
        restaurantTrie.insertCommand(DeleteRestaurantCommand.COMMAND_WORD);
        restaurantTrie.insertCommand(EditModeCommand.COMMAND_WORD);
    }

    /**
     * Editing commandWords to add to the editingTrie;
     */
    private void addEditingCommands() {
        editingTrie.insertCommand(AddFoodCommand.COMMAND_WORD);
        editingTrie.insertCommand(AddRatingCommand.COMMAND_WORD);
        editingTrie.insertCommand(DeleteFoodCommand.COMMAND_WORD);
        editingTrie.insertCommand(EditDetailsCommand.COMMAND_WORD);
        editingTrie.insertCommand(ExitEditCommand.COMMAND_WORD);
    }

    /**
     * Universal commandWords to add to all Tries;
     */
    private void addUniversalCommands() {
        insertCommandToAllTries(AddOrderCommand.COMMAND_WORD, AddOrderCommand.getPrefixesList());
        insertCommandToAllTries(AssignOrderCommand.COMMAND_WORD, AssignOrderCommand.getPrefixesList());
        insertCommandToAllTries(CompleteOrderCommand.COMMAND_WORD, CompleteOrderCommand.getPrefixesList());
        insertCommandToAllTries(DeleteOrderCommand.COMMAND_WORD, DeleteOrderCommand.getPrefixesList());
        insertCommandToAllTries(EditOrderCommand.COMMAND_WORD, EditOrderCommand.getPrefixesList());
        insertCommandToAllTries(ExitCommand.COMMAND_WORD);
        insertCommandToAllTries(HelpCommand.COMMAND_WORD);
        insertCommandToAllTries(ListOrderCommand.COMMAND_WORD);
        insertCommandToAllTries(RedoCommand.COMMAND_WORD);
        insertCommandToAllTries(UndoCommand.COMMAND_WORD);
        insertCommandToAllTries(UndoListCommand.COMMAND_WORD);
        insertCommandToAllTries(UndoTillCommand.COMMAND_WORD);
        insertCommandToAllTries(CustomerParser.COMMAND_WORD);
        insertCommandToAllTries(DeliverymanParser.COMMAND_WORD);
        insertCommandToAllTries(RestaurantParser.COMMAND_WORD);
    }

    /**
     * CommandWords to add to all Tries;
     */
    private void insertCommandToAllTries(String command) {
        universalTrie.insertCommand(command);
        customerTrie.insertCommand(command);
        deliverymanTrie.insertCommand(command);
        restaurantTrie.insertCommand(command);
        editingTrie.insertCommand(command);
    }

    /**
     * CommandWords to add to all Tries;
     */
    private void insertCommandToAllTries(String command, LinkedList<Prefix> prefixes) {
        universalTrie.insertCommand(command, prefixes);
        customerTrie.insertCommand(command, prefixes);
        deliverymanTrie.insertCommand(command, prefixes);
        restaurantTrie.insertCommand(command, prefixes);
        editingTrie.insertCommand(command, prefixes);
    }

    /**
     * Returns a list of autocomplete results.
     *
     * @param input   The String to search.
     * @param context The Context to search in.
     */
    LinkedList<String> getAutoCompleteResults(String input, Context context) {
        return autoCompleteCommandWord(input, context);
    }

    /**
     * Returns a list of matching commandWords depending on the input search String and Context to search in.
     *
     * @param input   The String to search.
     * @param context The Context to search in.
     */
    private LinkedList<String> autoCompleteCommandWord(String input, Context context) {
        switch (context) {
        case CUSTOMER:
            return customerTrie.autoCompleteCommandWord(input);
        case DELIVERYMEN:
            return deliverymanTrie.autoCompleteCommandWord(input);
        case RESTAURANT:
            return restaurantTrie.autoCompleteCommandWord(input);
        case EDITING:
            return editingTrie.autoCompleteCommandWord(input);
        default:
            return universalTrie.autoCompleteCommandWord(input);
        }
    }

    /**
     * Returns a boolean value depending on whether the output Trie contains prefixes.
     */
    boolean hasPrefixes(String input) {
        LinkedList<Prefix> prefixList = universalTrie.getPrefixes(input);
        if (prefixList.isEmpty()) {
            return false;
        }
        return true;
    }
}
