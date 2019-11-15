package seedu.address.calendar.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.calendar.logic.commands.AddCommand;
import seedu.address.calendar.logic.commands.DeleteCommand;
import seedu.address.calendar.model.Calendar;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses alternative commands.
 */
public class AlternativeCalendarParser extends Parser {
    private static final String VALID_COMMAND_YES = "yes";
    private static final String VALID_COMMAND_NO = "no";

    @Override
    protected Command<Calendar> parseCommand(String commandWord, String arguments) throws ParseException {
        switch(commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AlternativeAddCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD:
            return new AlternativeDeleteCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses the user input.
     *
     * @return The option that the user has chosen
     */
    public Option parseOptionCommand(String commandText) {
        String trimmed = commandText.trim();

        if (isYes(trimmed)) {
            return Option.getTrue();
        }

        if (isNo(trimmed)) {
            return Option.getFalse();
        }

        return Option.getInstance(textToInt(trimmed));
    }

    /**
     * Converts text to an integer.
     *
     * @param commandText The text to be converted
     * @return The converted integer
     */
    private int textToInt(String commandText) {
        return Integer.parseInt(commandText.trim());
    }

    /**
     * Checks if the user input is an alternative command.
     *
     * @param commandWord The user input
     * @return {@code true} if the user inputs an alternative command
     */
    public static boolean isAlternativeCommand(String commandWord) {
        return isValidCommand(commandWord);
    }

    /**
     * Checks if the command given is valid.
     *
     * @param commandWord The command given by the user
     * @return {@code true} if the command given is valid
     */
    private static boolean isValidCommand(String commandWord) {
        String commandWordTrimmed = commandWord.trim();

        boolean isYes = isYes(commandWordTrimmed);
        boolean isNo = isNo(commandWordTrimmed);
        boolean isInteger = isInteger(commandWordTrimmed);

        return isYes || isNo || isInteger;
    }

    private static boolean isYes(String commandWord) {
        return commandWord.equalsIgnoreCase(VALID_COMMAND_YES);
    }

    private static boolean isNo(String commandWord) {
        return commandWord.equalsIgnoreCase(VALID_COMMAND_NO);
    }

    /**
     * Checks if the user input represents an {@code int}.
     *
     * @param commandWord The user input
     * @return {@code true} if the user input represents an {@code int}
     */
    private static boolean isInteger(String commandWord) {
        boolean isInteger;
        try {
            Integer.parseInt(commandWord);
            isInteger = true;
        } catch (NumberFormatException e) {
            isInteger = false;
        }
        return isInteger;
    }
}
