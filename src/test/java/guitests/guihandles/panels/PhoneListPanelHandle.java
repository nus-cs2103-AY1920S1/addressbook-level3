package guitests.guihandles.panels;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.guihandles.NodeHandle;
import guitests.guihandles.cards.PhoneCardHandle;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.phone.Phone;


/**
 * Provides a handle for {@code PhoneListPanel} containing the list of {@code PhoneCard}.
 */
public class PhoneListPanelHandle extends NodeHandle<ListView<Phone>> {
    public static final String PHONE_LIST_VIEW = "#phoneListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Phone> lastRememberedSelectedPhoneCard;

    public PhoneListPanelHandle(ListView<Phone> phoneListPanelNode) {
        super(phoneListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PhoneCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PhoneCardHandle getHandleToSelectedCard() {
        List<Phone> selectedPhoneList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedPhoneList.size() != 1) {
            throw new AssertionError("Phone list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(PhoneCardHandle::new)
                .filter(handle -> handle.equals(selectedPhoneList.get(0)))
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
        List<Phone> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code phone}.
     */
    public void navigateToCard(Phone phone) {
        if (!getRootNode().getItems().contains(phone)) {
            throw new IllegalArgumentException("Phone does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(phone);
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
     * Selects the {@code PhoneCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the phone card handle of a phone associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PhoneCardHandle getPhoneCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(PhoneCardHandle::new)
                .filter(handle -> handle.equals(getPhone(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Phone getPhone(int index) {
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
     * Remembers the selected {@code PersonCard} in the list.
     */
    public void rememberSelectedPhoneCard() {
        List<Phone> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPhoneCard = Optional.empty();
        } else {
            lastRememberedSelectedPhoneCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<Phone> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPhoneCard.isPresent();
        } else {
            return !lastRememberedSelectedPhoneCard.isPresent()
                    || !lastRememberedSelectedPhoneCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
