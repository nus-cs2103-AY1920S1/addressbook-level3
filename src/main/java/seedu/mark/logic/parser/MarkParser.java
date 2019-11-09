package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.mark.logic.parser.ParserUtil.NoArgumentParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.mark.logic.commands.AddAnnotationCommand;
import seedu.mark.logic.commands.AddCommand;
import seedu.mark.logic.commands.AddFolderCommand;
import seedu.mark.logic.commands.AddReminderCommand;
import seedu.mark.logic.commands.AutotagCommand;
import seedu.mark.logic.commands.AutotagDeleteCommand;
import seedu.mark.logic.commands.CacheCommand;
import seedu.mark.logic.commands.ClearCommand;
import seedu.mark.logic.commands.CollapseCommand;
import seedu.mark.logic.commands.Command;
import seedu.mark.logic.commands.DeleteAnnotationCommand;
import seedu.mark.logic.commands.DeleteCacheCommand;
import seedu.mark.logic.commands.DeleteCommand;
import seedu.mark.logic.commands.DeleteFolderCommand;
import seedu.mark.logic.commands.DeleteReminderCommand;
import seedu.mark.logic.commands.EditAnnotationCommand;
import seedu.mark.logic.commands.EditCommand;
import seedu.mark.logic.commands.EditFolderCommand;
import seedu.mark.logic.commands.EditReminderCommand;
import seedu.mark.logic.commands.ExitCommand;
import seedu.mark.logic.commands.ExpandCommand;
import seedu.mark.logic.commands.ExportCommand;
import seedu.mark.logic.commands.FavoriteCommand;
import seedu.mark.logic.commands.FindCommand;
import seedu.mark.logic.commands.GotoCommand;
import seedu.mark.logic.commands.GotoReminderCommand;
import seedu.mark.logic.commands.HelpCommand;
import seedu.mark.logic.commands.ImportCommand;
import seedu.mark.logic.commands.ListCommand;
import seedu.mark.logic.commands.OfflineCommand;
import seedu.mark.logic.commands.RedoCommand;
import seedu.mark.logic.commands.TabCommand;
import seedu.mark.logic.commands.UndoCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class MarkParser {

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

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case FavoriteCommand.COMMAND_WORD:
            //Fallthrough
        case FavoriteCommand.COMMAND_ALIAS:
            return new FavoriteCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case GotoCommand.COMMAND_WORD:
            return new GotoCommandParser().parse(arguments);

        case GotoReminderCommand.COMMAND_WORD:
            return new GotoReminderCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new NoArgumentParser<>(ClearCommand::new).parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new NoArgumentParser<>(ListCommand::new).parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new NoArgumentParser<>(ExitCommand::new).parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new NoArgumentParser<>(HelpCommand::new).parse(arguments);

        case TabCommand.COMMAND_WORD:
            return new TabCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);

        case AddFolderCommand.COMMAND_WORD:
            return new AddFolderCommandParser().parse(arguments);

        case EditFolderCommand.COMMAND_WORD:
            return new EditFolderCommandParser().parse(arguments);

        case DeleteFolderCommand.COMMAND_WORD:
            return new DeleteFolderCommandParser().parse(arguments);

        case ExpandCommand.COMMAND_WORD:
            return new ExpandCommandParser().parse(arguments);

        case CollapseCommand.COMMAND_WORD:
            return new CollapseCommandParser().parse(arguments);

        case EditReminderCommand.COMMAND_WORD:
            return new EditReminderCommandParser().parse(arguments);

        case DeleteReminderCommand.COMMAND_WORD:
            return new DeleteReminderCommandParser().parse(arguments);

        case AddReminderCommand.COMMAND_WORD:
            return new AddReminderCommandParser().parse(arguments);

        case AddAnnotationCommand.COMMAND_WORD:
            return new AddAnnotationCommandParser().parse(arguments);

        case AutotagDeleteCommand.COMMAND_WORD:
            return new AutotagDeleteCommandParser().parse(arguments);

        case AutotagCommand.COMMAND_WORD:
            return new AutotagCommandParser().parse(arguments);

        case CacheCommand.COMMAND_WORD:
            return new CacheCommandParser().parse(arguments);

        case DeleteCacheCommand.COMMAND_WORD:
            return new DeleteCacheCommandParser().parse(arguments);

        case OfflineCommand.COMMAND_WORD:
            return new OfflineCommandParser().parse(arguments);

        case DeleteAnnotationCommand.COMMAND_WORD:
            return new DeleteAnnotationCommandParser().parse(arguments);

        case EditAnnotationCommand.COMMAND_WORD:
            return new EditAnnotationCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
