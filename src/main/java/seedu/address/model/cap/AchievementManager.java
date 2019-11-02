package seedu.address.model.cap;

import seedu.address.model.cap.person.Rank.Freshie;
import seedu.address.model.cap.person.Rank.Fail;
import seedu.address.model.cap.person.Rank.FirstClass;
import seedu.address.model.cap.person.Rank.Pass;
import seedu.address.model.cap.person.Rank.Rank;
import seedu.address.model.cap.person.Rank.SecondLower;
import seedu.address.model.cap.person.Rank.SecondUpper;
import seedu.address.model.cap.person.Rank.ThirdClass;

public class AchievementManager {

    private Rank currentRank = new Freshie(); // default rank for new students
    double prevCap;
    double currCap = 0.0; // default cap at the start
    boolean hasRankChanged = false;
    boolean hasDownGraded = false;
    boolean hasUpGraded = false;

    public AchievementManager() {
    }

    public void updateCap(double cap) {
        prevCap = currCap;
        currCap = cap;
        if (FirstClass.isWithinRange(currCap)) {
            currentRank = new FirstClass();
            hasRankChanged = true;
        } else if (SecondUpper.isWithinRange(currCap)) {
            currentRank = new SecondLower();
            hasRankChanged = true;
        } else if (SecondLower.isWithinRange(currCap)) {
            currentRank = new SecondUpper();
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
