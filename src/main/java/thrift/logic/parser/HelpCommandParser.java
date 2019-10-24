package thrift.logic.parser;

import static java.util.Objects.requireNonNull;

import thrift.commons.core.Messages;
import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.AddIncomeCommand;
import thrift.logic.commands.BudgetCommand;
import thrift.logic.commands.CloneCommand;
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
 * Parses user input and creates a new HelpCommand object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    public static final String EMPTY_STRING = "";

    @Override
    public HelpCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        switch (userInput.trim()) {
        case AddExpenseCommand.COMMAND_WORD:
            return new HelpCommand(AddExpenseCommand.HELP_MESSAGE);
        case AddIncomeCommand.COMMAND_WORD:
            return new HelpCommand(AddIncomeCommand.HELP_MESSAGE);
        case BudgetCommand.COMMAND_WORD:
            return new HelpCommand(BudgetCommand.HELP_MESSAGE);
        case CloneCommand.COMMAND_WORD:
            return new HelpCommand(CloneCommand.HELP_MESSAGE);
        case ConvertCommand.COMMAND_WORD:
            return new HelpCommand(ConvertCommand.HELP_MESSAGE);
        case DeleteCommand.COMMAND_WORD:
            return new HelpCommand(DeleteCommand.HELP_MESSAGE);
        case ExitCommand.COMMAND_WORD:
            return new HelpCommand(ExitCommand.HELP_MESSAGE);
        case FindCommand.COMMAND_WORD:
            return new HelpCommand(FindCommand.HELP_MESSAGE);
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand(HelpCommand.HELP_MESSAGE);
        case ListCommand.COMMAND_WORD:
            return new HelpCommand(ListCommand.HELP_MESSAGE);
        case RedoCommand.COMMAND_WORD:
            return new HelpCommand(RedoCommand.HELP_MESSAGE);
        case TagCommand.COMMAND_WORD:
            return new HelpCommand(TagCommand.HELP_MESSAGE);
        case UndoCommand.COMMAND_WORD:
            return new HelpCommand(UndoCommand.HELP_MESSAGE);
        case UntagCommand.COMMAND_WORD:
            return new HelpCommand(UntagCommand.HELP_MESSAGE);
        case UpdateCommand.COMMAND_WORD:
            return new HelpCommand(UpdateCommand.HELP_MESSAGE);
        case EMPTY_STRING:
            return new HelpCommand(EMPTY_STRING);
        default:
            throw new ParseException(String.format(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE)));
        }
    }
}
