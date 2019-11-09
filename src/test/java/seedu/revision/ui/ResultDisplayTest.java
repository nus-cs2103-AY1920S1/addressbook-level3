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
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void emptyDisplay_shouldDisplayEmptyText(FxRobot robot) {
        FxAssert.verifyThat(resultDisplay.getResultDisplay(), TextInputControlMatchers.hasText(""));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void setFeedbackToUser_helloWorld_shouldDisplayHelloWorld(FxRobot robot) {
        resultDisplay.setFeedbackToUser("hello world");
        FxAssert.verifyThat(resultDisplay.getResultDisplay(), TextInputControlMatchers.hasText("hello world"));
    }

}
