package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIRST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIDDLE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.logic.parser.utility.UpdateEntityDescriptor;
import seedu.address.model.entity.IdentificationNumber;

//@@author ambervoong
/**
 * Parses input arguments and creates a new UpdateCommand object.
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_FLAG,
                        PREFIX_IDENTIFICATION_NUMBER,
                        PREFIX_SEX,
                        PREFIX_FIRST_NAME, // Start of Body Fields
                        PREFIX_MIDDLE_NAME,
                        PREFIX_LAST_NAME,
                        PREFIX_NRIC,
                        PREFIX_RELIGION,
                        PREFIX_CAUSE_OF_DEATH,
                        PREFIX_ORGANS_FOR_DONATION,
                        PREFIX_STATUS,
                        PREFIX_FRIDGE_ID,
                        PREFIX_DATE_OF_BIRTH,
                        PREFIX_DATE_OF_DEATH,
                        PREFIX_NAME_NOK,
                        PREFIX_RELATIONSHIP,
                        PREFIX_PHONE_NOK
                        ); // Start of Worker Fields


        String flag = argMultimap.getValue(PREFIX_FLAG).orElse("");
        String idNum = argMultimap.getValue(PREFIX_IDENTIFICATION_NUMBER).orElse(null);

        IdentificationNumber identificationNumber;
        UpdateEntityDescriptor updateEntityDescriptor;

        boolean arePrefixesPresent;

        switch (flag) {
        case "b":
            identificationNumber = IdentificationNumber.customGenerateId("B", Integer.parseInt(idNum));
            arePrefixesPresent = arePrefixesPresent(argMultimap,
                    PREFIX_FIRST_NAME,
                    PREFIX_MIDDLE_NAME,
                    PREFIX_LAST_NAME,
                    PREFIX_SEX,
                    PREFIX_NRIC,
                    PREFIX_RELIGION,
                    PREFIX_CAUSE_OF_DEATH,
                    PREFIX_ORGANS_FOR_DONATION,
                    PREFIX_STATUS,
                    PREFIX_FRIDGE_ID,
                    PREFIX_DATE_OF_BIRTH,
                    PREFIX_DATE_OF_DEATH,
                    PREFIX_NAME_NOK,
                    PREFIX_RELATIONSHIP,
                    PREFIX_PHONE_NOK);
            break;
        case "w":
            identificationNumber = IdentificationNumber.customGenerateId("W", Integer.parseInt(idNum));

            // Add later.
        case "f":
            identificationNumber = IdentificationNumber.customGenerateId("F", Integer.parseInt(idNum));
            //add later
        default:
            throw new ParseException(String.format("INVALID FLAG", UpdateCommand.MESSAGE_USAGE));

        }

        if (!arePrefixesPresent || identificationNumber == null || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format("NO ARGUMENTS OR ID WAS GIVEN", UpdateCommand.MESSAGE_USAGE));
            // todo: update
        }

        // Get fields from arguments.
        switch(flag) {
        case "b":
            updateEntityDescriptor = parseBodyFields(new UpdateBodyDescriptor(), argMultimap);
            return new UpdateCommand(identificationNumber, updateEntityDescriptor);
        case "w":
            // fallthrough
        case "f":
            // fallthrough
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "INVALID FLAG: \n"
                    + UpdateCommand.MESSAGE_USAGE));

        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Maps arguments to an UpdateBodyDescriptor. The fields are all optional, provided at least one field was
     * specified.
     * @param bodyDescriptor
     * @param argMultimap
     * @return
     * @throws ParseException
     */
    private UpdateEntityDescriptor parseBodyFields(UpdateBodyDescriptor bodyDescriptor, ArgumentMultimap argMultimap)
            throws ParseException {

        String firstName = argMultimap.getValue(PREFIX_FIRST_NAME).orElse("");
        String middleName = argMultimap.getValue(PREFIX_MIDDLE_NAME).orElse("");
        String lastName = argMultimap.getValue(PREFIX_LAST_NAME).orElse("");
        if (!firstName.isEmpty() || !lastName.isEmpty()) {
            bodyDescriptor.setName(ParserUtil.parseName(firstName + " " + middleName + " " + lastName));
        }
        if (!argMultimap.getValue(PREFIX_SEX).orElse("").isEmpty()) {
            bodyDescriptor.setSex(ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).orElse("")));
        }
        if (!argMultimap.getValue(PREFIX_NRIC).orElse("").isEmpty()) {
            bodyDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (!argMultimap.getValue(PREFIX_RELIGION).orElse("").isEmpty()) {
            bodyDescriptor.setReligion(ParserUtil.parseReligion(argMultimap.getValue(PREFIX_RELIGION).get()));
        }
        if (!argMultimap.getValue(PREFIX_CAUSE_OF_DEATH).orElse("").isEmpty()) {
            bodyDescriptor.setCauseOfDeath(argMultimap.getValue(PREFIX_CAUSE_OF_DEATH).get());
        }
        if (!argMultimap.getValue(PREFIX_ORGANS_FOR_DONATION).orElse("").isEmpty()) {
            bodyDescriptor.setOrgansForDonation(ParserUtil.parseOrgansForDonation(
                    argMultimap.getValue(PREFIX_ORGANS_FOR_DONATION).get()));
        }
        if (!argMultimap.getValue(PREFIX_STATUS).orElse("").isEmpty()) {
            bodyDescriptor.setStatus(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }
        if (!argMultimap.getValue(PREFIX_FRIDGE_ID).orElse("").isEmpty()) {
            bodyDescriptor.setFridgeId(ParserUtil.parseIdentificationNumber(
                    argMultimap.getValue(PREFIX_FRIDGE_ID).get()));
        }
        if (!argMultimap.getValue(PREFIX_DATE_OF_BIRTH).orElse("").isEmpty()) {
            bodyDescriptor.setDateOfBirth(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_OF_BIRTH).get()));
        }
        if (!argMultimap.getValue(PREFIX_DATE_OF_DEATH).orElse("").isEmpty()) {
            bodyDescriptor.setDateOfDeath(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_OF_DEATH).get()));
        }
        if (!argMultimap.getValue(PREFIX_NAME_NOK).orElse("").isEmpty()) {
            bodyDescriptor.setNextOfKin(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME_NOK).get()));
        }
        if (!argMultimap.getValue(PREFIX_RELATIONSHIP).orElse("").isEmpty()) {
            bodyDescriptor.setRelationship(argMultimap.getValue(PREFIX_RELATIONSHIP).get());
        }
        if (!argMultimap.getValue(PREFIX_PHONE_NOK).orElse("").isEmpty()) {
            bodyDescriptor.setKinPhoneNumber(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE_NOK).get()));
        }
        if (!bodyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_EDITED);
        }
        return bodyDescriptor;
    }

}
