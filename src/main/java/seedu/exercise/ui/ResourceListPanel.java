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
            @Override
            public void onChanged(Change<? extends Resource> change) {
                while (change.next()) {
                    int index = -1;
                    index = change.getFrom();
                    if (index >= 0) {
                        selectGivenIndex(index);
                    }
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
