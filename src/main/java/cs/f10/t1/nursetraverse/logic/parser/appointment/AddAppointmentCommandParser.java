package cs.f10.t1.nursetraverse.logic.parser.appointment;

import java.util.stream.Stream;

import cs.f10.t1.nursetraverse.commons.core.Messages;
import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.appointment.AddAppointmentCommand;
import cs.f10.t1.nursetraverse.logic.parser.ArgumentMultimap;
import cs.f10.t1.nursetraverse.logic.parser.ArgumentTokenizer;
import cs.f10.t1.nursetraverse.logic.parser.CliSyntax;
import cs.f10.t1.nursetraverse.logic.parser.Parser;
import cs.f10.t1.nursetraverse.logic.parser.ParserUtil;
import cs.f10.t1.nursetraverse.logic.parser.Prefix;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.RecurringDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_PATIENT_INDEX,
                        CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME,
                        CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME,
                        CliSyntax.PREFIX_RECUR_YEARS, CliSyntax.PREFIX_RECUR_MONTHS, CliSyntax.PREFIX_RECUR_WEEKS,
                        CliSyntax.PREFIX_RECUR_DAYS,
                        CliSyntax.PREFIX_RECUR_HOURS, CliSyntax.PREFIX_RECUR_MINUTES,
                        CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_PATIENT_INDEX,
                CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME,
                CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        StartDateTime startDateTime = ParserUtil.parseStartDateTime(argMultimap
                .getValue(CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME).get());
        EndDateTime endDateTime = ParserUtil.parseEndDateTime(argMultimap
                .getValue(CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME).get(), argMultimap
                .getValue(CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME).get());

        Long years = ParserUtil.parseFrequency(argMultimap.getValue(CliSyntax.PREFIX_RECUR_YEARS).get());
        Long months = ParserUtil.parseFrequency(argMultimap.getValue(CliSyntax.PREFIX_RECUR_MONTHS).get());
        Long weeks = ParserUtil.parseFrequency(argMultimap.getValue(CliSyntax.PREFIX_RECUR_WEEKS).get());
        Long days = ParserUtil.parseFrequency(argMultimap.getValue(CliSyntax.PREFIX_RECUR_DAYS).get());
        Long hours = ParserUtil.parseFrequency(argMultimap.getValue(CliSyntax.PREFIX_RECUR_HOURS).get());
        Long minutes = ParserUtil.parseFrequency(argMultimap.getValue(CliSyntax.PREFIX_RECUR_MINUTES).get());
        Long[] freqArray = {years, months, weeks, days, hours, minutes};
        RecurringDateTime frequency = new RecurringDateTime(freqArray);

        Index indexPatient = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_PATIENT_INDEX).get());
        String description = ParserUtil.parseDescription(argMultimap
                .getValue(CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION).get());

        Appointment appointment = new Appointment(startDateTime, endDateTime, frequency, indexPatient, description);

        return new AddAppointmentCommand(appointment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
