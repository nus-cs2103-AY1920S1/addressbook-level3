package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.question.Question;
import seedu.address.ui.util.DisplayType;

/**
 * Panel containing the list of questions.
 */
public class QuizQuestionListPanel extends UiPart<Region> {

    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuizQuestionListPanel.class);

    @FXML
    private ListView<Question> listView;

    private DisplayType displayType;

    public QuizQuestionListPanel(ObservableList<Question> questionList, DisplayType displayType) {
        super(FXML);
        this.displayType = displayType;
        listView.setItems(questionList);
        listView.setCellFactory(listView -> new QuestionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Question} using a {@code
     * QuestionCard}.
     */
    class QuestionListViewCell extends ListCell<Question> {

        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                Region region = new QuestionCard(question, getIndex() + 1).getRoot();

                switch(displayType) {
                case QUESTIONS:
                    region = new QuizQuestionCard(question, getIndex() + 1).getRoot();
                    break;
                case ANSWERS:
                    region = new QuizAnswerCard(question, getIndex() + 1).getRoot();
                    break;
                default:
                    break;
                }

                setGraphic(region);
            }
        }
    }
}

