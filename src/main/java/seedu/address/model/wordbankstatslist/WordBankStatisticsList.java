package seedu.address.model.wordbankstatslist;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.wordbank.WordBank;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.statistics.JsonWordBankStatisticsStorage;

/**
 * Wraps all word bank statistics
 */
public class WordBankStatisticsList implements ReadOnlyWordBankStatisticsList {

    private final ObservableList<WordBankStatistics> internalList = FXCollections.observableArrayList();

    public WordBankStatisticsList() {
        Path filePath = Paths.get("data/wbstats/");
        JsonWordBankStatisticsStorage storage = new JsonWordBankStatisticsStorage(filePath);
        Optional<List<WordBankStatistics>> optionalWbStatsList = storage.getWordBankStatisticsList();
        optionalWbStatsList.ifPresent(internalList::addAll);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the card list with {@code wordBanksStatisticsList}.
     * {@code cards} must not contain any cards with the same meaning.
     */
    public void setWordBankStatisticsList(List<WordBankStatistics> wordBankStatisticsList) {
        this.internalList.setAll(wordBankStatisticsList);
    }

    /**
     * Resets the existing data of this {@code WordBankStatisticsList} with {@code newData}.
     */
    public void resetData(ReadOnlyWordBankStatisticsList newData) {
        requireNonNull(newData);
        setWordBankStatisticsList(newData.getWordBankStatisticsList());
    }

    //// card-level operations

    /**
     * Returns true if a word bank stats with the same name as {@code wordBankStats} exists in the word bank list.
     */
    public boolean hasWordBankStatistics(WordBankStatistics wordBankStats) {
        requireNonNull(wordBankStats);
        return internalList.contains(wordBankStats);
    }

    /**
     * Adds a {@code WordBankStatistics} to this.
     */
    public void addWordBanksStatistics(WordBankStatistics wbStats) {
        internalList.add(wbStats);
    }

    //    /**
    //     * Replaces the given card {@code target} in the list with {@code editedCard}.
    //     * {@code target} must exist in the word bank.
    //     * The card meaning of {@code editedCard} must not be the same as another existing card in the word bank.
    //     */
    //    public void setWordBankList(WordBank target, WordBank editedCard) {
    //        requireNonNull(editedCard);
    //
    //        wordBankList.setWordBankList(target, editedCard);
    //    }

    /**
     * Removes {@code key} from this {@code WordBankStatisticsList}.
     * {@code key} must exist in the word bank.
     */
    public void removeWordBankStatistics(WordBankStatistics key) {
        internalList.remove(key);
    }

    public Optional<WordBankStatistics> getMostPlayedWordBankStatistics() {
        return internalList.stream()
                .max((x, y) -> y.getGamesPlayed() - x.getGamesPlayed());
    }

    public int getTotalPlayed() {
        return internalList.stream()
                .mapToInt(WordBankStatistics::getGamesPlayed)
                .sum();
    }

    //// util methods

    @Override
    public String toString() {
        return size() + " word banks statistics";
        // TODO: refine later
    }

    @Override
    public int size() {
        return internalList.size();
    }

    @Override
    public ObservableList<WordBankStatistics> getWordBankStatisticsList() {
        return internalList;
    }

    @Override
    public WordBankStatistics getWordBankStatistics(String name) {
        for (WordBankStatistics wbStats : internalList) {
            if (wbStats.getWordBankName().equals(name)) {
                return wbStats;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordBank // instanceof handles nulls
                && internalList.equals(((WordBankStatisticsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
