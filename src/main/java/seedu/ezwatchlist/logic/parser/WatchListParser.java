package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;

import seedu.ezwatchlist.logic.commands.AddCommand;
import seedu.ezwatchlist.logic.commands.ClearCommand;
import seedu.ezwatchlist.logic.commands.Command;
import seedu.ezwatchlist.logic.commands.DeleteCommand;
import seedu.ezwatchlist.logic.commands.EditCommand;
import seedu.ezwatchlist.logic.commands.ExitCommand;
import seedu.ezwatchlist.logic.commands.HelpCommand;
import seedu.ezwatchlist.logic.commands.SearchCommand;
import seedu.ezwatchlist.logic.commands.SyncCommand;
import seedu.ezwatchlist.logic.commands.WatchCommand;

import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class WatchListParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param currentTab
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */

    public Command parseCommand(String userInput, String currentTab) throws ParseException, OnlineConnectionException {
        if (shortCutKey(userInput)) {
            return new GoToParser().parse(userInput, currentTab);
        }

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, currentTab);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments, currentTab);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments, currentTab);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case WatchCommand.COMMAND_WORD:
            return new WatchCommandParser().parse(arguments, currentTab);

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments, currentTab);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SyncCommand.COMMAND_WORD:
            return new SyncCommandParser().parse(arguments, currentTab);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Check if user input refers to a short cut key
     * @param userInput
     * @return check if is short cut key
     */
    private boolean shortCutKey(String userInput) {
        try {
            int shortCutKey = Integer.parseInt(userInput);
            boolean isShortCutKey = shortCutKey == 1 || shortCutKey == 2 || shortCutKey == 3 || shortCutKey == 4;
            return isShortCutKey;
        } catch (NumberFormatException e) {
            String userInputLowerCase = userInput.toLowerCase();
            boolean stringShortCutKey = userInputLowerCase.equals("watchlist")
                    || userInputLowerCase.equals("watched")
                    || userInputLowerCase.equals("search")
                    || userInputLowerCase.equals("statistics");
            return stringShortCutKey;
        }
    }

}
