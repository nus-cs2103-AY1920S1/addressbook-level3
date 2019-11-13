package budgetbuddy.logic.parser;

import static budgetbuddy.commons.util.AppUtil.getDateFormatter;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ASCENDING_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ASCENDING_DATE;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ASCENDING_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.SORT_DESCENDING_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_DESCENDING_DATE;
import static budgetbuddy.logic.parser.CliSyntax.SORT_DESCENDING_DESCRIPTION;
import static budgetbuddy.model.transaction.ComparatorUtil.SORT_BY_ASCENDING_AMOUNT;
import static budgetbuddy.model.transaction.ComparatorUtil.SORT_BY_ASCENDING_DATE;
import static budgetbuddy.model.transaction.ComparatorUtil.SORT_BY_ASCENDING_DESCRIPTION;
import static budgetbuddy.model.transaction.ComparatorUtil.SORT_BY_DESCENDING_AMOUNT;
import static budgetbuddy.model.transaction.ComparatorUtil.SORT_BY_DESCENDING_DATE;
import static budgetbuddy.model.transaction.ComparatorUtil.SORT_BY_DESCENDING_DESCRIPTION;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.regex.Matcher;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.commons.util.StringUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
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

/**
 * Contains utility methods used for parsing strings in the various *CommandParser classes.
 */
public class CommandParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index should be a positive integer.";
    public static final String MESSAGE_INVALID_DATE = "Date should be in the form of dd/mm/yyyy.";

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
     * Parses a {@code String account} into an {@code Name} of an Account.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code accountName} is invalid.
     */
    public static Name parseAccountName(String accountName) throws ParseException {
        requireNonNull(accountName);
        String trimmedAccount = accountName.trim();
        try {
            return new Name(trimmedAccount);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Name is invalid");
        }
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

        if (dollarCentArray[0].length() > Amount.MAX_AMOUNT.length()) {
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
     * Parses a {@code String sortInstruction} into a {@code Comparator<Transaction>}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sortInstruction} is invalid.
     */
    public static Comparator<Transaction> parseTransactionComparator(String sortInstruction) throws ParseException {
        requireNonNull(sortInstruction);
        String trimmedInstruction = sortInstruction.trim();
        switch (sortInstruction) {
        case SORT_ASCENDING_DATE:
            return SORT_BY_ASCENDING_DATE;
        case SORT_DESCENDING_DATE:
            return SORT_BY_DESCENDING_DATE;
        case SORT_ASCENDING_AMOUNT:
            return SORT_BY_ASCENDING_AMOUNT;
        case SORT_DESCENDING_AMOUNT:
            return SORT_BY_DESCENDING_AMOUNT;
        case SORT_ASCENDING_DESCRIPTION:
            return SORT_BY_ASCENDING_DESCRIPTION;
        case SORT_DESCENDING_DESCRIPTION:
            return SORT_BY_DESCENDING_DESCRIPTION;
        default:
            throw new ParseException("Invalid sort style!");
        }
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return LocalDate.parse(trimmedDate, getDateFormatter());
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
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
     * Parses a {@code String script} into a {@code PredicateScript}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code script} is invalid.
     */
    public static PredicateScript parsePredicateScript(String script) throws ParseException {
        requireNonNull(script);
        String trimmedScript = script.trim();

        ScriptName scriptName = parseScriptName(trimmedScript);
        return new PredicateScript(scriptName);
    }

    /**
     * Parses a {@code String script} into a {@code ActionScript}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code script} is invalid.
     */
    public static ActionScript parseActionScript(String script) throws ParseException {
        requireNonNull(script);
        String trimmedScript = script.trim();

        ScriptName scriptName = parseScriptName(trimmedScript);
        return new ActionScript(scriptName);
    }

    /**
     * Parses a {@code String predicate} into a {@code RulePredicate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code predicate} is invalid.
     */
    public static RulePredicate parsePredicate(String predicate) throws ParseException {
        requireNonNull(predicate);
        String trimmedPred = predicate.trim();
        if (trimmedPred.isEmpty()) {
            throw new ParseException(RulePredicate.MESSAGE_CONSTRAINTS);
        }

        if (trimmedPred.contains(" ")) {
            return parsePredicateExpr(trimmedPred);
        } else {
            return parsePredicateScript(trimmedPred);
        }
    }

    /**
     * Parses a {@code String action} into a {@code RuleAction}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code action} is invalid.
     */
    public static RuleAction parseAction(String action) throws ParseException {
        requireNonNull(action);
        String trimmedAct = action.trim();
        if (trimmedAct.isEmpty()) {
            throw new ParseException(RuleAction.MESSAGE_CONSTRAINTS);
        }

        if (action.contains(" ") || Operator.isValidOperator(trimmedAct)) {
            return parseActionExpr(trimmedAct);
        } else {
            return parseActionScript(trimmedAct);
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
     * Parses a {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param category the string to parse
     * @return the parsed category
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!Category.isValidCategory(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses a {@code String direction} into a {@link Direction}.
     * Leading and trailing whitespace is trimmed.
     *
     * @param direction the string to parse
     * @return the parsed direction
     * @throws ParseException if the given string is not a valid script name
     */
    public static Direction parseDirection(String direction) throws ParseException {
        if (direction.equalsIgnoreCase(Direction.OUT.toString())) {
            return Direction.OUT;
        } else if (direction.equalsIgnoreCase(Direction.IN.toString())) {
            return Direction.IN;
        } else {
            throw new ParseException(Direction.MESSAGE_CONSTRAINTS);
        }
    }
}
