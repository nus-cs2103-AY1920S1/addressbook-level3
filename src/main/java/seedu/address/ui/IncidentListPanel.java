package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.incident.Incident;

/**
 * Panel containing the list of incidents.
 */
public class IncidentListPanel extends UiPart<Region> {
    private static final String FXML = "IncidentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IncidentListPanel.class);

    @FXML
    private ListView<Incident> incidentListView;

    public IncidentListPanel(ObservableList<Incident> incidentList) {
        super(FXML);
        incidentListView.setItems(incidentList);
        incidentListView.setCellFactory(listView -> new IncidentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Incident} using a {@code IncidentCard}.
     */
    class IncidentListViewCell extends ListCell<Incident> {
        @Override
        protected void updateItem(Incident incident, boolean empty) {
            super.updateItem(incident, empty);

            if (empty || incident == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new IncidentCard(incident, getIndex() + 1).getRoot());
            }
        }
    }

}
