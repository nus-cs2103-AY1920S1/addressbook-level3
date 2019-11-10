package seedu.guilttrip.ui.gui.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.guilttrip.model.entry.AutoExpense;

/**
 * Provides a handle for {@code AutoExpenseListPanel} containing the list of {@code AutoExpenseCard}.
 */
public class AutoExpenseListPanelHandle extends NodeHandle<ListView<AutoExpense>> {
    public static final String AUTOEXPENSE_LIST_VIEW_ID = "#autoExpenseListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<AutoExpense> lastRememberedSelectedAutoExpenseCard;

    public AutoExpenseListPanelHandle(ListView<AutoExpense> autoExpenseListPanelNode) {
        super(autoExpenseListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code AutoExpenseCardHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError        if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public AutoExpenseCardHandle getHandleToSelectedCard() {
        List<AutoExpense> selectedAutoExpenseList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedAutoExpenseList.size() != 1) {
            throw new AssertionError("AutoExpense list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(AutoExpenseCardHandle::new)
                .filter(handle -> handle.equals(selectedAutoExpenseList.get(0)))
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
        List<AutoExpense> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code autoExpense}.
     */
    public void navigateToCard(AutoExpense autoExpense) {
        if (!getRootNode().getItems().contains(autoExpense)) {
            throw new IllegalArgumentException("AutoExpense does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(autoExpense);
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
     * Selects the {@code AutoExpenseCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the autoExpense card handle of a autoExpense associated with the {@code index} in the list.
     *
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public AutoExpenseCardHandle getAutoExpenseCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(AutoExpenseCardHandle::new)
                .filter(handle -> handle.equals(getAutoExpense(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private AutoExpense getAutoExpense(int index) {
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
     * Remembers the selected {@code AutoExpenseCard} in the list.
     */
    public void rememberSelectedAutoExpenseCard() {
        List<AutoExpense> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedAutoExpenseCard = Optional.empty();
        } else {
            lastRememberedSelectedAutoExpenseCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code AutoExpenseCard} is different from the value remembered by the most recent
     * {@code rememberSelectedAutoExpenseCard()} call.
     */
    public boolean isSelectedAutoExpenseCardChanged() {
        List<AutoExpense> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedAutoExpenseCard.isPresent();
        } else {
            return lastRememberedSelectedAutoExpenseCard.isEmpty()
                    || !lastRememberedSelectedAutoExpenseCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
