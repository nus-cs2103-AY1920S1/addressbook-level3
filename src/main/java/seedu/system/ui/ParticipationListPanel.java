package seedu.system.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.system.commons.core.LogsCenter;
import seedu.system.model.participation.Participation;

/**
 * Panel containing the list of Participations.
 */
public class ParticipationListPanel extends UiPart<Region> {
    private static final String FXML = "ParticipationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Participation> participationListView;

    public ParticipationListPanel(ObservableList<Participation> participationList) {
        super(FXML);
        participationListView.setItems(participationList);
        participationListView.setCellFactory(listView -> new ParticipationListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a
     * {@code Participation} using a {@code ParticipationCard}.
     */
    class ParticipationListViewCell extends ListCell<Participation> {
        @Override
        protected void updateItem(Participation participation, boolean empty) {
            super.updateItem(participation, empty);

            if (empty || participation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ParticipationCard(participation, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Hides the panel.
     */
    public void hide() {
        getRoot().setVisible(false);
    }

    /**
     * Shows the panel.
     */
    public void show() {
        getRoot().setVisible(true);
    }
}
