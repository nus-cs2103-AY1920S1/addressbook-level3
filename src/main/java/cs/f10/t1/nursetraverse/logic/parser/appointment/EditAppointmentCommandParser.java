package cs.f10.t1.nursetraverse.logic.parser.appointment;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_PATIENT_INDEX;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_DAYS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_HOURS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_MINUTES;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_MONTHS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_WEEKS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_YEARS;
import static cs.f10.t1.nursetraverse.logic.parser.ParserUtil.manageIndexParseException;
import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.appointment.EditAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.EditAppointmentCommand.EditAppointmentDescriptor;
import cs.f10.t1.nursetraverse.logic.parser.ArgumentMultimap;
import cs.f10.t1.nursetraverse.logic.parser.ArgumentTokenizer;
import cs.f10.t1.nursetraverse.logic.parser.ParserUtil;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditAppointmentCommand object
 */
public class EditAppointmentCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EditAppointmentCommand
     * and returns an EditAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     * Does not yet allow to selectively change years, months, etc. of the frequency with which the appointment recurs.
     */
    public EditAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPOINTMENT_START_DATE_AND_TIME,
                        PREFIX_APPOINTMENT_END_DATE_AND_TIME, PREFIX_PATIENT_INDEX,
                        PREFIX_RECUR_YEARS, PREFIX_RECUR_MONTHS, PREFIX_RECUR_WEEKS,
                        PREFIX_RECUR_DAYS,
                        PREFIX_RECUR_HOURS, PREFIX_RECUR_MINUTES,
                        PREFIX_APPOINTMENT_DESCRIPTION);

        Index index;
        boolean startChanged = false;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            //This will always throw a ParseException
            manageIndexParseException(pe, EditAppointmentCommand.MESSAGE_USAGE);
            //This is included to ensure compiler complies
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAppointmentCommand.MESSAGE_USAGE), pe);
        }

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        if (argMultimap.getValue(PREFIX_APPOINTMENT_START_DATE_AND_TIME).isPresent()) {
            editAppointmentDescriptor.setStartDateTime(ParserUtil.parseStartDateTime(argMultimap
                    .getValue(PREFIX_APPOINTMENT_START_DATE_AND_TIME).get()));
            startChanged = true;
        }
        if (argMultimap.getValue(PREFIX_APPOINTMENT_END_DATE_AND_TIME).isPresent()) {
            if (startChanged) {
                editAppointmentDescriptor.setEndDateTime(ParserUtil.parseEndDateTime(argMultimap
                        .getValue(PREFIX_APPOINTMENT_END_DATE_AND_TIME).get(), argMultimap
                        .getValue(PREFIX_APPOINTMENT_START_DATE_AND_TIME).get()));
            } else {
                editAppointmentDescriptor.setEndDateTime(ParserUtil.parseEndDateTime(argMultimap
                        .getValue(PREFIX_APPOINTMENT_END_DATE_AND_TIME).get()));
            }
        }
        if (argMultimap.getValue(PREFIX_PATIENT_INDEX).isPresent()) {
            editAppointmentDescriptor.setPatientIndex(ParserUtil.parseIndex(argMultimap
                    .getValue(PREFIX_PATIENT_INDEX).get()));
        }
        if (argMultimap.getValue(PREFIX_RECUR_YEARS).isPresent()) {
            editAppointmentDescriptor.setYears(ParserUtil.parseFrequency(argMultimap
                    .getValue(PREFIX_RECUR_YEARS)));
        }
        if (argMultimap.getValue(PREFIX_RECUR_MONTHS).isPresent()) {
            editAppointmentDescriptor.setMonths(ParserUtil.parseFrequency(argMultimap
                    .getValue(PREFIX_RECUR_MONTHS)));
        }
        if (argMultimap.getValue(PREFIX_RECUR_WEEKS).isPresent()) {
            editAppointmentDescriptor.setWeeks(ParserUtil.parseFrequency(argMultimap
                    .getValue(PREFIX_RECUR_WEEKS)));
        }
        if (argMultimap.getValue(PREFIX_RECUR_DAYS).isPresent()) {
            editAppointmentDescriptor.setDays(ParserUtil.parseFrequency(argMultimap
                    .getValue(PREFIX_RECUR_DAYS)));
        }
        if (argMultimap.getValue(PREFIX_RECUR_HOURS).isPresent()) {
            editAppointmentDescriptor.setHours(ParserUtil.parseFrequency(argMultimap
                    .getValue(PREFIX_RECUR_HOURS)));
        }
        if (argMultimap.getValue(PREFIX_RECUR_MINUTES).isPresent()) {
            editAppointmentDescriptor.setMinutes(ParserUtil.parseFrequency(argMultimap
                    .getValue(PREFIX_RECUR_MINUTES)));
        }
        if (argMultimap.getValue(PREFIX_APPOINTMENT_DESCRIPTION).isPresent()) {
            editAppointmentDescriptor.setDescription(ParserUtil.parseDescription(argMultimap
                    .getValue(PREFIX_APPOINTMENT_DESCRIPTION)));
        }

        if (!editAppointmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAppointmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAppointmentCommand(index, editAppointmentDescriptor);
    }

}
