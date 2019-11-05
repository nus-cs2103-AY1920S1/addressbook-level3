package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.resource.Regime;

/**
 * Panel containing list of regimes.
 */
public class RegimeListPanel extends ResourceListPanel {
    private static final String FXML = "RegimeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RegimeListPanel.class);

    @FXML
    private ListView<Regime> regimeListView;

    public RegimeListPanel(ObservableList<Regime> regimeList) {
        super(FXML, regimeList);
        regimeListView.setItems(regimeList);
        regimeListView.setCellFactory(listView -> new RegimeListViewCell());
        regimeListView.getFocusModel().focusedItemProperty().addListener(getDefaultListViewListener());
    }

    @Override
    protected void selectGivenIndex(int index) {
        if (index >= 0) {
            /*
                An extremely hacky way to get the list to select, focus and scroll to the newly changed item.
                Without this method, when any add/edit commands are supplied, the ListChangeListener attached to
                ObservableList is called first without the list actually changing its structure. So when the index
                is provided, the listview is not updated and thus cannot be focused on.
                So the solution is to make this focusing operation be done at a slightly later time when the
                list view has been updated to reflect the commands changes
             */
            Platform.runLater(() -> selectFocusAndScrollTo(regimeListView, index));
        }
    }

    @Override
    protected void resetListSelection() {
        regimeListView.getSelectionModel().clearSelection();
    }

    @Override
    protected ListView<Regime> getResourceListView() {
        return regimeListView;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Regime} using a {@code RegimeCard}.
     */
    class RegimeListViewCell extends ListCell<Regime> {
        @Override
        protected void updateItem(Regime regime, boolean empty) {
            super.updateItem(regime, empty);

            if (empty || regime == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RegimeCard(regime, getIndex() + 1).getRoot());
            }
        }
    }
}
