package budgetbuddy.logic.rules;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.logic.rules.performable.AppendDescriptionExpression;
import budgetbuddy.logic.rules.performable.Performable;
import budgetbuddy.logic.rules.performable.PerformableExpression;
import budgetbuddy.logic.rules.performable.PerformableScript;
import budgetbuddy.logic.rules.performable.PrependDescriptionExpression;
import budgetbuddy.logic.rules.performable.RemoveCategoryExpression;
import budgetbuddy.logic.rules.performable.SetCategoryExpression;
import budgetbuddy.logic.rules.performable.SetDescriptionExpression;
import budgetbuddy.logic.rules.performable.SetInwardExpression;
import budgetbuddy.logic.rules.performable.SetOutwardExpression;
import budgetbuddy.logic.rules.performable.SwitchDirectionExpression;
import budgetbuddy.logic.rules.testable.ContainsExpression;
import budgetbuddy.logic.rules.testable.EqualToExpression;
import budgetbuddy.logic.rules.testable.LessEqualExpression;
import budgetbuddy.logic.rules.testable.LessThanExpression;
import budgetbuddy.logic.rules.testable.MoreEqualExpression;
import budgetbuddy.logic.rules.testable.MoreThanExpression;
import budgetbuddy.logic.rules.testable.Testable;
import budgetbuddy.logic.rules.testable.TestableExpression;
import budgetbuddy.logic.rules.testable.TestableScript;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.logic.script.exceptions.ScriptException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ScriptLibrary;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.expression.ActionExpression;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.PredicateExpression;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.rule.script.ActionScript;
import budgetbuddy.model.rule.script.PredicateScript;
import budgetbuddy.model.script.ScriptName;
import budgetbuddy.model.transaction.Transaction;
import javafx.collections.ObservableList;

/**
 * Represents the Rule Engine that handles the creation and processing of rules.
 */
public class RuleEngine {
    public static final String TYPE_CATEGORY = "CATEGORY";
    public static final String TYPE_DESC = "DESC";
    public static final String TYPE_AMOUNT = "AMOUNT";
    public static final String TYPE_DATE = "DATE";
    public static final String TYPE_BLANK = "BLANK";

    public static final String MESSAGE_INVALID_VALUE = "Invalid value provided.";

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
        performableMap.put(Operator.REMOVE_CATEGORY, RemoveCategoryExpression::new);
        performableMap.put(Operator.SET_DESC, SetDescriptionExpression::new);
        performableMap.put(Operator.APPEND_DESC, AppendDescriptionExpression::new);
        performableMap.put(Operator.PREPEND_DESC, PrependDescriptionExpression::new);
        performableMap.put(Operator.SET_IN, SetInwardExpression::new);
        performableMap.put(Operator.SET_OUT, SetOutwardExpression::new);
        performableMap.put(Operator.SWITCH_DIRECTION, SwitchDirectionExpression::new);
    }

    /**
     * Is a private constructor for a static-only class.
     */
    private RuleEngine() {}

    /**
     * Runs all rules against a transaction, given a valid index and account.
     */
    public static void executeRules(Model model, ScriptEngine scriptEngine, Index txnIndex, Account account) {
        requireAllNonNull(model, model.getRuleManager(), model.getScriptLibrary(), scriptEngine, txnIndex, account);
        ScriptLibrary scriptLibrary = model.getScriptLibrary();
        ObservableList<Rule> rules = model.getRuleManager().getRules();

        for (Rule rule : rules) {
            Testable testable = generateTestable(rule.getPredicate(), scriptLibrary, scriptEngine);
            if (testable.test(txnIndex, account)) {
                Performable performable = generatePerformable(rule.getAction(), scriptLibrary, scriptEngine);
                performable.perform(model, txnIndex, account);
            }
        }
    }

    /**
     * Creates a {@code Testable} from a {@code RulePredicate predicate}.
     */
    public static Testable generateTestable(RulePredicate predicate, ScriptLibrary scriptLibrary,
                                            ScriptEngine scriptEngine) {
        requireAllNonNull(predicate, scriptEngine);
        if (predicate.getType().equals(Rule.TYPE_EXPRESSION)) {
            PredicateExpression predExpr = (PredicateExpression) predicate;
            return testableMap.get(predExpr.getOperator()).apply(predExpr.getAttribute(), predExpr.getValue());
        } else {
            ScriptName scriptName = ((PredicateScript) predicate).getScriptName();
            return new TestableScript((txnIndex, account) -> {
                try {
                    Transaction txn = account.getTransaction(txnIndex);
                    Object retVal = scriptEngine.evaluateScript(scriptLibrary.getScript(scriptName), txn, account);
                    if (!(retVal instanceof Boolean)) {
                        return false;
                    }
                    return (Boolean) retVal;
                } catch (ScriptException e) {
                    return false;
                }
            });
        }
    }

    /**
     * Creates a {@code Performable} from a {@code RuleAction action}.
     */
    public static Performable generatePerformable(RuleAction action, ScriptLibrary scriptLibrary,
                                                  ScriptEngine scriptEngine) {
        requireAllNonNull(action, scriptEngine);
        if (action.getType().equals(Rule.TYPE_EXPRESSION)) {
            ActionExpression actExpr = (ActionExpression) action;
            return performableMap.get(actExpr.getOperator()).apply(actExpr.getValue());
        } else {
            ScriptName scriptName = ((ActionScript) action).getScriptName();
            return new PerformableScript((txnIndex, account) -> {
                try {
                    Transaction txn = account.getTransaction(txnIndex);
                    scriptEngine.evaluateScript(scriptLibrary.getScript(scriptName), txn, account);
                } catch (ScriptException ignored) {
                    // If an error occurs, no need to do anything.
                }
            });
        }
    }

    /**
     * Converts a Value into the specified type.
     *
     * @throws ParseException if the value cannot be parsed.
     */
    public static Object convertValue(String typeName, Value value) throws ParseException {
        requireAllNonNull(typeName, value);
        switch (typeName) {
        case TYPE_CATEGORY:
            if (value.toString().isBlank()) {
                throw new ParseException(MESSAGE_INVALID_VALUE);
            }
            return CommandParserUtil.parseCategory(value.toString());
        case TYPE_DESC:
            if (value.toString().isBlank()) {
                throw new ParseException(MESSAGE_INVALID_VALUE);
            }
            return CommandParserUtil.parseDescription(value.toString());
        case TYPE_AMOUNT:
            return CommandParserUtil.parseAmount(value.toString());
        case TYPE_DATE:
            return CommandParserUtil.parseDate(value.toString());
        case TYPE_BLANK:
            return null;
        default:
            assert false : "Invalid type";
            throw new ParseException("Invalid type");
        }
    }

    /**
     * Returns the value of a transaction's attribute given the transaction.
     */
    public static Object extractAttribute(Attribute attribute, Index txnIndex, Account account) {
        requireAllNonNull(attribute, txnIndex, account);
        Transaction txn = account.getTransaction(txnIndex);
        switch (attribute) {
        case DESCRIPTION:
            return txn.getDescription();
        case IN_AMOUNT:
            if (txn.getDirection().equals(Direction.IN)) {
                return txn.getAmount();
            }
            break;
        case OUT_AMOUNT:
            if (txn.getDirection().equals(Direction.OUT)) {
                return txn.getAmount();
            }
            break;
        case DATE:
            return txn.getLocalDate();
        default:
            // impossible
            assert false : "Unhandled attribute";
            break;
        }
        return null;
    }
}
