package seedu.algobase.ui.action.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION_PROPERTY;

import java.time.LocalDate;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;

/**
 * Contains utility methods used for parsing fields of {@code UiActionDetails} the various *Parser classes.
 */
public class UiParserUtil {
    /**
     * Converts an id of type {@Object} into an id of type {@Id}
     *
     * @throws ParseException if given object is not of type {@Id}
     */
    public static Id parseId(Object id) throws ParseException {
        if (!(id instanceof Id)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (Id) id;
    }

    /**
     * Converts an index of type {@Object} into an id of type {@Index}
     *
     * @throws ParseException if given object is not of type {@Index}
     */
    public static Index parseIndex(Object index) throws ParseException {
        if (!(index instanceof Index)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (Index) index;
    }

    /**
     * Converts a modelType of type {@Object} into a modelType of type {@ModelType}
     *
     * @throws ParseException if given object is not of type {@ModelType}
     */
    public static ModelType parseModelType(Object modelType) throws ParseException {
        if (!(modelType instanceof ModelType)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (ModelType) modelType;
    }

    /**
     * Converts a string of type {@Object} into an string of type {@String}
     *
     * @throws ParseException if given object is not of type {@String}
     */
    public static String parseString(Object string) throws ParseException {
        if (!(string instanceof String)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return ((String) string).trim();
    }

    /**
     * Converts a boolean of type {@Object} into a boolean of type {@Boolean}
     *
     * @throws ParseException if given object is not of type {@Boolean}
     */
    public static Boolean parseBoolean(Object bool) throws ParseException {
        if (!(bool instanceof Boolean)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (Boolean) bool;
    }

    /**
     * Converts an date of type {@Object} into a date of type {@LocalDate}
     *
     * @throws ParseException if given object is not of type {@LocalDate}
     */
    public static LocalDate parseDate(Object date) throws ParseException {
        if (!(date instanceof LocalDate)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (LocalDate) date;
    }
}
