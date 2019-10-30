package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import io.xpire.commons.util.StringUtil;
import io.xpire.logic.commands.ClearCommand;
import io.xpire.logic.commands.Command;
import io.xpire.logic.commands.ExitCommand;
import io.xpire.logic.commands.HelpCommand;
import io.xpire.logic.commands.SearchCommand;
import io.xpire.logic.commands.ShiftToMainCommand;
import io.xpire.logic.commands.ViewCommand;
import io.xpire.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ReplenishParser implements Parser {

    /**
     * Parses user input into command for execution for the replenish list.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String userInput) throws ParseException {
        // Removes leading and trailing white spaces and trailing bars.
        String trimmedUserInput = userInput.trim()
                                           .replaceAll("\\|+$", "");

        String commandWord = trimmedUserInput.split("\\|", 2)[0].trim();
        if (commandWord.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        String arguments;
        try {
            arguments = trimmedUserInput.split("\\|", 2)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            arguments = "";
        }

        switch (commandWord) {

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand("replenish");

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ShiftToMainCommand.COMMAND_WORD:
            return new ShiftToMainCommandParser().parse(arguments);

        default:
            return parseUnknownCommandWord(commandWord);
        }
    }

    /**
     * Parses invalid command words to check if there were any possible input mistakes.
     *
     * @param command the invalid command word
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private static Command parseUnknownCommandWord(String command) throws ParseException {

        StringBuilder sb = new StringBuilder(MESSAGE_UNKNOWN_COMMAND);
        String[] allCommandWords = new String[]{
            ClearCommand.COMMAND_WORD, SearchCommand.COMMAND_WORD,
            ViewCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD, ShiftToMainCommand.COMMAND_WORD
        };
        Set<String> allCommandsSet = new TreeSet<>(Arrays.asList(allCommandWords));
        sb.append(StringUtil.findSimilar(command, allCommandsSet, 1));
        throw new ParseException(sb.toString());
    }
}
