//@@author nattanyz
package dream.fcard.util.stats;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;
import dream.fcard.logic.stats.TestSession;

/**
 * Utilities for easily manipulating and getting data from SessionList objects.
 */
public class SessionListUtil {

    /**
     * Retrieves a sublist of the given SessionList, containing only sessions which started AFTER
     * the given cutoff.
     * @param initialSessionList The SessionList to get the sublist from.
     * @param cutoff The LocalDateTime to be used as the cutoff time.
     * @return A sublist of the given SessionList, containing only sessions which started after the given cutoff.
     */
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

    /**
     * Retrieves a sublist of the given SessionList, containing only sessions which started within
     * the previous week.
     * @param initialSessionList The SessionList to get the sublist from.
     * @return A sublist of the given SessionList, containing only sessions which started within the previous week.
     */
    public static SessionList getSublistForThisWeek(SessionList initialSessionList) {
        LocalDateTime cutoff = DateTimeUtil.getLastWeekCutoffDate(LocalDateTime.now());
        return getSublistAfterCutoff(initialSessionList, cutoff);
    }

    /**
     * Given the score of a test as a String, convert it to a double representing the percentage
     * of correct answers, rounded to 2 decimal places.
     * @param score The score to be converted, as a String.
     * @return The score as a double, representing the percentage of correct answers.
     */
    public static double getScoreAsPercentageDouble(String score) {
        int finalScore = Integer.parseInt(score.split("/")[0]);
        int maxScore = Integer.parseInt(score.split("/")[1]);
        double scoreAsDouble = (finalScore / (double) maxScore) * 100;

        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // rounds to 2 d.p.
        String percentage = decimalFormat.format(scoreAsDouble);
        return Double.parseDouble(percentage);
    }

    /**
     * Given the score of a test as a String, convert it to a String representing the percentage
     * of correct answers, rounded to 2 decimal places.
     * @param score The score to be converted, as a String.
     * @return The score as a String, representing the percentage of correct answers.
     */
    public static String getScoreAsPercentageString(String score) {
        return getScoreAsPercentageDouble(score) + ("%");
    }

    /**
     * Given a Session, return the String representing the percentage of correct answers.
     * If the Session is not a TestSession or does not have a score, return 0.
     */
    public static String getScoreAsPercentageString(Session session) {
        if (!session.isTestSession()) {
            return "0";
        }
        TestSession testSession = (TestSession) session;
        if (!testSession.hasScore()) {
            return "0";
        }
        String scoreAsPercentage = SessionListUtil.getScoreAsPercentageString(testSession.getScore());
        return scoreAsPercentage;
    }

    /**
     * Calculates the average score of a list of test sessions.
     * @param sessionList The list of test sessions, each with a score.
     * @return The average score of a list of test sessions.
     */
    public static String getAverageScore(SessionList sessionList) {
        ArrayList<Session> sessionArrayList = sessionList.getSessionArrayList();

        double sumOfScores = 0.0;
        int numOfTestSessionsWithScore = 0;
        for (Session session : sessionArrayList) {
            if (!session.isTestSession()) {
                continue;
            }
            TestSession testSession = (TestSession) session;
            if (!testSession.hasScore()) {
                continue;
            }
            sumOfScores += Double.parseDouble(
                SessionListUtil.getScoreAsPercentageString(testSession.getScore()));
            numOfTestSessionsWithScore++;
        }

        if (sumOfScores == 0.0) {
            return "0";
        }

        double averageScoreAsDouble = sumOfScores / numOfTestSessionsWithScore;
        String averageScoreAsString = Double.toString(averageScoreAsDouble);
        return averageScoreAsString;
    }
}
