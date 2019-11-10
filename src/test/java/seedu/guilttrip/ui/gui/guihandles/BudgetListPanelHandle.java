package seedu.guilttrip.ui.gui.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.guilttrip.model.entry.Budget;

/**
 * Provides a handle for {@code BudgetListPanel} containing the list of {@code BudgetCard}.
 */
public class BudgetListPanelHandle extends NodeHandle<ListView<Budget>> {
    public static final String BUDGET_LIST_VIEW_ID = "#budgetListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Budget> lastRememberedSelectedBudgetCard;

    public BudgetListPanelHandle(ListView<Budget> budgetListPanelNode) {
        super(budgetListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code BudgetCardHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError        if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BudgetCardHandle getHandleToSelectedCard() {
        List<Budget> selectedBudgetList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedBudgetList.size() != 1) {
            throw new AssertionError("Budget list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(BudgetCardHandle::new)
                .filter(handle -> handle.equals(selectedBudgetList.get(0)))
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
        List<Budget> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code budget}.
     */
    public void navigateToCard(Budget budget) {
        if (!getRootNode().getItems().contains(budget)) {
            throw new IllegalArgumentException("Budget does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(budget);
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
     * Selects the {@code BudgetCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the budget card handle of a budget associated with the {@code index} in the list.
     *
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BudgetCardHandle getBudgetCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(BudgetCardHandle::new)
                .filter(handle -> handle.equals(getBudget(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Budget getBudget(int index) {
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
     * Remembers the selected {@code BudgetCard} in the list.
     */
    public void rememberSelectedBudgetCard() {
        List<Budget> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedBudgetCard = Optional.empty();
        } else {
            lastRememberedSelectedBudgetCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code BudgetCard} is different from the value remembered by the most recent
     * {@code rememberSelectedBudgetCard()} call.
     */
    public boolean isSelectedBudgetCardChanged() {
        List<Budget> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedBudgetCard.isPresent();
        } else {
            return lastRememberedSelectedBudgetCard.isEmpty()
                    || !lastRememberedSelectedBudgetCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
