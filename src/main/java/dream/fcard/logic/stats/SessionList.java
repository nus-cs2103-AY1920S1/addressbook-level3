//@@author nattanyz
package dream.fcard.logic.stats;

import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonValue;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A list of Session objects.
 */
public class SessionList implements JsonInterface {

    private ArrayList<Session> sessionArrayList;
    // todo: add another data structure to store sessions by date?

    /** Constructs a new instance of SessionList, with an empty list by default. */
    public SessionList() {
        this.sessionArrayList = new ArrayList<>();
    }

    /**
     * Constructs a new instance of SessionList, with an existing list.
     * @param initialArrayList  list
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
     * Returns the number of sessions in the list.
     * @return The number of sessions in the list.
     */
    public int numberOfSessions() {
        return this.sessionArrayList.size();
    }

    /**
     * Returns the list of sessions, sorted by start date, in chronological order (earliest first).
     * @return The list of sessions, sorted by start date, in chronological order (earliest first).
     */
    public ArrayList<Session> sortByDateChrono() {
        this.sessionArrayList.sort((a, b) -> {
            if (a.getSessionStart().isBefore(b.getSessionStart())) {
                return 1;
            } else {
                return -1;
            }
        });
        return this.sessionArrayList;

        // todo: write tests!!! what should happen if the dates are the same?
        // todo: problem!!! cannot sort if a session has not yet been terminated (no end!)
    }

    /**
     * Returns the list of sessions, sorted by start date, in chronological order (latest first).
     * @return The list of sessions, sorted by start date, in chronological order (latest first).
     */
    public ArrayList<Session> sortByDateChronoReversed() {
        this.sessionArrayList.sort((a, b) -> {
            if (a.getSessionStart().isAfter(b.getSessionStart())) {
                return 1;
            } else {
                return -1;
            }
        });
        return this.sessionArrayList;
    }

    /** Gets the sessionArrayList contained in this SessionList. */
    public ArrayList<Session> getSessionArrayList() {
        return this.sessionArrayList;
    }

    @Override
    public JsonValue toJson() {
        JsonArray arr = new JsonArray();
        for(Session s : sessionArrayList) {
            try {
                arr.add(s.toJson().getObject());
            } catch(JsonWrongValueException e) {
                System.out.println("SESSION JSON EXPECTED TO BE OBJECT\n" + e.getMessage());
            }
        }
        return new JsonValue(arr);
    }
    // todo: get sessions in the past week, past month etc. --> sublist? wrapped in SessionList?
}
