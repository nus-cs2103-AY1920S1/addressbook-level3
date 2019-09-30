package seedu.tarence.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Panel containing the list of classes taught by the user.
 */
public class TutorialListPanel extends UiPart<Region> {
    private static final String FXML = "TutorialListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TutorialListPanel.class);

    @FXML
    private ListView<Tutorial> tutorialListView;

    public TutorialListPanel(ObservableList<Tutorial> tutorialList) {
        super(FXML);
        tutorialListView.setItems(tutorialList);
        tutorialListView.setCellFactory(listView -> new TutorialListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tutorial} using a {@code TutorialCard}.
     */
    class TutorialListViewCell extends ListCell<Tutorial> {
        @Override
        protected void updateItem(Tutorial tutorial, boolean empty) {
            super.updateItem(tutorial, empty);

            if (empty || tutorial == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TutorialCard(tutorial, getIndex() + 1).getRoot());
            }
        }
    }
}
