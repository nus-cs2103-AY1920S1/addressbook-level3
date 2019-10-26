package thrift.logic.parser;

import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.AddIncomeCommand;
import thrift.logic.commands.BudgetCommand;
import thrift.logic.commands.CloneCommand;
import thrift.logic.commands.Command;
import thrift.logic.commands.ConvertCommand;
import thrift.logic.commands.DeleteCommand;
import thrift.logic.commands.ExitCommand;
import thrift.logic.commands.FindCommand;
import thrift.logic.commands.HelpCommand;
import thrift.logic.commands.ListCommand;
import thrift.logic.commands.RedoCommand;
import thrift.logic.commands.TagCommand;
import thrift.logic.commands.UndoCommand;
import thrift.logic.commands.UntagCommand;
import thrift.logic.commands.UpdateCommand;
import thrift.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ThriftParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public String getArguments(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String arguments = matcher.group("arguments");
        return arguments;
    }

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

        /*
         * Adding transactions or budget commands.
         */
        case AddExpenseCommand.COMMAND_WORD:
            return new AddExpenseCommandParser().parse(arguments);

        case AddIncomeCommand.COMMAND_WORD:
            return new AddIncomeCommandParser().parse(arguments);

        case BudgetCommand.COMMAND_WORD:
            return new BudgetCommandParser().parse(arguments);

        case CloneCommand.COMMAND_WORD:
            return new CloneCommandParser().parse(arguments);

        /*
         * Deleting transactions command.
         */
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        /*
         * Altering an existing transaction commands.
         */
        case UpdateCommand.COMMAND_WORD:
            return new UpdateCommandParser().parse(arguments);

        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);

        case UntagCommand.COMMAND_WORD:
            return new UntagCommandParser().parse(arguments);

        /*
         * Filtered transaction list manipulation commands.
         */
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        /*
         * Info related commands.
         */
        case ConvertCommand.COMMAND_WORD:
            return new ConvertCommandParser().parse(arguments);

        /*
         * System related commands.
         */
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
