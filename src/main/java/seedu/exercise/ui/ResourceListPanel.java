package seedu.exercise.ui;

import static java.util.Objects.requireNonNull;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.exercise.model.resource.Resource;

/**
 * Panel containing the list of resources.
 */
public abstract class ResourceListPanel extends UiPart<Region> {

    private OnItemSelectListener itemSelectListener;
    private ObservableList<? extends Resource> resourceList;

    public ResourceListPanel(String fxml, ObservableList<? extends Resource> resourceList) {
        super(fxml);
        this.resourceList = resourceList;

        setResourceListListener();
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        requireNonNull(listener);
        itemSelectListener = listener;
    }

    void notifyOnSelectListener(Resource r) {
        if (itemSelectListener != null) {
            itemSelectListener.onItemSelect(r);
        }
    }

    /**
     * Selects, focus and scrolls to the {@code index} in {@code resources} list on left panel.
     * <p>
     *     Method will additionally inform the {@link OnItemSelectListener} of this selection event
     * </p>
     */
    void selectFocusAndScrollTo(ListView<? extends Resource> resources, int index) {
        resources.getSelectionModel().select(index);
        resources.getFocusModel().focus(index);
        resources.scrollTo(index);
        notifyOnSelectListener(resources.getSelectionModel().getSelectedItem());
    }

    ChangeListener<Resource> getDefaultListViewListener() {
        return new ChangeListener<Resource>() {
            @Override
            public void changed(ObservableValue<? extends Resource> observableValue, Resource resource, Resource t1) {
                //This is to handle mouse click events.
                if (observableValue != null) {
                    notifyOnSelectListener(observableValue.getValue());
                }
            }
        };
    }

    private void setResourceListListener() {
        resourceList.addListener(new ListChangeListener<Resource>() {
            /**
             * Called after a change is made to the {@code resourceList}.
             *
             * It is important to note that the {@code change} reported
             * may consist of one or more actual changes that can be iterated by
             * the {@code next()} method. For our purposes, we are usually only
             * interested in the first change event that is notified by the
             * {@code ObservableList}.
             *
             * The basic CRUD that is implemented will rarely ever have
             * multiple changes we are interested in. For reference, below listed are some
             * basic expectations of CRUD in ExerHealth.
             * <p>
             *     C - create --> {@code change.wasAdded()}
             * </p>
             * <p>
             *     R - read -->   {no methods are called}
             * </p>
             * <p>
             *     U - update --> {@code change.wasUpdated()}
             * </p>
             * <p>
             *     D - delete --> {@code change.wasRemoved()}
             * </p>
             *
             * Also, according to javadocs, there is a order to follow for calling
             * changes of different types. This method adheres to the order provided
             * in the javadocs for consistency sake.
             * <p>
             * See
             * <a href="https://openjfx.io/javadoc/11/javafx.base/javafx/collections/ListChangeListener.Change.html">
             * ListChangeListener.Change</a>
             * </p>
             */
            @Override
            public void onChanged(Change<? extends Resource> change) {
                int index = -1;
                while (change.next()) {
                    if (change.wasReplaced()) {
                        index = change.getFrom();
                        break;
                    } else if (change.wasAdded()) {
                        index = change.getFrom();
                        break;
                    } else if (change.wasRemoved()) {
                        index = change.getFrom();
                        break;
                    } else if (change.wasUpdated()) {
                        index = change.getFrom();
                        break;
                    }
                }
                if (index >= 0) {
                    selectGivenIndex(index);
                }
            }
        });
    }

    /**
     * Selects the {@code index} in the resource list view on the left panel.
     * <p>
     *     This method will cause the selected {@code index} to propagate to {@link OnItemSelectListener}
     *     so that any class listening out for selection changes can be informed of the selection.
     *     Classes interested in listening out for selection events should implement the
     *     {@link OnItemSelectListener#onItemSelect(Resource)} to be informed of the resource
     *     that has been selected.
     * </p>
     */
    public void selectGivenIndex(int index) {
        ListView<? extends Resource> resourceListView = getResourceListView();
        if (index >= 0) {
            /*
                An extremely hacky way to get the list to select, focus and scroll to the newly changed item.
                Without this method, when any add/edit commands are supplied, the ListChangeListener attached to
                ObservableList is called first without the list actually changing its structure. So when the index
                is provided, the listview is not updated and thus cannot be focused on.
                So the solution is to make this focusing operation be done at a slightly later time when the
                list view has been updated to reflect the commands changes
             */
            Platform.runLater(() -> selectFocusAndScrollTo(resourceListView, index));
        }
    }

    public abstract void resetListSelection();

    public abstract ListView<? extends Resource> getResourceListView();

    /**
     * Listener for item selection events in this {@code ResourceListPanel}.
     */
    public interface OnItemSelectListener {
        void onItemSelect(Resource selected);
    }

}
