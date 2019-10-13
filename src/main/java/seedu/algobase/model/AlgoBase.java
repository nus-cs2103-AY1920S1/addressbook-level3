package seedu.algobase.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.UniqueProblemList;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.tag.UniqueTagList;

/**
 * Wraps all data at the algobase level
 * Duplicates are not allowed (by .isSameProblem comparison)
 */
public class AlgoBase implements ReadOnlyAlgoBase {

    private final UniqueProblemList problems;
    private final UniqueTagList tags;

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

    {
        tags = new UniqueTagList();
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

    //// tag methods
    /**
     * Replaces the contents of the Tag list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }


    //// tag-level operations

    /**
     * Returns true if a Tag with the same identity as {@code Tag} exists in the algobase.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }

    /**
     * Adds a Tag to the algobase.
     * The Tag must not already exist in the algobase.
     */
    public void addTag(Tag p) {
        tags.add(p);
    }

    /**
     * Replaces the given Tag {@code target} in the list with {@code editedTag}.
     * {@code target} must exist in the algobase.
     * The Tag identity of {@code editedTag} must not be the same as another existing Tag in the algobase.
     */
    public void setTag(Tag target, Tag editedTag) {
        requireNonNull(editedTag);

        tags.setTag(target, editedTag);
    }

    /**
     * Removes {@code key} from this {@code AlgoBase}.
     * {@code key} must exist in the algobase.
     */
    public void removeTag(Tag key) {
        tags.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return tags.asUnmodifiableObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Problem> getProblemList() {
        return problems.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
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
