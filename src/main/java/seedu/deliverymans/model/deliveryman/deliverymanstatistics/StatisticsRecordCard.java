package seedu.deliverymans.model.deliveryman.deliverymanstatistics;

/**
 * Represents a card for statistics to be recorded on.
 * Each variable represent a statistic.
 */
public class StatisticsRecordCard {

    // empty data fields
    private int numAvailableMen;
    private int numUnavailableMen;
    private int numDeliveringMen;
    private int numTotalMen;
    private double utilisationLevel;
    private double activityLevel;

    // verifiers
    private State utilisationState;
    private State activityState;

    // Initialises an empty record card with all values set to 0 as default.
    public StatisticsRecordCard() {
        numAvailableMen = 0;
        numUnavailableMen = 0;
        numDeliveringMen = 0;
        numTotalMen = 0;
        utilisationLevel = 0;
        activityLevel = 0;
    }

    /**
     *
     */
    public void editCard(int f1, int f2, int f3, int f4, double f5, double f6, State s1, State s2) {
        numAvailableMen = f1;
        numUnavailableMen = f2;
        numDeliveringMen = f3;
        numTotalMen = f4;
        utilisationLevel = f5;
        activityLevel = f6;
        utilisationState = s1;
        activityState = s2;
    }

    /**
     *
     */
    public String resultMessage() {
        String resultMessage = "=============================================\n" + "TOTAL DELIVERYMEN  :  "
                + numTotalMen + "  " + "AVAILABLE      :  " + numAvailableMen + "  " + "UNAVAILABLE    :  "
                + numUnavailableMen + "  " + "DELIVERING     :  " + numDeliveringMen + "  "
                + "=============================================\n";
        return resultMessage;
    }

    /**
     *
     */
    public String adviceMessage() {
        String advice;
        switch (utilisationState) {
        case LOW:
            return "You have too much manpower\nthat is not utilized! ";
        case MODERATE:
            return "All is well. Your deliverymen are balanced. ";
        case HIGH:
            return "Watch out!\nYou are running out of \navailable deliverymen! ";
        default:
            advice = "";
        }
        switch (activityState) {
        case LOW:
            return "";
        case MODERATE:
            return "";
        case HIGH:
            return "";
        default:
            advice = "";
        }
        return "advice";
    }

    // Record report that will be displayed on the UI.
    public String toString() {
        return "";
    }
}
