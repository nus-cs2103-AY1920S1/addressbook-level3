package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.algobase.logic.commands.AddPlanCommand;
import seedu.algobase.logic.commands.ClearCommand;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.DeletePlanCommand;
import seedu.algobase.logic.commands.EditPlanCommand;
import seedu.algobase.logic.commands.ExitCommand;
import seedu.algobase.logic.commands.FindPlanCommand;
import seedu.algobase.logic.commands.HelpCommand;
import seedu.algobase.logic.commands.ListPlanCommand;
import seedu.algobase.logic.commands.findrule.AddFindRuleCommand;
import seedu.algobase.logic.commands.findrule.ApplyCommand;
import seedu.algobase.logic.commands.findrule.DeleteFindRuleCommand;
import seedu.algobase.logic.commands.gui.CloseTabCommand;
import seedu.algobase.logic.commands.gui.OpenTabCommand;
import seedu.algobase.logic.commands.gui.SwitchTabCommand;
import seedu.algobase.logic.commands.problem.AddCommand;
import seedu.algobase.logic.commands.problem.DeleteCommand;
import seedu.algobase.logic.commands.problem.EditCommand;
import seedu.algobase.logic.commands.problem.FindCommand;
import seedu.algobase.logic.commands.problem.ListCommand;
import seedu.algobase.logic.commands.problem.SortCommand;
import seedu.algobase.logic.commands.storage.ExportCommand;
import seedu.algobase.logic.commands.storage.ImportCommand;
import seedu.algobase.logic.commands.tag.AddTagCommand;
import seedu.algobase.logic.commands.tag.DeleteTagCommand;
import seedu.algobase.logic.commands.tag.EditTagCommand;
import seedu.algobase.logic.commands.tag.ListTagCommand;
import seedu.algobase.logic.commands.task.AddTaskCommand;
import seedu.algobase.logic.commands.task.CopyTaskCommand;
import seedu.algobase.logic.commands.task.DeleteTaskCommand;
import seedu.algobase.logic.commands.task.DoneTaskCommand;
import seedu.algobase.logic.commands.task.EditTaskCommand;
import seedu.algobase.logic.commands.task.MoveTaskCommand;
import seedu.algobase.logic.commands.task.SetPlanCommand;
import seedu.algobase.logic.commands.task.UndoneTaskCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.logic.parser.findrule.AddFindRuleCommandParser;
import seedu.algobase.logic.parser.findrule.ApplyCommandParser;
import seedu.algobase.logic.parser.findrule.DeleteFindRuleParser;
import seedu.algobase.logic.parser.problem.AddCommandParser;
import seedu.algobase.logic.parser.problem.DeleteCommandParser;
import seedu.algobase.logic.parser.problem.EditCommandParser;
import seedu.algobase.logic.parser.problem.FindCommandParser;
import seedu.algobase.logic.parser.problem.SortCommandParser;
import seedu.algobase.logic.parser.storage.ExportCommandParser;
import seedu.algobase.logic.parser.storage.ImportCommandParser;
import seedu.algobase.logic.parser.task.AddTaskCommandParser;
import seedu.algobase.logic.parser.task.CopyTaskCommandParser;
import seedu.algobase.logic.parser.task.DeleteTaskCommandParser;
import seedu.algobase.logic.parser.task.DoneTaskCommandParser;
import seedu.algobase.logic.parser.task.EditTaskCommandParser;
import seedu.algobase.logic.parser.task.MoveTaskCommandParser;
import seedu.algobase.logic.parser.task.SetPlanCommandParser;
import seedu.algobase.logic.parser.task.UndoneTaskCommandParser;
/**
 * Parses user input.
 */
public class AlgoBaseParser {

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

        // Problem
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        // Plan
        case AddPlanCommand.COMMAND_WORD:
            return new AddPlanCommandParser().parse(arguments);

        case DeletePlanCommand.COMMAND_WORD:
            return new DeletePlanCommandParser().parse(arguments);

        case EditPlanCommand.COMMAND_WORD:
            return new EditPlanCommandParser().parse(arguments);

        case FindPlanCommand.COMMAND_WORD:
            return new FindPlanCommandParser().parse(arguments);

        case ListPlanCommand.COMMAND_WORD:
            return new ListPlanCommand();

        // Task
        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case CopyTaskCommand.COMMAND_WORD:
            return new CopyTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case DoneTaskCommand.COMMAND_WORD:
            return new DoneTaskCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskCommandParser().parse(arguments);

        case MoveTaskCommand.COMMAND_WORD:
            return new MoveTaskCommandParser().parse(arguments);

        case SetPlanCommand.COMMAND_WORD:
            return new SetPlanCommandParser().parse(arguments);

        case UndoneTaskCommand.COMMAND_WORD:
            return new UndoneTaskCommandParser().parse(arguments);

        // Tag
        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case ListTagCommand.COMMAND_WORD:
            return new ListTagCommand();

        case EditTagCommand.COMMAND_WORD:
            return new EditTagCommandParser().parse(arguments);

        // Find Rule
        case AddFindRuleCommand.COMMAND_WORD:
        case AddFindRuleCommand.SHORT_COMMAND_WORD:
            return new AddFindRuleCommandParser().parse(arguments);

        case ApplyCommand.COMMAND_WORD:
            return new ApplyCommandParser().parse(arguments);

        case DeleteFindRuleCommand.COMMAND_WORD:
        case DeleteFindRuleCommand.SHORT_COMMAND_WORD:
            return new DeleteFindRuleParser().parse(arguments);

        // Storage
        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        // UI
        case SwitchTabCommand.COMMAND_WORD:
        case SwitchTabCommand.SHORT_COMMAND_WORD:
            return new SwitchTabCommandParser().parse(arguments);

        case OpenTabCommand.COMMAND_WORD:
        case OpenTabCommand.SHORT_COMMAND_WORD:
            return new OpenTabCommandParser().parse(arguments);

        case CloseTabCommand.COMMAND_WORD:
        case CloseTabCommand.SHORT_COMMAND_WORD:
            return new CloseTabCommandParser().parse(arguments);

        // Util
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
