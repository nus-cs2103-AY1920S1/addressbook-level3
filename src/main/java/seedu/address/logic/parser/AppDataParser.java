package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.note.AddNoteCommand;
import seedu.address.logic.commands.note.ClearNoteCommand;
import seedu.address.logic.commands.note.DeleteNoteCommand;
import seedu.address.logic.commands.note.EditNoteCommand;
import seedu.address.logic.commands.note.FindNoteCommand;
import seedu.address.logic.commands.note.ListNoteCommand;
import seedu.address.logic.commands.questioncommands.AddQuestionCommand;
import seedu.address.logic.commands.questioncommands.ClearQuestionCommand;
import seedu.address.logic.commands.questioncommands.DeleteQuestionCommand;
import seedu.address.logic.commands.questioncommands.EditQuestionCommand;
import seedu.address.logic.commands.questioncommands.FindDifficultyCommand;
import seedu.address.logic.commands.questioncommands.FindQuestionCommand;
import seedu.address.logic.commands.questioncommands.FindSubjectCommand;
import seedu.address.logic.commands.questioncommands.ListQuestionCommand;
import seedu.address.logic.commands.quiz.QuizModeCommand;
import seedu.address.logic.commands.statistics.GetOverviewCommand;
import seedu.address.logic.commands.statistics.GetQnsCommand;
import seedu.address.logic.commands.statistics.GetReportCommand;
import seedu.address.logic.commands.statistics.GetStatisticsCommand;
import seedu.address.logic.commands.task.AddTaskForNoteCommand;
import seedu.address.logic.commands.task.AddTaskForQuestionCommand;
import seedu.address.logic.commands.task.ClearTaskCommand;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.commands.task.DoneTaskCommand;
import seedu.address.logic.commands.task.ListAllTaskCommand;
import seedu.address.logic.commands.task.ListDoneTaskCommand;
import seedu.address.logic.commands.task.ListNotDoneTaskCommand;
import seedu.address.logic.commands.task.ListOverdueTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.note.AddNoteCommandParser;
import seedu.address.logic.parser.note.DeleteNoteCommandParser;
import seedu.address.logic.parser.note.EditNoteCommandParser;
import seedu.address.logic.parser.note.FindNoteCommandParser;
import seedu.address.logic.parser.questionparser.AddQuestionCommandParser;
import seedu.address.logic.parser.questionparser.DeleteQuestionCommandParser;
import seedu.address.logic.parser.questionparser.EditQuestionCommandParser;
import seedu.address.logic.parser.questionparser.FindDifficultyCommandParser;
import seedu.address.logic.parser.questionparser.FindQuestionCommandParser;
import seedu.address.logic.parser.questionparser.FindSubjectCommandParser;
import seedu.address.logic.parser.quiz.QuizModeCommandParser;
import seedu.address.logic.parser.statistics.GetOverviewCommandParser;
import seedu.address.logic.parser.statistics.GetQnsCommandParser;
import seedu.address.logic.parser.statistics.GetReportCommandParser;
import seedu.address.logic.parser.statistics.GetStatisticsCommandParser;
import seedu.address.logic.parser.task.AddTaskForNoteCommandParser;
import seedu.address.logic.parser.task.AddTaskForQuestionCommandParser;
import seedu.address.logic.parser.task.DeleteTaskCommandParser;
import seedu.address.logic.parser.task.DoneTaskCommandParser;

/**
 * Parses user input.
 */
public class AppDataParser {

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

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddNoteCommand.COMMAND_WORD:
            return new AddNoteCommandParser().parse(arguments);

        case EditNoteCommand.COMMAND_WORD:
            return new EditNoteCommandParser().parse(arguments);

        case DeleteNoteCommand.COMMAND_WORD:
            return new DeleteNoteCommandParser().parse(arguments);

        case ClearNoteCommand.COMMAND_WORD:
            return new ClearNoteCommand();

        case FindNoteCommand.COMMAND_WORD:
            return new FindNoteCommandParser().parse(arguments);

        case ListNoteCommand.COMMAND_WORD:
            return new ListNoteCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case QuizModeCommand.COMMAND_WORD:
            return new QuizModeCommandParser().parse(arguments);

        case GetStatisticsCommand.COMMAND_WORD:
            return new GetStatisticsCommandParser().parse(arguments);

        case GetQnsCommand.COMMAND_WORD:
            return new GetQnsCommandParser().parse(arguments);

        case GetReportCommand.COMMAND_WORD:
            return new GetReportCommandParser().parse(arguments);

        case GetOverviewCommand.COMMAND_WORD:
            return new GetOverviewCommandParser().parse(arguments);

        case AddQuestionCommand.COMMAND_WORD:
            return new AddQuestionCommandParser().parse(arguments);

        case DeleteQuestionCommand.COMMAND_WORD:
            return new DeleteQuestionCommandParser().parse(arguments);

        case ListQuestionCommand.COMMAND_WORD:
            return new ListQuestionCommand();

        case EditQuestionCommand.COMMAND_WORD:
            return new EditQuestionCommandParser().parse(arguments);

        case FindQuestionCommand.COMMAND_WORD:
            return new FindQuestionCommandParser().parse(arguments);

        case FindDifficultyCommand.COMMAND_WORD:
            return new FindDifficultyCommandParser().parse(arguments);

        case FindSubjectCommand.COMMAND_WORD:
            return new FindSubjectCommandParser().parse(arguments);

        case ClearQuestionCommand.COMMAND_WORD:
            return new ClearQuestionCommand();

        case AddTaskForNoteCommand.COMMAND_WORD:
            return new AddTaskForNoteCommandParser().parse(arguments);

        case AddTaskForQuestionCommand.COMMAND_WORD:
            return new AddTaskForQuestionCommandParser().parse(arguments);

        case DoneTaskCommand.COMMAND_WORD:
            return new DoneTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case ClearTaskCommand.COMMAND_WORD:
            return new ClearTaskCommand();

        case ListDoneTaskCommand.COMMAND_WORD:
            return new ListDoneTaskCommand();

        case ListNotDoneTaskCommand.COMMAND_WORD:
            return new ListNotDoneTaskCommand();

        case ListAllTaskCommand.COMMAND_WORD:
            return new ListAllTaskCommand();

        case ListOverdueTaskCommand.COMMAND_WORD:
            return new ListOverdueTaskCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
