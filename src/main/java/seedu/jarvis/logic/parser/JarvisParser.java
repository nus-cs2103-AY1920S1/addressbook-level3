package seedu.jarvis.logic.parser;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.ExitCommand;
import seedu.jarvis.logic.commands.HelpCommand;

import seedu.jarvis.logic.commands.cca.AddCcaCommand;
import seedu.jarvis.logic.commands.cca.AddProgressCommand;
import seedu.jarvis.logic.commands.cca.DeleteCcaCommand;
import seedu.jarvis.logic.commands.cca.EditCcaCommand;
import seedu.jarvis.logic.commands.cca.FindCcaCommand;
import seedu.jarvis.logic.commands.cca.IncreaseProgressCommand;
import seedu.jarvis.logic.commands.cca.ListCcaCommand;
import seedu.jarvis.logic.commands.course.AddCourseCommand;
import seedu.jarvis.logic.commands.course.CheckCommand;
import seedu.jarvis.logic.commands.course.ClearCourseCommand;
import seedu.jarvis.logic.commands.course.DeleteCourseCommand;
import seedu.jarvis.logic.commands.course.ListCourseCommand;
import seedu.jarvis.logic.commands.course.LookUpCommand;
import seedu.jarvis.logic.commands.course.ShowCourseHelpCommand;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand;
import seedu.jarvis.logic.commands.finance.FindPurchaseCommand;
import seedu.jarvis.logic.commands.finance.ListFinancesCommand;
import seedu.jarvis.logic.commands.finance.RemoveInstallmentCommand;
import seedu.jarvis.logic.commands.finance.RemovePaidCommand;
import seedu.jarvis.logic.commands.finance.SetInstallmentCommand;
import seedu.jarvis.logic.commands.finance.SetMonthlyLimitCommand;
import seedu.jarvis.logic.commands.finance.SetPaidCommand;
import seedu.jarvis.logic.commands.history.ListHistoryCommand;
import seedu.jarvis.logic.commands.history.RedoCommand;
import seedu.jarvis.logic.commands.history.UndoCommand;
import seedu.jarvis.logic.commands.planner.AddTaskCommand;
import seedu.jarvis.logic.commands.planner.DeleteTaskCommand;
import seedu.jarvis.logic.commands.planner.DoneTaskCommand;
import seedu.jarvis.logic.commands.planner.FindTaskCommand;
import seedu.jarvis.logic.commands.planner.ListScheduleCommand;
import seedu.jarvis.logic.commands.planner.ListTaskCommand;
import seedu.jarvis.logic.commands.planner.PullTaskCommand;
import seedu.jarvis.logic.parser.cca.AddCcaCommandParser;
import seedu.jarvis.logic.parser.cca.AddProgressCommandParser;
import seedu.jarvis.logic.parser.cca.DeleteCcaCommandParser;
import seedu.jarvis.logic.parser.cca.EditCcaCommandParser;
import seedu.jarvis.logic.parser.cca.FindCcaCommandParser;
import seedu.jarvis.logic.parser.cca.IncreaseProgressCommandParser;
import seedu.jarvis.logic.parser.course.AddCourseCommandParser;
import seedu.jarvis.logic.parser.course.CheckCommandParser;
import seedu.jarvis.logic.parser.course.DeleteCourseCommandParser;
import seedu.jarvis.logic.parser.course.LookUpCommandParser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.logic.parser.finance.EditInstallmentCommandParser;
import seedu.jarvis.logic.parser.finance.FindPurchaseCommandParser;
import seedu.jarvis.logic.parser.finance.RemoveInstallmentCommandParser;
import seedu.jarvis.logic.parser.finance.RemovePaidCommandParser;
import seedu.jarvis.logic.parser.finance.SetInstallmentCommandParser;
import seedu.jarvis.logic.parser.finance.SetMonthlyLimitCommandParser;
import seedu.jarvis.logic.parser.finance.SetPaidCommandParser;
import seedu.jarvis.logic.parser.history.RedoCommandParser;
import seedu.jarvis.logic.parser.history.UndoCommandParser;
import seedu.jarvis.logic.parser.planner.AddTaskCommandParser;
import seedu.jarvis.logic.parser.planner.DeleteTaskCommandParser;
import seedu.jarvis.logic.parser.planner.DoneTaskCommandParser;
import seedu.jarvis.logic.parser.planner.FindTaskCommandParser;
import seedu.jarvis.logic.parser.planner.PullTaskCommandParser;

/**
 * Parses user input.
 */
public class JarvisParser {

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

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        // undo/redo commands
        case ListHistoryCommand.COMMAND_WORD:
            return new ListHistoryCommand();
        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);
        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);

        // courseplanner commands
        case LookUpCommand.COMMAND_WORD:
            return new LookUpCommandParser().parse(arguments);
        case AddCourseCommand.COMMAND_WORD:
            return new AddCourseCommandParser().parse(arguments);
        case DeleteCourseCommand.COMMAND_WORD:
            return new DeleteCourseCommandParser().parse(arguments);
        case CheckCommand.COMMAND_WORD:
            return new CheckCommandParser().parse(arguments);
        case ListCourseCommand.COMMAND_WORD:
            return new ListCourseCommand();
        case ClearCourseCommand.COMMAND_WORD:
            return new ClearCourseCommand();
        case ShowCourseHelpCommand.COMMAND_WORD:
            return new ShowCourseHelpCommand();

        // ccatracker commands
        case AddCcaCommand.COMMAND_WORD:
            return new AddCcaCommandParser().parse(arguments);
        case DeleteCcaCommand.COMMAND_WORD:
            return new DeleteCcaCommandParser().parse(arguments);
        case EditCcaCommand.COMMAND_WORD:
            return new EditCcaCommandParser().parse(arguments);
        case ListCcaCommand.COMMAND_WORD:
            return new ListCcaCommand();
        case FindCcaCommand.COMMAND_WORD:
            return new FindCcaCommandParser().parse(arguments);
        case AddProgressCommand.COMMAND_WORD:
            return new AddProgressCommandParser().parse(arguments);
        case IncreaseProgressCommand.COMMAND_WORD:
            return new IncreaseProgressCommandParser().parse(arguments);

        // planner commands
        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);
        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);
        case FindTaskCommand.COMMAND_WORD:
            return new FindTaskCommandParser().parse(arguments);
        case DoneTaskCommand.COMMAND_WORD:
            return new DoneTaskCommandParser().parse(arguments);
        case ListTaskCommand.COMMAND_WORD:
            return new ListTaskCommand();
        case ListScheduleCommand.COMMAND_WORD:
            return new ListScheduleCommand();
        case PullTaskCommand.COMMAND_WORD:
            return new PullTaskCommandParser().parse(arguments);

        // financetracker commands
        case SetPaidCommand.COMMAND_WORD:
            return new SetPaidCommandParser().parse(arguments);
        case RemovePaidCommand.COMMAND_WORD:
            return new RemovePaidCommandParser().parse(arguments);
        case SetInstallmentCommand.COMMAND_WORD:
            return new SetInstallmentCommandParser().parse(arguments);
        case RemoveInstallmentCommand.COMMAND_WORD:
            return new RemoveInstallmentCommandParser().parse(arguments);
        case EditInstallmentCommand.COMMAND_WORD:
            return new EditInstallmentCommandParser().parse(arguments);
        case ListFinancesCommand.COMMAND_WORD:
            return new ListFinancesCommand();
        case FindPurchaseCommand.COMMAND_WORD:
            return new FindPurchaseCommandParser().parse(arguments);
        case SetMonthlyLimitCommand.COMMAND_WORD:
            return new SetMonthlyLimitCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
