package seedu.address.ui.entitylistpanel;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.Participant;
import seedu.address.ui.EntityCard;

/**
 * Panel containing the list of persons.
 */
public class ParticipantListPanel extends EntityListPanel {
    private static final String FXML = "ParticipantListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ParticipantListPanel.class);

    @FXML
    private ListView<Participant> participantListView;

    //TODO: will be changed to take in paramter
    public ParticipantListPanel(ObservableList<Participant> participantList) {
        super(FXML);
        participantListView.setItems(participantList);
        participantListView.setCellFactory(listView -> new ParticipantListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ParticipantListViewCell extends ListCell<Participant> {
        @Override
        protected void updateItem(Participant participant, boolean isEmpty) {
            super.updateItem(participant, isEmpty);

            if (isEmpty || participant == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EntityCard(participant, getIndex() + 1).getRoot());
            }
        }
    }
}

