package seedu.revision.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class ResultDisplayTest {

    private static final String MESSAGE_SUCCESS = "success!";
    private ResultDisplay resultDisplay;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        resultDisplay = new ResultDisplay();
        resultDisplay.getResultDisplay().setId("myLabel");
        stage.setScene(new Scene(new StackPane(resultDisplay.getResultDisplay()))); // arbitrary height
        stage.show();
    }

    /**
     * Result display should be empty by default.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void resultDisplay_defaultDisplay_shouldDisplayEmptyText(FxRobot robot) {
        FxAssert.verifyThat(resultDisplay.getResultDisplay(), TextInputControlMatchers.hasText(""));
    }

    /**
     * Set Feedback to user should show success message.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void setFeedbackToUser_successMessage_shouldDisplayMessage(FxRobot robot) {
        resultDisplay.setFeedbackToUser(MESSAGE_SUCCESS);
        FxAssert.verifyThat(resultDisplay.getResultDisplay(), TextInputControlMatchers.hasText(MESSAGE_SUCCESS));
    }

}
