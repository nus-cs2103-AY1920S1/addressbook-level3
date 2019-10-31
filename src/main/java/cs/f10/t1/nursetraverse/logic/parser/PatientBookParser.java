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
import cs.f10.t1.nursetraverse.logic.commands.appointment.EditAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.FindAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.BeginVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.CancelOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.FinishOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.commands.visit.UpdateOngoingVisitCommand;
import cs.f10.t1.nursetraverse.logic.parser.appointment.AddAppointmentCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.appointment.DeleteAppointmentCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.appointment.EditAppointmentCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.appointment.FindAppointmentCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import cs.f10.t1.nursetraverse.logic.parser.visit.BeginVisitCommandParser;
import cs.f10.t1.nursetraverse.logic.parser.visit.UpdateOngoingVisitCommandParser;


/**
 * Parses user input.
 */
public class PatientBookParser {

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

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

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportReplaceCommand.COMMAND_WORD:
            return new ImportReplaceCommandParser().parse(arguments);

        case ImportMergeCommand.COMMAND_WORD:
            return new ImportMergeCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case BeginVisitCommand.COMMAND_WORD:
            return new BeginVisitCommandParser().parse(arguments);

        case FinishOngoingVisitCommand.COMMAND_WORD:
            return new FinishOngoingVisitCommand();

        case UpdateOngoingVisitCommand.COMMAND_WORD:
            return new UpdateOngoingVisitCommandParser().parse(arguments);

        case CancelOngoingVisitCommand.COMMAND_WORD:
            return new CancelOngoingVisitCommand();

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_WORD:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case EditAppointmentCommand.COMMAND_WORD:
            return new EditAppointmentCommandParser().parse(arguments);

        case FindAppointmentCommand.COMMAND_WORD:
            return new FindAppointmentCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
