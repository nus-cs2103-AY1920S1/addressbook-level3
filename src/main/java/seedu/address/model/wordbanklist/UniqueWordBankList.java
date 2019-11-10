// @@author chrischenhui
package seedu.address.model.wordbanklist;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbank.exceptions.DuplicateWordBankException;
import seedu.address.model.wordbank.exceptions.WordBankNotFoundException;

/**
 * A list of word banks that enforces uniqueness between its elements and does not allow nulls.
 * A word bank is considered unique by comparing using {@code WordBank#isSameName(WordBank)}. As such, adding and
 * updating of word banks uses WordBank#isSameName(WordBank) for equality so as to ensure that the word bank being
 * added or updated is unique in terms of names in UniqueWordBankList. However, the removal of a word bank uses
 * WordBank#equals(WordBank) so as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see seedu.address.model.wordbank.WordBank#isSameName(seedu.address.model.wordbank.WordBank)
 */
public class UniqueWordBankList implements Iterable<WordBank> {

    private final ObservableList<WordBank> internalList = FXCollections.observableArrayList();
    private final ObservableList<WordBank> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains a WordBank with the same name.
     */
    public boolean contains(WordBank toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameName);
    }

    /**
     * Returns true if the list contains a WordBank with the same name.
     * Similar to contains(WordBank toCheck), just different parameters.
     */
    public boolean contains(String toCheck) {
        requireNonNull(toCheck);
        WordBank temp = new WordBank(toCheck);
        return internalList.stream().anyMatch(temp::isSameName);
    }

    /**
     * Adds a WordBank to the list.
     * The WordBank must not exist in the list.
     *
     * @see UniqueWordBankList#contains(WordBank)
     */
    public void add(WordBank toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateWordBankException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code List<WordBank>}.
     * {@code List<WordBank>} must not contain duplicate WordBank.
     */
    void setWordBankList(List<WordBank> cards) {
        requireAllNonNull(cards);
        if (!wordBanksAreUnique(cards)) {
            throw new DuplicateWordBankException();
        }
        internalList.setAll(cards);
    }

    /**
     * Removes the equivalent wordBank from the list.
     * The wordBank must exist in the list.
     */
    public void remove(WordBank toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new WordBankNotFoundException();
        }

    }

    public WordBank get(Index index) {
        return internalList.get(index.getZeroBased());
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    ObservableList<WordBank> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<WordBank> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueWordBankList // instanceof handles nulls
                && internalList.equals(((UniqueWordBankList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code cards} contains only unique cards.
     */
    private boolean wordBanksAreUnique(List<WordBank> wordBankList) {
        for (int i = 0; i < wordBankList.size() - 1; i++) {
            for (int j = i + 1; j < wordBankList.size(); j++) {
                if (wordBankList.get(i).isSameName(wordBankList.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
