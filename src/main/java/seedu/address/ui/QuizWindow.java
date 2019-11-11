package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.QuizBank;

/**
 * Represents the window to display a group of students.
 */
public class QuizWindow extends UiPart<Stage> {
    private static final String FXML = "QuizWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(QuizWindow.class);

    @FXML
    private Label quizId;
    @FXML
    private ListView<Question> questionListView;
    @FXML
    private ListView<String> answerListView;
    @FXML
    private ListView<String> questionsAndAnswersListView;

    public QuizWindow(Stage root) {
        super(FXML, root);
        //root.setFullScreen(true);
        quizId.setText(QuizBank.getCurrentlyQueriedQuiz());
        // Set keyboard listener
        root.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                root.close();
            }
        });

    }


    /**
     * Creates a new QuizWindow.
     */
    public QuizWindow() {
        this(new Stage());
    }

    public void setQuestionsInQuiz(ObservableList<Question> questionList) {
        questionListView.setItems(questionList);
        questionListView.setCellFactory(listView -> new QuestionListViewCell());
    }

    public void setAnswersInQuiz(ObservableList<Question> questionList) {
        questionListView.setItems(questionList);
        questionListView.setCellFactory(listView -> new AnswerListViewCell());
    }

    public void setQuestionsAndAnswersInQuiz(ObservableList<Question> questionList) {
        questionListView.setItems(questionList);
        questionListView.setCellFactory(listView -> new QuestionsAndAnswersListViewCell());
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
                setGraphic(new QuizQuestionCard(question, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Answer} using a {@code QuestionCard}.
     */
    class AnswerListViewCell extends ListCell<Question> {
        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuizAnswerCard(question, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code QuestionsAndAnswers} using a {@code QuestionCard}.
     */
    class QuestionsAndAnswersListViewCell extends ListCell<Question> {
        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuizQuestionsAndAnswersCard(question, getIndex() + 1).getRoot());
            }
        }
    }


    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Show slideshow.");
        getRoot().show();
        getRoot().centerOnScreen();
        //getRoot().setFullScreen(true);
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the slideshow window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the slideshow window.
     */
    public void focus() {
        getRoot().requestFocus();
    }


}
