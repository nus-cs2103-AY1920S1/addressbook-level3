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
public class AnswerableListPanelWithAnswers extends UiPart<Region> {
    private static final String FXML = "AnswerableListPanelWithAnswers.fxml";
    private final Logger logger = LogsCenter.getLogger(AnswerableListPanel.class);

    @FXML
    private ListView<Answerable> answerableListViewWithAnswers;

    public AnswerableListPanelWithAnswers(ObservableList<Answerable> answerableList) {
        super(FXML);
        answerableListViewWithAnswers.setItems(answerableList);
        answerableListViewWithAnswers.setCellFactory(listView -> new AnswerableListViewCellWithAnswers());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Answerable}.
     * Using a {@code AnswerableCardWithAnswers}.
     */
    class AnswerableListViewCellWithAnswers extends ListCell<Answerable> {
        @Override
        protected void updateItem(Answerable answerable, boolean empty) {
            super.updateItem(answerable, empty);

            if (empty || answerable == null) {
                setGraphic(null);
                setText(null);
            } else {
                //To set the display when StartQuizCommand is run.
                setGraphic(new AnswerableCardWithAnswers(answerable, getIndex() + 1).getRoot());
            }
        }
    }

}
