package seedu.planner.logic.autocomplete;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import seedu.planner.logic.autocomplete.exceptions.CommandWordNotFoundException;
import seedu.planner.logic.autocomplete.exceptions.PreambleNotFoundException;
import seedu.planner.logic.autocomplete.exceptions.WordNotFoundException;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.OptimiseCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.SetCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.logic.commands.addcommand.AddCommand;
import seedu.planner.logic.commands.deletecommand.DeleteCommand;
import seedu.planner.logic.commands.editcommand.EditCommand;
import seedu.planner.logic.commands.listcommand.ListCommand;
import seedu.planner.logic.commands.schedulecommand.AutoScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.ScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.UnscheduleCommand;
import seedu.planner.logic.commands.viewcommand.ViewCommand;
import seedu.planner.logic.parser.Prefix;

/**
 * Parses text in CommandBox into parts of a command input.
 * @author 1nefootstep
 */
public class AutoCompleteParser {
    private final List<Prefix> listOfUsablePrefix = Arrays.asList(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_DAY,
            PREFIX_START_DATE, PREFIX_START_TIME, PREFIX_DURATION, PREFIX_PRIORITY, PREFIX_COST
    );

    /**
     * Parses the command word of a command input.
     * @param input the text in the command box
     * @return the command word
     * @throws CommandWordNotFoundException
     */
    public String parseCommandWord(String input) throws CommandWordNotFoundException {
        try {
            String firstWord = getWord(input, 0);
            if (isOneWordCommand(firstWord)) {
                return firstWord;
            }
            if (isTwoWordCommand(firstWord)) {
                String secondWord = getWord(input, firstWord.length() + 1);
                return firstWord + " " + secondWord;
            }
        } catch (WordNotFoundException e) {
            throw new CommandWordNotFoundException();
        }
        // the command word inputted is not recognised
        throw new CommandWordNotFoundException();
    }

    /**
     * Parses the preamble of a command input.
     * @param input the text in the command box
     * @param commandWord the command word
     * @return the preamble of the command
     */
    public String parsePreamble(String input, String commandWord) {
        String afterCommand = input.substring(commandWord.length() + 1);
        String[] splitAfterCommand = afterCommand.split(" ");
        if (splitAfterCommand[0].contains("/")) {
            throw new PreambleNotFoundException();
        } else {
            return splitAfterCommand[0];
        }
    }

    /**
     * Parses the prefixes of a command input.
     * @param input the text in the command box
     * @return a list of prefixes present in the input
     */
    public List<Prefix> parsePrefix(String input) {
        List<Prefix> listOfPrefixPresent = new ArrayList<>();
        for (Prefix p : listOfUsablePrefix) {
            if (containsPrefix(input, p)) {
                listOfPrefixPresent.add(p);
            }
        }
        return listOfPrefixPresent;
    }

    private boolean containsPrefix(String input, Prefix p) {
        return Pattern.compile("\\b" + p.toString()).matcher(input).find();
    }

    /**
     * Gets the first word starting from the index {@code from}.
     */
    private String getWord(String input, int from) throws WordNotFoundException {
        int whitespaceIndex = input.indexOf(' ', from);
        if (whitespaceIndex == -1) {
            throw new WordNotFoundException();
        }
        return input.substring(from, whitespaceIndex);
    }

    /**
     * Checks if the word is part of a two word command.
     */
    private boolean isTwoWordCommand(String commandWord) {
        switch(commandWord) {
        case AddCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
        case ViewCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks if the word is part of a one word command.
     */
    private boolean isOneWordCommand(String commandWord) {
        switch(commandWord) {
        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
        case ScheduleCommand.COMMAND_WORD:
        case AutoScheduleCommand.COMMAND_WORD:
        case UnscheduleCommand.COMMAND_WORD:
        case SetCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_WORD:
        case OptimiseCommand.COMMAND_WORD:
            return true;
        default:
            return false;
        }
    }
}
