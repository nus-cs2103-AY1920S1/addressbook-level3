package seedu.sugarmummy.model.achievements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.model.achievements.AchievementLevel.BRONZE;
import static seedu.sugarmummy.model.achievements.AchievementLevel.DIAMOND;
import static seedu.sugarmummy.model.achievements.AchievementLevel.GOLD;
import static seedu.sugarmummy.model.achievements.AchievementLevel.PLATINUM;
import static seedu.sugarmummy.model.achievements.AchievementLevel.SILVER;
import static seedu.sugarmummy.model.achievements.AchievementState.ACHIEVED;
import static seedu.sugarmummy.model.achievements.AchievementState.PREVIOUSLY_ACHIEVED;
import static seedu.sugarmummy.model.achievements.AchievementState.YET_TO_ACHIEVE;
import static seedu.sugarmummy.model.records.RecordType.BLOODSUGAR;
import static seedu.sugarmummy.model.records.RecordType.BMI;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.model.achievements.bloodsugar.BloodSugarBronze;
import seedu.sugarmummy.model.achievements.bloodsugar.BloodSugarDiamond;
import seedu.sugarmummy.model.achievements.bloodsugar.BloodSugarGold;
import seedu.sugarmummy.model.achievements.bloodsugar.BloodSugarPlatinum;
import seedu.sugarmummy.model.achievements.bloodsugar.BloodSugarSilver;
import seedu.sugarmummy.model.achievements.bmi.BmiBronze;
import seedu.sugarmummy.model.achievements.bmi.BmiDiamond;
import seedu.sugarmummy.model.achievements.bmi.BmiGold;
import seedu.sugarmummy.model.achievements.bmi.BmiPlatinum;
import seedu.sugarmummy.model.achievements.bmi.BmiSilver;

class AchievementTest {

    private BloodSugarBronze bloodSugarBronze = new BloodSugarBronze();
    private BloodSugarSilver bloodSugarSilver = new BloodSugarSilver();
    private BloodSugarGold bloodSugarGold = new BloodSugarGold();
    private BloodSugarPlatinum bloodSugarPlatinum = new BloodSugarPlatinum();
    private BloodSugarDiamond bloodSugarDiamond = new BloodSugarDiamond();
    private BmiBronze bmiBronze = new BmiBronze();
    private BmiSilver bmiSilver = new BmiSilver();
    private BmiGold bmiGold = new BmiGold();
    private BmiPlatinum bmiPlatinum = new BmiPlatinum();
    private BmiDiamond bmiDiamond = new BmiDiamond();

    private List<Achievement> achievementList = List.of(bloodSugarBronze, bloodSugarSilver, bloodSugarGold,
            bloodSugarPlatinum, bloodSugarDiamond, bmiBronze, bmiSilver, bmiGold, bmiPlatinum, bmiDiamond);

    private List<Achievement> bronzeList = List.of(bloodSugarBronze, bmiBronze);
    private List<Achievement> silverList = List.of(bloodSugarSilver, bmiSilver);
    private List<Achievement> goldList = List.of(bloodSugarGold, bmiGold);
    private List<Achievement> platinumList = List.of(bloodSugarPlatinum, bmiPlatinum);
    private List<Achievement> diamondList = List.of(bloodSugarDiamond, bmiDiamond);

    private List<Achievement> bloodSugarList = List.of(bloodSugarBronze, bloodSugarSilver, bloodSugarGold,
            bloodSugarPlatinum, bloodSugarDiamond);
    private List<Achievement> bmiList = List.of(bmiBronze, bmiSilver, bmiGold, bmiPlatinum, bmiDiamond);

    @Test
    public void getImageName_stateTest() {
        achievementList.forEach(achievement ->
                assertEquals(achievement.getRecordType().toString().toLowerCase()
                        + "_" + achievement.getAchievementLevel().toString().toLowerCase()
                        + "_" + YET_TO_ACHIEVE.toString().toLowerCase(), achievement.getImageName()));
        achievementList.forEach(achievement -> achievement.setAchievementState(ACHIEVED));
        achievementList.forEach(achievement ->
                assertEquals(achievement.getRecordType().toString().toLowerCase()
                        + "_" + achievement.getAchievementLevel().toString().toLowerCase()
                        + "_" + ACHIEVED.toString().toLowerCase(), achievement.getImageName()));
    }

