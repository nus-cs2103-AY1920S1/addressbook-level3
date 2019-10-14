package seedu.address.ui.entitylistpanel;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.Team;
import seedu.address.ui.EntityCard;

/**
 * Panel containing the list of persons.
 */
public class TeamListPanel extends EntityListPanel {
    private static final String FXML = "TeamListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ParticipantListPanel.class);

    @FXML
    private ListView<Team> teamListView;

    public TeamListPanel(ObservableList<Team> teamList) {
        super(FXML);
        teamListView.setItems(teamList);
        teamListView.setCellFactory(listView -> new TeamListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class TeamListViewCell extends ListCell<Team> {
        @Override
        protected void updateItem(Team team, boolean isEmpty) {
            super.updateItem(team, isEmpty);

            if (isEmpty || team == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EntityCard(team, getIndex() + 1).getRoot());
            }
        }
    }
}

