package seedu.address.ui.quiz;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.quiz.person.Question;


/**
 * Panel containing the list of questions.
 */
public class QuestionDetailPanel extends UiPart<Region> {
    private static final String FXML = "QuestionDetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuestionDetailPanel.class);

    private int questionNumber;

    @FXML
    private ListView<Question> questionDetailView;

    public QuestionDetailPanel() {
        super(FXML);
    }

    public QuestionDetailPanel(ObservableList<Question> questionList, int questionNumber) {
        super(FXML);
        this.questionNumber = questionNumber;

        questionDetailView.setItems(questionList);
        questionDetailView.setCellFactory(listView -> new QuestionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Question} using a {@code QuestionDetailCard}.
     */
    class QuestionListViewCell extends ListCell<Question> {
        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuestionDetailCard(question, questionNumber).getRoot());
            }
        }
    }

}
