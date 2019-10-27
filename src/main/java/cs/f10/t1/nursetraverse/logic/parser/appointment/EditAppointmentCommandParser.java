package cs.f10.t1.nursetraverse.logic.parser.appointment;

import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.commons.core.Messages;
import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.appointment.EditAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.commands.appointment.EditAppointmentCommand.EditAppointmentDescriptor;
import cs.f10.t1.nursetraverse.logic.parser.ArgumentMultimap;
import cs.f10.t1.nursetraverse.logic.parser.ArgumentTokenizer;
import cs.f10.t1.nursetraverse.logic.parser.CliSyntax;
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
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME,
                        CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME, CliSyntax.PREFIX_PATIENT_INDEX,
                        CliSyntax.PREFIX_RECUR_YEARS, CliSyntax.PREFIX_RECUR_MONTHS, CliSyntax.PREFIX_RECUR_WEEKS,
                        CliSyntax.PREFIX_RECUR_DAYS,
                        CliSyntax.PREFIX_RECUR_HOURS, CliSyntax.PREFIX_RECUR_MINUTES,
                        CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION);

        Index index;
        boolean startChanged = false;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAppointmentCommand.MESSAGE_USAGE), pe);
        }

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME).isPresent()) {
            editAppointmentDescriptor.setStartDateTime(ParserUtil.parseStartDateTime(argMultimap
                    .getValue(CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME).get()));
            startChanged = true;
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME).isPresent()) {
            if (startChanged) {
                editAppointmentDescriptor.setEndDateTime(ParserUtil.parseEndDateTime(argMultimap
                        .getValue(CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME).get(), argMultimap
                        .getValue(CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME).get()));
            } else {
                editAppointmentDescriptor.setEndDateTime(ParserUtil.parseEndDateTime(argMultimap
                        .getValue(CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME).get(), editAppointmentDescriptor
                        .getStartDateTime().toString()));
            }
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_PATIENT_INDEX).isPresent()) {
            editAppointmentDescriptor.setPatientIndex(ParserUtil.parseIndex(argMultimap
                    .getValue(CliSyntax.PREFIX_PATIENT_INDEX).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_RECUR_YEARS).isPresent()) {
            editAppointmentDescriptor.setYears(ParserUtil.parseFrequency(argMultimap
                    .getValue(CliSyntax.PREFIX_RECUR_YEARS).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_RECUR_MONTHS).isPresent()) {
            editAppointmentDescriptor.setYears(ParserUtil.parseFrequency(argMultimap
                    .getValue(CliSyntax.PREFIX_RECUR_MONTHS).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_RECUR_WEEKS).isPresent()) {
            editAppointmentDescriptor.setYears(ParserUtil.parseFrequency(argMultimap
                    .getValue(CliSyntax.PREFIX_RECUR_WEEKS).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_RECUR_DAYS).isPresent()) {
            editAppointmentDescriptor.setYears(ParserUtil.parseFrequency(argMultimap
                    .getValue(CliSyntax.PREFIX_RECUR_DAYS).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_RECUR_HOURS).isPresent()) {
            editAppointmentDescriptor.setYears(ParserUtil.parseFrequency(argMultimap
                    .getValue(CliSyntax.PREFIX_RECUR_HOURS).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_RECUR_MINUTES).isPresent()) {
            editAppointmentDescriptor.setYears(ParserUtil.parseFrequency(argMultimap
                    .getValue(CliSyntax.PREFIX_RECUR_MINUTES).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION).isPresent()) {
            editAppointmentDescriptor.setDescription(ParserUtil.parseDescription(argMultimap
                    .getValue(CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION).get()));
        }

        if (!editAppointmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAppointmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAppointmentCommand(index, editAppointmentDescriptor);
    }

}
