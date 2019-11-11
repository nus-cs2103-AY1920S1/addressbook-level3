package mams.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mams.commons.core.Messages;
import mams.logic.commands.AddModCommand;
import mams.logic.commands.ClashCommand;
import mams.logic.commands.Command;
import mams.logic.commands.ExitCommand;
import mams.logic.commands.FindCommand;
import mams.logic.commands.HelpCommand;
import mams.logic.commands.HistoryCommand;
import mams.logic.commands.ListCommand;
import mams.logic.commands.RedoCommand;
import mams.logic.commands.RemoveModCommand;
import mams.logic.commands.ResolveCommand;
import mams.logic.commands.RestoreCommand;
import mams.logic.commands.SaveCommand;
import mams.logic.commands.SetCredits;
import mams.logic.commands.StoreCommand;
import mams.logic.commands.UndoCommand;
import mams.logic.commands.ViewCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class MamsParser {

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
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case ClashCommand.COMMAND_WORD:
            return new ClashCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ResolveCommand.COMMAND_WORD_APPROVE_APPEAL:
            return new ApproveCommandParser().parse(arguments);

        case ResolveCommand.COMMAND_WORD_REJECT_APPEAL:
            return new RejectCommandParser().parse(arguments);

        case AddModCommand.COMMAND_WORD_ADD_MOD:
            return new AddModCommandParser().parse(arguments);

        case RemoveModCommand.COMMAND_WORD_REMOVE_MOD:
            return new RemoveModCommandParser().parse(arguments);

        case SetCredits.COMMAND_WORD_SET_CREDITS:
            return new SetCreditsParser().parse(arguments);

        case StoreCommand.COMMAND_UNDO:
            return new UndoCommand();

        case StoreCommand.COMMAND_REDO:
            return new RedoCommand();

        case SaveCommand.COMMAND_BACKUP:
            return new SaveCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommandParser().parse(arguments);

        case RestoreCommand.COMMAND_RESTORE:
            return new RestoreCommandParser().parse(arguments);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
