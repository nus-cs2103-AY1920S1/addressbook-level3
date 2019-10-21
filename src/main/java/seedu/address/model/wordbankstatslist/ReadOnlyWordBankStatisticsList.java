package seedu.address.model.wordbankstatslist;

import javafx.collections.ObservableList;
import seedu.address.statistics.WordBankStatistics;

/**
 * Unmodifiable view of a word bank.
 */
public interface ReadOnlyWordBankStatisticsList {

    /**
     * Returns an unmodifiable view of the word bank statistics.
     */
    ObservableList<WordBankStatistics> getWordBankStatisticsList();

    WordBankStatistics getWordBankStatistics(String name);

    int size();

}

