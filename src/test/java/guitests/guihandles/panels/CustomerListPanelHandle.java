package guitests.guihandles.panels;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.guihandles.NodeHandle;
import guitests.guihandles.cards.CustomerCardHandle;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.customer.Customer;


/**
 * Provides a handle for {@code CustomerListPanel} containing the list of {@code CustomerCard}.
 */
public class CustomerListPanelHandle extends NodeHandle<ListView<Customer>> {
    public static final String CUSTOMER_LIST_VIEW_ID = "#customerListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Customer> lastRememberedSelectedCustomerCard;

    public CustomerListPanelHandle(ListView<Customer> customerListPanelNode) {
        super(customerListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code CustomerListCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CustomerCardHandle getHandleToSelectedCard() {
        List<Customer> selectedCustomerList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCustomerList.size() != 1) {
            throw new AssertionError("Customer list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(CustomerCardHandle::new)
                .filter(handle -> handle.equals(selectedCustomerList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Customer> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code customer}.
     */
    public void navigateToCard(Customer customer) {
        if (!getRootNode().getItems().contains(customer)) {
            throw new IllegalArgumentException("Customer does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(customer);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code CustomerCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the customer card handle of a customer associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CustomerCardHandle getCustomerCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(CustomerCardHandle::new)
                .filter(handle -> handle.equals(getCustomer(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Customer getCustomer(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code CustomerCard} in the list.
     */
    public void rememberSelectedCustomerCard() {
        List<Customer> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedCustomerCard = Optional.empty();
        } else {
            lastRememberedSelectedCustomerCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code CustomerCard} is different from the value remembered by the most recent
     * {@code rememberSelectedCustomerCard()} call.
     */
    public boolean isSelectedCustomerCardChanged() {
        List<Customer> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedCustomerCard.isPresent();
        } else {
            return !lastRememberedSelectedCustomerCard.isPresent()
                    || !lastRememberedSelectedCustomerCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
