package seedu.revision.ui.bar;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.revision.logic.commands.main.CommandResultBuilder;

@ExtendWith(ApplicationExtension.class)
class TimerTest {

    private Timer timer;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        timer = new Timer(3, (unused) -> new CommandResultBuilder().build());
        stage.setScene(new Scene(new StackPane(timer.getTimerLabel()), 300, 100)); // arbitrary height
        stage.show();
    }

    /**
     * Timer should count down to zero and not below zero.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void timerStartAtThree_waitFourSeconds_shouldCountToZero(FxRobot robot) {
        timer.startTimer();
        //Delay has to be a bit longer for the robot
        robot.sleep(4, TimeUnit.SECONDS);
        FxAssert.verifyThat(timer.getTimerLabel(), LabeledMatchers.hasText("0"));
    }

    /**
     * Timer should stop when the Timer#stopTimer is called.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void timerStopped_waitTwoSeconds_shouldNotStartCountingDown(FxRobot robot) {
        timer.startTimer();
        timer.stopTimer();
        robot.sleep(2, TimeUnit.SECONDS);
        //Delay has to be a bit longer for the robot
        FxAssert.verifyThat(timer.getTimerLabel(), LabeledMatchers.hasText(""));
    }


    /**
     * Timer should display the starting time when reset.
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void timerResetAfterTwoSeconds_waitFourSeconds_shouldBeResetToThree(FxRobot robot) {
        timer.startTimer();
        robot.sleep(2, TimeUnit.SECONDS);
        timer.resetTimer();
        //Delay has to be a bit longer for the robot
        FxAssert.verifyThat(timer.getTimerLabel(), LabeledMatchers.hasText("3"));
    }
}
