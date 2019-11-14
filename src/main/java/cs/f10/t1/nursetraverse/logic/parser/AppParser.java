package cs.f10.t1.nursetraverse.logic.parser;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs.f10.t1.nursetraverse.logic.commands.AddCommand;
import cs.f10.t1.nursetraverse.logic.commands.ClearCommand;
import cs.f10.t1.nursetraverse.logic.commands.Command;
import cs.f10.t1.nursetraverse.logic.commands.DeleteCommand;
import cs.f10.t1.nursetraverse.logic.commands.EditCommand;
import cs.f10.t1.nursetraverse.logic.commands.ExitCommand;
import cs.f10.t1.nursetraverse.logic.commands.ExportCommand;
import cs.f10.t1.nursetraverse.logic.commands.FindCommand;
import cs.f10.t1.nursetraverse.logic.commands.HelpCommand;
import cs.f10.t1.nursetraverse.logic.commands.HistoryCommand;
import cs.f10.t1.nursetraverse.logic.commands.ImportMergeCommand;
import cs.f10.t1.nursetraverse.logic.commands.ImportReplaceCommand;
import cs.f10.t1.nursetraverse.logic.commands.ListCommand;
import cs.f10.t1.nursetraverse.logic.commands.RedoCommand;
import cs.f10.t1.nursetraverse.logic.commands.UndoCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.AddAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.DeleteAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.DeletePermanentlyAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.EditAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.FindAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.ListAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.BeginVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.CancelOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.FinishOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.UpdateOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.parser.appointment.AddAppointmentCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.appointment.DeleteAppointmentCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.appointment.DeletePermanentlyAppointmentCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.appointment.EditAppointmentCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.appointment.FindAppointmentCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import cs.f10.t1.nursetraverse.logic.parser.visit.BeginVisitCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.visit.UpdateOngoingVisitCommandParser;


/**
 * Parses user input.
 */
public class AppParser {

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
        Command command;
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            command = new AddCommandParser().parse(arguments);
            break;

        case EditCommand.COMMAND_WORD:
            command = new EditCommandParser().parse(arguments);
            break;

        case DeleteCommand.COMMAND_WORD:
            command = new DeleteCommandParser().parse(arguments);
            break;

        case ClearCommand.COMMAND_WORD:
            command = new ClearCommand();
            break;

        case FindCommand.COMMAND_WORD:
            command = new FindCommandParser().parse(arguments);
            break;

        case ListCommand.COMMAND_WORD:
            command = new ListCommand();
            break;

        case ExitCommand.COMMAND_WORD:
            command = new ExitCommand();
            break;

        case HelpCommand.COMMAND_WORD:
            command = new HelpCommand();
            break;

        case ExportCommand.COMMAND_WORD:
            command = new ExportCommandParser().parse(arguments);
            break;

        case ImportReplaceCommand.COMMAND_WORD:
            command = new ImportReplaceCommandParser().parse(arguments);
            break;

        case ImportMergeCommand.COMMAND_WORD:
            command = new ImportMergeCommandParser().parse(arguments);
            break;

        case HistoryCommand.COMMAND_WORD:
            command = new HistoryCommand();
            break;

        case UndoCommand.COMMAND_WORD:
            command = new UndoCommandParser().parse(arguments);
            break;

        case RedoCommand.COMMAND_WORD:
            command = new RedoCommand();
            break;

        case BeginVisitCommand.COMMAND_WORD:
            command = new BeginVisitCommandParser().parse(arguments);
            break;

        case FinishOngoingVisitCommand.COMMAND_WORD:
            command = new FinishOngoingVisitCommand();
            break;

        case UpdateOngoingVisitCommand.COMMAND_WORD:
            command = new UpdateOngoingVisitCommandParser().parse(arguments);
            break;

        case CancelOngoingVisitCommand.COMMAND_WORD:
            command = new CancelOngoingVisitCommand();
            break;

        case AddAppointmentCommand.COMMAND_WORD:
            command = new AddAppointmentCommandParser().parse(arguments);
            break;

        case DeleteAppointmentCommand.COMMAND_WORD:
            command = new DeleteAppointmentCommandParser().parse(arguments);
            break;

        case DeletePermanentlyAppointmentCommand.COMMAND_WORD:
            command = new DeletePermanentlyAppointmentCommandParser().parse(arguments);
            break;

        case EditAppointmentCommand.COMMAND_WORD:
            command = new EditAppointmentCommandParser().parse(arguments);
            break;

        case FindAppointmentCommand.COMMAND_WORD:
            command = new FindAppointmentCommandParser().parse(arguments);
            break;

        case ListAppointmentCommand.COMMAND_WORD:
            command = new ListAppointmentCommand();
            break;

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        command.setCommandText(userInput);
        return command;
    }

}
