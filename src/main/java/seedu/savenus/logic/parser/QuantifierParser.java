package seedu.savenus.logic.parser;

import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_EQUALS_TO;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_LESS_THAN;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_MORE_THAN;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Price;

//@@author seanlowjk
/**
 * Checks if the fields and quantifiers are valid.
 */
public class QuantifierParser {

    public static final String NO_ARGUMENTS_USAGE = "Note you have entered in zero arguments:\n";
    public static final String DUPLICATE_FIELD_USAGE = "Note you have entered a duplicate field.";
    public static final String WRONG_ARGUMENT_NUMBER = "Note that you have key-ed in a wrong number of arguments.\n"
            + "Please fix the formatting to: FIELD QUANTIFIER VALUE\n"
            + "Note that VALUE MUST be only one word.";
    public static final String INVALID_FIELD_USAGE = "Note you have entered an invalid field! \n"
            + "You are only allowed to enter PRICE or price.";
    public static final String INVALID_QUANTIFIER_USAGE = "Note you have entered an invalid quantifier! \n"
            + "You are only allowed to enter the following quantifiers:\n"
            + "LESS_THAN, EQUALS_TO or MORE_THAN";
    public static final String WRONG_CASE_QUANTIFIER_USAGE = "Note that your quantifier must entirely be in"
            + " upper or lower case!";
    public static final String WRONG_CASE_FIELD_USAGE = "Note that your fields must entirely be in"
            + " upper or lower case!";
    public static final String INVALID_VALUE_USAGE = "Note you have entered an invalid value! \n"
            + "Please do make sure your value follow the field requirements.";

    /**
     * Parses the given String and returns a List of fields for simple execution.
     * @throws ParseException if the String contains duplicate or invalid fields.
     */
    public List<String> parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(NO_ARGUMENTS_USAGE);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        checkKeywords(nameKeywords);

        for (int i = 0; i < nameKeywords.length; i++) {
            nameKeywords[i] = nameKeywords[i].toUpperCase();
        }

