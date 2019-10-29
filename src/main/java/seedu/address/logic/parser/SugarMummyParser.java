package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_ARGUMENTS_MUST_BE_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddFoodCommand;
import seedu.address.logic.commands.AverageCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RecmFoodCommand;
import seedu.address.logic.commands.achvm.AchvmCommand;
import seedu.address.logic.commands.aesthetics.BackgroundCommand;
import seedu.address.logic.commands.aesthetics.FontColourCommand;
import seedu.address.logic.commands.bio.AddBioCommand;
import seedu.address.logic.commands.bio.BioCommand;
import seedu.address.logic.commands.bio.ClearBioCommand;
import seedu.address.logic.commands.bio.EditBioCommand;
import seedu.address.logic.commands.calendar.CalendarCommand;
import seedu.address.logic.commands.calendar.EventCommand;
import seedu.address.logic.commands.calendar.ReminderCommand;
import seedu.address.logic.parser.aesthetics.BackgroundCommandParser;
import seedu.address.logic.parser.aesthetics.FontColourCommandParser;
import seedu.address.logic.parser.bio.AddBioCommandParser;
import seedu.address.logic.parser.bio.EditBioCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import sugarmummy.recmfood.exception.FoodNotSuitableException;
import sugarmummy.recmfood.parser.AddFoodCommandParser;
import sugarmummy.recmfood.parser.RecmFoodCommandParser;

/**
 * Parses user input.
 */
public class SugarMummyParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, FoodNotSuitableException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord.toLowerCase()) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new HelpCommand();

        case AverageCommand.COMMAND_WORD:
            return new AverageCommandParser().parse(arguments);

        case RecmFoodCommand.COMMAND_WORD:
            return new RecmFoodCommandParser().parse(arguments);

        case AddFoodCommand.COMMAND_WORD:
            return new AddFoodCommandParser().parse(arguments);

        //=========== Calendar ===========================================================

        case EventCommand.COMMAND_WORD:
            return new EventCommandParser().parse(arguments);

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments);

        case CalendarCommand.COMMAND_WORD:
            return new CalendarCommandParser().parse(arguments);

        //=========== User List =============================================================

        case BioCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new BioCommand();

        case AddBioCommand.COMMAND_WORD:
            return new AddBioCommandParser().parse(arguments);

        case EditBioCommand.COMMAND_WORD:
            return new EditBioCommandParser().parse(arguments);

        case ClearBioCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new ClearBioCommand();

        //=========== Achievements =============================================================

        case AchvmCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new AchvmCommand();

        //=========== Aesthetics =============================================================

        case FontColourCommand.COMMAND_WORD:
            return new FontColourCommandParser().parse(arguments);

        case BackgroundCommand.COMMAND_WORD:
            return new BackgroundCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Ensures commands not meant to have trailing arguments do not have any. Trailing spaces following commands are
     * fine and would have been trimmed off automatically by the program.
     *
     * @param arguments Argument inputs keyed in by the user following the command.
     * @throws ParseException If there are additional arguments after the command.
     */
    private void requireEmptyArguments(String arguments) throws ParseException {
        if (!arguments.isEmpty()) {
            throw new ParseException(MESSAGE_ARGUMENTS_MUST_BE_EMPTY);
        }
    }

}
