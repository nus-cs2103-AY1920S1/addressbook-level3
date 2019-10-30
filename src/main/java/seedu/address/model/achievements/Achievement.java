package seedu.address.model.achievements;

import static seedu.address.model.achievements.AchievementState.ACHIEVED;
import static seedu.address.model.achievements.AchievementState.PREVIOUSLY_ACHIEVED;
import static seedu.address.model.achievements.AchievementState.YET_TO_ACHIEVE;

import seedu.address.model.record.RecordType;

/**
 * Class that represents an achievement earned by a user of this program.
 */
public abstract class Achievement {

    private RecordType recordType;
    private String title;
    private String description;
    private AchievementLevel achievementLevel;
    private AchievementState achievementState;

    public Achievement(RecordType recordType, String title, String description, AchievementLevel achievementLevel) {
        this.recordType = recordType;
        this.title = title;
        this.description = description;
        this.achievementLevel = achievementLevel;
        this.achievementState = YET_TO_ACHIEVE;
    }

    /**
     * Returns the name of the image file representing this achievement.
     */
    public String getImageName() {
        return recordType.toString().toLowerCase()
                + "_" + achievementLevel.toString().toLowerCase()
                + "_" + achievementState.toString().toLowerCase();
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public AchievementLevel getAchievementLevel() {
        return achievementLevel;
    }

    public AchievementState getAchievementState() {
        return achievementState;
    }

    /**
     * Sets the state of the achievement to the given achievement state.
     *
     * @param achievementState Achievement state for this achievement to be set to.
     */
    public void setAchievementState(AchievementState achievementState) {
        this.achievementState = achievementState;
    }

    /**
     * Returns whether or not this achievement is currently achieved.
     */
    public boolean isAchieved() {
        return achievementState == ACHIEVED;
    }

    /**
     * Returns whether or not this achievement has previously been achieved but no longer so.
     */
    public boolean isPreviouslyAchieved() {
        return achievementState == PREVIOUSLY_ACHIEVED;
    }

    /**
     * Returns whether or not this achievement is yet to be achieved.
     */
    public boolean isYetToBeAchieved() {
        return achievementState == YET_TO_ACHIEVE;
    }

    /**
     * Returns value of the duration during which a record type is maintained at optimal level before this achievement
     * is eligible.
     */
    public abstract double getDurationValue();

    /**
     * Returns the units for the duration during which a record type is maintained at optimal level before this
     * achievement is eligible.
     */
    public abstract DurationUnit getDurationUnits();

    /**
     * Returns the maximum value of a record type to be maintained over the defined duration before this achievement
     * is eligible.
     */
    public abstract double getMaximum();

    /**
     * Returns the minimum value of a record type to be maintained over the defined duration before this achievement
     * is eligible.
     */
    public abstract double getMinimum();

    /**
     * Returns the message to be shown when this achievement is displayed to the user.
     */
    public String getDisplayMessage() {
        return "Title: " + title + "\n\n"
                + "Level: " + achievementLevel.toString() + "\n\n"
                + "Current State:\n" + achievementState.toString().replace("_", " ")  + "\n\n"
                + "Requirement: " + description;
    }

    /**
     * Returns a copy of this achievement instance;
     */
    public abstract Achievement copy();

    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        } else if (!(other instanceof Achievement)) {
            return false;
        } else {
            Achievement otherAchievement = (Achievement) other;
            if (this.recordType == otherAchievement.recordType
                    && this.title.equals(otherAchievement.title)
                    && this.description.equals(otherAchievement.description)
                    && this.achievementLevel == otherAchievement.achievementLevel
                    && this.achievementState == otherAchievement.achievementState) {
                return true;
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return "Type: " + recordType.toString() + "\n"
                + "Title: " + title + "\n"
                + "Description: " + description + "\n"
                + "Level: " + achievementLevel.toString() + "\n"
                + "State: " + achievementState.toString();
    }
}
