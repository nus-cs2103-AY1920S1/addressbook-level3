//@@author nattanyz
package dream.fcard.logic.stats;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

import dream.fcard.util.DateTimeUtil;

/**
 * A list of Session objects.
 */
public class SessionList implements Serializable {
    private ArrayList<Session> sessionArrayList;

    /** Constructs a new instance of SessionList, with an empty list by default. */
    public SessionList() {
        this.sessionArrayList = new ArrayList<>();
    }

    /**
     * Adds the given Session to the list.
     * @param session The Session to be added to the list.
     */
    public void addSession(Session session) {
        this.sessionArrayList.add(session);
    }

    /**
     * Returns the number of sessions in the list.
     * @return The number of sessions in the list.
     */
    public int numberOfSessions() {
        return this.sessionArrayList.size();
    }

    /** Gets the sessionArrayList contained in this SessionList. */
    public ArrayList<Session> getSessionArrayList() {
        return this.sessionArrayList;
    }

    /** Gets the total duration of sessions contained in this SessionList. */
    public String getTotalDurationAsString() {
        Duration duration = Duration.ZERO;

        for (Session session : sessionArrayList) {
            Duration sessionDuration = session.getDuration();
            duration = duration.plus(sessionDuration);
        }

        return DateTimeUtil.getStringFromDuration(duration);
    }
    // todo: get sessions in the past week, past month etc. --> sublist? wrapped in SessionList?
}
