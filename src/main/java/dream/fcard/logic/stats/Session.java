package dream.fcard.logic.stats;

import java.time.Duration;
import java.time.LocalDateTime;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.DateTimeUtil;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;


/**
 * A Session object represents a length of time the user spends doing a task, e.g. using the app
 * or running a test on a deck.
 */
public class Session implements JsonInterface {

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

    /**
     * Construct a new instance of session and sets the session's start time to argument.
     * @param start start time
     */
    public Session(LocalDateTime start) {
        startSession(start);
    }

    /**
     * Construct a new instance of session and sets the session's start and end time to arguments.
     * @param start start time
     * @param end   end time
     */
    public Session(LocalDateTime start, LocalDateTime end) {
        startSession(start);
        endSession(end);
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
        // todo: add tests for String properties
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
    public void endSession(LocalDateTime end) {
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

    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        try {
            obj.put(Schema.SESSION_START,
                    DateTimeUtil.getJsonFromDateTime(sessionStart).getObject());
            obj.put(Schema.SESSION_END,
                    DateTimeUtil.getJsonFromDateTime(sessionEnd).getObject());
            obj.put(Schema.SESSION_SCORE, score);
        } catch (JsonWrongValueException e) {
            System.out.println("DATETIME JSON MUST BE AN OBJECT\n" + e.getMessage());
        }
        return new JsonValue(obj);
    }
}
