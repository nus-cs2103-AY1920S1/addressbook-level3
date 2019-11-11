package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GST_REGISTRATION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGISTRATION_NUMBER;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.CompanyDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SavePdfCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SavePdfCommand
     * and returns a SavePdfCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Prefix[] prefixes = new Prefix[]{PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_FAX, PREFIX_EMAIL,
                                            PREFIX_REGISTRATION_NUMBER, PREFIX_GST_REGISTRATION_NUMBER};

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, prefixes);

        if (!ParserUtil.isAnyPrefixPresent(argMultimap, prefixes)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        CompanyDescriptor companyDescriptor = new CompanyDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            companyDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            companyDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            companyDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_FAX).isPresent()) {
            companyDescriptor.setFax(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_FAX).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            companyDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_REGISTRATION_NUMBER).isPresent()) {
            companyDescriptor.setRegistrationNumber(ParserUtil.parseRegistrationNumber(argMultimap.getValue(
                    PREFIX_REGISTRATION_NUMBER).get()));
        }
        if (argMultimap.getValue(PREFIX_GST_REGISTRATION_NUMBER).isPresent()) {
            companyDescriptor.setGstRegistrationNumber(ParserUtil.parseGstRegistrationNumber(argMultimap.getValue(
                    PREFIX_GST_REGISTRATION_NUMBER).get()));
        }

        if (!companyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOTHING_IS_PROVIDED);
        }

        return new UpdateCommand(companyDescriptor);

    }

}
