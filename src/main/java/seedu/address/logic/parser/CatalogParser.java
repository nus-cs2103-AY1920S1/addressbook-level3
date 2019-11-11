package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditBorrowerCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InfoCommand;
import seedu.address.logic.commands.LoanCommand;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.RenewCommand;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.commands.ServeCommand;
import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.commands.ToggleUiCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnregisterCommand;
import seedu.address.logic.commands.ViewSettingsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CatalogParser {

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
    public Command parseCommand(String userInput) throws ParseException, CommandException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditBorrowerCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand(arguments);

        case LoanCommand.COMMAND_WORD:
            return new LoanCommandParser().parse(arguments);

        case InfoCommand.COMMAND_WORD:
            return new InfoCommandParser().parse(arguments);

        case RegisterCommand.COMMAND_WORD:
            return new RegisterCommandParser().parse(arguments);

        case UnregisterCommand.COMMAND_WORD:
            return new UnregisterCommandParser().parse(arguments);

        case ServeCommand.COMMAND_WORD:
            return new ServeCommandParser().parse(arguments);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommand(arguments);

        case ReturnCommand.COMMAND_WORD:
            return new ReturnCommandParser().parse(arguments);

        case RenewCommand.COMMAND_WORD:
            return new RenewCommandParser().parse(arguments);

        case SetCommand.COMMAND_WORD:
            if (arguments.equals("")) {
                return new ViewSettingsCommand();
            }
            return new SetCommandParser().parse(arguments);

        case PayCommand.COMMAND_WORD:
            return new PayCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);

        case ToggleUiCommand.COMMAND_WORD:
            return new ToggleUiCommand(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
