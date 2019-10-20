package seedu.address.logic.parser.bio;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INCONSISTENT_SUBARGUMENT_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SUBARGUMENT_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_SUBARGUMENT_INDEX_OUT_OF_BOUNDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DP_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOALS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OTHER_BIO_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROFILE_DESC;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.bio.EditBioCommand;
import seedu.address.logic.commands.bio.EditBioCommand.EditUserDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bio.Goal;
import seedu.address.model.bio.MedicalCondition;
import seedu.address.model.bio.Phone;

/**
 * Parses input arguments and creates a new EditBioCommand object
 */
public class EditBioCommandParser implements Parser<EditBioCommand> {

    // Separator used to separate sub-arguments.
    private static final String SEPARATOR = "/";

    /**
     * Parses the given {@code String} of arguments in the context of the EditBioCommand
     * and returns an EditBioCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBioCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DP_PATH, PREFIX_PROFILE_DESC, PREFIX_NRIC,
                        PREFIX_GENDER, PREFIX_DATE_OF_BIRTH, PREFIX_CONTACT_NUMBER, PREFIX_EMERGENCY_CONTACT,
                        PREFIX_MEDICAL_CONDITION, PREFIX_ADDRESS, PREFIX_GOALS, PREFIX_OTHER_BIO_INFO);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBioCommand.MESSAGE_USAGE));
        }

        EditUserDescriptor editUserDescriptor = new EditBioCommand.EditUserDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editUserDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_DP_PATH).isPresent()) {
            editUserDescriptor.setDpPath(ParserUtil.parseDpPath(argMultimap.getValue(PREFIX_DP_PATH)));
        }

        if (argMultimap.getValue(PREFIX_PROFILE_DESC).isPresent()) {
            editUserDescriptor.setProfileDesc(ParserUtil.parseProfileDesc(argMultimap.getValue(PREFIX_PROFILE_DESC)));
        }

        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editUserDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC)));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editUserDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER)));
        }
        if (argMultimap.getValue(PREFIX_DATE_OF_BIRTH).isPresent()) {
            editUserDescriptor.setDateOfBirth(ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DATE_OF_BIRTH)));
        }

        if (!argMultimap.getAllValues(PREFIX_CONTACT_NUMBER).isEmpty()) {
            List<String> contactNumberStringList = argMultimap.getAllValues(PREFIX_CONTACT_NUMBER);
            if (hasIndexes(contactNumberStringList, PREFIX_CONTACT_NUMBER)) {
                addIndividualEdits(contactNumberStringList, PREFIX_CONTACT_NUMBER, editUserDescriptor);
            } else {
                parsePhonesForEdit(contactNumberStringList).ifPresent(editUserDescriptor::setContactNumbers);
            }
        }

        if (!argMultimap.getAllValues(PREFIX_EMERGENCY_CONTACT).isEmpty()) {
            List<String> emergencyContactStringList = argMultimap.getAllValues(PREFIX_EMERGENCY_CONTACT);
            if (hasIndexes(emergencyContactStringList, PREFIX_EMERGENCY_CONTACT)) {
                addIndividualEdits(emergencyContactStringList, PREFIX_EMERGENCY_CONTACT, editUserDescriptor);
            } else {
                parsePhonesForEdit(emergencyContactStringList).ifPresent(editUserDescriptor::setEmergencyContacts);
            }
        }

        if (!argMultimap.getAllValues(PREFIX_MEDICAL_CONDITION).isEmpty()) {
            List<String> medicalConditionStringList = argMultimap.getAllValues(PREFIX_MEDICAL_CONDITION);
            if (hasIndexes(medicalConditionStringList, PREFIX_MEDICAL_CONDITION)) {
                addIndividualEdits(medicalConditionStringList, PREFIX_MEDICAL_CONDITION, editUserDescriptor);
            } else {
                parseMedicalConditionsForEdit(medicalConditionStringList)
                        .ifPresent(editUserDescriptor::setMedicalConditions);
            }
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editUserDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)));
        }

        if (!argMultimap.getAllValues(PREFIX_GOALS).isEmpty()) {
            List<String> goalStringList = argMultimap.getAllValues(PREFIX_GOALS);
            if (hasIndexes(goalStringList, PREFIX_GOALS)) {
                addIndividualEdits(goalStringList, PREFIX_GOALS, editUserDescriptor);
            } else {
                parseGoalsForEdit(goalStringList)
                        .ifPresent(editUserDescriptor::setGoals);
            }
        }

        if (argMultimap.getValue(PREFIX_OTHER_BIO_INFO).isPresent()) {
            editUserDescriptor.setOtherBioInfo(ParserUtil
                    .parseOtherBioInfo(argMultimap.getValue(PREFIX_OTHER_BIO_INFO)));
        }

        if (!editUserDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBioCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBioCommand(editUserDescriptor);
    }

    /**
     * Adds individual edits and corresponding indices given in the stringList to the editUserDescriptor.
     * @param stringList  List of strings representing tokens for given prefixes.
     * @param prefix Prefix containing values with indexes to specify individual items to replace in
     *               editUserDescription.
     * @param editUserDescriptor EditUserDescriptor instance containing items that replace those in the original list.
     * @throws ParseException
     */
    private static void addIndividualEdits(List<String> stringList, Prefix prefix,
                                           EditUserDescriptor editUserDescriptor) throws ParseException {
        for (String phoneString : stringList) {
            HashMap<Index, String> indexMap = getValidatedIndexValueMap(phoneString, prefix);
            Index index = indexMap.keySet().iterator().next();
            if (PREFIX_CONTACT_NUMBER.equals(prefix)) {
                Phone contactNumber = ParserUtil.parsePhone(indexMap.get(index));
                HashMap<Index, Phone> indexContactNumberMap = new HashMap<>();
                indexContactNumberMap.put(index, contactNumber);
                editUserDescriptor.addToIndividualContactNumberEdit(indexContactNumberMap);
            } else if (PREFIX_EMERGENCY_CONTACT.equals(prefix)) {
                Phone emergencyContact = ParserUtil.parsePhone(indexMap.get(index));
                HashMap<Index, Phone> indexEmergencyContactMap = new HashMap<>();
                indexEmergencyContactMap.put(index, emergencyContact);
                editUserDescriptor.addToIndividualEmergencyContactsEdit(indexEmergencyContactMap);
            } else if (PREFIX_MEDICAL_CONDITION.equals(prefix)) {
                MedicalCondition medicalCondition = ParserUtil.parseMedicalCondition(indexMap.get(index));
                HashMap<Index, MedicalCondition> indexMedicalConditionMap = new HashMap<>();
                indexMedicalConditionMap.put(index, medicalCondition);
                editUserDescriptor.addToIndividualMedicalConditionsEdit(indexMedicalConditionMap);
            } else if (PREFIX_GOALS.equals(prefix)) {
                Goal goal = ParserUtil.parseGoal(indexMap.get(index));
                HashMap<Index, Goal> indexGoalMap = new HashMap<>();
                indexGoalMap.put(index, goal);
                editUserDescriptor.addToIndividualGoalsEdit(indexGoalMap);
            } else {
                throw new ParseException(MESSAGE_INVALID_SUBARGUMENT_INDEX);
            }
        }
    }

