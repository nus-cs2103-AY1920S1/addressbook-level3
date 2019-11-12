package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static io.xpire.commons.core.Messages.MESSAGE_XPIRE_COMMAND_ONLY;
import static io.xpire.model.ListType.REPLENISH;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import io.xpire.commons.util.StringUtil;
import io.xpire.logic.commands.AddCommand;
import io.xpire.logic.commands.CheckCommand;
import io.xpire.logic.commands.ClearCommand;
import io.xpire.logic.commands.Command;
import io.xpire.logic.commands.DeleteCommand;
import io.xpire.logic.commands.ExitCommand;
import io.xpire.logic.commands.ExportCommand;
import io.xpire.logic.commands.HelpCommand;
import io.xpire.logic.commands.RedoCommand;
import io.xpire.logic.commands.SearchCommand;
import io.xpire.logic.commands.SetReminderCommand;
import io.xpire.logic.commands.ShiftToMainCommand;
import io.xpire.logic.commands.SortCommand;
import io.xpire.logic.commands.TagCommand;
import io.xpire.logic.commands.UndoCommand;
import io.xpire.logic.commands.ViewCommand;
import io.xpire.logic.parser.exceptions.ParseException;

//@@author febee99
/**
 * Parses user input for the {@code ReplenishList}.
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
            //fallthrough
        case ClearCommand.COMMAND_SHORTHAND:
            return new ClearCommand(REPLENISH);

        case SearchCommand.COMMAND_WORD:
            //fallthrough
        case SearchCommand.COMMAND_SHORTHAND:
            return new SearchCommandParser(REPLENISH).parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            //fallthrough
        case DeleteCommand.COMMAND_SHORTHAND:
            return new DeleteCommandParser(REPLENISH).parse(arguments);

        case TagCommand.COMMAND_WORD:
            //fallthrough
        case TagCommand.COMMAND_SHORTHAND:
            return new TagCommandParser(REPLENISH).parse(arguments);

        case ViewCommand.COMMAND_WORD:
            //fallthrough
        case ViewCommand.COMMAND_SHORTHAND:
            return new ViewCommandParser(REPLENISH).parse(arguments);

        case ExitCommand.COMMAND_WORD:
            //fallthrough
        case ExitCommand.COMMAND_SHORTHAND:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            //fallthrough
        case HelpCommand.COMMAND_SHORTHAND:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            //fallthrough
        case UndoCommand.COMMAND_SHORTHAND:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            //fallthrough
        case RedoCommand.COMMAND_SHORTHAND:
            return new RedoCommand();

        case ShiftToMainCommand.COMMAND_WORD:
            //fallthrough
        case ShiftToMainCommand.COMMAND_SHORTHAND:
            return new ShiftToMainCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            //fallthrough
        case ExportCommand.COMMAND_SHORTHAND:
            return new ExportCommand();

        case AddCommand.COMMAND_WORD:
            //fallthrough
        case AddCommand.COMMAND_SHORTHAND:
            //fallthrough
        case SortCommand.COMMAND_WORD:
            //fallthrough
        case SortCommand.COMMAND_SHORTHAND:
            //fallthrough
        case CheckCommand.COMMAND_WORD:
            //fallthrough
        case CheckCommand.COMMAND_SHORTHAND:
            //fallthrough
        case SetReminderCommand.COMMAND_WORD:
            //fallthrough
        case SetReminderCommand.COMMAND_SHORTHAND:

            throw new ParseException(MESSAGE_XPIRE_COMMAND_ONLY);


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
            HelpCommand.COMMAND_WORD, ShiftToMainCommand.COMMAND_WORD,
            ExportCommand.COMMAND_WORD, TagCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD
        };
        Set<String> allCommandsSet = new TreeSet<>(Arrays.asList(allCommandWords));
        sb.append(StringUtil.findSimilar(command, allCommandsSet, 1));
        throw new ParseException(sb.toString());
    }
}
