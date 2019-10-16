package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddInventoryCommand;
import seedu.address.logic.commands.AddMemberCommand;
import seedu.address.logic.commands.AddMemberToTaskCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.AddTaskToMemberCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteInventoryCommand;
import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.DoingTaskCommand;
import seedu.address.logic.commands.DoneTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditMemberCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindMemberCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListMemberCommand;
import seedu.address.logic.commands.RemoveMemberFromTaskCommand;
import seedu.address.logic.commands.RemoveTaskFromMemberCommand;
import seedu.address.logic.commands.SetDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//Remove these lines once stubbing not required
import seedu.address.logic.parser.stub.DoneTaskCommandParserStub;
import seedu.address.logic.parser.stub.SetDeadlineCommandParserStub;

/**
 * Parses user input.
 */
public class ProjectDashboardParser {

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

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case DoingTaskCommand.COMMAND_WORD:
            return new DoingTaskCommandParser().parse(arguments);

        case SetDeadlineCommand.COMMAND_WORD:
            return new SetDeadlineCommandParser().parse(arguments);

        case DoneTaskCommand.COMMAND_WORD:
            return new DoneTaskCommandParserStub().parse(arguments);

        case AddMemberCommand.COMMAND_WORD:
            return new AddMemberCommandParser().parse(arguments);

        case DeleteMemberCommand.COMMAND_WORD:
            return new DeleteMemberCommandParser().parse(arguments);

        case EditMemberCommand.COMMAND_WORD:
            return new EditMemberCommandParser().parse(arguments);

        case FindMemberCommand.COMMAND_WORD:
            return new FindMemberCommandParser().parse(arguments);

        case ListMemberCommand.COMMAND_WORD:
            return new ListMemberCommand();

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

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case AddInventoryCommand.COMMAND_WORD:
            return new AddInventoryCommandParser().parse(arguments);

        case DeleteInventoryCommand.COMMAND_WORD:
            return new DeleteInventoryCommandParser().parse(arguments);

        case AddTaskToMemberCommand.COMMAND_WORD:
            return new AddTaskToMemberParser().parse(arguments);

        case AddMemberToTaskCommand.COMMAND_WORD:
            return new AddMemberToTaskParser().parse(arguments);

        case RemoveTaskFromMemberCommand.COMMAND_WORD:
            return new RemoveTaskFromMemberParser().parse(arguments);

        case RemoveMemberFromTaskCommand.COMMAND_WORD:
            return new RemoveMemberFromTaskParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
