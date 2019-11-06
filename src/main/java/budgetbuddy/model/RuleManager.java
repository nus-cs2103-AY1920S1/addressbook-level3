package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.exceptions.RuleNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Maintains a list of unique rules.
 */
public class RuleManager {

    private final ObservableList<Rule> internalList = FXCollections.observableArrayList();
    private final ObservableList<Rule> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Creates a new (empty) list of rules.
     */
    public RuleManager() {}

    /**
     * Creates and fills a new list of rules.
     * @param rules A list of rules with which to fill the new list.
     */
    public RuleManager(List<Rule> rules) {
        requireNonNull(rules);
        this.internalList.setAll(rules);
    }

    /**
     * Retrieves the list of rules
     */
    public ObservableList<Rule> getRules() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the rule at the specified index in the list.
     * @param toGet The index of the target rule.
     * @throws RuleNotFoundException If the rule is not in the list.
     */
    public Rule getRule(Index toGet) throws RuleNotFoundException {
        checkIndexValidity(toGet);
        return getRules().get(toGet.getZeroBased());
    }

    /**
     * Returns the current number of rules in the list.
     * @return The current number of rules in the list as an {@code int}.
     */
    public int getRuleCount() {
        return getRules().size();
    }

    /**
     * Returns true if the list contains an equivalent rule as the given argument.
     */
    public boolean hasRule(Rule toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds a rule to the rule manager.
     * The rule must not already exist in the rule manager.
     */
    public void addRule(Rule toAdd) {
        internalList.add(toAdd);
    }

    /**
     * Replaces a target rule with the given rule.
     * @param toEdit The index of the target rule to replace.
     * @param editedRule The edited rule to replace the target rule with.
     */
    public void editRule(Index toEdit, Rule editedRule) throws RuleNotFoundException {
        checkIndexValidity(toEdit);
        internalList.set(toEdit.getZeroBased(), editedRule);
    }

    /**
     * Swaps two rules in the list given their indices.
     * @param firstIndex The first rule index.
     * @param secondIndex The second rule index.
     */
    public void swapRules(Index firstIndex, Index secondIndex) throws RuleNotFoundException {
        checkIndexValidity(firstIndex);
        checkIndexValidity(secondIndex);
        Collections.swap(internalList, firstIndex.getZeroBased(), secondIndex.getZeroBased());
    }

    /**
     * Deletes a target rule from the list.
     * @param toDelete The index of the target rule to delete.
     */
    public void deleteRule(Index toDelete) {
        checkIndexValidity(toDelete);
        internalList.remove(toDelete.getZeroBased());
    }

    /**
     * Check that the index given is valid.
     * @param toCheck The index to check.
     * @throws RuleNotFoundException If the index exceeds the current number of rules.
     */
    private void checkIndexValidity(Index toCheck) throws RuleNotFoundException {
        if (toCheck.getOneBased() > getRuleCount()) {
            throw new RuleNotFoundException();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RuleManager // instanceof handles nulls
                && getRules().equals(((RuleManager) other).getRules()));
    }

    @Override
    public int hashCode() {
        return getRules().hashCode();
    }
}
