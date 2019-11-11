package seedu.revision.logic.parser.main;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.commands.main.ClearCommand;
import seedu.revision.logic.commands.main.DeleteCommand;
import seedu.revision.logic.commands.main.EditCommand;
import seedu.revision.logic.commands.main.ExitCommand;
import seedu.revision.logic.commands.main.FindCommand;
import seedu.revision.logic.commands.main.HelpCommand;
import seedu.revision.logic.commands.main.HistoryCommand;
import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.commands.main.RestoreCommand;
import seedu.revision.logic.commands.main.StartCommand;
import seedu.revision.logic.commands.main.StatsCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.quiz.McqInputCommandParser;
import seedu.revision.logic.parser.quiz.SaqInputCommandParser;
import seedu.revision.logic.parser.quiz.TfInputCommandParser;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Saq;
import seedu.revision.model.answerable.TrueFalse;

/**
 * @author wilfredbtan
 * Parses user input.
 */
public class ParserManager {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string.
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format or is not a configuration mode
     * command.
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

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case RestoreCommand.COMMAND_WORD:
            return new RestoreCommandParser().parse(arguments);

        case StartCommand.COMMAND_WORD:
            return new StartCommandParser().parse(arguments);

        case StatsCommand.COMMAND_WORD:
            return new StatsCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Checks if user input during quiz is valid.
     * It should be either an exit command, or an answer to the current question.
     * @param userInput user response or command.
     * @param currentAnswerable the current answerable.
     * @return exit command or an answerable input command.
     * @throws ParseException throws an exception if the user input is not a quiz command.
     */
    public Command parseCommand(String userInput, Answerable currentAnswerable) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        final String commandWord = matcher.group("commandWord");

        if (commandWord.equalsIgnoreCase(ExitCommand.COMMAND_WORD)) {
            return new ExitCommand();
        }

        if (currentAnswerable instanceof Mcq) {
            return new McqInputCommandParser().parse(userInput, currentAnswerable);
        } else if (currentAnswerable instanceof TrueFalse) {
            return new TfInputCommandParser().parse(userInput, currentAnswerable);
        } else if (currentAnswerable instanceof Saq) {
            return new SaqInputCommandParser().parse(userInput, currentAnswerable);
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }


}