        return (Arrays.asList(nameKeywords));
    }

    /**
     * Checks if the array of keywords are valid or not.
     * @param keywords the array of keywords.
     * @throws ParseException if there is an error found in the array.
     */
    public void checkKeywords(String[] keywords) throws ParseException {
        if (isWrongArgumentNumber(keywords)) {
            throw new ParseException(WRONG_ARGUMENT_NUMBER);
        } else if (areFieldsInvalid(keywords)) {
            if (hasWrongCaseField(keywords)) {
                throw new ParseException(WRONG_CASE_FIELD_USAGE);
            } else {
                throw new ParseException(INVALID_FIELD_USAGE);
            }
        } else if (areQuantifiersInvalid(keywords)) {
            if (hasWrongCaseQuantifier(keywords)) {
                throw new ParseException(WRONG_CASE_QUANTIFIER_USAGE);
            } else {
                throw new ParseException(INVALID_QUANTIFIER_USAGE);
            }
        } else if (areValuesInvalid(keywords)) {
            throw new ParseException(INVALID_VALUE_USAGE);
        } else if (areFieldsDuplicate(keywords)) {
            throw new ParseException(DUPLICATE_FIELD_USAGE);
        }
    }

    /**
     * Checks if the array of fields contain invalid values.
     * @param keywords the array of fields.
     * @return true if the fields do contain invalid fields. False if otherwise.
     */
    public boolean areFieldsInvalid(String[] keywords) {
        for (int i = 0; i < keywords.length; i = i + 3) {
            String field = keywords[i];
            if (!isValidField(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the array of fields has a valid field but not entirely in upper or lower case.
     * @param keywords the array of fields.
     * @return true if the fields do contain a field with invalid case. False if otherwise.
     */
    public boolean hasWrongCaseField(String[] keywords) {
        for (int i = 0; i < keywords.length; i = i + 3) {
            String field = keywords[i];
            if (isWrongCaseField(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the quantifiers are missing for certain fields.
     * @param keywords the array of fields.
     * @return true if quantifiers are missing, false if otherwise.
     */
    public boolean isWrongArgumentNumber(String[] keywords) {
        return keywords.length % 3 != 0;
    }

    /**
     * Checks if the array of fields contain invalid directions.
     * @param keywords the array of fields.
     * @return true if the fields do contain invalid directions. False if otherwise.
     */
    public boolean areQuantifiersInvalid(String[] keywords) {
        for (int i = 1; i < keywords.length; i = i + 3) {
            String field = keywords[i];
            if (!isValidQuantifier(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the array of fields has a valid direction but not entirely in upper or lower case.
     * @param keywords the array of fields.
     * @return true if the fields do contain a field with invalid direction. False if otherwise.
     */
    public boolean hasWrongCaseQuantifier(String[] keywords) {
        for (int i = 1; i < keywords.length; i = i + 3) {
            String field = keywords[i];
            if (isWrongCaseQuantifier(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the array of fields contain invalid values.
     * @param keywords the array of fields.
     * @return true if the fields do contain invalid values. False if otherwise.
     */
    public boolean areValuesInvalid(String[] keywords) {
        for (int i = 0; i < keywords.length; i = i + 3) {
            String field = keywords[i];
            String value = keywords[i + 2];
            if (!isValidValue(field, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the array of fields contain duplicate values.
     * @param keywords the array of fields.
     * @return true if the fields do contain duplicate fields. False if otherwise.
     */
    public boolean areFieldsDuplicate(String[] keywords) {
        Set<String> noDuplicateFields = new HashSet<String>();
        for (int i = 0; i < keywords.length; i += 3) {
            String field = keywords[i];
            if (!noDuplicateFields.add(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the field is valid or not.
     * @param field the field.
     * @return true if the field is valid. False if otherwise.
     */
    public boolean isValidField(String field) {
        return field.equals(FIELD_NAME_PRICE)
                || field.equals(FIELD_NAME_PRICE.toLowerCase());
    }

    /**
     * Checks if the field is entirely in upper or lower case.
     * @param field the field.
     * @return true if the field is not entirely in upper or lower case. False if otherwise.
     */
    public boolean isWrongCaseField(String field) {
        return field.toUpperCase().equals(FIELD_NAME_PRICE);
    }

    /**
     * Checks if the quantifier is valid or not.
     * @param quantifier the quantifier.
     * @return true if the quantifier is valid. False if otherwise.
     */
    public boolean isValidQuantifier(String quantifier) {
        return quantifier.equals(QUANTIFY_EQUALS_TO)
                || quantifier.equals(QUANTIFY_EQUALS_TO.toLowerCase())
                || quantifier.equals(QUANTIFY_LESS_THAN)
                || quantifier.equals(QUANTIFY_LESS_THAN.toLowerCase())
                || quantifier.equals(QUANTIFY_MORE_THAN)
                || quantifier.equals(QUANTIFY_MORE_THAN.toLowerCase());
    }

    /**
     * Checks if the direction is entirely in upper or lower case.
     * @param quantifier the quantifier.
     * @return true if the quantifier is not entirely in upper or lower case. False if otherwise.
     */
    public boolean isWrongCaseQuantifier(String quantifier) {
        return quantifier.toUpperCase().equals(QUANTIFY_EQUALS_TO)
                || quantifier.toUpperCase().equals(QUANTIFY_LESS_THAN)
                || quantifier.toUpperCase().equals(QUANTIFY_MORE_THAN);
    }

    /**
     * Checks if the value is valid or not.
     * @param field the field.
     * @param value the value.
     * @return true if the value is valid. False if otherwise.
     */
    public boolean isValidValue(String field, String value) {

        String correctedField = field.toUpperCase();

        switch(correctedField) {

        case FIELD_NAME_PRICE:
            return Price.isValidPrice(value);

        default:
            return false;
        }
    }
}