    @Test
    public void getImageName_levelTest() {
        bronzeList.forEach(achievement ->
                assertEquals(achievement.getRecordType().toString().toLowerCase()
                        + "_" + BRONZE.toString().toLowerCase()
                        + "_" + YET_TO_ACHIEVE.toString().toLowerCase(), achievement.getImageName()));
        silverList.forEach(achievement ->
                assertEquals(achievement.getRecordType().toString().toLowerCase()
                        + "_" + SILVER.toString().toLowerCase()
                        + "_" + YET_TO_ACHIEVE.toString().toLowerCase(), achievement.getImageName()));
        goldList.forEach(achievement ->
                assertEquals(achievement.getRecordType().toString().toLowerCase()
                        + "_" + GOLD.toString().toLowerCase()
                        + "_" + YET_TO_ACHIEVE.toString().toLowerCase(), achievement.getImageName()));
        platinumList.forEach(achievement ->
                assertEquals(achievement.getRecordType().toString().toLowerCase()
                        + "_" + PLATINUM.toString().toLowerCase()
                        + "_" + YET_TO_ACHIEVE.toString().toLowerCase(), achievement.getImageName()));
        diamondList.forEach(achievement ->
                assertEquals(achievement.getRecordType().toString().toLowerCase()
                        + "_" + DIAMOND.toString().toLowerCase()
                        + "_" + YET_TO_ACHIEVE.toString().toLowerCase(), achievement.getImageName()));
    }

    @Test
    public void getImageName_recordTest() {
        bloodSugarList.forEach(achievement ->
                assertEquals(BLOODSUGAR.toString().toLowerCase()
                        + "_" + achievement.getAchievementLevel().toString().toLowerCase()
                        + "_" + YET_TO_ACHIEVE.toString().toLowerCase(), achievement.getImageName()));
        bmiList.forEach(achievement ->
                assertEquals(BMI.toString().toLowerCase()
                        + "_" + achievement.getAchievementLevel().toString().toLowerCase()
                        + "_" + YET_TO_ACHIEVE.toString().toLowerCase(), achievement.getImageName()));
    }

    @Test
    public void getRecordType() {
        bloodSugarList.forEach(achievement ->
                assertEquals(BLOODSUGAR, achievement.getRecordType()));
        bmiList.forEach(achievement ->
                assertEquals(BMI, achievement.getRecordType()));
    }

    @Test
    public void getTitle() {
        List<String> titleList = List.of("Sugar Control Apprentice",
                "Sugar Manipulation Master",
                "Sugar Legendary Whisperer",
                "Sugar Elemental Mage",
                "Grand Sorcerer Supreme of the Sugar Arts",
                "Mass Index Novice",
                "Mass Index Lead Warrior",
                "Mass Index Elder of Newtonian Physics",
                "Chief Guardian of Mass and Space-Time Continuum",
                "Prime Governor of the Masses, Slayer of Cross-Dimensional Obesity");
        Iterator<String> titleIterator = titleList.iterator();
        achievementList.forEach(achievement ->
                assertEquals(achievement.getTitle(), titleIterator.next()));
    }

    @Test
    void getDescription() {
        List<String> descriptionList = List.of("Attain between 4.0 and 7.8mmol/L (inclusive) of daily average "
                        + "bloodsugar levels for at least 3 consecutive days.",
                "Attain between 4.0 and 7.8mmol/L (inclusive) of daily average bloodsugar levels for at least 14 "
                        + "consecutive days.",
                "Attain between 4.0 and 7.8mmol/L (inclusive) of daily average bloodsugar levels for at least 30 "
                        + "consecutive days.",
                "Attain between 4.0 and 7.8mmol/L (inclusive) of daily average bloodsugar levels for at least 90 "
                        + "consecutive days.",
                "Attain between 4.0 and 7.8mmol/L (inclusive) of daily average bloodsugar levels for at least 180 "
                        + "consecutive days.",
                "Attain between 18.5 and 25.0 (inclusive) of daily average BMI for at least 3 consecutive days.",
                "Attain between 18.5 and 25.0 (inclusive) of daily average BMI for at least 14 consecutive days.",
                "Attain between 18.5 and 25.0 (inclusive) of daily average BMI for at least 30 consecutive days.",
                "Attain between 18.5 and 25.0 (inclusive) of daily average BMI for at least 90 consecutive days.",
                "Attain between 18.5 and 25.0 (inclusive) of daily average BMI for at least 180 consecutive days.");
        Iterator<String> descriptionIterator = descriptionList.iterator();
        achievementList.forEach(achievement ->
                assertEquals(achievement.getDescription(), descriptionIterator.next()));
    }

