package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.revision.model.answerable.Answerable;

/**
 * Provides a handle for {@code AnswerableListPanel} containing the list of {@code AnswerableCard}.
 */
public class AnswerableListPanelHandle extends NodeHandle<ListView<Answerable>> {
    public static final String ANSWERABLE_LIST_VIEW_ID = "#answerableListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Answerable> lastRememberedSelectedAnswerableCard;

    public AnswerableListPanelHandle(ListView<Answerable> answerableListPanelNode) {
        super(answerableListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code AnswerableCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public AnswerableCardHandle getHandleToSelectedCard() {
        List<Answerable> selectedAnswerableList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedAnswerableList.size() != 1) {
            throw new AssertionError("Answerable list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(AnswerableCardHandle::new)
                .filter(handle -> handle.equals(selectedAnswerableList.get(0)))
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
        List<Answerable> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code answerable}.
     */
    public void navigateToCard(Answerable answerable) {
        if (!getRootNode().getItems().contains(answerable)) {
            throw new IllegalArgumentException("Answerable does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(answerable);
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
     * Selects the {@code AnswerableCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the answerable card handle of a answerable associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public AnswerableCardHandle getAnswerableCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(AnswerableCardHandle::new)
                .filter(handle -> handle.equals(getAnswerable(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Answerable getAnswerable(int index) {
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
     * Remembers the selected {@code AnswerableCard} in the list.
     */
    public void rememberSelectedAnswerableCard() {
        List<Answerable> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedAnswerableCard = Optional.empty();
        } else {
            lastRememberedSelectedAnswerableCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code AnswerableCard} is different from the value remembered by the most recent
     * {@code rememberSelectedAnswerableCard()} call.
     */
    public boolean isSelectedAnswerableCardChanged() {
        List<Answerable> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedAnswerableCard.isPresent();
        } else {
            return !lastRememberedSelectedAnswerableCard.isPresent()
                    || !lastRememberedSelectedAnswerableCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
