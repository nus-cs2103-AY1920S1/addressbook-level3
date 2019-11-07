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
     * Converts a double representing the percentage of correct answers to a String with "%" appended.
     * @param score The score to be converted, as a double.
     * @return The score as a String, representing the percentage of correct answers.
     */
    public static String convertScoreDoubleToString(double score) {
        return score + ("%");
    }

    /**
     * Given a Session, check if it is a TestSession, and whether it has a score.
     * Returns false if the Session is not a TestSession, or if the Session is a TestSession but
     * does not have a score.
     */
    public static boolean isTestSessionAndHasScore(Session session) {
        if (!session.isTestSession()) {
            return false;
        }
        TestSession testSession = (TestSession) session;
        return testSession.hasScore();
    }

    /**
     * Given a Session, return the String representing the percentage of correct answers.
     * If the Session is not a TestSession or does not have a score, return 0.0.
     */
    public static double getScoreAsPercentageDouble(Session session) {
        if (!SessionListUtil.isTestSessionAndHasScore(session)) {
            return 0.0;
        }
        String score = ((TestSession) session).getScore();
        return SessionListUtil.getScoreAsPercentageDouble(score);
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
            if (!SessionListUtil.isTestSessionAndHasScore(session)) {
                continue;
            }
            sumOfScores += SessionListUtil.getScoreAsPercentageDouble(session);
            numOfTestSessionsWithScore++;
        }

        if (sumOfScores == 0.0) {
            return "0";
        }

        double averageScoreAsDouble = sumOfScores / numOfTestSessionsWithScore;
        String averageScoreAsString = convertScoreDoubleToString(averageScoreAsDouble);
        return averageScoreAsString;
    }
}
