//@@author CarbonGrid
package seedu.address.ui;

import java.util.HashSet;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.util.Callback;

/**
 * Generic OmniPanel.
 */
public abstract class OmniPanel<T> extends UiPart<Region> {

    private static final String FXML = "OmniPanelListView.fxml";
    private int lastSelectedIndex = 0;
    private ObservableList<T> ols;

    @FXML
    private ListView<T> omniPanelListView;

    protected OmniPanel(HashSet<Runnable> deferredUntilMouseClickOuter) {
        super(FXML);

        omniPanelListView.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            int size = ols.size();
            MultipleSelectionModel<T> msm = omniPanelListView.getSelectionModel();
            switch (keyEvent.getCode()) {
            case DOWN:
                if (msm.getSelectedIndex() < size - 1) {
                    return;
                }
                msm.select(0);
                omniPanelListView.scrollTo(0);
                keyEvent.consume();
                break;
            case UP:
                if (msm.getSelectedIndex() > 0) {
                    return;
                }
                msm.select(size - 1);
                omniPanelListView.scrollTo(size - 1);
                keyEvent.consume();
                break;
            case TAB:
            case LEFT:
                dropSelector();
                break;
            default:
            }
        });
        Runnable dropSelectorDeferred = this::dropSelector;
        omniPanelListView.setOnMouseExited(e -> deferredUntilMouseClickOuter.add(dropSelectorDeferred));
        omniPanelListView.setOnMouseEntered(e -> deferredUntilMouseClickOuter.remove(dropSelectorDeferred));
        omniPanelListView.setOnMouseClicked(e -> omniPanelListView.requestFocus());
    }

    protected void setItems(ObservableList<T> ols) {
        omniPanelListView.setItems(ols);
        this.ols = ols;
    }

    protected void setCellFactory(Callback<ListView<T>, ListCell<T>> cellFactory) {
        omniPanelListView.setCellFactory(cellFactory);
    }

    /**
     * Saves the current selected index.
     * Then unselect the cell.
     */
    private void dropSelector() {
        lastSelectedIndex = omniPanelListView.getSelectionModel().getSelectedIndex();
        omniPanelListView.getSelectionModel().select(-1);
    }

    /**
     * Restores the selection on the listview with the lastSelectedIndex.
     */
    public void regainSelector() {
        if (lastSelectedIndex >= ols.size()) {
            lastSelectedIndex = 0;
        }
        omniPanelListView.getSelectionModel().select(lastSelectedIndex);
    }
}
