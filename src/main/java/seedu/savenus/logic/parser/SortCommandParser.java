package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_RESTRICTIONS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.savenus.logic.commands.SortCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given String and returns a SortCommand object for simple execution.
     * @throws ParseException if the String contains duplicate or invalid fields.
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.NO_ARGUMENTS_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        if (areFieldsInvalid(nameKeywords)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.INVALID_FIELD_USAGE));
        } else if (areFieldsDuplicate(nameKeywords)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.DUPLICATE_FIELD_USAGE));
        } else if (areDirectionsMissing(nameKeywords)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MISSING_DIRECTION_USAGE));
        } else if (areDirectionsInaccurate(nameKeywords)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.INVALID_DIRECTION_USAGE));
        }

        return new SortCommand((Arrays.asList(nameKeywords)));
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
                || field.equals(FIELD_NAME_DESCRIPTION)
                || field.equals(FIELD_NAME_LOCATION)
                || field.equals(FIELD_NAME_NAME)
                || field.equals(FIELD_NAME_OPENING_HOURS)
                || field.equals(FIELD_NAME_PRICE)
                || field.equals(FIELD_NAME_RESTRICTIONS);
    }

    /**
     * Checks if the field represents ascending or descending.
     * @param direction the field.
     * @return true if the field is ascending or descending. False if otherwise.
     */
    public boolean isAscOrDesc(String direction) {
        return direction.equals("ASC") || direction.equals("DESC");
    }
}
