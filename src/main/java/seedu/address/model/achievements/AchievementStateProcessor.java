package seedu.address.model.achievements;

import static seedu.address.model.achievements.AchievementState.ACHIEVED;
import static seedu.address.model.achievements.AchievementState.PREVIOUSLY_ACHIEVED;
import static seedu.address.model.achievements.AchievementState.YET_TO_ACHIEVE;
import static seedu.address.model.achievements.DurationUnit.MONTH;
import static seedu.address.model.achievements.DurationUnit.WEEK;
import static seedu.address.model.achievements.DurationUnit.YEAR;
import static seedu.address.model.statistics.AverageType.DAILY;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.Model;
import seedu.address.model.record.RecordType;
import seedu.address.model.statistics.RecordContainsRecordTypePredicate;

/**
 * Class that processes the changes made to the list of achievements stored in this program, if any.
 */
public class AchievementStateProcessor {

    private static final int DAY_IN_DAYS = 1;
    private static final int WEEK_IN_DAYS = 7;
    private static final int MONTH_IN_DAYS = 30;
    private static final int YEAR_IN_DAYS = 365;

    private Model model;
    private Map<RecordType, Map<LocalDate, Double>> averageRecordMap;
    private Set<AchievementState> newStatesSet;
    private Set<RecordType> recordTypeSet;

    public AchievementStateProcessor(Model model) {
        this.model = model;
        averageRecordMap = new HashMap<>();
        newStatesSet = new HashSet<>();
        recordTypeSet = new HashSet<>(model.getAchievementsMap().keySet());
    }

    /**
     * Returns the set of changes made to the list of achievements stored in this program, if any.
     */
    public Set<AchievementState> getNewAchievementStates() {
        initialiseAverageRecordMap();

        for (RecordType recordType : model.getAchievementsMap().keySet()) {
            List<Achievement> achievementList = model.getAchievementsMap().get(recordType);
            processAchievements(achievementList, recordType);
        }
        return newStatesSet;
    }

    /**
     * Initialises a map that contains average daily records listed in descending order of date.
     */
    private void initialiseAverageRecordMap() {
        for (RecordType recordType : recordTypeSet) {
            model.updateFilteredRecordList(new RecordContainsRecordTypePredicate(recordType));
            model.calculateAverageMap(DAILY, recordType, model.getRecordList().size());

            // Sort by descending date
            Map<LocalDate, Double> averageMap = new TreeMap<>(Collections.reverseOrder());
            averageMap.putAll(model.getAverageMap());

            averageRecordMap.put(recordType, averageMap);
        }
    }

    /**
     * Sets the state of an achievement in this class and adds the new state to the set of new states stored by this
     * processor object.
     *
     * @param achievement           Achievement with state to be changed.
     * @param achievementStateToSet New achievement state of an achievement that is to be changed to.
     */
    private void setAchievementState(Achievement achievement, AchievementState achievementStateToSet) {
        if (achievement.getAchievementState() != achievementStateToSet) {
            newStatesSet.add(achievementStateToSet);
            achievement.setAchievementState(achievementStateToSet);
        }
    }

    /**
     * Demotes an achievement to previously achieved, if it's current state is achieved.
     *
     * @param achievement Achievement that is to have it's state demoted to previously achieved.
     */
    private void demote(Achievement achievement) {
        if (!achievement.isYetToBeAchieved()) {
            setAchievementState(achievement, YET_TO_ACHIEVE);
        }
    }

    /**
     * Promotes an achievement to achieved, if it's current state is not already achieved.
     *
     * @param achievement Achievement that is to have it's state promoted to achieved.
     */
    private void promote(Achievement achievement) {
        if (!achievement.isAchieved()) {
            setAchievementState(achievement, ACHIEVED);
        }
    }

    /**
     * Promotes an achievement that was previously achieved but no longer achieved, if it's current state is not
     * yet achieved.
     *
     * @param achievement Achievement that is to have it's state promoted to previously achieved.
     */
    private void promotePrev(Achievement achievement) {
        if (!achievement.isPreviouslyAchieved()) {
            setAchievementState(achievement, PREVIOUSLY_ACHIEVED);
        }
    }


