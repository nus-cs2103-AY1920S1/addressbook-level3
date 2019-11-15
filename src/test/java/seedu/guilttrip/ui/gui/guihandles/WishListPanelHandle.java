package seedu.guilttrip.ui.gui.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.guilttrip.model.entry.Wish;

/**
 * Provides a handle for {@code WishListPanel} containing the list of {@code WishListCard}.
 */
public class WishListPanelHandle extends NodeHandle<ListView<Wish>> {
    public static final String WISH_LIST_VIEW_ID = "#wishListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Wish> lastRememberedSelectedWishListCard;

    public WishListPanelHandle(ListView<Wish> wishListPanelNode) {
        super(wishListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code WishListCardHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError        if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public WishListCardHandle getHandleToSelectedCard() {
        List<Wish> selectedWishList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedWishList.size() != 1) {
            throw new AssertionError("Wish list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(WishListCardHandle::new)
                .filter(handle -> handle.equals(selectedWishList.get(0)))
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
        List<Wish> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code wish}.
     */
    public void navigateToCard(Wish wish) {
        if (!getRootNode().getItems().contains(wish)) {
            throw new IllegalArgumentException("Wish does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(wish);
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
     * Selects the {@code WishListCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the wish card handle of a wish associated with the {@code index} in the list.
     *
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public WishListCardHandle getWishListCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(WishListCardHandle::new)
                .filter(handle -> handle.equals(getWish(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Wish getWish(int index) {
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
     * Remembers the selected {@code WishListCard} in the list.
     */
    public void rememberSelectedWishListCard() {
        List<Wish> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedWishListCard = Optional.empty();
        } else {
            lastRememberedSelectedWishListCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code WishListCard} is different from the value remembered by the most recent
     * {@code rememberSelectedWishListCard()} call.
     */
    public boolean isSelectedWishListCardChanged() {
        List<Wish> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedWishListCard.isPresent();
        } else {
            return lastRememberedSelectedWishListCard.isEmpty()
                    || !lastRememberedSelectedWishListCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
