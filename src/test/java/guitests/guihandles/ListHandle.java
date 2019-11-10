package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javafx.scene.Node;
import javafx.scene.control.ListView;

/**
 * Provides a base for handle for {@code ListPanel} containing the list of {@code E} to extend from.
 *
 * Adapted from:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/guitests/guihandles/PersonListPanelHandle.java
 */
public abstract class ListHandle<T extends CardHandle, E> extends NodeHandle<ListView<E>> {

    private Optional<E> lastRememberedSelectedElementCard;
    private Function<Node, T> converter;
    private final String fxId;

    public ListHandle(ListView<E> listViewPanel, String fxId, Function<Node, T> converter) {
        super(listViewPanel);
        this.fxId = fxId;
        this.converter = converter;
    }

    /**
     * Returns a handle to the selected {@code T}.
     * A maximum of 1 item can be selected at any time.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public T getHandleToSelectedCard() {
        List<E> selectedListOfElement = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedListOfElement.size() != 1) {
            throw new AssertionError("List size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(converter)
                .filter(handle -> handle.wraps(selectedListOfElement.get(0)))
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
        List<E> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code E}.
     */
    public void navigateToCard(E element) {
        if (!getRootNode().getItems().contains(element)) {
            throw new IllegalArgumentException("Element does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(element);
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
     * Selects the {@code E} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the T wrapping E associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public T getCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(converter)
                .filter(handle -> {
                    E element = getWrappedElement(index);
                    return handle.wraps(element);
                })
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private E getWrappedElement(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(fxId).queryAll();
    }

    /**
     * Remembers the selected {@code T} in the list.
     */
    public void rememberSelectedCard() {
        List<E> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedElementCard = Optional.empty();
        } else {
            lastRememberedSelectedElementCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code T} is different from the value remembered by the most recent
     * {@code rememberSelectedCard()} call.
     */
    public boolean isSelectedElementCardChanged() {
        List<E> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedElementCard.isPresent();
        } else {
            return !lastRememberedSelectedElementCard.isPresent()
                    || !lastRememberedSelectedElementCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
