package seedu.jarvis.logic.parser.cca;

import static java.util.Objects.requireNonNull;

import java.util.Collection;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.model.cca.EquipmentList;
import seedu.jarvis.model.cca.ccaprogress.CcaCurrentProgress;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.model.cca.exceptions.DuplicateEquipmentException;

/**
 * Contains utility methods used for parsing strings in the Cca classes.
 */
public class CcaParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final String MESSSAGE_INVALID_PROGRESS_LEVEL = "Progress is not a non-negative unsigned integer.";

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
    public static EquipmentList parseEquipments(Collection<String> equipments) throws ParseException,
            DuplicateEquipmentException {
        requireNonNull(equipments);
        final EquipmentList equipmentList = new EquipmentList();
        for (String equipmentName : equipments) {
            equipmentList.addEquipment(parseEquipment(equipmentName));
        }
        return equipmentList;
    }

    /**
     * Parses a {@code String ccaMilestone} into a {@code CcaMilestone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ccaMilestone} is invalid.
     */
    public static CcaMilestone parseCcaMilestone(String ccaMilestone) throws ParseException {
        requireNonNull(ccaMilestone);
        String trimmedCcaMilestone = ccaMilestone.trim();
        if (!CcaMilestone.isValidCcaMilestone(trimmedCcaMilestone)) {
            throw new ParseException(CcaMilestone.MESSAGE_CONSTRAINTS);
        }
        return new CcaMilestone(trimmedCcaMilestone);
    }

    /**
     * Parses {@code Collection<String> ccaMilestones} into a {@code CcaMilestoneList}.
     */
    public static CcaMilestoneList parseCcaMilestones(Collection<String> ccaMilestones) throws ParseException {
        requireNonNull(ccaMilestones);
        final CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
        for (String ccaMilestoneName : ccaMilestones) {
            ccaMilestoneList.add(parseCcaMilestone(ccaMilestoneName));
        }
        return ccaMilestoneList;
    }

    /**
     * Parses {@code currentProgress} into a {@code CcaCurrentProgress}.
     */
    public static CcaCurrentProgress parseCcaCurrentProgress(String currentProgress) throws ParseException {
        requireNonNull(currentProgress);
        String trimmedCurrentProgress = currentProgress.trim();
        if (!StringUtil.isNonNegativeUnsignedInteger(currentProgress)) {
            throw new ParseException(MESSSAGE_INVALID_PROGRESS_LEVEL);
        }
        int currentProgressInt = Integer.parseInt(trimmedCurrentProgress);
        return new CcaCurrentProgress(currentProgressInt);
    }
}
