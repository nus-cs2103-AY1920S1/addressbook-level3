package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.model.searchrule.problemsearchrule.exceptions.DuplicateProblemSearchRuleException;
import seedu.algobase.model.searchrule.problemsearchrule.exceptions.ProblemSearchRuleNotFoundException;

/**
 * A list of find rules that enforces uniqueness between its elements and does not allow nulls.
 * A find rule is considered unique by comparing using {@code ProblemSearchRule#isSameProblemSearchRule(other)}.
 * As such, adding and updating of find rules uses ProblemSearchRule#isSameProblemSearchRule(other) for equality
 * so as to ensure that the find rule being added or updated is unique in terms of identity in the UniqueFindRuleList.
 * However, the removal of a find rule uses ProblemSearchRule#equals(Object) so as to ensure that the find rule
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see ProblemSearchRule#isSameProblemSearchRule(ProblemSearchRule)
 */
public class UniqueFindRuleList implements Iterable<ProblemSearchRule> {

    private final ObservableList<ProblemSearchRule> internalList = FXCollections.observableArrayList();
    private final ObservableList<ProblemSearchRule> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent ProblemSearchRule as the {@code toCheck}.
     * @param toCheck find rule to be checked
     */
    public boolean contains(ProblemSearchRule toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProblemSearchRule);
    }

    /**
     * Adds a ProblemSearchRule to the list.
     * The ProblemSearchRule must not exist in the list.
     * @param rule find rule to be added
     */
    public void add(ProblemSearchRule rule) {
        requireNonNull(rule);
        if (contains(rule)) {
            throw new DuplicateProblemSearchRuleException();
        }
        internalList.add(rule);
    }

    /**
     * Replaces the ProblemSearchRule {@code target} in the list with {@code editedRule}.
     * {@code target} must exist in the list.
     * The ProblemSearchRule identity of {@code editedRule} must not be the same with any other ProblemSearchRule
     * in the list.
     * @param target find rule to be replaced
     * @param editedRule find rule to replace
     */
    public void setFindRule(ProblemSearchRule target, ProblemSearchRule editedRule) {
        requireAllNonNull(target, editedRule);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProblemSearchRuleNotFoundException();
        }
        if (!target.isSameProblemSearchRule(editedRule) && contains(editedRule)) {
            throw new DuplicateProblemSearchRuleException();
        }

        internalList.set(index, editedRule);
    }

    /**
     * Removes the equivalent ProblemSearchRule from the list.
     * {@code toRemove} must exist in the list.
     * @param toRemove find rule to be removed
     */
    public void remove(ProblemSearchRule toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProblemSearchRuleNotFoundException();
        }
    }

    /**
     * Replaces the contents of the list with {@code replacement}.
     * {@code replacement} must not contain any
     * @param replacement replacement list
     */
    public void setFindRules(UniqueFindRuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setFindRules(List<ProblemSearchRule> rules) {
        requireAllNonNull(rules);
        if (!findRulesAreUnique(rules)) {
            throw new DuplicateProblemSearchRuleException();
        }

        internalList.setAll(rules);
    }

    public ObservableList<ProblemSearchRule> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if {@code rules} contains only unique {@code ProblemSearchRule}.
     *
     * @param rules list of find rules to be checked
     */
    private boolean findRulesAreUnique(List<ProblemSearchRule> rules) {
        for (int i = 0; i < rules.size() - 1; i++) {
            for (int j = i + 1; j < rules.size(); j++) {
                if (rules.get(i).isSameProblemSearchRule(rules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Iterator<ProblemSearchRule> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueFindRuleList // instanceof handles nulls
            && internalList.equals(((UniqueFindRuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
