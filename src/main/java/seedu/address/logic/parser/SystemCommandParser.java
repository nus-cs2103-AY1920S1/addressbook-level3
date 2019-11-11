package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.OmniPanelTab;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.NextCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SetFocusOnTabCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.appointments.AckApptCommand;
import seedu.address.logic.commands.appointments.AddApptCommand;
import seedu.address.logic.commands.appointments.AppointmentsCommand;
import seedu.address.logic.commands.appointments.CancelApptCommand;
import seedu.address.logic.commands.appointments.EditApptCommand;
import seedu.address.logic.commands.appointments.MissApptCommand;
import seedu.address.logic.commands.appointments.SettleApptCommand;
import seedu.address.logic.commands.common.Command;
import seedu.address.logic.commands.common.CommandHistory;
import seedu.address.logic.commands.duties.AddDutyShiftCommand;
import seedu.address.logic.commands.duties.CancelDutyShiftCommand;
import seedu.address.logic.commands.duties.ChangeDutyShiftCommand;
import seedu.address.logic.commands.duties.DutyShiftCommand;
import seedu.address.logic.commands.patients.EditPatientDetailsCommand;
import seedu.address.logic.commands.patients.ListPatientCommand;
import seedu.address.logic.commands.patients.RegisterPatientCommand;
import seedu.address.logic.commands.queue.AddConsultationRoomCommand;
import seedu.address.logic.commands.queue.BreakCommand;
import seedu.address.logic.commands.queue.DequeueCommand;
import seedu.address.logic.commands.queue.EnqueueCommand;
import seedu.address.logic.commands.queue.RemoveRoomCommand;
import seedu.address.logic.commands.queue.ResumeCommand;
import seedu.address.logic.commands.staff.EditStaffDetailsCommand;
import seedu.address.logic.commands.staff.ListStaffCommand;
import seedu.address.logic.commands.staff.RegisterStaffCommand;
import seedu.address.logic.parser.appointments.AckApptCommandParser;
import seedu.address.logic.parser.appointments.AddApptCommandParser;
import seedu.address.logic.parser.appointments.AppointmentsCommandParser;
import seedu.address.logic.parser.appointments.CancelApptCommandParser;
import seedu.address.logic.parser.appointments.EditApptCommandParser;
import seedu.address.logic.parser.appointments.MissApptCommandParser;
import seedu.address.logic.parser.appointments.SettleApptCommandParser;
import seedu.address.logic.parser.duties.AddDutyShiftCommandParser;
import seedu.address.logic.parser.duties.CancelDutyShiftCommandParser;
import seedu.address.logic.parser.duties.ChangeDutyShiftCommandTimingParser;
import seedu.address.logic.parser.duties.DutyShiftCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.patients.EditPatientDetailsCommandParser;
import seedu.address.logic.parser.patients.ListPatientCommandParser;
import seedu.address.logic.parser.patients.RegisterPatientCommandParser;
import seedu.address.logic.parser.queue.AddConsultationRoomCommandParser;
import seedu.address.logic.parser.queue.BreakCommandParser;
import seedu.address.logic.parser.queue.DequeueCommandParser;
import seedu.address.logic.parser.queue.EnqueueCommandParser;
import seedu.address.logic.parser.queue.NextCommandParser;
import seedu.address.logic.parser.queue.RemoveRoomCommandParser;
import seedu.address.logic.parser.queue.ResumeCommandParser;
import seedu.address.logic.parser.staff.EditStaffDetailsCommandParser;
import seedu.address.logic.parser.staff.ListStaffCommandParser;
import seedu.address.logic.parser.staff.RegisterStaffCommandParser;
import seedu.address.model.Model;

/**
 * Parses user input.
 */
