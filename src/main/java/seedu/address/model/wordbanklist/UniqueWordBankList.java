package seedu.address.model.wordbanklist;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;

/**
 * A list of cards that enforces uniqueness between its elements and does not allow nulls.
 * A card is considered unique by comparing using {@code Card#isSameMeaning(Card)}. As such, adding and updating of
 * cards uses Card#isSameMeaning(Card) for equality so as to ensure that the person being added or updated is
 * unique in terms of names in UniqueCardList. However, the removal of a card uses Card#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see seedu.address.model.wordbank.WordBank#isSameMeaning(seedu.address.model.wordbank.WordBank)
 */
public class UniqueWordBankList implements Iterable<WordBank> {

    private final ObservableList<WordBank> internalList = FXCollections.observableArrayList();
    private final ObservableList<WordBank> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains a card with the same meaning.
     */
    public boolean contains(WordBank toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMeaning);
    }


    /**
     * Adds a card to the list.
     * The card must not exist in the list.
     *
     * @see UniqueWordBankList#contains(WordBank)
     */
    public void add(WordBank toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCardException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the card {@code target} in the list with {@code editedCard}.
     * {@code target} must exist in the list.
     * The name of {@code editedPerson} must not be the same as another existing card in the list.
     */
    public void setWordBankList(WordBank target, WordBank editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CardNotFoundException();
        }

        if (!target.isSameMeaning(editedPerson) && contains(editedPerson)) {
            throw new DuplicateCardException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent card from the list.
     * The card must exist in the list.
     */
    public void remove(WordBank toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CardNotFoundException();
        }
    }

    public void setWordBankList(UniqueWordBankList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code cards}.
     * {@code cards} must not contain duplicate persons.
     */
    public void setWordBankList(List<WordBank> cards) {
        requireAllNonNull(cards);
        if (!wordBanksAreUnique(cards)) {
            throw new DuplicateCardException();
        }

        internalList.setAll(cards);
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
    public ObservableList<WordBank> asUnmodifiableObservableList() {
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
    private boolean wordBanksAreUnique(List<WordBank> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).isSameMeaning(cards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
