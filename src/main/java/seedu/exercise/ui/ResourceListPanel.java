package seedu.exercise.ui;

import static java.util.Objects.requireNonNull;

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

    void selectFocusAndScrollTo(ListView<? extends Resource> resources, int index) {
        resources.getSelectionModel().select(index);
        resources.getFocusModel().focus(index);
        resources.scrollTo(index);
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

    protected abstract void selectGivenIndex(int index);

    /**
     * Listener for item selection events in this {@code ResourceListPanel}.
     */
    public interface OnItemSelectListener {
        void onItemSelect(Resource selected);
    }

}
