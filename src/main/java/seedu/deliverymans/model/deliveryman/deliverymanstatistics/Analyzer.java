package seedu.deliverymans.model.deliveryman.deliverymanstatistics;

import static seedu.deliverymans.model.deliveryman.deliverymanstatistics.State.HIGH;
import static seedu.deliverymans.model.deliveryman.deliverymanstatistics.State.LOW;
import static seedu.deliverymans.model.deliveryman.deliverymanstatistics.State.MAX;
import static seedu.deliverymans.model.deliveryman.deliverymanstatistics.State.MODERATE;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * This is a singleton Analyzer object.
 * It analyzes the current status lists of deliverymen and compute relevant data and records the data on
 * a {@code StatisticsRecordCard}
 */
public class Analyzer {
    private static Analyzer theAnalyzer = null;

    private static double lowUtilisationBoundary = 20.0;
    private static double highUtilisationBoundary = 80.0;
    private static double lowActivityBoundary = 40.0;
    private static double highActivityBoundary = 90.0;
    private static double maxBoundary = 100;

    public Analyzer() {}

    /**
     * Prevents instantiation of Analyzer from outside. Only one copy is allowed.
     */
    public static Analyzer getInstance() {
        if (theAnalyzer == null) {
            theAnalyzer = new Analyzer();
        }
        return theAnalyzer;
    }

    /**
     * Analyzes the time in database, the delivery rate, order completion rate of a deliveryman record.
     * Then records it on the record card.
     * @return the same {@code StatisticsRecordCard} which the function took in as parameter.
     */
    public StatisticsRecordCard analyze(StatisticsRecordCard recordCard, ObservableList<Deliveryman> availableMenList,
                                        ObservableList<Deliveryman> unavailableMenList,
                                        ObservableList<Deliveryman> deliveringMenList) {
        int availableMen = calcAvailableMen(availableMenList);
        int unavailableMen = calcUnavailableMen(unavailableMenList);
        int deliveringMen = calcDeliveringMen(deliveringMenList);
        int totalMen = calcTotalMenSize(availableMenList, unavailableMenList, deliveringMenList);
        double utilisationLevel = calcUtilisationLevel(availableMenList, unavailableMenList, deliveringMenList);
        double activityLevel = calcActivityLevel(availableMenList, unavailableMenList, deliveringMenList);

        State utilisationState = calcUtilisationState(utilisationLevel);
        State activityState = calcActivityState(activityLevel);

        recordCard.editCard(availableMen, unavailableMen, deliveringMen, totalMen, utilisationLevel, activityLevel,
                utilisationState, activityState);

        return recordCard;
    }

    // ======= Functions to calculate statistics ============================================================

    private int calcAvailableMen(ObservableList<Deliveryman> list) {
        return list.size();
    }

    private int calcUnavailableMen(ObservableList<Deliveryman> list) {
        return list.size();
    }

    private int calcDeliveringMen(ObservableList<Deliveryman> list) {
        return list.size();
    }

    private int calcTotalMenSize(ObservableList<Deliveryman> list1, ObservableList<Deliveryman> list2,
                                 ObservableList<Deliveryman> list3) {
        return list1.size() + list2.size() + list3.size();
    }

    private double calcUtilisationLevel(ObservableList<Deliveryman> list1, ObservableList<Deliveryman> list2,
                                        ObservableList<Deliveryman> list3) {
        return ((double) calcDeliveringMen(list3) / (calcDeliveringMen(list3) + calcAvailableMen(list1))) * 100.0;
    }

    private double calcActivityLevel(ObservableList<Deliveryman> list1, ObservableList<Deliveryman> list2,
                                     ObservableList<Deliveryman> list3) {
        return ((double) (calcDeliveringMen(list3) + calcAvailableMen(list1))
                / (calcTotalMenSize(list1, list2, list3))) * 100.00;
    }

    // ======= Functions to calculate state levels ============================================================

    /**
     * Function to compute the {@code State} of the {@code utilLevel}.
     */
    private State calcUtilisationState(double utilLevel) {
        if (utilLevel <= lowUtilisationBoundary) {
            return LOW;
        } else if (utilLevel == maxBoundary) {
            return MAX;
        } else if (utilLevel >= highUtilisationBoundary) {
            return HIGH;
        } else {
            return MODERATE;
        }
    }

    /**
     * Function to compute the {@code State} of the {@code activeLevel}.
     */
    private State calcActivityState(double activeLevel) {
        if (activeLevel <= lowActivityBoundary) {
            return LOW;
        } else if (activeLevel == maxBoundary) {
            return MAX;
        } else if (activeLevel >= highActivityBoundary) {
            return HIGH;
        } else {
            return MODERATE;
        }
    }

}
