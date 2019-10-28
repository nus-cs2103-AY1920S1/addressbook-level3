package seedu.address.appmanager.timer;

import seedu.address.appmanager.AppManager;

/**
 * API for GameTimer, specifies the required methods for a GameTimer to operate as intended with Dukemon
 */
public interface GameTimerInterface extends Runnable {

    void abortTimer();

    void run();

    long getElapsedMillis();

    void setHintTimingQueue(int hintFormatSize, long timeAllowedPerQuestion);

    static GameTimerInterface getInstance(String mainMessage, long totalDurationAllowed,
                                          AppManager.MainWindowExecuteCallBack mainWindowExecuteCallBack,
                                          AppManager.TimerDisplayCallBack timerDisplayCallBack,
                                          GameTimerImpl.RequestUpdateHintCallBack requestUpdateHintCallBack) {
        return new GameTimerImpl(mainMessage, totalDurationAllowed,
                mainWindowExecuteCallBack, timerDisplayCallBack, requestUpdateHintCallBack);

    }

}
