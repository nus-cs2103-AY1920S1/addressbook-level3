package seedu.planner.logic.commands.autocomplete;

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

import seedu.planner.logic.commands.AddCommand;
import seedu.planner.logic.commands.AutoScheduleCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.EditCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.InitCommand;
import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.commands.OptimiseCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.ScheduleCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.logic.commands.UnscheduleCommand;
import seedu.planner.logic.commands.ViewCommand;
import seedu.planner.logic.commands.autocomplete.exceptions.CommandWordNotFoundException;
import seedu.planner.logic.commands.autocomplete.exceptions.PreambleNotFoundException;
import seedu.planner.logic.commands.autocomplete.exceptions.WordNotFoundException;
import seedu.planner.logic.parser.Prefix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class AutoCompleteParser {
    private final List<Prefix> listOfUsablePrefix = Arrays.asList(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_DAY,
            PREFIX_START_DATE, PREFIX_START_TIME, PREFIX_DURATION, PREFIX_PRIORITY, PREFIX_COST
    );

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

    public String parsePreamble(String input, String commandWord) {
        String afterCommand = input.substring(commandWord.length() + 1);
        String[] splitAfterCommand = afterCommand.split(" ");
        if (splitAfterCommand[0].contains("/")) {
            throw new PreambleNotFoundException();
        } else {
            return splitAfterCommand[0];
        }
    }

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

    private String getWord(String input, int from) throws WordNotFoundException {
        int whitespaceIndex = input.indexOf(' ', from);
        if (whitespaceIndex == -1) {
            throw new WordNotFoundException();
        }
        return input.substring(from, whitespaceIndex);
    }

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

    private boolean isOneWordCommand(String commandWord) {
        switch(commandWord) {
        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
        case ScheduleCommand.COMMAND_WORD:
        case AutoScheduleCommand.COMMAND_WORD:
        case UnscheduleCommand.COMMAND_WORD:
        case InitCommand.COMMAND_WORD:
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
