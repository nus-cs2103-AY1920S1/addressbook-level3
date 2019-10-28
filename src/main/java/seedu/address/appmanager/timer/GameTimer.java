package seedu.address.appmanager.timer;

import seedu.address.appmanager.AppManager;

/**
 * API for GameTimer, specifies the required methods for a GameTimer to operate as intended with AppManager
 */
public interface GameTimer extends Runnable {

    void abortTimer();

    void run();

    long getElapsedMillis();

    void setHintTimingQueue(int hintFormatSize, long timeAllowedPerQuestion);

    static GameTimer getInstance(String mainMessage, long totalDurationAllowed,
                                 AppManager.MainWindowExecuteCallBack mainWindowExecuteCallBack,
                                 AppManager.TimerDisplayCallBack timerDisplayCallBack,
                                 GameTimerManager.RequestUpdateHintCallBack requestUpdateHintCallBack) {
        return new GameTimerManager(mainMessage, totalDurationAllowed,
                mainWindowExecuteCallBack, timerDisplayCallBack, requestUpdateHintCallBack);

    }

}
