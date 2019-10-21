package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.UniqueRuleList;
import javafx.collections.ObservableList;

/**
 * Manages all rules in a unique rule list.
 */
public class RuleManager {

    private final UniqueRuleList rules;

    /**
     * Creates a new (empty) list of rules.
     */
    public RuleManager() {
        this.rules = new UniqueRuleList();
    }

    /**
     * Creates a RuleManager using the Rules in the {@code toBeCopied}
     */
    public RuleManager(RuleManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the rule list with {@code rules}.
     * {@code rules} must not contain duplicate rules.
     */
    public void setRules(List<Rule> rules) {
        this.rules.setRules(rules);
    }

    /**
     * Resets the existing data of this {@code RuleManager} with {@code newData}.
     */
    public void resetData(RuleManager newData) {
        requireNonNull(newData);

        setRules(newData.getRuleList());
    }

    //// rule-level operations

    /**
     * Returns true if a rule with the same identity as {@code rule} exists in the rule manager.
     */
    public boolean hasRule(Rule rule) {
        requireNonNull(rule);
        return rules.contains(rule);
    }

    /**
     * Adds a rule to the rule manager.
     * The rule must not already exist in the rule manager.
     */
    public void addRule(Rule rule) {
        rules.add(rule);
    }

    /**
     * Replaces the given rule {@code target} in the list with {@code editedRule}.
     * {@code target} must exist in the rule manager.
     * The rule identity of {@code editedRule} must not be the same as another existing rule in the rule manager.
     */
    public void setRule(Rule target, Rule editedRule) {
        requireNonNull(editedRule);

        rules.setRule(target, editedRule);
    }

    /**
     * Removes {@code key} from this {@code RuleManager}.
     * {@code key} must exist in the rule manager.
     */
    public void removeRule(Rule key) {
        rules.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return rules.asUnmodifiableObservableList().size() + " rules";
    }

    /**
     * Retrieves the list of rules.
     */
    public ObservableList<Rule> getRuleList() {
        return rules.asUnmodifiableObservableList();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RuleManager // instanceof handles nulls
                && rules.equals(((RuleManager) other).rules));
    }

    @Override
    public int hashCode() {
        return rules.hashCode();
    }
}
