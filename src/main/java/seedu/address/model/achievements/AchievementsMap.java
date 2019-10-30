package seedu.address.model.achievements;

import static java.util.Map.entry;
import static seedu.address.model.record.RecordType.BLOODSUGAR;
import static seedu.address.model.record.RecordType.BMI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            .unmodifiableMap(Map.ofEntries(
                    entry(BLOODSUGAR, BLOOD_SUGAR_ACHIEVEMENTS_LIST),
                    entry(BMI, BMI_ACHIEVEMENTS_LIST)));

    private static final Comparator<RecordType> RECORD_TYPE_COMPARATOR = Comparator.comparing(Enum::toString);

    /**
     * Returns whether or not a given achievement map is the same as the current one stored in the program.
     * @param prevAchievementsMap Previous achievement map to be checked again current achievement map stored.
     * @return Primitive boolean denoting whether the given achievement map is the same as the current one stored in
     * the program.
     */
    public static boolean currAchievementsMapIsSameAs(Map<RecordType, List<Achievement>> prevAchievementsMap) {
        if (prevAchievementsMap == null) {
            return false;
        }

        if (prevAchievementsMap.size() != ACHIEVEMENTS_MAP.size()) {
            return false;
        }
        List<RecordType> currAchievementsMapKeyList = getSortedKeyList(ACHIEVEMENTS_MAP, RECORD_TYPE_COMPARATOR);
        List<RecordType> prevAchievementsMapKeyList = getSortedKeyList(prevAchievementsMap, RECORD_TYPE_COMPARATOR);

        if (!sameElementsAreInLists(currAchievementsMapKeyList, prevAchievementsMapKeyList)) {
            return false;
        }

        for (RecordType recordtype: currAchievementsMapKeyList) {
            if (!sameElementsAreInLists(ACHIEVEMENTS_MAP.get(recordtype), prevAchievementsMap.get(recordtype))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether two lists contain the same elements.
     * @param firstList List containing elements to compare with the second list.
     * @param secondList List containing elements to compare with the first list.
     * @param <T> Generic type T of elements in the given lists.
     * @return Primitive boolean denoting whether the two lists contain the same elements.
     */
    private static <T> boolean sameElementsAreInLists(List<T> firstList, List<T> secondList) {
        for (int i = 0; i < firstList.size(); i++) {
            if (!firstList.get(i).equals(secondList.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a sorted List of map keys, given a map and comparator.
     * @param map Map containing keys to be returned in a sorted List.
     * @param keyComparator Comparator used to compare keys in the given map.
     * @param <V> Generic type V of values in the given map.
     * @param <K> Generic type K of keys in the given map.
     * @return List of keys of the map, arranged in order based on given key comparator.
     */
    private static <V, K> List<K> getSortedKeyList(Map<K, V> map, Comparator<K> keyComparator) {
        List<K> keyList = new ArrayList<>(map.keySet());
        keyList.sort(keyComparator);
        return keyList;
    }


}
