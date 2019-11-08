package dream.fcard.logic.stats;

import java.time.Duration;
import java.util.ArrayList;

import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonValue;
import dream.fcard.util.stats.DateTimeUtil;
import dream.fcard.util.stats.SessionListUtil;

public class TestSessionList extends SessionList {

    private ArrayList<TestSession> sessionArrayList;

    /** Constructs a new instance of SessionList, with an empty list by default. */
    public TestSessionList() {
        this.sessionArrayList = new ArrayList<>();
    }

    /**
     * Constructs a new instance of SessionList, with an existing list.
     * @param initialArrayList The initial list of sessions to be added to the SessionList.
     */
    public TestSessionList(ArrayList<TestSession> initialArrayList) {
        this.sessionArrayList = initialArrayList;
    }

    @Override
    public void addSession(Session session) {
        this.sessionArrayList.add((TestSession) session);
    }

    @Override
    public ArrayList<Session> getSessionArrayList() {
        return new ArrayList<>(this.sessionArrayList);
    }

    /** Returns the average score of all TestSessions contained in this TestSessionList, as a String. */
    public String getAverageScore() {
        return SessionListUtil.getAverageScore(this);
    }

    /** Gets the sessionArrayList contained in this TestSessionList, as an ArrayList of TestSessions. */
    public ArrayList<TestSession> getTestSessionArrayList() {
        return this.sessionArrayList;
    }
}
