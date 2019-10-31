package budgetbuddy.logic.rules;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.logic.rules.performable.Performable;
import budgetbuddy.logic.rules.performable.PerformableExpression;
import budgetbuddy.logic.rules.performable.SetCategoryExpression;
import budgetbuddy.logic.rules.performable.SetDescExpression;
import budgetbuddy.logic.rules.testable.ContainsExpression;
import budgetbuddy.logic.rules.testable.EqualToExpression;
import budgetbuddy.logic.rules.testable.LessEqualExpression;
import budgetbuddy.logic.rules.testable.LessThanExpression;
import budgetbuddy.logic.rules.testable.MoreEqualExpression;
import budgetbuddy.logic.rules.testable.MoreThanExpression;
import budgetbuddy.logic.rules.testable.Testable;
import budgetbuddy.logic.rules.testable.TestableExpression;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.model.Model;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.expression.ActionExpression;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.PredicateExpression;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Contains utility methods and constants used for processing rules.
 */
public class RuleProcessor {
    public static final String TYPE_CATEGORY = "CATEGORY";
    public static final String TYPE_DESC = "DESC";
    public static final String TYPE_AMOUNT = "AMOUNT";
    public static final String TYPE_DATE = "DATE";
    public static final String TYPE_BLANK = "BLANK";
    private static final HashMap<Operator, BiFunction<Attribute, Value, TestableExpression>> testableMap;
    private static final HashMap<Operator, Function<Value, PerformableExpression>> performableMap;

    static {
        testableMap = new HashMap<>();
        performableMap = new HashMap<>();

        testableMap.put(Operator.CONTAINS, ContainsExpression::new);
        testableMap.put(Operator.EQUAL_TO, EqualToExpression::new);
        testableMap.put(Operator.LESS_EQUAL, LessEqualExpression::new);
        testableMap.put(Operator.LESS_THAN, LessThanExpression::new);
        testableMap.put(Operator.MORE_EQUAL, MoreEqualExpression::new);
        testableMap.put(Operator.MORE_THAN, MoreThanExpression::new);

        performableMap.put(Operator.SET_CATEGORY, SetCategoryExpression::new);
        performableMap.put(Operator.SET_DESC, SetDescExpression::new);
    }

    /**
     * Is a private constructor for a static-only class.
     */
    private RuleProcessor() {}

    /**
     * Returns the value of a transaction's attribute given the transaction.
     */
    public static Object extractAttribute(Attribute attribute, Transaction txn) {
        switch (attribute) {
        case DESCRIPTION:
            return txn.getDescription();
        case IN_AMOUNT:
            return (txn.getDirection().equals(Direction.IN) ? 1 : -1)
                    * (txn.getAmount().toLong() / 100.0);
        case OUT_AMOUNT:
            return (txn.getDirection().equals(Direction.OUT) ? 1 : -1)
                    * (txn.getAmount().toLong() / 100.0);
        case DATE:
            return txn.getDate();
        default:
            // impossible
            assert false : "Unhandled attribute";
            return null;
        }
    }

    /**
     * Parses a {@code RulePredicate predicate} into a {@code Testable}.
     */
    public static Testable parseTestable(RulePredicate predicate, ScriptEngine scriptEngine) {
        requireAllNonNull(predicate, scriptEngine);
        if (predicate.getType().equals(Rule.TYPE_EXPRESSION)) {
            PredicateExpression predExpr = (PredicateExpression) predicate;
            return testableMap.get(predExpr.getOperator()).apply(predExpr.getAttribute(), predExpr.getValue());
        } else {
            // todo: script retrieval
            return null;
        }
    }

    /**
     * Parses a {@code RuleAction action} into a {@code Performable}
     */
    public static Performable parsePerformable(RuleAction action, ScriptEngine scriptEngine) {
        requireAllNonNull(action, scriptEngine);
        if (action.getType().equals(Rule.TYPE_EXPRESSION)) {
            ActionExpression actExpr = (ActionExpression) action;
            return performableMap.get(actExpr.getOperator()).apply(actExpr.getValue());
        } else {
            // todo: script retrieval
            return null;
        }
    }

    /**
     * Runs all rules against transaction
     */
    public static void executeRules(Model model, ScriptEngine scriptEngine, Transaction txn) {
        requireAllNonNull(model, model.getRuleManager(), scriptEngine, txn);
        for (Rule rule : model.getRuleManager().getRules()) {
            Testable testable = parseTestable(rule.getPredicate(), scriptEngine);
            if (testable.test(txn)) {
                Performable performable = parsePerformable(rule.getAction(), scriptEngine);
                performable.perform(model, txn);
            }
        }
    }

    /**
     * Returns if a value can be parsed into the specified type.
     */
    public static boolean isValueParsable(String typeName, Value value) {
        switch (typeName) {
        case TYPE_CATEGORY:
            CommandParserUtil.parseCategory(value.toString());
            break;
        case TYPE_DESC:
            try {
                CommandParserUtil.parseDescription(value.toString());
                break;
            } catch (ParseException e) {
                return false;
            }
        case TYPE_AMOUNT:
            try {
                CommandParserUtil.parseAmount(value.toString());
                break;
            } catch (ParseException e) {
                return false;
            }
        case TYPE_DATE:
            // todo: need to try parsing date
            return false;
        case TYPE_BLANK:
            if (value.toString().isEmpty()) {
                break;
            }
            return false;
        default:
            return false;
        }

        return true;
    }
}