    /**
     * Returns whether or not given stringList contains indexes.
     * Ensures that presence of indexing given for each type of prefix is consistent.
     * @param stringList List of strings representing tokens for given prefixes.
     * @param prefix Prefix which has values possibly containing indexes.
     */
    private static boolean hasIndexes(List<String> stringList, Prefix prefix) throws ParseException {
        if (stringList.size() > 0) {
            boolean containsSeparator = stringList.get(0).contains(SEPARATOR);
            for (int i = 1; i < stringList.size(); i++) {
                if (stringList.get(i).contains(SEPARATOR) != containsSeparator) {
                    throw new ParseException(MESSAGE_INCONSISTENT_SUBARGUMENT_INDEX);
                }
            }
            return containsSeparator;
        } else {
            return false;
        }
    }

    /**
     * Returns a map containing a validated one-based index and value, given a string.
     * @param subArgs String containing items representing a one-based index and value to be
     *               validated respectively.
     * @param prefix Prefix for which the map is to be generated for.
     * @return A HashMap containing a validated one-based index and value for the prefix's value.
     * @throws ParseException if the tokens are of invalid size, or index cannot be parsed.
     */
    private static HashMap<Index, String> getValidatedIndexValueMap(String subArgs, Prefix prefix)
            throws ParseException {
        String[] tokens = subArgs.split(SEPARATOR);

        if (tokens.length == 1) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        } else if (tokens.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBioCommand.MESSAGE_USAGE));
        }

        String indexString = tokens[0];
        String valueString = tokens[1];

        try {
            int index = Integer.parseInt(indexString);
            if (index < 0) {
                throw new NumberFormatException();
            }
            HashMap<Index, String> indexMap = new HashMap<>(1);
            indexMap.put(Index.fromOneBased(index), valueString);
            return indexMap;
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_SUBARGUMENT_INDEX);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_SUBARGUMENT_INDEX_OUT_OF_BOUNDS);
        }
    }

    /**
     * Parses {@code Collection<String> phones} into a {@code List<Phone>} if {@code phones} is non-empty.
     * If {@code phones} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Phone>} containing zero phones.
     */
    private Optional<List<Phone>> parsePhonesForEdit(Collection<String> phones) throws ParseException {
        assert phones != null;

        if (phones.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> phoneList = phones.size() == 1 && phones.contains("") ? Collections.emptyList() : phones;
        return Optional.of(ParserUtil.parsePhones(phoneList));
    }

    /**
     * Parses {@code Collection<String> medicalConditions} into a {@code List<MedicalCondition>} if
     * {@code medicalConditions} is non-empty.
     * If {@code medicalConditions} contain only one element which is an empty string, it will be parsed into a
     * {@code List<MedicalCondition>} containing zero medicalConditions.
     */
    private Optional<List<MedicalCondition>> parseMedicalConditionsForEdit(Collection<String> medicalConditions)
            throws ParseException {
        assert medicalConditions != null;

        if (medicalConditions.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> medicalConditionList = medicalConditions.size() == 1 && medicalConditions.contains("")
                ? Collections.emptyList()
                : medicalConditions;
        return Optional.of(ParserUtil.parseMedicalConditions(medicalConditionList));
    }

    /**
     * Parses {@code Collection<String> goals} into a {@code List<Goal>} if {@code goals} is non-empty.
     * If {@code goals} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Goal>} containing zero goals.
     */
    private Optional<List<Goal>> parseGoalsForEdit(Collection<String> goals) throws ParseException {
        assert goals != null;

        if (goals.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> goalList = goals.size() == 1 && goals.contains("") ? Collections.emptyList() : goals;
        return Optional.of(ParserUtil.parseGoals(goalList));
    }



}
