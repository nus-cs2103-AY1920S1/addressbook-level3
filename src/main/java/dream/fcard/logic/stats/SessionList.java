package dream.fcard.logic.stats;

import java.util.ArrayList;

/**
 * A list of Session objects.
 */
public class SessionList {

    private ArrayList<Session> sessionArrayList;
    // todo: add another data structure to store sessions by date?

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
}
