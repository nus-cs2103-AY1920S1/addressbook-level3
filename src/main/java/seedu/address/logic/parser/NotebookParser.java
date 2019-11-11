package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddClassroomCommand;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.commands.DeleteClassroomCommand;
import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.commands.EditLessonCommand;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindAssignmentCommand;
import seedu.address.logic.commands.FindLessonCommand;
import seedu.address.logic.commands.FindStudentCommand;
import seedu.address.logic.commands.GetStudentGradesCommand;
import seedu.address.logic.commands.GetUnsubmittedAssignmentsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAssignmentCommand;
import seedu.address.logic.commands.ListStudentCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ResetDisplayPictureCommand;
import seedu.address.logic.commands.SetClassroomCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateGradesCommand;
import seedu.address.logic.commands.UploadPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class NotebookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * Please add any new command to the command masterlist as well.
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

        case SetClassroomCommand.COMMAND_WORD:
            return new SetClassroomCommandParser().parse(arguments);

        case AddClassroomCommand.COMMAND_WORD:
            return new AddClassroomCommandParser().parse(arguments);

        case ListAssignmentCommand.COMMAND_WORD:
            return new ListAssignmentCommand();

        case UpdateGradesCommand.COMMAND_WORD:
            return new UpdateGradesCommandParser().parse(arguments);

        case AddAssignmentCommand.COMMAND_WORD:
            return new AddAssignmentCommandParser().parse(arguments);

        case EditAssignmentCommand.COMMAND_WORD:
            return new EditAssignmentCommandParser().parse(arguments);

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case DeleteAssignmentCommand.COMMAND_WORD:
            return new DeleteAssignmentCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindStudentCommand.COMMAND_WORD:
            return new FindStudentCommandParser().parse(arguments);

        case FindAssignmentCommand.COMMAND_WORD:
            return new FindAssignmentCommandParser().parse(arguments);

        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(arguments);

        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(arguments);

        case DeleteClassroomCommand.COMMAND_WORD:
            return new DeleteClassroomCommandParser().parse(arguments);

        case EditLessonCommand.COMMAND_WORD:
            return new EditLessonCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);

        case GetStudentGradesCommand.COMMAND_WORD:
            return new GetStudentGradesCommandParser().parse(arguments);

        case GetUnsubmittedAssignmentsCommand.COMMAND_WORD:
            return new GetUnsubmittedAssignmentsCommand();

        case UploadPictureCommand.COMMAND_WORD:
            return new UploadPictureCommandParser().parse(arguments);

        case ResetDisplayPictureCommand.COMMAND_WORD:
            return new ResetDisplayPictureCommandParser().parse(arguments);

        case FindLessonCommand.COMMAND_WORD:
            return new FindLessonCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
