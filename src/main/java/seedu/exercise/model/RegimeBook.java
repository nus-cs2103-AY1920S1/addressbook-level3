package seedu.exercise.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.exercise.model.regime.Regime;
import seedu.exercise.model.regime.UniqueRegimeList;

/**
 * Wraps all data at the regime-book level
 * Duplicates are not allowed (by .isSameRegime comparison)
 */
public class RegimeBook implements ReadOnlyRegimeBook {
    private final UniqueRegimeList regimes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        regimes = new UniqueRegimeList();
    }

    public RegimeBook() {}

    /**
     * Creates an RegimeBook using the Regime in the {@code toBeCopied}
     */
    public RegimeBook(ReadOnlyRegimeBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the regime list with {@code regimes}.
     * {@code regimes} must not contain duplicate regimes.
     */
    public void setRegimes(List<Regime> regimes) {
        this.regimes.setRegimes(regimes);
    }

    /**
     * Resets the existing data of this {@code RegimeBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRegimeBook newData) {
        requireNonNull(newData);

        setRegimes(newData.getRegimeList());
    }

    /**
     * Returns true if a regime with the same name exists in the regime book.
     */
    public boolean hasRegime(Regime regime) {
        requireNonNull(regime);
        return regimes.contains(regime);
    }

    /**
     * Adds a regime to the regime book.
     * THe regime must not already exist in the regime book.
     */
    public void addRegime(Regime r) {
        regimes.add(r);
    }

    public void setRegime(Regime target, Regime editedRegime) {
        requireNonNull(editedRegime);

        regimes.setRegime(target, editedRegime);
    }

    /**
     * Removes {@code key} from this {@code RegimeBook}.
     * {@code key} must exist in the regime book.
     */
    public void removeRegime(Regime key) {
        regimes.remove(key);
    }

    /**
     * Returns the index of regime in regime book.
     */
    public int getRegimeIndex(Regime regime) {
        int i = 0;
        for (Regime r : regimes) {
            if (r.equals(regime)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public String toString() {
        return regimes.asUnmodifiableObservableList().size() + " regimes";
        // TODO: refine later
    }


    @Override
    public ObservableList<Regime> getRegimeList() {
        return regimes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegimeBook // instanceof handles nulls
                && regimes.equals(((RegimeBook) other).regimes));
    }

    @Override
    public int hashCode() {
        return regimes.hashCode();
    }
}
