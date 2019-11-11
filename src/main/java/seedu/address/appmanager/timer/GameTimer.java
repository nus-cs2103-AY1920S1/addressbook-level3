package seedu.address.appmanager.timer;

/**
 * API for GameTimer, specifies the required methods for a GameTimer to operate as intended with AppManager
 *
 * @@author kohyida1997
 */
public interface GameTimer extends Runnable {

    void abortTimer();

    void run();

    long getElapsedMillis();

    void initHintTimingQueue(int hintFormatSize, long timeAllowedPerQuestion);

    /** Initializes and returns a new GameTimer instance. (Factory method)*/
    static GameTimer getInstance(String mainMessage, long totalDurationAllowed,
                                 SkipOverCallBack skipOverCallBack,
                                 UpdateTimerCallBack updateTimerCallBack,
                                 UpdateHintCallBack updateHintCallBack) {
        return new GameTimerManager(mainMessage, totalDurationAllowed,
                skipOverCallBack, updateTimerCallBack, updateHintCallBack);

    }

    /**
     * Call-back method from GameTimer to AppManager to Update Hints
     */
    @FunctionalInterface
    interface UpdateHintCallBack {
        void requestHint();
    }

    /**
     * Call-back method from GameTimer to AppManager to Skip over to next question
     */
    @FunctionalInterface
    interface SkipOverCallBack {
        void skipOverToNextQuestion();
    }

    /**
     * Call-back method from GameTimer to AppManager to update the Timer Display in UI
     */
    @FunctionalInterface
    interface UpdateTimerCallBack {
        void updateTimerDisplay(String timerMessage, long timeLeft, long totalTimeGiven);
    }
}
