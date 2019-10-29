package dream.fcard.logic.stats;

import dream.fcard.util.DateTimeUtil;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * A Session object represents a length of time the user spends doing a task, e.g. using the app
 * or running a test on a deck.
 */
public class Session {
    // should implement JsonInterface, todo: @AHaliq can store LocalDateTime?

    /** The start time of the session, in the local time zone. */
    private LocalDateTime sessionStart;

    /** The end time of the session, in the local time zone. */
    private LocalDateTime sessionEnd;

    /** The duration of the session, as a Duration object. */
    private Duration duration;

    /**
     * Constructs a new instance of Session and sets the session's start time to the present.
     */
    public Session() {
        startSession();
    }

    /** Sets the session's start time to the present. */
    private void startSession() {
        this.sessionStart = LocalDateTime.now();
    }

    /**
     * Ends this session, by setting its end time to the present.
     * To be called when the session's controller exits.
     */
    public void endSession() {
        this.sessionEnd = LocalDateTime.now();
        this.setDuration();
    }

    /** Gets the start time of this session. */
    public LocalDateTime getSessionStart() {
        return this.sessionStart;
    }

    /** Gets the end time of this session. */
    public LocalDateTime getSessionEnd() {
        return this.sessionEnd;
    }

    /** Calculates and sets the duration of this session. */
    public void setDuration() {
        this.duration = DateTimeUtil.calculateDuration(this.getSessionStart(), this.getSessionEnd());
    }

    /** Gets the duration of this session, as a Duration object. */
    public Duration getDuration() {
        return this.duration;
    }
}