    @Test
    public void getAchievementLevel() {
        bronzeList.forEach(bronze -> assertEquals(bronze.getAchievementLevel(), BRONZE));
        silverList.forEach(silver -> assertEquals(silver.getAchievementLevel(), SILVER));
        goldList.forEach(gold -> assertEquals(gold.getAchievementLevel(), GOLD));
        platinumList.forEach(platinum -> assertEquals(platinum.getAchievementLevel(), PLATINUM));
        diamondList.forEach(diamond -> assertEquals(diamond.getAchievementLevel(), DIAMOND));

    }

    @Test
    public void getAchievementState() {
        achievementList.forEach(achievement -> achievement.setAchievementState(YET_TO_ACHIEVE));
        achievementList.forEach(achievement -> assertEquals(achievement.getAchievementState(), YET_TO_ACHIEVE));
        achievementList.forEach(achievement -> achievement.setAchievementState(PREVIOUSLY_ACHIEVED));
        achievementList.forEach(achievement -> assertEquals(achievement.getAchievementState(), PREVIOUSLY_ACHIEVED));
        achievementList.forEach(achievement -> achievement.setAchievementState(ACHIEVED));
        achievementList.forEach(achievement -> assertEquals(achievement.getAchievementState(), ACHIEVED));
    }

    @Test
    public void setAchievementState() {
        achievementList.forEach(achievement -> achievement.setAchievementState(YET_TO_ACHIEVE));
        achievementList.forEach(achievement -> assertEquals(achievement.getAchievementState(), YET_TO_ACHIEVE));
        achievementList.forEach(achievement -> achievement.setAchievementState(PREVIOUSLY_ACHIEVED));
        achievementList.forEach(achievement -> assertEquals(achievement.getAchievementState(), PREVIOUSLY_ACHIEVED));
        achievementList.forEach(achievement -> achievement.setAchievementState(ACHIEVED));
        achievementList.forEach(achievement -> assertEquals(achievement.getAchievementState(), ACHIEVED));
    }

    @Test
    public void isAchieved() {
        achievementList.forEach(achievement -> achievement.setAchievementState(ACHIEVED));
        achievementList.forEach(achievement -> assertTrue(achievement.isAchieved()));
        achievementList.forEach(achievement -> assertFalse(achievement.isPreviouslyAchieved()));
        achievementList.forEach(achievement -> assertFalse(achievement.isYetToBeAchieved()));

    }

    @Test
    public void isPreviouslyAchieved() {
        achievementList.forEach(achievement -> achievement.setAchievementState(PREVIOUSLY_ACHIEVED));
        achievementList.forEach(achievement -> assertFalse(achievement.isAchieved()));
        achievementList.forEach(achievement -> assertTrue(achievement.isPreviouslyAchieved()));
        achievementList.forEach(achievement -> assertFalse(achievement.isYetToBeAchieved()));
    }

    @Test
    public void isYetToBeAchieved() {
        achievementList.forEach(achievement -> achievement.setAchievementState(YET_TO_ACHIEVE));
        achievementList.forEach(achievement -> assertFalse(achievement.isAchieved()));
        achievementList.forEach(achievement -> assertFalse(achievement.isPreviouslyAchieved()));
        achievementList.forEach(achievement -> assertTrue(achievement.isYetToBeAchieved()));
    }

