package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.commons.core.Messages.MESSAGE_QUIZ_UNSUPPORTED_COMMAND;
import static seedu.flashcard.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.flashcard.logic.commands.AddCommand;
import seedu.flashcard.logic.commands.Command;
import seedu.flashcard.logic.commands.DeleteCommand;
import seedu.flashcard.logic.commands.DeleteTagCommand;
import seedu.flashcard.logic.commands.EditCommand;
import seedu.flashcard.logic.commands.EndCommand;
import seedu.flashcard.logic.commands.ExitCommand;
import seedu.flashcard.logic.commands.FindCommand;
import seedu.flashcard.logic.commands.FlipCommand;
import seedu.flashcard.logic.commands.HelpCommand;
import seedu.flashcard.logic.commands.ListCardByTagCommand;
import seedu.flashcard.logic.commands.ListCommand;
import seedu.flashcard.logic.commands.ListTagCommand;
import seedu.flashcard.logic.commands.QuizCommand;
import seedu.flashcard.logic.commands.QuizTagCommand;
import seedu.flashcard.logic.commands.StatsCommand;
import seedu.flashcard.logic.commands.ViewCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;

/**
 * The major controlling panel of command parsers.
 */
public class FlashcardListParser {

    private static boolean quizMode = false;

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses the original user input and calls corresponding commands or sub-parsers.
     * @param userInput the original user input.
     * @return the command being extracted out from the user input.
     * @throws ParseException when the user input format does not fit the pattern.
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (quizMode) {
            return parseQuizMode(commandWord, arguments);
        }

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCardByTagCommand.COMMAND_WORD:
            return new ListCardByTagCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListTagCommand.COMMAND_WORD:
            return new ListTagCommand();

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case QuizCommand.COMMAND_WORD:
            Command nextQuizCommand = (new QuizCommandParser()).parse(arguments);
            quizMode = true;
            return nextQuizCommand;

        case QuizTagCommand.COMMAND_WORD:
            Command nextQuizTagCommand = (new QuizTagCommandParser()).parse(arguments);
            quizMode = true;
            return nextQuizTagCommand;

        case FlipCommand.COMMAND_WORD:
            throw new ParseException(FlipCommand.MESSAGE_NULL_QUIZ_FLASHCARD);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case StatsCommand.COMMAND_WORD:
            return new StatsCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses corresponding commands that are triggered in quiz mode.
     * @param commandWord String representing the command.
     * @param arguments Arguments of the command.
     * @return Command based on user input.
     * @throws ParseException when the user input is unrecognised.
     */
    public Command parseQuizMode(String commandWord, String arguments) throws ParseException {

        switch (commandWord) {

        case EndCommand.COMMAND_WORD:
            quizMode = false;
            return new EndCommand();

        case ExitCommand.COMMAND_WORD:
            quizMode = false;
            return new ExitCommand();

        case FlipCommand.COMMAND_WORD:
            return new FlipCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_QUIZ_UNSUPPORTED_COMMAND);
        }
    }

    /**
     * Getter for quiz mode boolean.
     * @return True if logic is currently in quiz mode.
     */
    public boolean isQuizMode() {
        return quizMode;
    }

    /**
     * Setter for quiz mode boolean.
     * @param quizMode Sets quiz mode boolean to quizMode.
     */
    public static void setQuizMode(boolean quizMode) {
        FlashcardListParser.quizMode = quizMode;
    }

}
