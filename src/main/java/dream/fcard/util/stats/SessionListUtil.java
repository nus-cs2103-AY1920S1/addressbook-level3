//@@author nattanyz
package dream.fcard.util.stats;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;
import dream.fcard.logic.stats.TestSession;
import dream.fcard.logic.stats.TestSessionList;

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
     * Given a TestSession, return a double representing the percentage of correct answers.
     * Assumes that the TestSession provided has a score.
     * @param session The TestSession whose score to get.
     * @return A double representing the percentage of correct answers.
     */
    public static double getScoreAsPercentageDouble(TestSession session) {
        String score = session.getScore();
        return SessionListUtil.getScoreAsPercentageDouble(score);
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
     * Calculates the average score of a list of test sessions.
     * @param testSessionList The list of test sessions.
     * @return The average score of the list of test sessions, as a String.
     */
    public static String getAverageScore(TestSessionList testSessionList) {
        ArrayList<TestSession> sessionArrayList = testSessionList.getTestSessionArrayList();

        double sumOfScores = 0.0;
        int numOfTestSessionsWithScore = 0;
        for (TestSession session : sessionArrayList) {
            if (!session.hasScore()) {
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
