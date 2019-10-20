package budgetbuddy.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.commons.util.StringUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.logic.rules.RuleProcessingUtil;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.ExpressionPredicate;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.tag.Tag;
import budgetbuddy.model.transaction.Amount;
import budgetbuddy.model.transaction.stub.Description;

/**
 * Contains utility methods used for parsing strings in the various *CommandParser classes.
 */
public class CommandParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String TYPE_EXPRESSION = "EXPRESSION";

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
     * Parses a {@code String amount} into an {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();

        String[] dollarCentArray = trimmedAmount.split("\\.");
        if (dollarCentArray.length < 1) {
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
            // TODO Some problems, e.g. 12/13/2020 gets parsed to 12/01/2021
            return new SimpleDateFormat("dd/MM/yy").parse(trimmedDate);
        } catch (java.text.ParseException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
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
        return Attribute.of(trimmedAttr).get();
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
        return Operator.of(trimmedOp).get();
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
     * Parses a {@code String expr} into an {@code ExpressionPredicate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expr} is invalid.
     */
    public static ExpressionPredicate parseExprPredicate(String expr) throws ParseException {
        requireNonNull(expr);
        String trimmedExpr = expr.trim();

        Matcher matcher = ExpressionPredicate.FORMAT_REGEX.matcher(trimmedExpr);
        if (!matcher.matches()) {
            throw new ParseException(ExpressionPredicate.MESSAGE_CONSTRAINTS);
        }

        Attribute attribute = parseAttribute(matcher.group("exprAttribute"));
        Operator operator = parseOperator(matcher.group("exprOperator"));
        Value value = parseValue(matcher.group("exprValue"));


        if (!RuleProcessingUtil.isValidExprPredicate(attribute, operator, value)) {
            throw new ParseException(ExpressionPredicate.MESSAGE_TYPE_REQUIREMENTS);
        }
        return new ExpressionPredicate(attribute, operator, value);
    }

    /**
     * Parses a {@code String predicate} into a {@code RulePredicate}.
     */
    public static RulePredicate parsePredicate(String predicate, String type) throws ParseException {
        requireNonNull(predicate);
        if (type.equals(TYPE_EXPRESSION)) {
            return parseExprPredicate(predicate);
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
    public static RuleAction parseAction(String action) {
        return null;
    }
}
