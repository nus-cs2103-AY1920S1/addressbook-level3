package budgetbuddy.storage.rules;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.expression.ActionExpression;
import budgetbuddy.model.rule.expression.PredicateExpression;
import budgetbuddy.model.rule.script.ActionScript;
import budgetbuddy.model.rule.script.PredicateScript;

/**
 * Jackson-friendly version of {@link Rule}.
 */
public class JsonAdaptedRule {

    private final JsonAdaptedPredicate predicate;
    private final JsonAdaptedAction action;

    /**
     * Constructs a {@code JsonAdaptedRule} with the given rule details.
     */
    @JsonCreator
    public JsonAdaptedRule(@JsonProperty("predicate") JsonAdaptedPredicate predicate,
                           @JsonProperty("action") JsonAdaptedAction action) {
        requireAllNonNull(predicate, action);
        this.predicate = predicate;
        this.action = action;
    }

    /**
     * Converts a given {@code Rule} into this class for Jackson use.
     */
    public JsonAdaptedRule(Rule source) {
        RulePredicate sPred = source.getPredicate();
        RuleAction sAct = source.getAction();
        if (sPred.getType().equals(Rule.TYPE_EXPRESSION)) {
            predicate = new JsonAdaptedPredicateExpression((PredicateExpression) sPred);
        } else {
            predicate = new JsonAdaptedPredicateScript((PredicateScript) sPred);
        }

        if (sAct.getType().equals(Rule.TYPE_EXPRESSION)) {
            action = new JsonAdaptedActionExpression((ActionExpression) sAct);
        } else {
            action = new JsonAdaptedActionScript((ActionScript) sAct);
        }
    }

    /**
     * Converts this Jackson-friendly adapted rule object into the model's {@code Rule} object.
     * @throws IllegalValueException If any data constraints were violated in the adapted rule.
     */
    public Rule toModelType() throws IllegalValueException {
        return new Rule(predicate.toModelType(), action.toModelType());
    }
}
