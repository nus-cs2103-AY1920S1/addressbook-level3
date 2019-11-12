package seedu.eatme.logic.parser;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.eatme.logic.commands.AddCommand;
import seedu.eatme.logic.commands.AddFeedCommand;
import seedu.eatme.logic.commands.AddTagCommand;
import seedu.eatme.logic.commands.ClearCommand;
import seedu.eatme.logic.commands.CloseCommand;
import seedu.eatme.logic.commands.Command;
import seedu.eatme.logic.commands.DeleteCommand;
import seedu.eatme.logic.commands.DeleteFeedCommand;
import seedu.eatme.logic.commands.DeleteReviewCommand;
import seedu.eatme.logic.commands.EditCommand;
import seedu.eatme.logic.commands.EditReviewCommand;
import seedu.eatme.logic.commands.ExitCommand;
import seedu.eatme.logic.commands.FindCommand;
import seedu.eatme.logic.commands.HelpCommand;
import seedu.eatme.logic.commands.ListCommand;
import seedu.eatme.logic.commands.LoadCommand;
import seedu.eatme.logic.commands.ModeCommand;
import seedu.eatme.logic.commands.RemoveTagCommand;
import seedu.eatme.logic.commands.ReopenCommand;
import seedu.eatme.logic.commands.ReviewCommand;
import seedu.eatme.logic.commands.SaveTodoCommand;
import seedu.eatme.logic.commands.ShowCommand;
import seedu.eatme.logic.commands.StatsCommand;

import seedu.eatme.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class EatMeParser {

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
    public Command parseCommand(String userInput, boolean isMainMode) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            if (isMainMode) {
                return new AddCommandParser().parse(arguments);
            } else {
                return new AddTodoCommandParser().parse(arguments);
            }

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case CloseCommand.COMMAND_WORD:
            return new CloseCommandParser().parse(arguments);

        case ReopenCommand.COMMAND_WORD:
            return new ReopenCommandParser().parse(arguments);

        case ModeCommand.COMMAND_WORD:
            return new ModeCommand();

        case ReviewCommand.COMMAND_WORD:
            return new ReviewCommandParser().parse(arguments);

        case SaveTodoCommand.COMMAND_WORD:
            return new SaveTodoCommandParser().parse(arguments);

        case AddFeedCommand.COMMAND_WORD:
            return new AddFeedCommandParser().parse(arguments);

        case DeleteFeedCommand.COMMAND_WORD:
            return new DeleteFeedCommandParser().parse(arguments);

        case StatsCommand.COMMAND_WORD:
            return new StatsCommand(isMainMode);

        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(arguments);

        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);

        case RemoveTagCommand.COMMAND_WORD:
            return new RemoveTagCommandParser().parse(arguments);

        case EditReviewCommand.COMMAND_WORD:
            return new EditReviewCommandParser().parse(arguments);

        case DeleteReviewCommand.COMMAND_WORD:
            return new DeleteReviewCommandParser().parse(arguments);

        case LoadCommand.COMMAND_WORD:
            return new LoadCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }


    }

}
