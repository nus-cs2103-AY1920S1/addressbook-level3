package seedu.algobase.model;

import javafx.collections.ObservableList;
import seedu.algobase.model.Problem.Problem;
import seedu.algobase.model.Problem.UniqueProblemList;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameProblem comparison)
 */
public class AlgoBase implements ReadOnlyAlgoBase {

    private final UniqueProblemList problems;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        problems = new UniqueProblemList();
    }

    public AlgoBase() {}

    /**
     * Creates an AlgoBase using the Problems in the {@code toBeCopied}
     */
    public AlgoBase(ReadOnlyAlgoBase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Problem list with {@code problems}.
     * {@code problems} must not contain duplicate problems.
     */
    public void setProblems(List<Problem> problems) {
        this.problems.setProblems(problems);
    }

    /**
     * Resets the existing data of this {@code AlgoBase} with {@code newData}.
     */
    public void resetData(ReadOnlyAlgoBase newData) {
        requireNonNull(newData);

        setProblems(newData.getProblemList());
    }

    //// Problem-level operations

    /**
     * Returns true if a Problem with the same identity as {@code Problem} exists in the algobase.
     */
    public boolean hasProblem(Problem problem) {
        requireNonNull(problem);
        return problems.contains(problem);
    }

    /**
     * Adds a Problem to the algobase.
     * The Problem must not already exist in the algobase.
     */
    public void addProblem(Problem p) {
        problems.add(p);
    }

    /**
     * Replaces the given Problem {@code target} in the list with {@code editedProblem}.
     * {@code target} must exist in the algobase.
     * The Problem identity of {@code editedProblem} must not be the same as another existing Problem in the algobase.
     */
    public void setProblem(Problem target, Problem editedProblem) {
        requireNonNull(editedProblem);

        problems.setProblem(target, editedProblem);
    }

    /**
     * Removes {@code key} from this {@code AlgoBase}.
     * {@code key} must exist in the algobase.
     */
    public void removeProblem(Problem key) {
        problems.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return problems.asUnmodifiableObservableList().size() + " problems";
        // TODO: refine later
    }

    @Override
    public ObservableList<Problem> getProblemList() {
        return problems.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AlgoBase // instanceof handles nulls
                && problems.equals(((AlgoBase) other).problems));
    }

    @Override
    public int hashCode() {
        return problems.hashCode();
    }
}
