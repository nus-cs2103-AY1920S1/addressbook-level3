package seedu.address.model.cap;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cap.module.rank.Fail;
import seedu.address.model.cap.module.rank.FirstClass;
import seedu.address.model.cap.module.rank.Freshie;
import seedu.address.model.cap.module.rank.Pass;
import seedu.address.model.cap.module.rank.Rank;
import seedu.address.model.cap.module.rank.SecondLower;
import seedu.address.model.cap.module.rank.SecondUpper;
import seedu.address.model.cap.module.rank.ThirdClass;

/**
 * Encapsulates the AchievementManager class that manages the achievement processes.
 */
public class AchievementManager {
    private static final Logger logger = LogsCenter.getLogger(ModelCapManager.class);
    private Rank currentRank = new Freshie(); // default rank for new students
    private double prevCap;
    private double currCap = 0.0; // default cap at the start
    private boolean hasRankChanged = false;
    private boolean hasDownGraded = false;
    private boolean hasUpGraded = false;

    public AchievementManager() {
        logger.fine("Initializing with achievement.");
    }

    /**
     * This method updates the achievementManager with the new cap which allows it to execute the necessary
     * procedures to update the achievement status.
     * @param cap
     */
    public void updateCap(double cap) {
        prevCap = currCap;
        currCap = cap;

        if (FirstClass.isWithinRange(currCap)) {
            currentRank = new FirstClass();
            hasRankChanged = true;
        } else if (SecondUpper.isWithinRange(currCap)) {
            currentRank = new SecondUpper();
            hasRankChanged = true;
        } else if (SecondLower.isWithinRange(currCap)) {
            currentRank = new SecondLower();
            hasRankChanged = true;
        } else if (ThirdClass.isWithinRange(currCap)) {
            currentRank = new ThirdClass();
            hasRankChanged = true;
        } else if (Pass.isWithinRange(currCap)) {
            currentRank = new Pass();
            hasRankChanged = true;
        } else if (Fail.isWithinRange(currCap)) {
            currentRank = new Fail();
            hasRankChanged = true;
        } else {
            hasRankChanged = false;
        }
        if (hasRankChanged && currCap > prevCap) {
            hasDownGraded = false;
            hasUpGraded = true;
        } else if (hasRankChanged && currCap < prevCap) {
            hasUpGraded = false;
            hasDownGraded = true;
        }
    }

    public boolean hasAchievementChanged() {
        return hasRankChanged;
    }

    public boolean hasUpGraded() {
        return hasUpGraded;
    }

    public boolean hasDownGraded() {
        return hasDownGraded;
    }

    public Rank getCurrentRank() {
        return currentRank;
    }

    public boolean isPromoted() {
        return hasRankChanged && hasUpGraded;
    }

    public boolean isDownGraded() {
        return hasRankChanged && hasDownGraded;
    }

    public String getRankImageFilePath() {
        return currentRank.getRankImageFilePath();
    }

    private double getPointsToNextClass() {
        if (FirstClass.isWithinRange(currCap)) {
            return 0.0;
        } else if (SecondUpper.isWithinRange(currCap)) {
            return FirstClass.getMinimumCap() - currCap;
        } else if (SecondLower.isWithinRange(currCap)) {
            return SecondUpper.getMinimumCap() - currCap;
        } else if (ThirdClass.isWithinRange(currCap)) {
            return SecondLower.getMinimumCap() - currCap;
        } else if (Pass.isWithinRange(currCap)) {
            return ThirdClass.getMinimumCap() - currCap;
        } else if (Fail.isWithinRange(currCap)) {
            return Pass.getMinimumCap() - currCap;
        }
        return 0.0;
    }

}
