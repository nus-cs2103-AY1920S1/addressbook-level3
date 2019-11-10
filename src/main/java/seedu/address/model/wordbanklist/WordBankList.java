// @@author chrischenhui
package seedu.address.model.wordbanklist;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;

/**
 * Wraps all data at the word bank folder level.
 * Duplicates are not allowed (by WordBank#isSameName(WordBank) comparison)
 */
public class WordBankList implements ReadOnlyWordBankList {

    private final UniqueWordBankList wordBankList = new UniqueWordBankList();

    public WordBankList(List<WordBank> wbl) {
        for (WordBank wb : wbl) {
            wordBankList.add(wb);
        }
    }

    /**
     * Replaces the contents of the word bank list with {@code List<WordBank>}.
     * {@code List<WordBank>} must not contain any cards with the same meaning.
     */
    public void setWordBankList(List<WordBank> wordBankList) {
        this.wordBankList.setWordBankList(wordBankList);
    }

    /**
     * Adds a card to the word bank.
     * A card with the same meaning must not already exist in the word bank.
     */
    public void addWordBank(ReadOnlyWordBank wordBank) {
        wordBankList.add((WordBank) wordBank);
    }

    /**
     * Removes {@code key} from this {@code WordBank}.
     * {@code key} must exist in the word bank.
     * @param wordBankName
     */
    public void removeWordBank(ReadOnlyWordBank wordBankName) {
        wordBankList.remove((WordBank) wordBankName);
    }

    /**
     * Returns number of word banks in the word bank list.
     *
     * @return number of word banks in the word bank list.
     */
    @Override
    public int size() {
        return wordBankList.size();
    }

    /**
     * Returns an unmodifiable view of the word bank.
     * This list will not contain any duplicate word banks.
     * Every word bank can be specified by its unique name.
     * This is used so that any updates to the word bank list will be notified to the observer.
     */
    @Override
    public ObservableList<WordBank> getFilteredWordBankList() {
        return wordBankList.asUnmodifiableObservableList();
    }

    /**
     * Retrieves a word bank from its unique name.
     *
     * @param name word bank name.
     * @return word bank.
     */
    @Override
    public WordBank getWordBankFromName(String name) {
        for (WordBank wb : wordBankList) {
            if (wb.isSameName(name)) {
                return wb;
            }
        }
        return SampleDataUtil.getPokemonWordBank();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WordBankList // instanceof handles nulls
                && wordBankList.equals(((WordBankList) other).wordBankList));
    }

    @Override
    public int hashCode() {
        return wordBankList.hashCode();
    }

    /**
     * Checks if the word bank list contain this particular word bank.
     *
     * @param name of word bank.
     * @return true if word bank exist and false otherwise.
     */
    @Override
    public boolean hasWordBankName(String name) {
        return wordBankList.contains(name);
    }
}
