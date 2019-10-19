package seedu.ichifund.ui;

import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.model.repeater.Repeater;

/**
 * Panel containing the list of repeaters.
 */
public class RepeaterListPanel extends UiPart<Region> {
    private static final String FXML = "RepeaterListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RepeaterListPanel.class);

    @FXML
    private ListView<Repeater> repeaterListView;

    public RepeaterListPanel(ObservableList<Repeater> repeaterList) {
        super(FXML);
        repeaterListView.setItems(repeaterList);
        repeaterListView.setCellFactory(listView -> new RepeaterListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Repeater} using a {@code RepeaterCard}.
     */
    class RepeaterListViewCell extends ListCell<Repeater> {
        @Override
        protected void updateItem(Repeater repeater, boolean empty) {
            super.updateItem(repeater, empty);

            if (empty || repeater == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RepeaterCard(repeater, getIndex() + 1).getRoot());
            }
        }
    }

}
