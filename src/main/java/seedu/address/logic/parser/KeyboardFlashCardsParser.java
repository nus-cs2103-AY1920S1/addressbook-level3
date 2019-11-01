package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_TEST_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BadCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeadlineCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EndTestCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.HelpCommand;

import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListCategoryCommand;
import seedu.address.logic.commands.RateQuestionCommand;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.commands.SearchAnswerCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SearchQuestionCommand;
import seedu.address.logic.commands.SetThemeCommand;
import seedu.address.logic.commands.ShowAnswerCommand;
import seedu.address.logic.commands.StartCommand;
import seedu.address.logic.commands.StatsCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class KeyboardFlashCardsParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    //@@author keiteo
    private boolean isRunningFlashcardTest = false;

    private boolean isAwaitingAnswer = false;

    //@@author
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
        if (isRunningFlashcardTest) {
            return parseTestCommand(matcher);
        }
        return parseNormalCommand(matcher);
    }

    //@@author keiteo
    public void startTestMode() {
        isRunningFlashcardTest = true;
    }

    public void endTestMode() {
        isRunningFlashcardTest = false;
    }

    public void setAwaitingAnswer(boolean isAwaitingAnswer) {
        this.isAwaitingAnswer = isAwaitingAnswer;
    }

    /** Parses test specific commands. */
    private Command parseTestCommand(Matcher matcher) throws ParseException {

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        if (commandWord.equals(EndTestCommand.COMMAND_WORD)) {
            return new EndTestCommand(this);
        }
        if (commandWord.equals(ShowAnswerCommand.COMMAND_WORD) && isAwaitingAnswer) {
            return new ShowAnswerCommand(this);
        }
        if (commandWord.equals(RateQuestionCommand.COMMAND_WORD) && !isAwaitingAnswer) {
            return new RateQuestionCommandParser(this).parse(arguments);
        }
        throw new ParseException(MESSAGE_UNKNOWN_TEST_COMMAND);
    }

    //@@author
    /** Parses commands outside test mode i.e. list, add etc. */
    private Command parseNormalCommand(Matcher matcher) throws ParseException {
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
            return new ClearCommand();

        case SearchCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCategoryCommand.COMMAND_WORD:
            return new FindCategoryCommandParser().parse(arguments);

        case SearchAnswerCommand.COMMAND_WORD:
            return new FindAnswerCommandParser().parse(arguments);

        case SearchQuestionCommand.COMMAND_WORD:
            return new FindQuestionCommandParser().parse(arguments);

        case ListAllCommand.COMMAND_WORD:
            return new ListAllCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case CalendarCommand.COMMAND_WORD:
            return new CalendarCommand();

        case StatsCommand.COMMAND_WORD:
            return new StatsCommand();

        case DeadlineCommand.COMMAND_WORD:
            return new DeadlineCommandParser().parse(arguments);

        case BadCommand.COMMAND_WORD:
            return new BadCommandParser().parse(arguments);

        case RemoveCommand.COMMAND_WORD:
            return new RemoveCommandParser().parse(arguments);

        case StartCommand.COMMAND_WORD:
            return new StartCommandParser(this).parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case SetThemeCommand.COMMAND_WORD:
            return new SetThemeCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
