package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.answerable.Answerable;

/**
 * Panel containing the list of answerables.
 */
public class AnswerableListPanel extends UiPart<Region> {
    private static final String FXML = "AnswerableListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AnswerableListPanel.class);

    @FXML
    private ListView<Answerable> answerableListView;

    public AnswerableListPanel(ObservableList<Answerable> answerableList) {
        super(FXML);
        answerableListView.setItems(answerableList);
        answerableListView.setCellFactory(listView -> new AnswerableListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Answerable} using a {@code AnswerableCard}.
     */
    class AnswerableListViewCell extends ListCell<Answerable> {
        @Override
        protected void updateItem(Answerable answerable, boolean empty) {
            super.updateItem(answerable, empty);

            if (empty || answerable == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AnswerableCard(answerable, getIndex() + 1).getRoot());
            }
        }
    }

}
