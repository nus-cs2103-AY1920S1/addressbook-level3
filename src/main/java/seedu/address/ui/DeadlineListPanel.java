package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.deadline.Deadline;

//@@author dalsontws
/**
 * Panel containing the list of flashcards.
 */
public class DeadlineListPanel extends UiPart<Region> {
    private static final String FXML = "DeadlineListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeadlineListPanel.class);

    @FXML
    private ListView<Deadline> deadlineListView;

    public DeadlineListPanel(ObservableList<Deadline> deadlineList) {
        super(FXML);
        deadlineListView.setItems(deadlineList);
        deadlineListView.setCellFactory(listView -> new DeadlineListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code FlashCard} using a {@code FlashCardPanel}.
     */
    class DeadlineListViewCell extends ListCell<Deadline> {
        @Override
        protected void updateItem(Deadline deadline, boolean empty) {
            super.updateItem(deadline, empty);

            if (empty || deadline == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeadlinePanel(deadline, getIndex() + 1).getRoot());
            }
        }
    }

}
