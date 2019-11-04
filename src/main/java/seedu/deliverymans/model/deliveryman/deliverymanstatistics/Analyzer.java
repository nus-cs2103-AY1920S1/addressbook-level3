package seedu.deliverymans.model.deliveryman.deliverymanstatistics;

import static seedu.deliverymans.model.deliveryman.deliverymanstatistics.State.HIGH;
import static seedu.deliverymans.model.deliveryman.deliverymanstatistics.State.LOW;
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
        recordCard.editCard(
                calcAvailableMen(availableMenList),
                calcUnavailableMen(unavailableMenList),
                calcDeliveringMen(deliveringMenList),
                calcTotalMenSize(availableMenList, unavailableMenList, deliveringMenList),
                calcUtilisationLevel(availableMenList, unavailableMenList, deliveringMenList),
                calcActivityLevel(availableMenList, unavailableMenList, deliveringMenList),
                HIGH,
                MODERATE);

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
        return (double) calcDeliveringMen(list3) / (calcDeliveringMen(list3) + calcAvailableMen(list1)) * 100;
    }

    private double calcActivityLevel(ObservableList<Deliveryman> list1, ObservableList<Deliveryman> list2,
                                     ObservableList<Deliveryman> list3) {
        return (double) ((calcDeliveringMen(list3) + calcAvailableMen(list1)) / (calcTotalMenSize(list1, list2, list3)))
                * 100;
    }

    // ======= Functions to calculate state levels ============================================================

    private State calcUtilisationState(double utilLevel) {
        return HIGH;
    }

    private State calcActivityState(double activeLevel) {
        return LOW;
    }

}
