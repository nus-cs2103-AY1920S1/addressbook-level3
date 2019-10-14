package seedu.jarvis.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.address.person.Address;
import seedu.jarvis.model.address.person.Email;
import seedu.jarvis.model.address.person.Name;
import seedu.jarvis.model.address.person.Phone;
import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.model.cca.EquipmentList;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code CcaName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    //=========== CcaTracker =============================================================================

    /**
     * Parses a {@code String name} into a {@code CcaName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static CcaName parseCcaName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!CcaName.isValidName(trimmedName)) {
            throw new ParseException(CcaName.MESSAGE_CONSTRAINTS);
        }
        return new CcaName(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code CcaName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static CcaType parseCcaType(String ccaType) throws ParseException {
        requireNonNull(ccaType);
        String trimmedCcaType = ccaType.trim();
        if (!CcaType.isValidCcaType(trimmedCcaType)) {
            throw new ParseException(CcaType.MESSAGE_CONSTRAINTS);
        }
        return new CcaType(trimmedCcaType);
    }

    /**
     * Parses a {@code String equipment} into a {@code Equipment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code equipment} is invalid.
     */
    public static Equipment parseEquipment(String equipment) throws ParseException {
        requireNonNull(equipment);
        String trimmedEquipment = equipment.trim();
        if (!Equipment.isValidEquipmentName(trimmedEquipment)) {
            throw new ParseException(Equipment.MESSAGE_CONSTRAINTS);
        }
        return new Equipment(trimmedEquipment);
    }

    /**
     * Parses {@code Collection<String> equipments} into a {@code EquipmentList}.
     */
    public static EquipmentList parseEquipments(Collection<String> equipments) throws ParseException {
        requireNonNull(equipments);
        final EquipmentList equipmentList = new EquipmentList();
        for (String equipmentName : equipments) {
            equipmentList.addEquipment(parseEquipment(equipmentName));
        }
        return equipmentList;
    }
}
