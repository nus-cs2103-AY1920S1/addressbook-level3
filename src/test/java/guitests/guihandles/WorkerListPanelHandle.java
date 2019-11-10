package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.entity.worker.Worker;

//@@author shaoyi1997-reused
//Reused from SE-EDU Address Book Level 4
/**
 * Provides a handle for {@code WorkerListPanel} containing the list of {@code WorkerCard}.
 */
public class WorkerListPanelHandle extends NodeHandle<ListView<Worker>> {
    public static final String WORKER_LIST_VIEW_ID = "#workerListView";

    private static final String CARD_PANE_ID = "#workerCardPane";

    private Optional<Worker> lastRememberedSelectedWorkerCard;

    public WorkerListPanelHandle(ListView<Worker> workerListPanelNode) {
        super(workerListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code WorkerCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public WorkerCardHandle getHandleToSelectedCard() {
        List<Worker> selectedWorkerList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedWorkerList.size() != 1) {
            throw new AssertionError("Worker list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(WorkerCardHandle::new)
                .filter(handle -> handle.equals(selectedWorkerList.get(0)))
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
        List<Worker> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code worker}.
     */
    public void navigateToCard(Worker worker) {
        if (!getRootNode().getItems().contains(worker)) {
            throw new IllegalArgumentException("Worker does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(worker);
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
     * Selects the {@code WorkerCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the worker card handle of a worker associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public WorkerCardHandle getWorkerCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(WorkerCardHandle::new)
                .filter(handle -> handle.equals(getWorker(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Worker getWorker(int index) {
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
     * Remembers the selected {@code WorkerCard} in the list.
     */
    public void rememberSelectedWorkerCard() {
        List<Worker> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedWorkerCard = Optional.empty();
        } else {
            lastRememberedSelectedWorkerCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code WorkerCard} is different from the value remembered by the most recent
     * {@code rememberSelectedWorkerCard()} call.
     */
    public boolean isSelectedWorkerCardChanged() {
        List<Worker> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedWorkerCard.isPresent();
        } else {
            return !lastRememberedSelectedWorkerCard.isPresent()
                    || !lastRememberedSelectedWorkerCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
//@@author
