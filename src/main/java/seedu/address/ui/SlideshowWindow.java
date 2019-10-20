package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.question.Question;

/**
 * Controller for a help page
 */
public class SlideshowWindow extends UiPart<Stage> {

    public static final String TIP = "Press 'Esc' to quit slideshow\nPress 'Space' to show the answer";

    private static final Logger logger = LogsCenter.getLogger(SlideshowWindow.class);
    private static final String FXML = "SlideshowWindow.fxml";
    private final ObservableList<QuestionPanel> questionPanels = FXCollections
        .observableArrayList();
    private final Logic logic;

    private int currQuestionIndex = 0;

    @FXML
    private Pane questionPane;
    @FXML
    private Label tipLabel;
    @FXML
    private Label questionNoLabel;

    /**
     * Creates a new SlideshowWindow.
     *
     * @param root  Stage to use as the root of the SlideshowWindow.
     * @param logic to be used for interacting with models.
     */
    public SlideshowWindow(Stage root, Logic logic) {
        super(FXML, root);
        this.logic = logic;

        tipLabel.setText(TIP);
        root.setFullScreen(true);
        initialiseKeyboardControls(root);
    }

    /**
     * Unhides the window.
     */
    public void show() {
        logger.fine("Show slideshow.");

        updateQuestions();
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the slideshow window is currently being shown.
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

    /**
     * Sets the keyboard listener and their corresponding actions.
     */
    private void initialiseKeyboardControls(Stage root) {
        root.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) { // End slideshow
                hide();
            } else if (keyEvent.getCode() == KeyCode.RIGHT) { // Next question
                nextQuestion();
            } else if (keyEvent.getCode() == KeyCode.LEFT) { // Previous question
                prevQuestion();
            } else if (keyEvent.getCode() == KeyCode.SPACE) { // Show answer
                showAnswer();
            }
        });
    }

    /**
     * Sets the next question to be displayed and closes the window if there's no more questions.
     */
    private void nextQuestion() {
        currQuestionIndex++;
        updateQuestionNoLabel();

        if (currQuestionIndex >= questionPanels.size()) {
            hide();
        } else {
            questionPanels.get(currQuestionIndex - 1).setVisible(false);
            questionPanels.get(currQuestionIndex).setVisible(true);
        }
    }

    /**
     * Sets the previous question to be displayed and closes the window if there's no more
     * questions.
     */
    private void prevQuestion() {
        currQuestionIndex--;
        updateQuestionNoLabel();

        if (currQuestionIndex < 0) {
            hide();
        } else {
            questionPanels.get(currQuestionIndex + 1).setVisible(false);
            questionPanels.get(currQuestionIndex).setVisible(true);
        }
    }

    /**
     * Shows the current answer of the question.
     */
    private void showAnswer() {
        QuestionPanel questionPanel = questionPanels.get(currQuestionIndex);
        questionPanel.showAnswer();
    }

    /**
     * Updates the question no. label to follow the current question index.
     */
    private void updateQuestionNoLabel() {
        questionNoLabel.setText("Question " + (currQuestionIndex + 1));
    }

    /**
     * Updates the questions display.
     */
    private void updateQuestions() {

        // Clear existing data
        currQuestionIndex = 0;
        questionPanels.clear();
        questionPane.getChildren().clear();

        // Adding question panels from question list to the pane
        ObservableList<Question> questions = logic.getSlideshowQuestions();
        for (int i = 0; i < questions.size(); i++) {
            QuestionPanel panel = new QuestionPanel(questions.get(i));
            Region region = panel.getRoot();
            region.setVisible(false);
            questionPanels.add(panel);
            questionPane.getChildren().add(region);
        }

        // Set visible for first question
        // when there is at least one question in the pane
        if (questionPanels.size() > 0) {
            updateQuestionNoLabel();
            questionPanels.get(0).setVisible(true);
        }
    }
}
