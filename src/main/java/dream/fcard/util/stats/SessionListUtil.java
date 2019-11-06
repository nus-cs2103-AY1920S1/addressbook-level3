//@@author nattanyz
package dream.fcard.util.stats;

import java.time.LocalDateTime;
import java.util.ArrayList;

import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;

/**
 * Utilities for easily manipulating and getting data from SessionList objects.
 */
public class SessionListUtil {

    public static SessionList getSublistAfterCutoff(SessionList initialSessionList, LocalDateTime cutoff) {
        SessionList sublist = new SessionList();
        ArrayList<Session> sessionArrayList = initialSessionList.getSessionArrayList();

        for (Session session : sessionArrayList) {
            if (session.getSessionStart().isAfter(cutoff)) {
                sublist.addSession(session);
            }
        }

        return sublist;
    }

    public static SessionList getSublistForThisWeek(SessionList initialSessionList) {
        LocalDateTime cutoff = DateTimeUtil.getLastWeekCutoffDate(LocalDateTime.now());
        return getSublistAfterCutoff(initialSessionList, cutoff);
    }
}
