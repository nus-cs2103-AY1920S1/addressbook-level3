package seedu.address.appmanager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.application.Platform;
import javafx.stage.Stage;

import seedu.address.appmanager.timer.GameTimer;
import seedu.address.appmanager.timer.GameTimerManager;
import seedu.address.logic.commands.CommandResult;

@ExtendWith(ApplicationExtension.class)
public class GameTimerTest {

    private GameTimer dummyTimer;
    private MainWindowStub mainWindowStub;
    private TimerDisplayStub timerDisplayStub;
    private HintDisplayStub hintDisplayStub;

    /**
     * Initializes the JavaFX Application Thread when this test starts.
     */
    @Start
    public void start(Stage stage) {
    }

    @Test
    public void run() {
        Platform.runLater(() -> {
            mainWindowStub = new MainWindowStub();
            timerDisplayStub = new TimerDisplayStub();
            hintDisplayStub = new HintDisplayStub();

            AppManager.MainWindowExecuteCallBack dummyMainCallBack = mainWindowStub::execute;
            AppManager.TimerDisplayCallBack dummyTimerCallBack = timerDisplayStub::updateTimerDisplay;
            GameTimerManager.RequestUpdateHintCallBack dummyHintCallBack = hintDisplayStub::requestHint;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    10, dummyMainCallBack, dummyTimerCallBack, dummyHintCallBack);
            dummyTimer.run();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                assertTrue(mainWindowStub.isExecutedFromGameTimer);
                assertTrue(timerDisplayStub.isUpdatedFromGameTimer);
            });
        });

        // todo: create own implementation of clock that can support manual elapsing of time, to avoid using
    }

    @Test
    public void run_durationIsZero() {
        Platform.runLater(() -> {
            mainWindowStub = new MainWindowStub();
            timerDisplayStub = new TimerDisplayStub();
            hintDisplayStub = new HintDisplayStub();

            AppManager.MainWindowExecuteCallBack dummyMainCallBack = mainWindowStub::execute;
            AppManager.TimerDisplayCallBack dummyTimerCallBack = timerDisplayStub::updateTimerDisplay;
            GameTimerManager.RequestUpdateHintCallBack dummyHintCallBack = hintDisplayStub::requestHint;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    10, dummyMainCallBack, dummyTimerCallBack, dummyHintCallBack);
            dummyTimer.run();
            Platform.runLater(() -> {
                assertFalse(mainWindowStub.isExecutedFromGameTimer);
                assertFalse(timerDisplayStub.isUpdatedFromGameTimer);
            });
        });
    }


    @Test
    public void abortTimer() {
        Platform.runLater(() -> {
            mainWindowStub = new MainWindowStub();
            timerDisplayStub = new TimerDisplayStub();
            hintDisplayStub = new HintDisplayStub();

            AppManager.MainWindowExecuteCallBack dummyMainCallBack = mainWindowStub::execute;
            AppManager.TimerDisplayCallBack dummyTimerCallBack = timerDisplayStub::updateTimerDisplay;
            GameTimerManager.RequestUpdateHintCallBack dummyHintCallBack = hintDisplayStub::requestHint;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    1000, dummyMainCallBack, dummyTimerCallBack, dummyHintCallBack);
            dummyTimer.abortTimer();
            // abortTimer() is supposed to pass timeLeft = 0 to the timerDisplay.
            assertTrue(timerDisplayStub.timeLeftEqualsZero);
        });

    }



    // Stub class for TimerDisplay component of UI
    class TimerDisplayStub {
        private boolean isUpdatedFromGameTimer = false;
        private boolean timeLeftEqualsZero = false;

        void updateTimerDisplay(String timerMessage, long timeLeft, long totalTimeGiven) {
            this.isUpdatedFromGameTimer = true;
            this.timeLeftEqualsZero = timeLeft == 0 ? true : false;
        }
    }

    // Stub class for MainWindow component of UI
    class MainWindowStub {
        private boolean isExecutedFromGameTimer = false;

        CommandResult execute(String commandText) {
            isExecutedFromGameTimer = true;
            return null;
        }
    }

    class HintDisplayStub {
        private boolean isExecutedFromGameTimer = false;

        void requestHint() {
            isExecutedFromGameTimer = true;
        }
    }

}
