package seedu.address.model.rule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.rule.exceptions.DuplicateRuleException;
import seedu.address.model.rule.exceptions.RuleNotFoundException;

/**
 * A list of rules that enforces uniqueness between its elements and does not allow nulls.
 * A rule is considered unique by comparing using {@code Rule#equals(Rule)}. As such, adding and updating of
 * rules uses Rule#equals(Rule) for equality so as to ensure that the rule being added or updated is
 * unique in terms of identity in the UniqueRuleList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Rule#equals(Object)
 */
public class UniqueRuleList implements Iterable<Rule> {

    private final ObservableList<Rule> internalList = FXCollections.observableArrayList();
    private final ObservableList<Rule> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public UniqueRuleList() {}

    public UniqueRuleList(List<Rule> toBeCopied) {
        requireNonNull(toBeCopied);
        setRules(toBeCopied);
    }

    /**
     * Returns true if the list contains an equivalent rule as the given argument.
     */
    public boolean contains(Rule toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a rule to the list.
     * The rule must not already exist in the list.
     */
    public void add(Rule toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Retrieves a rule from the list equivalent to the given rule.
     * @param toGet The equivalent rule (identical attributes to the target rule).
     * @return The retrieved rule.
     * @throws RuleNotFoundException if rule is not in the list.
     */
    public Rule get(Rule toGet) {
        requireNonNull(toGet);

        Rule targetRule = null;
        for (Rule rule : internalUnmodifiableList) {
            if (rule.equals(targetRule)) {
                targetRule = rule;
            }
        }

        if (targetRule == null) {
            throw new RuleNotFoundException();
        }

        return targetRule;
    }

    /**
     * Replaces the Rule {@code target} in the list with {@code editedRule}.
     * {@code target} must exist in the list.
     * The rule identity of {@code editedRule} must not be the same as another existing Rule in the list.
     */
    public void setRule(Rule target, Rule editedRule) {
        requireAllNonNull(target, editedRule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RuleNotFoundException();
        }

        if (!target.equals(editedRule) && contains(editedRule)) {
            throw new DuplicateRuleException();
        }

        internalList.set(index, editedRule);
    }

    /**
     * Removes the equivalent rule from the list.
     * The rule must exist in the list.
     */
    public void remove(Rule toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RuleNotFoundException();
        }
    }

    public void setRules(UniqueRuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code rules}.
     * {@code rules} must not contain duplicate rules.
     */
    public void setRules(List<Rule> rules) {
        requireAllNonNull(rules);
        if (!rulesAreUnique(rules)) {
            throw new DuplicateRuleException();
        }

        internalList.setAll(rules);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Rule> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Rule> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRuleList // instanceof handles nulls
                        && internalList.equals(((UniqueRuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code rules} contains only unique rules.
     */
    private boolean rulesAreUnique(List<Rule> rules) {
        for (int i = 0; i < rules.size() - 1; i++) {
            for (int j = i + 1; j < rules.size(); j++) {
                if (rules.get(i).equals(rules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
