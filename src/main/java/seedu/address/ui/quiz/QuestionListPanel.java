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
public class QuestionListPanel extends UiPart<Region> {
    private static final String FXML = "QuestionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuestionListPanel.class);

    @FXML
    private ListView<Question> questionListView;

    public QuestionListPanel(ObservableList<Question> questionList) {
        super(FXML);
        questionListView.setItems(questionList);
        questionListView.setCellFactory(listView -> new QuestionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Question} using a {@code QuestionCard}.
     */
    class QuestionListViewCell extends ListCell<Question> {
        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuestionCard(question, getIndex() + 1).getRoot());
            }
        }
    }

}
