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
import seedu.address.ui.util.OptionType;

/**
 * Controller for a help page
 */
public class SlideshowWindow extends UiPart<Stage> {

    public static final String TIP = "Press 'Esc' to quit slideshow\n"
        + "Press 'Space' to show the answer\n"
        + "Use the 'Left/Right' arrow keys to navigate";

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
        initialiseKeyboardControls(root);
    }

    /**
     * Unhides the window.
     */
    public void show() {
        logger.fine("Show slideshow.");

        updateQuestions();
        getRoot().show();
        getRoot().setFullScreen(true);
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
        /*
         * Note: it is important to disable fullscreen before hiding else application
         * might hang.
         */
        getRoot().setFullScreen(false);
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
                showHideAnswer();
            } else if (keyEvent.getCode() == KeyCode.DIGIT1) { // Poll option A
                addToPoll(OptionType.OPTION_A);
            } else if (keyEvent.getCode() == KeyCode.DIGIT2) { // Poll option B
                addToPoll(OptionType.OPTION_B);
            } else if (keyEvent.getCode() == KeyCode.DIGIT3) { // Poll option C
                addToPoll(OptionType.OPTION_C);
            } else if (keyEvent.getCode() == KeyCode.DIGIT4) { // Poll option D
                addToPoll(OptionType.OPTION_D);
            }
        });
    }

    /**
     * Sets the next question to be displayed and closes the window if there's no more questions.
     */
    private void nextQuestion() {
        currQuestionIndex++;
        updateQuestionNoLabel();

        if (currQuestionIndex > questionPanels.size()) {
            hide();
        } else if (currQuestionIndex == questionPanels.size()) {
            questionPanels.get(currQuestionIndex - 1).setVisible(false);
            questionNoLabel.setText("End of slideshow");
        } else {
            questionPanels.get(currQuestionIndex - 1).setVisible(false);
            questionPanels.get(currQuestionIndex).setVisible(true);
        }
    }

    /**
     * Sets the previous question to be displayed.
     */
    private void prevQuestion() {
        if (currQuestionIndex > 0) {
            currQuestionIndex--;
            updateQuestionNoLabel();

            if (currQuestionIndex == questionPanels.size() - 1) {
                questionPanels.get(currQuestionIndex).setVisible(true);
            } else {
                questionPanels.get(currQuestionIndex + 1).setVisible(false);
                questionPanels.get(currQuestionIndex).setVisible(true);
            }
        }
    }

    /**
     * Shows or hide the current answer of the question.
     */
    private void showHideAnswer() {
        if (currQuestionIndex < questionPanels.size()) {
            QuestionPanel questionPanel = questionPanels.get(currQuestionIndex);
            questionPanel.showHideAnswer();
        }
    }

    /**
     * Adds the corresponding option pressed to the poll results.
     */
    private void addToPoll(OptionType option) {
        if (currQuestionIndex < questionPanels.size()) {
            QuestionPanel questionPanel = questionPanels.get(currQuestionIndex);
            questionPanel.addToPoll(option);
        }
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