    @Test
    public void getDurationValue() {
        bronzeList.forEach(bronze -> assertEquals(bronze.getDurationValue(), 3));
        silverList.forEach(silver -> assertEquals(silver.getDurationValue(), 14));
        goldList.forEach(gold -> assertEquals(gold.getDurationValue(), 30));
        platinumList.forEach(platinum -> assertEquals(platinum.getDurationValue(), 90));
        diamondList.forEach(diamond -> assertEquals(diamond.getDurationValue(), 180));
    }

    @Test
    public void getDurationUnits() {
        achievementList.forEach(achievement -> assertEquals(achievement.getDurationUnits(), DurationUnit.DAY));
        achievementList.forEach(achievement -> assertNotEquals(achievement.getDurationUnits(), DurationUnit.WEEK));
        achievementList.forEach(achievement -> assertNotEquals(achievement.getDurationUnits(), DurationUnit.MONTH));
        achievementList.forEach(achievement -> assertNotEquals(achievement.getDurationUnits(), DurationUnit.YEAR));
    }

    @Test
    public void getMaximum() {
        bloodSugarList.forEach(bloodSugar -> assertEquals(bloodSugar.getMaximum(), 7.8));
        bmiList.forEach(bmi -> assertEquals(bmi.getMaximum(), 25));
    }

    @Test
    public void getMinimum() {
        bloodSugarList.forEach(bloodSugar -> assertEquals(bloodSugar.getMinimum(), 4.0));
        bmiList.forEach(bmi -> assertEquals(bmi.getMinimum(), 18.5));
    }

    @Test
    public void getDisplayMessage() {
        achievementList.forEach(achievement -> assertEquals(achievement.getDisplayMessage(),
                "Title: " + achievement.getTitle() + "\n\n"
                        + "Level: " + achievement.getAchievementLevel().toString() + "\n\n"
                        + "Current State:\n" + achievement.getAchievementState().toString().replace("_", " ") + "\n\n"
                        + "Requirement: " + achievement.getDescription()));
    }

    @Test
    public void copy_test() {
        achievementList.forEach(achievement -> assertNotSame(achievement, achievement.copy()));
    }

    @Test
    public void copy_sameAchievementTest() {
        achievementList.forEach(achievement -> assertSame(achievement, achievement));
    }


    @Test
    public void testEquals_sameAchievement() {
        achievementList.forEach(achievement -> assertEquals(achievement, achievement));
    }

    @Test
    public void testEquals_achievementCopy() {
        achievementList.forEach(achievement -> assertEquals(achievement, achievement.copy()));
    }

    @Test
    public void testEquals_differentState() {
        achievementList.forEach(achievement -> {
            Achievement achievementCopy = achievement.copy();
            achievementCopy.setAchievementState(PREVIOUSLY_ACHIEVED);
            assertNotEquals(achievement, achievementCopy);
        });
    }

    @Test
    public void testEquals_differentLevel() {
        Iterator<Achievement> silverListIterator = silverList.iterator();
        bronzeList.forEach(bronze -> assertNotEquals(silverListIterator.next(), bronze));
        Iterator<Achievement> goldListIterator = goldList.iterator();
        silverList.forEach(silver -> assertNotEquals(goldListIterator.next(), silver));
        Iterator<Achievement> platinumListIterator = platinumList.iterator();
        goldList.forEach(gold -> assertNotEquals(platinumListIterator.next(), gold));
        Iterator<Achievement> diamondListIterator = diamondList.iterator();
        platinumList.forEach(platinum -> assertNotEquals(diamondListIterator.next(), platinum));
        Iterator<Achievement> bronzeListIterator = bronzeList.iterator();
        diamondList.forEach(diamond -> assertNotEquals(bronzeListIterator.next(), diamond));
    }

    @Test
    public void testEquals_differentRecordType() {
        Iterator<Achievement> bmiListIterator = bmiList.iterator();
        bloodSugarList.forEach(bloodSugar -> assertNotEquals(bmiListIterator.next(), bloodSugar));
        Iterator<Achievement> bloodSugarListIterator = bloodSugarList.iterator();
        bmiList.forEach(bmi -> assertNotEquals(bloodSugarListIterator.next(), bmi));
    }

    @Test
    public void testEquals_object() {
        achievementList.forEach(achievement -> assertNotEquals(achievement, new Object()));
    }

    @Test
    public void testToString() {
    }
}
