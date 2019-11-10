package guitests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextMatchers;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.revision.ui.bar.ProgressIndicatorBar;

@ExtendWith(ApplicationExtension.class)
class ProgressIndicatorBarTextTest {

    private ProgressIndicatorBar progressIndicatorBar;

    private ReadOnlyDoubleWrapper currentProgressIndex = new ReadOnlyDoubleWrapper(
            this, "currentProgressIndex", 0);
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        progressIndicatorBar = new ProgressIndicatorBar(currentProgressIndex, 2, "%.0f/2");
        stage.setScene(new Scene(new StackPane(progressIndicatorBar.getText()), 300, 100)); // arbitrary height
        stage.show();
    }

    /**
     * Default progress bar should show no progress.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void progressBar_noChangeInProgress_shouldShowZeroProgress(FxRobot robot) {
        FxAssert.verifyThat(progressIndicatorBar.getText(), TextMatchers.hasText("0/2"));
    }

    /**
     * Progress updated, should show change in progress.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void progressBar_progressChanged_inputShouldChange(FxRobot robot) {
        currentProgressIndex.set(1);
        FxAssert.verifyThat(progressIndicatorBar.getText(), TextMatchers.hasText("1/2"));
    }
}
