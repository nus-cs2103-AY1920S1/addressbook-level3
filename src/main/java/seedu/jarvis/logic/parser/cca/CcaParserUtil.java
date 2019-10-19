package seedu.jarvis.logic.parser.cca;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.model.cca.EquipmentList;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * Contains utility methods used for parsing strings in the Cca classes.
 */
public class CcaParserUtil {

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
