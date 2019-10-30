package seedu.address.model.achievements;

import static java.util.Map.entry;
import static seedu.address.model.record.RecordType.BLOODSUGAR;
import static seedu.address.model.record.RecordType.BMI;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.achievements.bloodsugar.BloodSugarBronze;
import seedu.address.model.achievements.bloodsugar.BloodSugarDiamond;
import seedu.address.model.achievements.bloodsugar.BloodSugarGold;
import seedu.address.model.achievements.bloodsugar.BloodSugarPlatinum;
import seedu.address.model.achievements.bloodsugar.BloodSugarSilver;
import seedu.address.model.achievements.bmi.BmiBronze;
import seedu.address.model.achievements.bmi.BmiDiamond;
import seedu.address.model.achievements.bmi.BmiGold;
import seedu.address.model.achievements.bmi.BmiPlatinum;
import seedu.address.model.achievements.bmi.BmiSilver;
import seedu.address.model.record.RecordType;

/**
 * Class containing a predefined map of achievements that maps record types to respective achievement lists.
 */
public class AchievementsMap {

    private static final List<Achievement> BLOOD_SUGAR_ACHIEVEMENTS_LIST = Collections.unmodifiableList(List.of(
            new BloodSugarDiamond(), new BloodSugarPlatinum(), new BloodSugarGold(),
            new BloodSugarSilver(), new BloodSugarBronze()));

    private static final List<Achievement> BMI_ACHIEVEMENTS_LIST = Collections.unmodifiableList(List.of(
            new BmiDiamond(), new BmiPlatinum(), new BmiGold(),
            new BmiSilver(), new BmiBronze()));

    public static final Map<RecordType, List<Achievement>> ACHIEVEMENTS_MAP = Collections
            .unmodifiableMap(new LinkedHashMap<>(Map.ofEntries(
                    entry(BLOODSUGAR, BLOOD_SUGAR_ACHIEVEMENTS_LIST),
                    entry(BMI, BMI_ACHIEVEMENTS_LIST))));
}
