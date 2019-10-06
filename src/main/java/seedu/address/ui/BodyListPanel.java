package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.body.Body;

/**
 * Panel containing the list of bodies.
 */
public class BodyListPanel extends UiPart<Region> {
    private static final String FXML = "BodyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BodyListPanel.class);

    @FXML
    private ListView<Body> bodyListView;

    public BodyListPanel(ObservableList<Body> bodyList) {
        super(FXML);
        bodyListView.setItems(bodyList);
        bodyListView.setCellFactory(listView -> new BodyListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Body} using a {@code BodyCard}.
     */
    class BodyListViewCell extends ListCell<Body> {
        @Override
        protected void updateItem(Body body, boolean empty) {
            super.updateItem(body, empty);

            if (empty || body == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BodyCard(body, getIndex() + 1).getRoot());
            }
        }
    }

}
