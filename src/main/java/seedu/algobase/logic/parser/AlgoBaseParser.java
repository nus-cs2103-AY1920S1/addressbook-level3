package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.algobase.logic.commands.AddCommand;
import seedu.algobase.logic.commands.AddTaskCommand;
import seedu.algobase.logic.commands.AddPlanCommand;
import seedu.algobase.logic.commands.ClearCommand;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.DeleteCommand;
import seedu.algobase.logic.commands.DeletePlanCommand;
import seedu.algobase.logic.commands.EditCommand;
import seedu.algobase.logic.commands.EditPlanCommand;
import seedu.algobase.logic.commands.ExitCommand;
import seedu.algobase.logic.commands.FindCommand;
import seedu.algobase.logic.commands.FindPlanCommand;
import seedu.algobase.logic.commands.HelpCommand;
import seedu.algobase.logic.commands.ListCommand;
import seedu.algobase.logic.commands.SortCommand;
import seedu.algobase.logic.commands.ListPlanCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AlgoBaseParser {

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
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddPlanCommand.COMMAND_WORD:
            return new AddPlanCommandParser().parse(arguments);

        case EditPlanCommand.COMMAND_WORD:
            return new EditPlanCommandParser().parse(arguments);

        case DeletePlanCommand.COMMAND_WORD:
            return new DeletePlanCommandParser().parse(arguments);

        case FindPlanCommand.COMMAND_WORD:
            return new FindPlanCommandParser().parse(arguments);

        case ListPlanCommand.COMMAND_WORD:
            return new ListPlanCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
