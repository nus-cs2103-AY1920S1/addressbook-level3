//@@author nattanyz
package dream.fcard.logic.stats;

import java.time.Duration;
import java.util.ArrayList;

import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonValue;
import dream.fcard.util.stats.DateTimeUtil;
import dream.fcard.util.stats.SessionListUtil;

/**
 * A list of Session objects.
 */
public class SessionList implements JsonInterface {

    private ArrayList<Session> sessionArrayList;

    /** Constructs a new instance of SessionList, with an empty list by default. */
    public SessionList() {
        this.sessionArrayList = new ArrayList<>();
    }

    /**
     * Constructs a new instance of SessionList, with an existing list.
     * @param initialArrayList The initial list of sessions to be added to the SessionList.
     */
    public SessionList(ArrayList<Session> initialArrayList) {
        this.sessionArrayList = initialArrayList;
    }

    /**
     * Adds the given Session to the list.
     * @param session The Session to be added to the list.
     */
    public void addSession(Session session) {
        this.sessionArrayList.add(session);
    }

    /**
     * Adds all sessions in the given SessionList to itself.
     * @param sessionList The list of sessions to be added to this session list.
     */
    void addSessions(SessionList sessionList) {
        this.sessionArrayList.addAll(sessionList.getSessionArrayList());
    }

    /**
     * Returns the number of sessions in the list.
     * @return The number of sessions in the list.
     */
    public int getNumberOfSessions() {
        return this.sessionArrayList.size();
    }

    /** Gets the sessionArrayList contained in this SessionList. */
    public ArrayList<Session> getSessionArrayList() {
        return this.sessionArrayList;
    }

    /** Gets the total duration of sessions in this SessionList, as a String. */
    public String getTotalDurationAsString() {
        Duration duration = Duration.ZERO;

        for (Session session : sessionArrayList) {
            Duration sessionDuration = session.getDuration();
            duration = duration.plus(sessionDuration);
        }

        return DateTimeUtil.getStringFromDuration(duration);
    }

    /** Gets the average duration of sessions in this SessionList, as a String. */
    public String getAverageDurationAsString() {
        Duration averageDuration = DateTimeUtil.getAverageDuration(this);
        return DateTimeUtil.getStringFromDuration(averageDuration);
    }

    /** Returns the average score of all sessions contained in this SessionList, as a String. */
    public String getAverageScore() {
        return SessionListUtil.getAverageScore(this);
    }

    /** Returns true if this SessionList contains no sessions. */
    public boolean isEmpty() {
        return this.sessionArrayList.isEmpty();
    }

    @Override
    public JsonValue toJson() {
        JsonArray arr = new JsonArray();
        for (Session s : sessionArrayList) {
            try {
                arr.add(s.toJson().getObject());
            } catch (JsonWrongValueException e) {
                System.out.println("SESSION JSON EXPECTED TO BE OBJECT\n" + e.getMessage());
            }
        }
        return new JsonValue(arr);
    }
}
