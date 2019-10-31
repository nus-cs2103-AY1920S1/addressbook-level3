package budgetbuddy.logic.parser;

import static budgetbuddy.commons.util.AppUtil.getDateFormat;
import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.regex.Matcher;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.commons.util.StringUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.expression.ActionExpression;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.PredicateExpression;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.script.ScriptName;
import budgetbuddy.model.transaction.Amount;
import budgetbuddy.model.transaction.TransactionList;

/**
 * Contains utility methods used for parsing strings in the various *CommandParser classes.
 */
public class CommandParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String account} into an {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code account} is invalid.
     */
    public static Account parseAccount(String account) {
        requireNonNull(account);
        String trimmedAccount = account.trim();
        return new Account(new Name(trimmedAccount), new Description("null"), new TransactionList());
    }

    /**
     * Parses a {@code String amount} into an {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();

        String[] dollarCentArray = trimmedAmount.split("\\.");
        if (dollarCentArray.length <= 0 || dollarCentArray.length >= 3) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }

        Long parsedDollars;
        if (StringUtil.isNonNegativeUnsignedLong(dollarCentArray[0])) {
            parsedDollars = Long.parseLong(dollarCentArray[0]) * 100L;
        } else {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }

        Long parsedCents = 0L;
        if (dollarCentArray.length == 2) {
            if (dollarCentArray[1].length() <= 2
                    && StringUtil.isNonNegativeUnsignedLong(dollarCentArray[1])) {
                parsedCents = dollarCentArray[1].length() == 1
                        ? Long.parseLong(dollarCentArray[1] + "0")
                        : Long.parseLong(dollarCentArray[1]);
            } else {
                throw new ParseException(Amount.MESSAGE_CENTS_PARSE_ERROR);
            }
        }

        return new Amount(parsedDollars + parsedCents);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }


    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return getDateFormat().parse(trimmedDate);
        } catch (java.text.ParseException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Parses a {@code String attribute} into a {@code Attribute}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code attribute} is invalid.
     */
    public static Attribute parseAttribute(String attribute) throws ParseException {
        requireNonNull(attribute);
        String trimmedAttr = attribute.trim();
        if (!Attribute.isValidAttribute(trimmedAttr)) {
            throw new ParseException(Attribute.MESSAGE_CONSTRAINTS);
        }
        return Attribute.of(trimmedAttr);
    }

    /**
     * Parses a {@code String operator} into an {@code Operator}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code operator} is invalid.
     */
    public static Operator parseOperator(String operator) throws ParseException {
        requireNonNull(operator);
        String trimmedOp = operator.trim();
        if (!Operator.isValidOperator(trimmedOp)) {
            throw new ParseException(Operator.MESSAGE_CONSTRAINTS);
        }
        return Operator.of(trimmedOp);
    }

    /**
     * Parses a {@code String value} into a {@code Value}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code value} is invalid.
     */
    public static Value parseValue(String value) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();
        if (!Value.isValidValue(trimmedValue)) {
            throw new ParseException(Value.MESSAGE_CONSTRAINTS);
        }
        return new Value(trimmedValue);
    }

    /**
     * Parses a {@code String expr} into an {@code PredicateExpression}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expr} is invalid.
     */
    public static PredicateExpression parsePredicateExpr(String expr) throws ParseException {
        requireNonNull(expr);
        String trimmedExpr = expr.trim();

        Matcher matcher = PredicateExpression.FORMAT_REGEX.matcher(trimmedExpr);
        if (!matcher.matches()) {
            throw new ParseException(PredicateExpression.MESSAGE_CONSTRAINTS);
        }

        Attribute attribute = parseAttribute(matcher.group("exprAttribute"));
        Operator operator = parseOperator(matcher.group("exprOperator"));
        Value value = parseValue(matcher.group("exprValue"));

        if (!PredicateExpression.isValidPredicateExpr(attribute, operator, value)) {
            throw new ParseException(PredicateExpression.MESSAGE_TYPE_REQUIREMENTS);
        }
        return new PredicateExpression(attribute, operator, value);
    }

    /**
     * Parses a {@code String expr} into an {@code ActionExpression}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expr} is invalid.
     */
    public static ActionExpression parseActionExpr(String expr) throws ParseException {
        requireNonNull(expr);
        String trimmedExpr = expr.trim();

        Matcher matcher = ActionExpression.FORMAT_REGEX.matcher(trimmedExpr);
        if (!matcher.matches()) {
            throw new ParseException(ActionExpression.MESSAGE_CONSTRAINTS);
        }

        Operator operator = parseOperator(matcher.group("exprOperator"));
        Value value = parseValue(matcher.group("exprValue"));

        if (!ActionExpression.isValidActionExpr(operator, value)) {
            throw new ParseException(ActionExpression.MESSAGE_TYPE_REQUIREMENTS);
        }
        return new ActionExpression(operator, value);
    }

    /**
     * Parses a {@code String predicate} into a {@code RulePredicate}.
     */
    public static RulePredicate parsePredicate(String predicate, String type) throws ParseException {
        requireNonNull(predicate);
        if (type.equals(Rule.TYPE_EXPRESSION)) {
            return parsePredicateExpr(predicate);
        } else {
            throw new ParseException(RulePredicate.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String action} into a {@code RuleAction}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code action} is invalid.
     */
    public static RuleAction parseAction(String action, String type) throws ParseException {
        requireNonNull(action);
        if (type.equals(Rule.TYPE_EXPRESSION)) {
            return parseActionExpr(action);
        } else {
            throw new ParseException(RuleAction.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String name} into a {@link ScriptName}.
     *
     * Leading and trailing whitespace is trimmed.
     *
     * @param name the string to parse
     * @return the parsed script name
     * @throws ParseException if the given string is not a valid script name
     */
    public static ScriptName parseScriptName(String name) throws ParseException {
        requireNonNull(name);
        String trimmed = name.trim();
        try {
            return new ScriptName(trimmed);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * TODO IMPLEMENT, right now just returns a new Category
     */
    public static Category parseCategory(String s) {
        return new Category(s);
    }
}
