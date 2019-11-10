package seedu.savenus.logic.parser;

import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_RESTRICTIONS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.savenus.logic.parser.exceptions.ParseException;

//@@author seanlowjk
/**
 * Parses and Checks validity of fields.
 */
public class FieldParser {
    public static final String DUPLICATE_FIELD_USAGE = "Note you have entered a duplicate field.";
    public static final String MISSING_DIRECTION_USAGE = "Note that you need to have a direction for each field.";
    public static final String INVALID_DIRECTION_USAGE = "Note you have entered an invalid direction:\n"
            + "Directions can only be ASC or DESC";
    public static final String INVALID_FIELD_USAGE = "Note you have entered an invalid field:\n";
    public static final String WRONG_CASE_FIELD_USAGE = "Note that your fields must entirely be in"
            + " upper or lower case!";
    public static final String WRONG_CASE_DIRECTION_USAGE = "Note that your directions must entirely be in"
            + " upper or lower case!";


    /**
     * Parses the given String and returns a List of fields for simple execution.
     * @throws ParseException if the String contains duplicate or invalid fields.
     */
    public List<String> parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ArrayList<String>();
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        checkKeywords(nameKeywords);

        for (int i = 0; i < nameKeywords.length; i++) {
            nameKeywords[i] = nameKeywords[i].toUpperCase();
        }

        return (Arrays.asList(nameKeywords));
    }

    /**
     * Checks if the keywords are valid or not.
     * @param keywords array of fields.
     * @throws ParseException if the keywords contain wrong values.
     */
    public void checkKeywords(String[] keywords) throws ParseException {
        if (areFieldsInvalid(keywords)) {
            if (hasWrongCaseFields(keywords)) {
                throw new ParseException(WRONG_CASE_FIELD_USAGE);
            } else {
                throw new ParseException(INVALID_FIELD_USAGE);
            }
        } else if (areFieldsDuplicate(keywords)) {
            throw new ParseException(DUPLICATE_FIELD_USAGE);
        } else if (areDirectionsMissing(keywords)) {
            throw new ParseException(MISSING_DIRECTION_USAGE);
        } else if (areDirectionsInaccurate(keywords)) {
            if (hasWrongCaseDirections(keywords)) {
                throw new ParseException(WRONG_CASE_DIRECTION_USAGE);
            } else {
                throw new ParseException(INVALID_DIRECTION_USAGE);
            }
        }
    }

    /**
     * Checks if the keywords are valid or not.
     * @param keywords list of keywords
     */
    public void checkKeywords(List<String> keywords) throws ParseException {
        checkKeywords(keywords.toArray(new String[keywords.size()]));
    }

    /**
     * Checks if the array of fields contain invalid values.
     * @param keywords the array of fields.
     * @return true if the fields do contain invalid fields. False if otherwise.
     */
    public boolean areFieldsInvalid(String[] keywords) {
        for (int i = 0; i < keywords.length; i = i + 2) {
            String field = keywords[i];
            if (!isValidField(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the array of fields have some fields that are not entirely in upper or lower case.
     * @param keywords the array of fields.
     * @return true if some fields are not entirely in upper or lower case. False if otherwise.
     */
    public boolean hasWrongCaseFields(String[] keywords) {
        for (int i = 0; i < keywords.length; i = i + 2) {
            String field = keywords[i];
            if (isWrongCaseField(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the directions are missing for certain fields.
     * @param keywords the array of fields.
     * @return true if directions are missing, false if otherwise.
     */
    public boolean areDirectionsMissing(String[] keywords) {
        return keywords.length % 2 != 0;
    }

    /**
     * Checks if the array of fields contain invalid directions.
     * @param keywords the array of fields.
     * @return true if the fields do contain invalid directions. False if otherwise.
     */
    public boolean areDirectionsInaccurate(String[] keywords) {
        for (int i = 1; i < keywords.length; i = i + 2) {
            String field = keywords[i];
            if (!isAscOrDesc(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the array of fields contain directions not entirely in upper or lower case.
     * @param keywords the array of fields.
     * @return true if some directions are not entirely in upper or lower case. False if otherwise.
     */
    public boolean hasWrongCaseDirections(String[] keywords) {
        for (int i = 1; i < keywords.length; i = i + 2) {
            String field = keywords[i];
            if (isWrongCaseDirection(field)) {
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
        for (int i = 0; i < keywords.length; i += 2) {
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
        return field.equals(FIELD_NAME_CATEGORY)
                || field.equals(FIELD_NAME_CATEGORY.toLowerCase())
                || field.equals(FIELD_NAME_DESCRIPTION)
                || field.equals(FIELD_NAME_DESCRIPTION.toLowerCase())
                || field.equals(FIELD_NAME_LOCATION)
                || field.equals(FIELD_NAME_LOCATION.toLowerCase())
                || field.equals(FIELD_NAME_NAME)
                || field.equals(FIELD_NAME_NAME.toLowerCase())
                || field.equals(FIELD_NAME_OPENING_HOURS)
                || field.equals(FIELD_NAME_OPENING_HOURS.toLowerCase())
                || field.equals(FIELD_NAME_PRICE)
                || field.equals(FIELD_NAME_PRICE.toLowerCase())
                || field.equals(FIELD_NAME_RESTRICTIONS)
                || field.equals(FIELD_NAME_RESTRICTIONS.toLowerCase());
    }

    /**
     * Checks if the field is entirely in upper or lower case or not.
     * @param field the field.
     * @return true if the field is not entirely in upper or lower case, false if otherwise.
     */
    public boolean isWrongCaseField(String field) {
        return field.toUpperCase().equals(FIELD_NAME_CATEGORY)
                || field.toUpperCase().equals(FIELD_NAME_DESCRIPTION)
                || field.toUpperCase().equals(FIELD_NAME_LOCATION)
                || field.toUpperCase().equals(FIELD_NAME_NAME)
                || field.toUpperCase().equals(FIELD_NAME_OPENING_HOURS)
                || field.toUpperCase().equals(FIELD_NAME_PRICE)
                || field.toUpperCase().equals(FIELD_NAME_RESTRICTIONS);
    }

    /**
     * Checks if the field represents ascending or descending.
     * @param direction the field.
     * @return true if the field is ascending or descending. False if otherwise.
     */
    public boolean isAscOrDesc(String direction) {
        return direction.equals("ASC")
                || direction.equals("asc")
                || direction.equals("DESC")
                || direction.equals("desc");
    }

    /**
     * Checks if the direction is entirely in upper or lower case or not.
     * @param direction the direction.
     * @return true if the direction is not entirely in upper or lower case, false if otherwise.
     */
    public boolean isWrongCaseDirection(String direction) {
        return direction.toUpperCase().equals("ASC")
                || direction.toUpperCase().equals("DESC");
    }
}
