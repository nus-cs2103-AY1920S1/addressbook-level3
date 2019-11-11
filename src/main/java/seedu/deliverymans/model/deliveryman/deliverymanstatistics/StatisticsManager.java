package seedu.deliverymans.model.deliveryman.deliverymanstatistics;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * Manages all statistics-related methods and functions.
 */
public class StatisticsManager {

    private final Analyzer analyzer;

    {
        analyzer = Analyzer.getInstance();
    }

    /**
     * Creates an empty {@code StatisticsRecordCard} for Analyzer to analyzed and record on.
     * Returns a filled-up StatisticsRecordCard
     */
    public StatisticsRecordCard analyzeStatusLists(ObservableList<Deliveryman> availableMenList,
                                                   ObservableList<Deliveryman> unavailableMenList,
                                                   ObservableList<Deliveryman> deliveringMenList) {
        StatisticsRecordCard recordCard = createNewRecordCard();
        analyzer.analyze(recordCard, availableMenList, unavailableMenList, deliveringMenList);

        return recordCard;
    }

    /**
     * Creates a new record card.
     * @return a new and empty {@code StatisticsRecordCard}
     */
    public StatisticsRecordCard createNewRecordCard() {
        return new StatisticsRecordCard();
    }

}
