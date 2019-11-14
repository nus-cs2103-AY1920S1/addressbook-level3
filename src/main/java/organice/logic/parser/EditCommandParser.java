package organice.logic.parser;

import static java.util.Objects.requireNonNull;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import organice.logic.commands.EditCommand;
import organice.logic.commands.EditCommand.EditPersonDescriptor;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Nric;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_NRIC, PREFIX_NAME,
                        PREFIX_AGE, PREFIX_PHONE, PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_DOCTOR_IN_CHARGE,
                                PREFIX_ORGAN_EXPIRY_DATE, PREFIX_ORGAN, PREFIX_TISSUE_TYPE);

        Nric nric;

        try {
            nric = ParserUtil.parseNric(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            editPersonDescriptor.setAge(ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get()));
        }

        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editPersonDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }

        if (argMultimap.getValue(PREFIX_BLOOD_TYPE).isPresent()) {
            editPersonDescriptor.setBloodType(ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOOD_TYPE).get()));
        }

        if (argMultimap.getValue(PREFIX_DOCTOR_IN_CHARGE).isPresent()) {
            editPersonDescriptor.setDoctorInCharge(ParserUtil.parseDoctorInCharge(argMultimap.getValue(
                    PREFIX_DOCTOR_IN_CHARGE).get()));
        }

        if (argMultimap.getValue(PREFIX_ORGAN_EXPIRY_DATE).isPresent()) {
            editPersonDescriptor.setOrganExpiryDate(ParserUtil.parseOrganExpiryDate(
                    argMultimap.getValue(PREFIX_ORGAN_EXPIRY_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_ORGAN).isPresent()) {
            editPersonDescriptor.setOrgan(ParserUtil.parseOrgan(argMultimap.getValue(PREFIX_ORGAN).get()));
        }

        if (argMultimap.getValue(PREFIX_TISSUE_TYPE).isPresent()) {
            editPersonDescriptor.setTissueType(ParserUtil.parseTissueType(argMultimap.getValue(
                    PREFIX_TISSUE_TYPE).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(nric, editPersonDescriptor);
    }

}
