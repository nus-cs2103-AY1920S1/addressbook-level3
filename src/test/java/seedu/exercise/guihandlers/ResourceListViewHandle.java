package seedu.exercise.guihandlers;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import seedu.exercise.model.resource.Resource;

/**
 * Handle for exercise list views
 */
public class ResourceListViewHandle<T extends Resource> extends NodeHandle<ListView<T>> {

    public ResourceListViewHandle(ListView<T> rootNode) {
        super(rootNode);
    }

    /**
     * Selects the item at {@code index}
     */
    public void select(int index) {
        if (index >= getListSize()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        getRootNode().getSelectionModel().select(index);
    }

    public int getListSize() {
        return getRootNode().getItems().size();
    }

    public SelectionModel<T> getSelectionModel() {
        return getRootNode().getSelectionModel();
    }

    public ListView<T> getListView() {
        return getRootNode();
    }

    /**
     * Adds a resource to the underlying list view
     */
    public void addResource(T r) {
        List<T> current = getRootNode().getItems();
        List<T> added = new ArrayList<>(current);
        added.add(r);
        getRootNode().setItems(FXCollections.observableList(added));
    }
}
