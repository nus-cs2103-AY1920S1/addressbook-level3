package seedu.deliverymans.model.deliveryman.deliverymanstatistics;

/**
 * Represents a card for statistics to be recorded on.
 * Each variable represent a statistic.
 */
public class StatisticsRecordCard {

    private static final String UTILIZATION_MESSAGE = "(Utilisation level signals the level of \nidle deliverymen.)";
    private static final String ACTIVITY_MESSAGE = "(Activity level signals the level of \nactive deliverymen.)";

    // empty data fields
    private Integer numAvailableMen;
    private Integer numUnavailableMen;
    private Integer numDeliveringMen;
    private Integer numTotalMen;
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
     * Fills in the empty data fields in the StatisticsReportCard.
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
     * Represents the analysis results that is to be output to user.
     */
    public String resultMessage() {
        String resultMessage = "=========================================\n" + "TOTAL DELIVERYMEN  :  "
                + numTotalMen + "\n" + "AVAILABLE      :  " + numAvailableMen + "\n" + "UNAVAILABLE    :  "
                + numUnavailableMen + "\n" + "DELIVERING     :  " + numDeliveringMen + "\n"
                + "=========================================\n\n" + "UTILIZATION LEVEL  :  "
                + String.format("%.2f%%", utilisationLevel) + "\n" + UTILIZATION_MESSAGE + "\n\n"
                + "ACTIVITY LEVEL  :  " + String.format("%.2f%%", activityLevel) + "\n" + ACTIVITY_MESSAGE + "\n\n";
        return resultMessage;
    }

    /**
     * Represents the summary of the analysis that is to be output to user.
     */
    public String adviceMessage() {
        StringBuilder sb = new StringBuilder();
        switch (utilisationState) {
        case LOW:
            sb.append("You have too much manpower that is not utilized!\n\n");
            break;
        case HIGH:
            sb.append("Watch out!You are running out of available deliverymen!\n\n");
            break;
        case MODERATE:
            sb.append("All is well. Your deliverymen are balanced.\n\n");
            break;
        default:
            sb.append("***DeliveryMANS***\n");
        }
        switch (activityState) {
        case LOW:
            sb.append("You have too many deliverymen who are off.\n\n");
            break;
        case MODERATE:
            sb.append("You have a fair number of active deliverymen.\n\n");
            break;
        case HIGH:
            sb.append("Great! Your deliverymen are mostly active.\n\n");
            break;
        case MAX:
            sb.append("Amazing! All your deliveryMANS are working hard to serve your restaurant\n\n");
            break;
        default:
            sb.append("***DeliveryMANS***\n");
        }
        return sb.toString();
    }

    /**
     * Returns the data based on the field index requested.
     */
    public Object retrieveRecordCardField(int fieldIndex) {
        assert (fieldIndex >= 1 && fieldIndex < 9);

        switch (fieldIndex) {
        case 1:
            return numAvailableMen;
        case 2:
            return numUnavailableMen;
        case 3:
            return numDeliveringMen;
        case 4:
            return numTotalMen;
        case 5:
            return utilisationLevel;
        case 6:
            return activityLevel;
        case 7:
            return utilisationState;
        case 8:
            return activityState;
        default:
            return null;
        }
    }

    // Record report that will be displayed on the UI.
    public String toString() {
        return "";
    }
}
