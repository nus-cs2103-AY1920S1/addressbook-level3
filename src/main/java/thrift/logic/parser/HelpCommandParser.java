package thrift.logic.parser;

import static java.util.Objects.requireNonNull;

import thrift.commons.core.Messages;
import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.AddIncomeCommand;
import thrift.logic.commands.BudgetCommand;
import thrift.logic.commands.CloneCommand;
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
 * Parses user input and creates a new HelpCommand object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    public static final String EMPTY_STRING = "";

    @Override
    public HelpCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        switch (userInput.trim()) {
        case AddExpenseCommand.COMMAND_WORD:
            return new HelpCommand(AddExpenseCommand.MESSAGE_USAGE);
        case AddIncomeCommand.COMMAND_WORD:
            return new HelpCommand(AddIncomeCommand.MESSAGE_USAGE);
        case BudgetCommand.COMMAND_WORD:
            return new HelpCommand(BudgetCommand.MESSAGE_USAGE);
        case CloneCommand.COMMAND_WORD:
            return new HelpCommand(CloneCommand.MESSAGE_USAGE);
        case DeleteCommand.COMMAND_WORD:
            return new HelpCommand(DeleteCommand.MESSAGE_USAGE);
        case ExitCommand.COMMAND_WORD:
            return new HelpCommand(ExitCommand.MESSAGE_USAGE);
        case FindCommand.COMMAND_WORD:
            return new HelpCommand(FindCommand.MESSAGE_USAGE);
        case ListCommand.COMMAND_WORD:
            return new HelpCommand(ListCommand.MESSAGE_USAGE);
        case RedoCommand.COMMAND_WORD:
            return new HelpCommand(RedoCommand.MESSAGE_USAGE);
        case TagCommand.COMMAND_WORD:
            return new HelpCommand(TagCommand.MESSAGE_USAGE);
        case UndoCommand.COMMAND_WORD:
            return new HelpCommand(UndoCommand.MESSAGE_USAGE);
        case UntagCommand.COMMAND_WORD:
            return new HelpCommand(UntagCommand.MESSAGE_USAGE);
        case UpdateCommand.COMMAND_WORD:
            return new HelpCommand(UpdateCommand.MESSAGE_USAGE);
        case EMPTY_STRING:
            return new HelpCommand(EMPTY_STRING);
        default:
            throw new ParseException(String.format(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE)));
        }
    }
}
