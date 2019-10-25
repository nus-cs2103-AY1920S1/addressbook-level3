package dukecooks.testutil.diary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.components.Diary;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalDiaries {

    public static final Diary ALL_MEAT = new DiaryBuilder().withName("Meat Lovers").build();
    public static final Diary BRUNCH = new DiaryBuilder().withName("Brunches").build();
    public static final Diary CARB_FOODS = new DiaryBuilder().withName("Carbs For Life").build();
    public static final Diary DRINKS = new DiaryBuilder().withName("Drinks All Day").build();
    public static final Diary ENERGY_FOODS = new DiaryBuilder().withName("High Energy Foods").build();
    public static final Diary FUN_FOODS = new DiaryBuilder().withName("Fun Foods").build();
    public static final Diary GREAT_GRAPES = new DiaryBuilder().withName("Great Grapes").build();

    // Manually added
    public static final Diary HEALTHY_LIVING = new DiaryBuilder().withName("Healthy Living").build();
    public static final Diary ICE_CREAM_GALORE = new DiaryBuilder().withName("Ice Cream Galore").build();

    // Manually added - Diary's details found in {@code CommandTestUtil}
    public static final Diary AMY_DIARY = new DiaryBuilder().withName(CommandTestUtil.VALID_NAME_AMY).build();
    public static final Diary BOB_DIARY = new DiaryBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalDiaries() {} // prevents instantiation

    /**
     * Returns an {@code DiaryRecords} with all the typical diaries.
     */
    public static DiaryRecords getTypicalDiaryRecords() {
        DiaryRecords ab = new DiaryRecords();
        for (Diary diary : getTypicalDiaries()) {
            ab.addDiary(diary);
        }
        return ab;
    }

    public static List<Diary> getTypicalDiaries() {
        return new ArrayList<>(Arrays.asList(ALL_MEAT, BRUNCH, CARB_FOODS,
                DRINKS, ENERGY_FOODS, FUN_FOODS, GREAT_GRAPES));
    }
}
