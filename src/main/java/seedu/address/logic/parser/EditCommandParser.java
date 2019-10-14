package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICALHISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.EditProfileCommand;
import seedu.address.logic.commands.EditProfileCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.profile.medical.MedicalHistory;

/**
 * Parses input arguments and creates a new EditProfileCommand object
 */
public class EditCommandParser implements Parser<EditProfileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditProfileCommand
     * and returns an EditProfileCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditProfileCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DOB, PREFIX_GENDER, PREFIX_BLOODTYPE,
                        PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_MEDICALHISTORY);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_DOB).isPresent()) {
            editPersonDescriptor.setDateOfBirth(ParserUtil.parseDoB(argMultimap.getValue(PREFIX_DOB).get()));
        }

        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editPersonDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }

        if (argMultimap.getValue(PREFIX_BLOODTYPE).isPresent()) {
            editPersonDescriptor.setBloodType(ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE).get()));
        }

        if (argMultimap.getValue(PREFIX_WEIGHT).isPresent()) {
            editPersonDescriptor.setWeight(ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get()));
        }

        if (argMultimap.getValue(PREFIX_HEIGHT).isPresent()) {
            editPersonDescriptor.setHeight(ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT).get()));
        }

        parseMedicalHistoriesForEdit(argMultimap.getAllValues(PREFIX_MEDICALHISTORY))
                .ifPresent(editPersonDescriptor::setMedicalHistories);

        if (!editPersonDescriptor.isAnyFieldEdited() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(EditProfileCommand.MESSAGE_NOT_EDITED);
        }

        return new EditProfileCommand(editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> medicalHistories} into a {@code Set<MedicalHistory>} if
     * {@code medicalHistories} is non-empty.
     *
     * If {@code medicalHistories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<MedicalHistory>} containing zero medicalHistories.
     */
    private Optional<Set<MedicalHistory>> parseMedicalHistoriesForEdit(Collection<String> medicalHistories)
            throws ParseException {
        assert medicalHistories != null;

        if (medicalHistories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = medicalHistories.size() == 1 && medicalHistories.contains("")
                ? Collections.emptySet() : medicalHistories;
        return Optional.of(ParserUtil.parseMedicalHistories(tagSet));
    }

}