public class SystemCommandParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final CommandHistory commandHistory;

    public SystemCommandParser(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, Model model) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments") + " ";
        switch (commandWord) {

        case ListPatientCommand.COMMAND_WORD:
            return new ListPatientCommandParser().parse(arguments);

        case RegisterPatientCommand.COMMAND_WORD:
            return new RegisterPatientCommandParser().parse(arguments);

        case EditPatientDetailsCommand.COMMAND_WORD:
            return new EditPatientDetailsCommandParser(model).parse(arguments);


        case ListStaffCommand.COMMAND_WORD:
            return new ListStaffCommandParser().parse(arguments);

        case RegisterStaffCommand.COMMAND_WORD:
            return new RegisterStaffCommandParser().parse(arguments);

        case EditStaffDetailsCommand.COMMAND_WORD:
            return new EditStaffDetailsCommandParser(model).parse(arguments);


        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand(commandHistory);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand(commandHistory);


        case EnqueueCommand.COMMAND_WORD:
            return new EnqueueCommandParser().parse(arguments);

        case DequeueCommand.COMMAND_WORD:
            return new DequeueCommandParser(model).parse(arguments);


        case AppointmentsCommand.COMMAND_WORD:
            return new AppointmentsCommandParser(model).parse(arguments);

        case AddApptCommand.COMMAND_WORD:
            return new AddApptCommandParser(model).parse(arguments);

        case CancelApptCommand.COMMAND_WORD:
            return new CancelApptCommandParser(model).parse(arguments);

        case EditApptCommand.COMMAND_WORD:
            return new EditApptCommandParser(model).parse(arguments);


        case DutyShiftCommand.COMMAND_WORD:
            return new DutyShiftCommandParser().parse(arguments);

        case AddDutyShiftCommand.COMMAND_WORD:
            return new AddDutyShiftCommandParser(model).parse(arguments);

        case CancelDutyShiftCommand.COMMAND_WORD:
            return new CancelDutyShiftCommandParser(model).parse(arguments);

        case ChangeDutyShiftCommand.COMMAND_WORD:
            return new ChangeDutyShiftCommandTimingParser(model).parse(arguments);


        case AckApptCommand.COMMAND_WORD:
            return new AckApptCommandParser(model).parse(arguments);

        case MissApptCommand.COMMAND_WORD:
            return new MissApptCommandParser().parse(arguments);

        case SettleApptCommand.COMMAND_WORD:
            return new SettleApptCommandParser(model).parse(arguments);


        case AddConsultationRoomCommand.COMMAND_WORD:
            return new AddConsultationRoomCommandParser(model).parse(arguments);

        case RemoveRoomCommand.COMMAND_WORD:
            return new RemoveRoomCommandParser(model).parse(arguments);


        case NextCommand.COMMAND_WORD:
            return new NextCommandParser(model).parse(arguments);

        case BreakCommand.COMMAND_WORD:
            return new BreakCommandParser(model).parse(arguments);

        case ResumeCommand.COMMAND_WORD:
            return new ResumeCommandParser(model).parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution eagerly.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command eagerEvaluateCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new SetFocusOnTabCommand(null);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case ListPatientCommand.COMMAND_WORD:
            return new ListPatientCommand(arguments);

        case ListStaffCommand.COMMAND_WORD:
            return new ListStaffCommand(arguments);

        case AppointmentsCommand.COMMAND_WORD:
            return new AppointmentsCommand(arguments);

        case DutyShiftCommand.COMMAND_WORD:
            return new DutyShiftCommand(arguments);

        case RegisterPatientCommand.COMMAND_WORD:
        case EditPatientDetailsCommand.COMMAND_WORD:
            return new SetFocusOnTabCommand(OmniPanelTab.PATIENTS_TAB);

        case RegisterStaffCommand.COMMAND_WORD:
        case EditStaffDetailsCommand.COMMAND_WORD:
        case AddConsultationRoomCommand.COMMAND_WORD:
        case RemoveRoomCommand.COMMAND_WORD:
            return new SetFocusOnTabCommand(OmniPanelTab.DOCTORS_TAB);

        case AddApptCommand.COMMAND_WORD:
        case AckApptCommand.COMMAND_WORD:
        case CancelApptCommand.COMMAND_WORD:
        case EditApptCommand.COMMAND_WORD:
        case MissApptCommand.COMMAND_WORD:
        case SettleApptCommand.COMMAND_WORD:
            return new SetFocusOnTabCommand(OmniPanelTab.APPOINTMENTS_TAB);

        case AddDutyShiftCommand.COMMAND_WORD:
        case CancelDutyShiftCommand.COMMAND_WORD:
        case ChangeDutyShiftCommand.COMMAND_WORD:
            return new SetFocusOnTabCommand(OmniPanelTab.DUTY_SHIFT_TAB);

        default:
            return new SetFocusOnTabCommand(null);
        }
    }

}
