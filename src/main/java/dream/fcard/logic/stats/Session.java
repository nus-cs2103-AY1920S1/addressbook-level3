package dream.fcard.logic.stats;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

import dream.fcard.util.DateTimeUtil;


/**
 * A Session object represents a length of time the user spends doing a task, e.g. using the app
 * or running a test on a deck.
 */
public class Session implements Serializable {
    // should implement JsonInterface, todo: @AHaliq can store LocalDateTime?

    /** The start time of the session, in the user's local time zone. */
    private LocalDateTime sessionStart;

    /** The start time of the session, as a String for rendering in the GUI. */
    private String sessionStartString;

    /** The end time of the session, in the user's local time zone. */
    private LocalDateTime sessionEnd;

    /** The start time of the session, as a String for rendering in the GUI. */
    private String sessionEndString;

    /** The duration of the session, as a Duration object. */
    private Duration duration;

    /** The duration of the session, as a String for rendering in the GUI. */
    private String durationString;

    private int score = -1; // optional, default is -1

    /**
     * Constructs a new instance of Session and sets the session's start time to the present.
     */
    public Session() {
        startSession();
    }

    /** Sets the session's start time to the present. */
    private void startSession() {
        // todo: add tests for String properties
        this.sessionStart = LocalDateTime.now();
        this.sessionStartString = DateTimeUtil.getStringFromDateTime(this.sessionStart);
    }

    /**
     * Ends this session, by setting its end time to the present.
     * To be called when the session's controller exits.
     */
    public void endSession() {
        this.sessionEnd = LocalDateTime.now();
        this.sessionEndString = DateTimeUtil.getStringFromDateTime(this.sessionEnd);
        this.setDuration();
        this.durationString = DateTimeUtil.getStringFromDuration(this.duration);
    }

    /** Gets the start time of this session, as a LocalDateTime object. */
    public LocalDateTime getSessionStart() {
        return this.sessionStart;
    }

    /** Gets the start time of this session, as a String. */
    public String getSessionStartString() {
        return this.sessionStartString;
    }

    /** Gets the end time of this session, as a LocalDateTimeObject. */
    public LocalDateTime getSessionEnd() {
        return this.sessionEnd;
    }

    /** Gets the end time of this session, as a String. */
    public String getSessionEndString() {
        return this.sessionEndString;
    }

    /** Calculates and sets the duration of this session. */
    public void setDuration() {
        Duration duration = DateTimeUtil.calculateDuration(this.getSessionStart(), this.getSessionEnd());
        this.duration = duration;
        System.out.println("Duration set to: " + DateTimeUtil.getStringFromDuration(duration));
    }

    /** Gets the duration of this session, as a Duration object. */
    public Duration getDuration() {
        return this.duration;
    }

    /** Gets the duration of this session, as a String. */
    public String getDurationString() {
        return this.durationString;
    }

    /** Sets the score of this session. */
    public void setScore(int score) {
        this.score = score;
    }

    /** Gets the score of this session.*/
    public int getScore() {
        if (this.score == -1) {
            return 0; // todo: is this a good idea???
        }
        return this.score;
    }

    /** Returns true if this session has an associated score, false otherwise. */
    public boolean hasScore() {
        return this.score != -1;
    }
}
