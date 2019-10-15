package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.question.Question;

/**
 * Panel containing the list of quiz questions.
 */
public class QuizQuestionListPanel extends UiPart<Region> {
    private static final String FXML = "QuizQuestionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuizQuestionListPanel.class);

    @FXML
    private ListView<Question> quizQuestionListView;

    public QuizQuestionListPanel(ObservableList<Question> quizQuestionList) {
        super(FXML);
        quizQuestionListView.setItems(quizQuestionList);
        quizQuestionListView.setCellFactory(listView -> new quizQuestionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Question} using a {@code QuizQuestionListCard}.
     */
    class quizQuestionListViewCell extends ListCell<Question> {
        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuizQuestionListCard(question, getIndex() + 1).getRoot());
            }
        }
    }
}