    /**
     * Returns whether or not the requirement for an achievement has previously been met.
     *
     * @param achievement Achievement which is to be assessed on whether or not its requirement
     *                    to achieve it has previously been met.
     * @param daysToIterate Duration in number of days to check on whether or not achievement has
     *                      met the requirement previously.
     * @param averageAchievementKeyValueList List containing map entries of local dates and average daily values for
     *                                       a particular record type.
     * @return Whether or not the requirement for an achievement has previously been met.
     */
    private boolean fulfillsRequirementsPreviously(Achievement achievement, int daysToIterate,
                                                List<Map.Entry<LocalDate, Double>>
                                                        averageAchievementKeyValueList) {
        List<Map.Entry<LocalDate, Double>> subList = new ArrayList<>(List.copyOf(averageAchievementKeyValueList));
        subList.remove(0);
        for (int i = 1; i < averageAchievementKeyValueList.size() - daysToIterate; i++) {
            if (requirementIsMet(achievement, daysToIterate, subList)) {
                return true;
            }
            subList.remove(0);
        }
        return false;
    }

    /**
     * Returns whether or not the requirement for an achievement has already been met.
     *
     * @param achievement Achievement which is to be assessed on whether or not its requirement
     *                    to achieve it has been met.
     * @param daysToIterate Duration in number of days to check on whether or not achievement has
     *                      met the requirement.
     * @param averageAchievementKeyValueList List containing map entries of local dates and average daily values for
     *                                       a particular record type.
     * @return Whether or not the requirement for an achievement has already been met.
     */
    private boolean requirementIsMet(Achievement achievement, int daysToIterate,
                                     List<Map.Entry<LocalDate, Double>> averageAchievementKeyValueList) {
        boolean fulfillsRequirements = true;
        LocalDate moreRecentDate = null;
        for (int i = 0; i < daysToIterate; i++) {
            Map.Entry<LocalDate, Double> averageAchievementKeyValue = averageAchievementKeyValueList.get(i);
            LocalDate lessRecentDate = averageAchievementKeyValue.getKey();
            Double averageDailyValue = averageAchievementKeyValue.getValue();
            if (moreRecentDate != null) {
                if (lessRecentDate.until(moreRecentDate, ChronoUnit.DAYS) > 1) {
                    fulfillsRequirements = false;
                    break;
                }
            }
            if (averageDailyValue > achievement.getMaximum()
                    || averageDailyValue < achievement.getMinimum()) {
                fulfillsRequirements = false;
                break;
            }
            moreRecentDate = lessRecentDate;
        }
        return fulfillsRequirements;
    }

    /**
     * Returns duration in number of days required to potentially meet the given achievement.
     *
     * @param achievement Achievement for which duration required to assess on whether or not it has been achieved is
     *                    to be determined.
     * @return Duration in number of days required to potentially meet the given achievement.
     */
    private int getDaysToIterate(Achievement achievement) {
        return (int) achievement.getDurationValue() * (achievement.getDurationUnits() == WEEK
                ? WEEK_IN_DAYS
                : achievement.getDurationUnits() == MONTH
                        ? MONTH_IN_DAYS
                        : achievement.getDurationUnits() == YEAR
                                ? YEAR_IN_DAYS
                                : DAY_IN_DAYS);
    }

    /**
     * Processes a list of achievements for a given record type.
     *
     * @param achievementList List of achievements to be processed.
     * @param recordType      Record type of achievements to be processed.
     */
    private void processAchievements(List<Achievement> achievementList, RecordType recordType) {
        boolean achievementIsAttained = false;
        for (Achievement achievement : achievementList) {
            assert achievement.getRecordType() == recordType : "Record type in achievements list differ from each "
                    + "other.";

            if (!achievementIsAttained) {
                achievementIsAttained = achievement.isAchieved();
            } else {
                setAchievementState(achievement, ACHIEVED);
                continue;
            }

            int daysToIterate = getDaysToIterate(achievement);
            Set<Map.Entry<LocalDate, Double>> averageRecordMapKeyValues =
                    averageRecordMap.get(recordType).entrySet();

            if (daysToIterate > averageRecordMapKeyValues.size()) {
                demote(achievement);
            } else {
                Iterator<Map.Entry<LocalDate, Double>> averageAchievementKeyValueIterator =
                        averageRecordMapKeyValues.iterator();
                List<Map.Entry<LocalDate, Double>> averageAchievementKeyValueList = new ArrayList<>();
                averageAchievementKeyValueIterator.forEachRemaining(averageAchievementKeyValueList::add);
                boolean fulfillsRequirements = requirementIsMet(achievement, daysToIterate,
                        averageAchievementKeyValueList);
                if (fulfillsRequirements) {
                    promote(achievement);
                } else if (fulfillsRequirementsPreviously(achievement, daysToIterate,
                        averageAchievementKeyValueList)) {
                    promotePrev(achievement);
                } else {
                    demote(achievement);
                }
            }
        }
    }

}
