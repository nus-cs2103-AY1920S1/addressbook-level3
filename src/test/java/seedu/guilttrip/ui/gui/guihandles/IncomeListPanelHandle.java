package seedu.guilttrip.ui.gui.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.guilttrip.model.entry.Income;

/**
 * Provides a handle for {@code IncomeListPanel} containing the list of {@code IncomeCard}.
 */
public class IncomeListPanelHandle extends NodeHandle<ListView<Income>> {
    public static final String INCOME_LIST_VIEW_ID = "#incomeListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Income> lastRememberedSelectedIncomeCard;

    public IncomeListPanelHandle(ListView<Income> incomeListPanelNode) {
        super(incomeListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code IncomeCardHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError        if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public IncomeCardHandle getHandleToSelectedCard() {
        List<Income> selectedIncomeList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedIncomeList.size() != 1) {
            throw new AssertionError("Income list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(IncomeCardHandle::new)
                .filter(handle -> handle.equals(selectedIncomeList.get(0)))
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
        List<Income> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code income}.
     */
    public void navigateToCard(Income income) {
        if (!getRootNode().getItems().contains(income)) {
            throw new IllegalArgumentException("Income does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(income);
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
     * Selects the {@code IncomeCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the income card handle of a income associated with the {@code index} in the list.
     *
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public IncomeCardHandle getIncomeCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(IncomeCardHandle::new)
                .filter(handle -> handle.equals(getIncome(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Income getIncome(int index) {
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
     * Remembers the selected {@code IncomeCard} in the list.
     */
    public void rememberSelectedIncomeCard() {
        List<Income> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedIncomeCard = Optional.empty();
        } else {
            lastRememberedSelectedIncomeCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code IncomeCard} is different from the value remembered by the most recent
     * {@code rememberSelectedIncomeCard()} call.
     */
    public boolean isSelectedIncomeCardChanged() {
        List<Income> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedIncomeCard.isPresent();
        } else {
            return lastRememberedSelectedIncomeCard.isEmpty()
                    || !lastRememberedSelectedIncomeCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
