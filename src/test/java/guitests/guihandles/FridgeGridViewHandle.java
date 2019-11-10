package guitests.guihandles;

import java.util.Set;

import org.controlsfx.control.GridView;

import javafx.scene.Node;
import seedu.address.model.entity.fridge.Fridge;

//@@author shaoyi1997
/**
 * Provides a handle for {@code FridgeGridView} containing the list of {@code FridgeCard}.
 */
public class FridgeGridViewHandle extends NodeHandle<GridView<Fridge>> {
    public static final String FRIDGE_GRID_VIEW_ID = "#fridgeGridView";

    private static final String CARD_PANE_ID = "#fridgeCardPane";

    public FridgeGridViewHandle(GridView<Fridge> fridgeGridViewNode) {
        super(fridgeGridViewNode);
    }

    /**
     * Checks if {@code fridge} exists in the {@code fridgeGridView}.
     */
    public void isValidFridgeInFridgeGridView(Fridge fridge) {
        if (!getRootNode().getItems().contains(fridge)) {
            throw new IllegalArgumentException("Fridge does not exist.");
        }
    }

    /**
     * Returns the fridge card handle of a fridge associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public FridgeCardHandle getFridgeCardHandle(int index) {
        return getAllCardNodes().stream()
            .map(FridgeCardHandle::new)
            .filter(handle -> handle.equals(getFridge(index)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    private Fridge getFridge(int index) {
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
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
//@@author
