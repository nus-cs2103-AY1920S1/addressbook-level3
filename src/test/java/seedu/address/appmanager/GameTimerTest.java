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

@ExtendWith(ApplicationExtension.class)
public class GameTimerTest {

    private GameTimer dummyTimer;
    private AppManagerStub appManagerStub;

    /**
     * Initializes the JavaFX Application Thread when this test starts.
     */
    @Start
    public void start(Stage stage) {
    }

    @Test
    public void run() {
        Platform.runLater(() -> {
            appManagerStub = new AppManagerStub();

            GameTimer.SkipOverCallBack dummySkipCallBack = appManagerStub::skipOver;
            GameTimer.UpdateTimerCallBack dummyTimerCallBack = appManagerStub::updateTimer;
            GameTimer.UpdateHintCallBack dummyHintCallBack = appManagerStub::updateHints;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    10, dummySkipCallBack, dummyTimerCallBack, dummyHintCallBack);
            dummyTimer.run();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                assertTrue(appManagerStub.skipCalled);
                assertTrue(appManagerStub.timerDisplayUpdated);
            });
        });

        // todo: create own implementation of clock that can support manual elapsing of time, to avoid using
    }

    @Test
    public void run_durationIsZero() {
        Platform.runLater(() -> {
            appManagerStub = new AppManagerStub();

            GameTimer.SkipOverCallBack dummySkipCallBack = appManagerStub::skipOver;
            GameTimer.UpdateTimerCallBack dummyTimerCallBack = appManagerStub::updateTimer;
            GameTimer.UpdateHintCallBack dummyHintCallBack = appManagerStub::updateHints;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    10, dummySkipCallBack, dummyTimerCallBack, dummyHintCallBack);
            dummyTimer.run();
            Platform.runLater(() -> {
                assertFalse(appManagerStub.skipCalled);
                assertFalse(appManagerStub.timerDisplayUpdated);
            });
        });
    }


    @Test
    public void abortTimer() {
        Platform.runLater(() -> {
            appManagerStub = new AppManagerStub();

            GameTimer.SkipOverCallBack dummySkipCallBack = appManagerStub::skipOver;
            GameTimer.UpdateTimerCallBack dummyTimerCallBack = appManagerStub::updateTimer;
            GameTimer.UpdateHintCallBack dummyHintCallBack = appManagerStub::updateHints;

            dummyTimer = GameTimer.getInstance("Dummy Message",
                    10, dummySkipCallBack, dummyTimerCallBack, dummyHintCallBack);
            dummyTimer.abortTimer();
            // abortTimer() is supposed to pass timeLeft = 0 to the timerDisplay.
            assertTrue(appManagerStub.timeLeftIsZero);
        });

    }



    // Stub Class for AppManager's methods that will be called from GameTimer
    private class AppManagerStub {

        private boolean skipCalled = false;
        private boolean hintsUpdated = false;
        private boolean timerDisplayUpdated = false;
        private boolean timeLeftIsZero = false;

        private void skipOver() {
            skipCalled = true;
        }

        private void updateHints() {
            hintsUpdated = true;
        }

        private void updateTimer(String timerMessage, long timeLeft, long totalTimeGiven) {
            timeLeftIsZero = timeLeft == 0 ? true : false;
            timerDisplayUpdated = true;
        }

    }

}
