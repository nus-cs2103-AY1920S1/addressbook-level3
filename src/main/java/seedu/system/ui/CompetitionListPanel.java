package seedu.system.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.system.model.competition.Competition;

/**
 * Panel containing the list of competitions.
 */
public class CompetitionListPanel extends UiPart<Region> {
    private static final String FXML = "CompetitionListPanel.fxml";

    @FXML
    private ListView<Competition> competitionListView;

    public CompetitionListPanel(ObservableList<Competition> competitionList) {
        super(FXML);
        competitionListView.setItems(competitionList);
        competitionListView.setCellFactory(listView -> new CompetitionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Competition} using a {@code CompetitionCard}.
     */
    class CompetitionListViewCell extends ListCell<Competition> {
        @Override
        protected void updateItem(Competition competition, boolean empty) {
            super.updateItem(competition, empty);

            if (empty || competition == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CompetitionCard(competition, getIndex() + 1).getRoot());
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
