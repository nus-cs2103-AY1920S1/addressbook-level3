package seedu.address.gamemanager;

import javafx.stage.Stage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import seedu.address.logic.commands.CommandResult;


@ExtendWith(ApplicationExtension.class)
public class GameTimerTest {

    private GameTimer dummyTimer;
    MainWindowStub mainWindowStub;
    TimerDisplayStub timerDisplayStub;

    @Start
    public void start(Stage stage) {
         mainWindowStub = new MainWindowStub();
         timerDisplayStub = new TimerDisplayStub();
    }

    @Test
    public void run() {
        GameManager.MainWindowExecuteCallBack dummyMainCallBack = mainWindowStub::execute;
        GameManager.TimerDisplayCallBack dummyTimerCallBack = timerDisplayStub::updateTimerDisplay;
        dummyTimer = new GameTimer("Dummy Message",
                10, dummyMainCallBack, dummyTimerCallBack);
        dummyTimer.run();
        // todo: create own implementation of clock that can support manual elapsing of time, to avoid using
        //  Thread.sleep().

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assertions.assertThat(mainWindowStub.isExecutedFromGameTimer).isEqualTo(true);
        Assertions.assertThat(timerDisplayStub.isUpdatedFromGameTimer).isEqualTo(true);
    }

    @Test
    public void abortTimer() {
        GameManager.MainWindowExecuteCallBack dummyMainCallBack = mainWindowStub::execute;
        GameManager.TimerDisplayCallBack dummyTimerCallBack = timerDisplayStub::updateTimerDisplay;
        dummyTimer = new GameTimer("Dummy Message",
                1000, dummyMainCallBack, dummyTimerCallBack);
        dummyTimer.abortTimer();
        // abortTimer() is supposed to pass timeLeft = 0 to the timerDisplay.
        Assertions.assertThat(timerDisplayStub.timeLeftEqualsZero).isEqualTo(true);
    }


    // Stub class for TimerDisplay component of UI
    class TimerDisplayStub {
        boolean isUpdatedFromGameTimer = false;
        boolean timeLeftEqualsZero = false;

        void updateTimerDisplay(String timerMessage, long timeLeft, long totalTimeGiven) {
            this.isUpdatedFromGameTimer = true;
            this.timeLeftEqualsZero = timeLeft == 0 ? true : false;
        }

    }

    // Stub class for MainWindow component of UI
    class MainWindowStub {
        boolean isExecutedFromGameTimer = false;

        CommandResult execute(String commandText) {
            isExecutedFromGameTimer = true;
            return null;
        }
    }

}
