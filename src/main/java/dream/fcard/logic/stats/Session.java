//@@author nattanyz
package dream.fcard.logic.stats;

import java.time.Duration;
import java.time.LocalDateTime;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import dream.fcard.util.stats.DateTimeUtil;

/**
 * A Session object represents a length of time the user spends doing a task, e.g. using the app
 * or running a test on a deck.
 */
public class Session implements JsonInterface {

    /** The start time of the session, in the user's local time zone. */
    protected LocalDateTime sessionStart;

    /** The end time of the session, in the user's local time zone. */
    protected LocalDateTime sessionEnd;

    /** The score of the session, if applicable. */
    protected String score = null;

    /** The start time of the session, as a String for rendering in the GUI. */
    private String sessionStartString;

    /** The start time of the session, as a String for rendering in the GUI. */
    private String sessionEndString;

    /** The duration of the session, as a Duration object. */
    private Duration duration;

    /** The duration of the session, as a String for rendering in the GUI. */
    private String durationString;

    /**
     * Constructs a new instance of Session and sets the session's start time to the present.
     */
    public Session() {
        startSession();
    }

    /**
     * Constructs a new instance of session and sets the session's start time to argument.
     * @param start start time
     */
    public Session(LocalDateTime start) {
        startSession(start);
    }

    /**
     * Constructs a new instance of session and sets the session's start and end time to arguments.
     * @param start start time
     * @param end   end time
     */
    public Session(LocalDateTime start, LocalDateTime end) {
        startSession(start);
        endSession(end);
    }

    /**
     * Constructs a new instance of Session and sets its start, end and score to arguemnts.
     * @param start Start time
     * @param end End time
     * @param score Score of the Session
     */
    public Session(LocalDateTime start, LocalDateTime end, String score) {
        startSession(start);
        endSession(end);
        setScore(score);
    }

    /**
     * Start session start time to present.
     */
    public void startSession() {
        startSession(LocalDateTime.now());
    }

    /**
     * Start session start time to argument.
     * @param start start time
     */
    public void startSession(LocalDateTime start) {
        this.sessionStart = start;
        this.sessionStartString = DateTimeUtil.getStringFromDateTime(this.sessionStart);
    }

    /**
     * Ends this session, by setting its end time to the present.
     * To be called when the session's controller exits.
     */
    public void endSession() {
        endSession(LocalDateTime.now());
    }

    /**
     * Ends this session, to argument datetime
     * @param end   end time
     */
    private void endSession(LocalDateTime end) {
        this.sessionEnd = end;
        this.sessionEndString = DateTimeUtil.getStringFromDateTime(this.sessionEnd);
        this.setDuration();
    }

    /**
     * Calculates and sets the duration of this session.
     */
    public void setDuration() {
        Duration duration = DateTimeUtil.calculateDuration(this.getSessionStart(), this.getSessionEnd());
        this.duration = duration;
        this.durationString = DateTimeUtil.getStringFromDuration(this.duration);
        System.out.println("Duration set to: " + durationString);
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

    /** Gets the duration of this session, as a Duration object. */
    public Duration getDuration() {
        return this.duration;
    }

    /** Gets the duration of this session, as a String. */
    public String getDurationString() {
        return this.durationString;
    }

    /** Returns true if this Session has a Score. */
    public boolean hasScore() {
        return this.score != null;
    }

    /** Gets the score of this session, as a String. */
    public String getScore() {
        if (!this.hasScore()) {
            return "";
        }
        return this.score;
    }

    /** Sets the score of this session to the given String. Assumes that the valid format is used. */
    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        try {
            obj.put(Schema.SESSION_START,
                    DateTimeUtil.getJsonFromDateTime(sessionStart).getObject());
            obj.put(Schema.SESSION_END,
                    DateTimeUtil.getJsonFromDateTime(sessionEnd).getObject());

            if (this.hasScore()) {
                obj.put(Schema.SESSION_SCORE, score);
            }
        } catch (JsonWrongValueException e) {
            System.out.println("DATETIME JSON MUST BE AN OBJECT\n" + e.getMessage());
        }
        return new JsonValue(obj);
    }
}
