//@@author nattanyz
package dream.fcard.util.stats;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;

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
        return roundToTwoDecimalPlaces(scoreAsDouble);
    }

    /**
     * Given a Session, return a double representing the percentage of correct answers.
     * Assumes that the Session provided has a score.
     * @param session The Session whose score to get.
     * @return A double representing the percentage of correct answers.
     */
    public static double getScoreAsPercentageDouble(Session session) {
        String score = session.getScore();
        return SessionListUtil.getScoreAsPercentageDouble(score);
    }

    /**
     * Rounds a given score, as a double, to 2 decimal places and returns it as a double.
     */
    public static double roundToTwoDecimalPlaces(double scoreAsDouble) {
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
     * Converts a String representing the percentage of correct answers to a double.
     */
    public static double convertScoreStringToDouble(String score) {
        String strippedScore = score.split("%")[0];
        return Double.parseDouble(strippedScore);
    }

    /**
     * Calculates the average score of a list of test sessions.
     * @param sessionList The list of test sessions.
     * @return The average score of the list of test sessions, as a String.
     */
    public static String getAverageScore(SessionList sessionList) {
        ArrayList<Session> sessionArrayList = sessionList.getSessionArrayList();

        double sumOfScores = 0.0;
        int numOfTestSessionsWithScore = 0;
        for (Session session : sessionArrayList) {
            if (!session.hasScore()) {
                continue;
            }
            double score = SessionListUtil.getScoreAsPercentageDouble(session);
            //System.out.println("score = " + score);
            sumOfScores += score;
            //System.out.println("sumOfScores = " + sumOfScores);
            numOfTestSessionsWithScore++;
        }

        if (sumOfScores == 0.0) {
            return "0";
        }

        double averageScoreAsDouble = sumOfScores / numOfTestSessionsWithScore;
        //System.out.println("averageScoreAsDouble = " + averageScoreAsDouble);
        double roundedAverageScore = roundToTwoDecimalPlaces(averageScoreAsDouble);
        //System.out.println("roundedAverageScore = " + roundedAverageScore);
        String averageScoreAsString = convertScoreDoubleToString(roundedAverageScore);
        return averageScoreAsString;
    }

    /**
     * Calculates the average score of a list of test session lists.
     * @param sessionLists The list of test session lists.
     * @return The average score of the list of test sessions, as a String.
     */
    public static String getAverageScore(ArrayList<SessionList> sessionLists) {
        int numSessionList = 0; // number of non-empty SessionLists
        double sumOfScores = 0.0;

        for (SessionList sessionList : sessionLists) {
            if (sessionList.isEmpty()) {
                continue;
            }
            String averageScoreOfSessionList = getAverageScore(sessionList);
            sumOfScores += convertScoreStringToDouble(averageScoreOfSessionList);
            numSessionList++;
        }

        double averageScoreAsDouble = sumOfScores / numSessionList;
        double roundedAverageScore = roundToTwoDecimalPlaces(averageScoreAsDouble);
        String averageScoreAsString = convertScoreDoubleToString(roundedAverageScore);
        return averageScoreAsString;
    }
}
