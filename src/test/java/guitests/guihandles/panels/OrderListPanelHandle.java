package guitests.guihandles.panels;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.guihandles.NodeHandle;
import guitests.guihandles.cards.OrderCardHandle;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.order.Order;

/**
 * Provides a handle for {@code OrderListPanel} containing the list of {@code OrderCard}.
 */
public class OrderListPanelHandle extends NodeHandle<ListView<Order>> {
    public static final String ORDER_LIST_VIEW_ID = "#orderListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Order> lastRememberedSelectedOrderCard;

    public OrderListPanelHandle(ListView<Order> orderListPanelNode) {
        super(orderListPanelNode);
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
        List<Order> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code order}.
     */
    public void navigateToCard(Order order) {
        if (!getRootNode().getItems().contains(order)) {
            throw new IllegalArgumentException("Order does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(order);
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
     * Returns the order card handle of a order associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public OrderCardHandle getOrderCardHandle(int index) {
        System.out.println(getAllCardNodes().stream()
                .map(OrderCardHandle::new).count());

        return getAllCardNodes().stream()
                .map(OrderCardHandle::new)
                .filter(handle -> handle.equals(getOrder(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }


    /**
     * Selects the {@code OrderCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }


    private Order getOrder(int index) {
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
     * Remembers the selected {@code OrderCard} in the list.
     */
    public void rememberSelectedOrderCard() {
        List<Order> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedOrderCard = Optional.empty();
        } else {
            lastRememberedSelectedOrderCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code OrderCard} is different from the value remembered by the most recent
     * {@code rememberSelectedOrderCard()} call.
     */
    public boolean isSelectedOrderCardChanged() {
        List<Order> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedOrderCard.isPresent();
        } else {
            return !lastRememberedSelectedOrderCard.isPresent()
                    || !lastRememberedSelectedOrderCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
