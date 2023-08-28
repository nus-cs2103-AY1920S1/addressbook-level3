package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALD_FRIDE_ID_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.logic.parser.utility.UpdateEntityDescriptor;
import seedu.address.logic.parser.utility.UpdateWorkerDescriptor;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.worker.Photo;

//@@author ambervoong
/**
 * Parses input arguments and creates a new UpdateCommand object.
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Returns an {@code ArgumentMultimap} that tokenizes prefixes the command can receive.
     * @param argsString string of arguments
     * @return
     */
    private ArgumentMultimap tokenize(String argsString) {
        return ArgumentTokenizer.tokenize(argsString,
                PREFIX_FLAG,
                PREFIX_IDENTIFICATION_NUMBER,
                PREFIX_SEX,
                PREFIX_NAME, // Start of Body Fields
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
                PREFIX_PHONE_NOK,
                PREFIX_BODY_DETAILS,
                PREFIX_PHONE_NUMBER, // Worker-only Fields
                PREFIX_DATE_JOINED,
                PREFIX_DESIGNATION,
                PREFIX_PHOTO,
                PREFIX_EMPLOYMENT_STATUS);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = tokenize(args);
        String flag = argMultimap.getValue(PREFIX_FLAG).orElse("");
        String idNum = argMultimap.getValue(PREFIX_IDENTIFICATION_NUMBER).orElse(null);

        IdentificationNumber identificationNumber;
        UpdateEntityDescriptor updateEntityDescriptor;

        boolean arePrefixesPresent;
        switch (flag) {
        case "b":
            arePrefixesPresent = arePrefixesPresent(argMultimap,
                    PREFIX_NAME,
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
                    PREFIX_PHONE_NOK,
                    PREFIX_BODY_DETAILS);
            break;
        case "w":
            arePrefixesPresent = arePrefixesPresent(argMultimap,
                PREFIX_PHONE_NUMBER,
                    PREFIX_SEX,
                    PREFIX_DATE_OF_BIRTH,
                    PREFIX_DATE_JOINED,
                    PREFIX_DESIGNATION,
                    PREFIX_EMPLOYMENT_STATUS,
                    PREFIX_PHOTO);
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));

        }

        if (!arePrefixesPresent || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        if (idNum == null || idNum.isEmpty() || idNum.chars().anyMatch(Character::isLetter)
                || Integer.parseInt(idNum) <= 0) {
            throw new ParseException(MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX + ". Please give a "
                    + "positive non-zero number.");
        }

        // Get input fields from arguments.
        switch(flag) {
        case "b":
            identificationNumber = IdentificationNumber.customGenerateId("B", Integer.parseInt(idNum));
            updateEntityDescriptor = parseBodyFields(new UpdateBodyDescriptor(), argMultimap);
            return new UpdateCommand(identificationNumber, updateEntityDescriptor);
        case "w":
            identificationNumber = IdentificationNumber.customGenerateId("W", Integer.parseInt(idNum));
            updateEntityDescriptor = parseWorkerFields(new UpdateWorkerDescriptor(), argMultimap);
            return new UpdateCommand(identificationNumber, updateEntityDescriptor);
        case "f":
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "INVALID FLAG: -f "
                    + "is not valid for this command."));
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
     * @param bodyDescriptor contains values for various fields in a Body.
     * @param argMultimap contains mappings of arguments to their prefixes.
     * @return an UpdateBodyDescriptor containing the new Body values.
     * @throws ParseException if none of the fields were changed.
     */
    private UpdateEntityDescriptor parseBodyFields(UpdateBodyDescriptor bodyDescriptor, ArgumentMultimap argMultimap)
            throws ParseException {

        if (!argMultimap.getValue(PREFIX_NAME).orElse("").isEmpty()) {
            bodyDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElse("")));
        }
        if (!argMultimap.getValue(PREFIX_SEX).orElse("").isEmpty()) {
            bodyDescriptor.setSex(ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).orElse("")));
        }
        if (!argMultimap.getValue(PREFIX_NRIC).orElse("").isEmpty()) {
            bodyDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (!argMultimap.getValue(PREFIX_RELIGION).orElse("").isEmpty()) {
            bodyDescriptor.setReligion(ParserUtil.parseStringFields(argMultimap.getValue(PREFIX_RELIGION).get()));
        }
        if (!argMultimap.getValue(PREFIX_CAUSE_OF_DEATH).orElse("").isEmpty()) {
            bodyDescriptor.setCauseOfDeath(argMultimap.getValue(PREFIX_CAUSE_OF_DEATH).get());
        }
        if (!argMultimap.getValue(PREFIX_ORGANS_FOR_DONATION).orElse("").isEmpty()) {
            bodyDescriptor.setOrgansForDonation(ParserUtil.parseOrgansForDonation(
                    argMultimap.getValue(PREFIX_ORGANS_FOR_DONATION).get()));
        }
        if (!argMultimap.getValue(PREFIX_STATUS).orElse("").isEmpty()) {
            bodyDescriptor.setBodyStatus(ParserUtil.parseBodyStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }
        if (!argMultimap.getValue(PREFIX_FRIDGE_ID).orElse("").isEmpty()) {
            String idNum = argMultimap.getValue(PREFIX_FRIDGE_ID).orElse(null);
            try {
                IdentificationNumber identificationNumber = IdentificationNumber
                        .customGenerateId("F", Integer.parseInt(idNum));
                bodyDescriptor.setFridgeId(identificationNumber);
            } catch (NumberFormatException exp) {
                throw new ParseException(MESSAGE_INVALD_FRIDE_ID_FORMAT);
            }
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
            bodyDescriptor.setKinPhoneNumber(ParserUtil.parsePhoneNumber(argMultimap.getValue(PREFIX_PHONE_NOK).get()));
        }

        String details = ParserUtil.parseStringFields(argMultimap.getValue(PREFIX_BODY_DETAILS).orElse(""));
        bodyDescriptor.setDetails(details);

        if (!bodyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_EDITED);
        }
        return bodyDescriptor;
    }

    /**
     * Maps arguments to an UpdateWorkerDescriptor. The fields are all optional, provided at least one field was
     * specified.
     * @param workerDescriptor contains values for various fields in a Worker.
     * @param argMultimap contains mappings of arguments to their prefixes.
     * @return an UpdateWorkerDescriptor containing the new Worker values.
     * @throws ParseException if none of the fields were changed.
     */
    private UpdateEntityDescriptor parseWorkerFields(UpdateWorkerDescriptor workerDescriptor,
                                                   ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_PHONE_NUMBER).orElse("").isEmpty()) {
            workerDescriptor.setPhone(ParserUtil.parsePhoneNumber(argMultimap.getValue(PREFIX_PHONE_NUMBER).get()));
        }
        if (!argMultimap.getValue(PREFIX_SEX).orElse("").isEmpty()) {
            workerDescriptor.setSex(ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).orElse("")));
        }
        if (!argMultimap.getValue(PREFIX_DATE_OF_BIRTH).orElse("").isEmpty()) {
            workerDescriptor.setDateOfBirth(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_OF_BIRTH).get()));
        }
        if (!argMultimap.getValue(PREFIX_DATE_JOINED).orElse("").isEmpty()) {
            workerDescriptor.setDateJoined(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_JOINED).get()));
        }
        if (!argMultimap.getValue(PREFIX_DESIGNATION).orElse("").isEmpty()) {
            workerDescriptor.setDesignation(argMultimap.getValue(PREFIX_DESIGNATION).get());
        }
        if (!argMultimap.getValue(PREFIX_EMPLOYMENT_STATUS).orElse("").isEmpty()) {
            workerDescriptor.setEmploymentStatus(argMultimap.getValue(PREFIX_EMPLOYMENT_STATUS).get());
        }

        Photo photo = ParserUtil.parsePhoto(argMultimap.getValue(PREFIX_PHOTO).orElse(""));
        workerDescriptor.setPhoto(photo);


        if (!workerDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_EDITED);
        }
        return workerDescriptor;
    }
}
//@@author
