package seedu.exercise.testutil;

import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CHART;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CONFLICT_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CUSTOM_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_FULL_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_OPERATION_TYPE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_PARAMETER_TYPE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_REMOVE_CUSTOM;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST_TYPE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;

import seedu.exercise.logic.commands.builder.EditExerciseBuilder;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.testutil.builder.EditExerciseDescriptorBuilder;

/**
 * Contains test data that is commonly used throughout the test suites
 */
public class CommonTestData {

    //=======================For valid index=============================
    public static final String VALID_INDEX = "1";
    public static final String VALID_PREFIX_INDEX = " " + PREFIX_INDEX + VALID_INDEX;
    public static final String VALID_INDEX_2 = "2";
    public static final String VALID_PREFIX_INDEX_2 = " " + PREFIX_INDEX + VALID_INDEX_2;
    public static final String VALID_INDEX_3 = "3";
    public static final String VALID_PREFIX_INDEX_3 = " " + PREFIX_INDEX + VALID_INDEX_3;

    //=======================For valid conflict index=============================
    public static final String VALID_PREFIX_CONFLICT_INDEX = " " + PREFIX_CONFLICT_INDEX + VALID_INDEX;

    ///=======================For valid name=============================
    public static final String VALID_NAME_CARDIO = "cardio";
    public static final String VALID_NAME_LEGS = "legs";

    public static final String VALID_PREFIX_NAME_CARDIO = " " + PREFIX_NAME + VALID_NAME_CARDIO;
    public static final String VALID_PREFIX_NAME_LEGS = " " + PREFIX_NAME + VALID_NAME_LEGS;

    ///=======================For invalid name=============================
    public static final String INVALID_NAME_NOT_ENGLISH = "我妈妈";
    public static final String INVALID_NAME_SYMBOLS = "^&*(_!";

    public static final String INVALID_PREFIX_NAME_NOT_ENGLISH = " " + PREFIX_NAME + INVALID_NAME_NOT_ENGLISH;
    public static final String INVALID_PREFIX_NAME_SYMBOLS = " " + PREFIX_NAME + INVALID_NAME_SYMBOLS;

    ///=======================For valid date=============================
    public static final String VALID_DATE = "12/12/2019";
    public static final String VALID_DATE_2 = "13/12/2019";

    public static final String VALID_PREFIX_DATE = " " + PREFIX_DATE + VALID_DATE;
    public static final String VALID_PREFIX_DATE_2 = " " + PREFIX_DATE + VALID_DATE_2;

    //=======================For invalid date=============================
    public static final String INVALID_DATE_DAY = "33/12/2019";
    public static final String INVALID_DATE_WRONG_FORMAT = "12-12-2019";
    public static final String INVALID_DATE_ALPHABETS = "12/12a/2019";
    public static final String INVALID_DATE_SYMBOLS = "12/#12%/2019";

    public static final String INVALID_PREFIX_DATE_DAY = " " + PREFIX_DATE + INVALID_DATE_DAY;
    public static final String INVALID_PREFIX_DATE_WRONG_FORMAT = " " + PREFIX_DATE + INVALID_DATE_WRONG_FORMAT;
    public static final String INVALID_PREFIX_DATE_ALPHABETS = " " + PREFIX_DATE + INVALID_DATE_ALPHABETS;
    public static final String INVALID_PREFIX_DATE_SYMBOLS = " " + PREFIX_DATE + INVALID_DATE_SYMBOLS;

    //=======================For invalid ALPHABETS AND SPACES=============================
    public static final String INVALID_NUMBERS_FOR_ALPHABETS_AND_SPACES = "words123";
    public static final String INVALID_SYMBOLS_FOR_ALPHABETS_AND_SPACES = "legitwords@#%";

    //=======================For invalid index=============================
    public static final String INVALID_INDEX_ZERO = "0";
    public static final String INVALID_INDEX_NEGATIVE = "-1";
    public static final String INVALID_INDEX_EMPTY = "";
    public static final String INVALID_INDEX_ALPHABETS = "alkfj";
    public static final String INVALID_INDEX_SYMBOLS = "@@##%)!)*(";
    public static final String INVALID_INDEX_NOT_ENGLISH = "我妈妈";

    public static final String INVALID_PREFIX_INDEX_ZERO = " " + PREFIX_INDEX + INVALID_INDEX_ZERO;
    public static final String INVALID_PREFIX_INDEX_NEGATIVE = " " + PREFIX_INDEX + INVALID_INDEX_NEGATIVE;
    public static final String INVALID_PREFIX_INDEX_ALPHABETS = " " + PREFIX_INDEX + INVALID_INDEX_ALPHABETS;
    public static final String INVALID_PREFIX_INDEX_SYMBOLS = " " + PREFIX_INDEX + INVALID_INDEX_SYMBOLS;
    public static final String INVALID_PREFIX_INDEX_NOT_ENGLISH = " " + PREFIX_INDEX + INVALID_INDEX_NOT_ENGLISH;
    public static final String INVALID_PREFIX_INDEX_EMPTY = " " + PREFIX_INDEX + INVALID_INDEX_EMPTY;

    //=======================For invalid conflict index=============================
    public static final String INVALID_PREFIX_CONFLICT_INDEX_ZERO = " "
        + PREFIX_CONFLICT_INDEX + INVALID_INDEX_ZERO;
    public static final String INVALID_PREFIX_CONFLICT_INDEX_NOT_ENGLISH = " "
        + PREFIX_CONFLICT_INDEX + INVALID_INDEX_NOT_ENGLISH;

    //=======================For commonly used file names=============================
    public static final String EXERCISE_BOOK_FILE_NAME = "exerciseBook.json";
    public static final String REGIME_BOOK_FILE_NAME = "regimeBook.json";
    public static final String EXERCISE_DATABASE_FILE_NAME = "exercisedatabase.json";
    public static final String SCHEDULE_BOOK_FILE_NAME = "scheduleBook.json";
    public static final String USER_PREFS_FILE_NAME = "userPrefs.json";
    public static final String PROPERTY_BOOK_FILE_NAME = "propertyBook.json";

    //=======================Some useless prefixes that are not used in ExerHealth=============================
    public static final Prefix UNKNOWN_PREFIX = new Prefix("--u");
    public static final Prefix P_SLASH = new Prefix("p/");
    public static final Prefix DASH_T = new Prefix("-t");
    public static final Prefix HAT_Q = new Prefix("^Q");
    public static final Prefix NUMBER_SLASH = new Prefix("34/");
    public static final Prefix PUNCTUATION_SLASH = new Prefix("?!/");

    //=======================For being used to attach to front of commands=============================
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //=======================For valid exercise properties=============================
    public static final String VALID_CATEGORY_EXERCISE = "exercise";
    public static final String CATEGORY_DESC_EXERCISE = " " + PREFIX_CATEGORY + VALID_CATEGORY_EXERCISE;
    public static final String VALID_NAME_AEROBICS = "Aerobics";
    public static final String NAME_DESC_AEROBICS = " " + PREFIX_NAME + VALID_NAME_AEROBICS;
    public static final String VALID_NAME_BASKETBALL = "Basketball";
    public static final String NAME_DESC_BASKETBALL = " " + PREFIX_NAME + VALID_NAME_BASKETBALL;
    public static final String VALID_DATE_AEROBICS = "26/09/2019";
    public static final String DATE_DESC_AEROBICS = " " + PREFIX_DATE + VALID_DATE_AEROBICS;
    public static final String VALID_DATE_BASKETBALL = "27/09/2019";
    public static final String DATE_DESC_BASKETBALL = " " + PREFIX_DATE + VALID_DATE_BASKETBALL;
    public static final String VALID_CALORIES_AEROBICS = "333";
    public static final String CALORIES_DESC_AEROBICS = " " + PREFIX_CALORIES + VALID_CALORIES_AEROBICS;
    public static final String VALID_CALORIES_BASKETBALL = "444";
    public static final String CALORIES_DESC_BASKETBALL = " " + PREFIX_CALORIES + VALID_CALORIES_BASKETBALL;
    public static final String VALID_QUANTITY_AEROBICS = "30";
    public static final String QUANTITY_DESC_AEROBICS = " " + PREFIX_QUANTITY + VALID_QUANTITY_AEROBICS;
    public static final String VALID_QUANTITY_BASKETBALL = "3";
    public static final String QUANTITY_DESC_BASKETBALL = " " + PREFIX_QUANTITY + VALID_QUANTITY_BASKETBALL;
    public static final String VALID_UNIT_AEROBICS = "counts";
    public static final String UNIT_DESC_AEROBICS = " " + PREFIX_UNIT + VALID_UNIT_AEROBICS;
    public static final String VALID_UNIT_BASKETBALL = "hour";
    public static final String UNIT_DESC_BASKETBALL = " " + PREFIX_UNIT + VALID_UNIT_BASKETBALL;
    public static final String VALID_MUSCLE_AEROBICS = "Back";
    public static final String MUSCLE_DESC_AEROBICS = " " + PREFIX_MUSCLE + VALID_MUSCLE_AEROBICS;
    public static final String VALID_MUSCLE_BASKETBALL = "Arms";
    public static final String MUSCLE_DESC_BASKETBALL = " " + PREFIX_MUSCLE + VALID_MUSCLE_BASKETBALL;

    //=======================For invalid exercise properties=============================
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Dance&"; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "31a/10/2019"; // 'a' not allowed in date
    public static final String INVALID_CALORIES_DESC = " " + PREFIX_CALORIES + "33a"; // 'a' not allowed in calories
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_QUANTITY + "22a"; // 'a' not allowed in quantity
    public static final String INVALID_UNIT_DESC = " " + PREFIX_UNIT; // empty string not allowed in unit
    public static final String INVALID_MUSCLE_DESC = " " + PREFIX_MUSCLE + "Chest*"; // '*' not allowed in muscle

    //=======================For valid exercise editors=============================
    public static final EditExerciseBuilder DESC_AEROBICS = new EditExerciseDescriptorBuilder()
        .withName(CommonTestData.VALID_NAME_AEROBICS)
        .withDate(CommonTestData.VALID_DATE_AEROBICS)
        .withCalories(CommonTestData.VALID_CALORIES_AEROBICS)
        .withQuantity(CommonTestData.VALID_QUANTITY_AEROBICS)
        .withMuscles(CommonTestData.VALID_MUSCLE_AEROBICS)
        .build();

    public static final EditExerciseBuilder DESC_BASKETBALL = new EditExerciseDescriptorBuilder()
        .withName(CommonTestData.VALID_NAME_BASKETBALL)
        .withDate(CommonTestData.VALID_DATE_BASKETBALL)
        .withCalories(CommonTestData.VALID_CALORIES_BASKETBALL)
        .withQuantity(CommonTestData.VALID_QUANTITY_BASKETBALL)
        .withMuscles(CommonTestData.VALID_MUSCLE_AEROBICS, CommonTestData.VALID_MUSCLE_BASKETBALL)
        .build();

    //=======================For valid custom properties=============================
    public static final String VALID_PREFIX_NAME_RATING = "r";
    public static final String PREFIX_NAME_DESC_RATING = " " + PREFIX_CUSTOM_NAME + VALID_PREFIX_NAME_RATING;
    public static final String VALID_PREFIX_NAME_REMARK = "b";
    public static final String PREFIX_NAME_DESC_REMARK = " " + PREFIX_CUSTOM_NAME + VALID_PREFIX_NAME_REMARK;
    public static final String VALID_PREFIX_NAME_END_DATE = "ed";
    public static final String PREFIX_NAME_DESC_END_DATE = " " + PREFIX_CUSTOM_NAME + VALID_PREFIX_NAME_END_DATE;
    public static final String VALID_FULL_NAME_RATING = "Rating";
    public static final String FULL_NAME_DESC_RATING = " " + PREFIX_FULL_NAME + VALID_FULL_NAME_RATING;
    public static final String VALID_FULL_NAME_REMARK = "Remark";
    public static final String FULL_NAME_DESC_REMARK = " " + PREFIX_FULL_NAME + VALID_FULL_NAME_REMARK;
    public static final String VALID_FULL_NAME_END_DATE = "End Date";
    public static final String FULL_NAME_DESC_END_DATE = " " + PREFIX_FULL_NAME + VALID_FULL_NAME_END_DATE;
    public static final String VALID_PARAMETER_TYPE_RATING = "Number";
    public static final String PARAMETER_TYPE_DESC_RATING = " "
        + PREFIX_PARAMETER_TYPE + VALID_PARAMETER_TYPE_RATING;
    public static final String VALID_PARAMETER_TYPE_REMARK = "Text";
    public static final String PARAMETER_TYPE_DESC_REMARK = " "
        + PREFIX_PARAMETER_TYPE + VALID_PARAMETER_TYPE_REMARK;
    public static final String VALID_PARAMETER_TYPE_END_DATE = "Date";
    public static final String PARAMETER_TYPE_DESC_END_DATE = " "
        + PREFIX_PARAMETER_TYPE + VALID_PARAMETER_TYPE_END_DATE;
    public static final String VALID_VALUE_RATING = "1";
    public static final String VALID_VALUE_REMARK = "hi";
    public static final String VALID_PREFIX_REMOVE_CUSTOM_PROPERTY = " " + PREFIX_REMOVE_CUSTOM;

    //=======================For invalid custom properties=============================
    public static final String INVALID_PREFIX_NAME_DESC = " "
        + PREFIX_CUSTOM_NAME + "r r"; // whitespace not allowed in short name
    public static final String INVALID_FULL_NAME_DESC = " "
        + PREFIX_FULL_NAME + "R3mark"; //'3' not allowed in full name
    public static final String INVALID_PARAMETER_TYPE_DESC = " "
        + PREFIX_PARAMETER_TYPE + "integer"; //integer not allowed in parameter type

    //=======================For valid suggestions=============================
    public static final String VALID_SUGGEST_TYPE_BASIC = "basic";
    public static final String VALID_SUGGEST_TYPE_POSSIBLE = "possible";
    public static final String DESC_SUGGEST_TYPE_BASIC = " " + PREFIX_SUGGEST_TYPE + VALID_SUGGEST_TYPE_BASIC;
    public static final String DESC_SUGGEST_TYPE_POSSIBLE = " " + PREFIX_SUGGEST_TYPE + VALID_SUGGEST_TYPE_POSSIBLE;

    //=======================For invalid suggestions=============================
    public static final String INVALID_SUGGEST_TYPE = "basicallyimpossible";
    public static final String INVALID_DESC_SUGGEST_TYPE = " " + PREFIX_SUGGEST_TYPE + INVALID_SUGGEST_TYPE;

    //=======================For valid operation types=============================
    public static final String VALID_OPERATION_TYPE_AND = "and";
    public static final String DESC_OPERATION_TYPE_AND = " " + PREFIX_OPERATION_TYPE + VALID_OPERATION_TYPE_AND;

    //=======================For valid exercise toString============================
    public static final String VALID_BASKETBALL_STRING_WITH_CUSTOM_PROPERTY =
        VALID_NAME_BASKETBALL
            + " Date: "
            + VALID_DATE_BASKETBALL
            + " Calories: "
            + VALID_CALORIES_BASKETBALL
            + " Quantity: "
            + VALID_QUANTITY_BASKETBALL
            + " Unit: "
            + VALID_UNIT_BASKETBALL
            + " Muscle(s): "
            +
            "["
            + VALID_MUSCLE_AEROBICS
            + "]"
            + "["
            + VALID_MUSCLE_BASKETBALL
            + "] "
            + VALID_FULL_NAME_REMARK
            + ": "
            + VALID_PREFIX_NAME_REMARK;

    //=======================For valid regime toString============================
    public static final String VALID_REGIME_STRING_FOR_TYPICAL_REGIME_CARDIO = "Exercise 1: Bench Press\n"
        + "Exercise 2: Dancing\n"
        + "Exercise 3: Skipping\n"
        + "Exercise 4: Snapping\n"
        + "Exercise 5: Sprinting\n"
        + "Exercise 6: Swimming\nExercise 7: Walking\n";
    //====================For valid statistic=============================================
    public static final String VALID_LINE_CHART = "linechart";
    public static final String VALID_BAR_CHART = "barchart";
    public static final String VALID_PIE_CHART = "piechart";
    public static final String VALID_END_DATE = "23/10/2019";
    public static final String VALID_START_DATE = "17/10/2019";
    public static final String VALID_STATISTIC_CATEGORY_CALORIES = "calories";
    public static final String VALID_STATISTIC_CATEGORY_EXERCISE = "exercise";

    public static final String LINE_CHART_DESC = " " + PREFIX_CHART + VALID_LINE_CHART;
    public static final String END_DATE_DESC = " " + PREFIX_END_DATE + VALID_END_DATE;
    public static final String START_DATE_DESC = " " + PREFIX_START_DATE + VALID_START_DATE;
    public static final String STATISTIC_CATEGORY_DESC = " " + PREFIX_CATEGORY + VALID_STATISTIC_CATEGORY_CALORIES;

    //======================For invalid statistic=======================================================
    public static final String INVALID_CHART = "invalidchart";
    public static final String INVALID_END_DATE = "01/01/2019"; //invalid because is before start date
    public static final String INVALID_END_DATE_TOO_FAR_APART = "12/12/2019";
    public static final String INVALID_STATISTIC_CATEGORY = "invalidcalories";

    public static final String INVALID_CHART_DESC = " " + PREFIX_CHART + INVALID_CHART;
    public static final String INVALID_STATISTIC_CATEGORY_DESC = " " + PREFIX_CATEGORY + INVALID_STATISTIC_CATEGORY;
    public static final String INVALID_END_DATE_DESC = " " + PREFIX_END_DATE + INVALID_END_DATE;
    public static final String INVALID_END_DATE_TOO_FAR_APART_DESC = " " + PREFIX_END_DATE
        + INVALID_END_DATE_TOO_FAR_APART;

    //======================For valid list type=========================================================
    public static final String VALID_LIST_TYPE_EXERCISE = "exercise";
    public static final String VALID_PREFIX_LIST_TYPE_EXERCISE = " " + PREFIX_CATEGORY + VALID_LIST_TYPE_EXERCISE;
    public static final String VALID_LIST_TYPE_REGIME = "regime";
    public static final String VALID_PREFIX_LIST_TYPE_REGIME = " " + PREFIX_CATEGORY + VALID_LIST_TYPE_REGIME;
    public static final String VALID_LIST_TYPE_SCHEDULE = "schedule";
    public static final String VALID_PREFIX_LIST_TYPE_SCHEDULE = " " + PREFIX_CATEGORY + VALID_LIST_TYPE_SCHEDULE;
    public static final String VALID_LIST_TYPE_SUGGESTION = "suggestion";
    public static final String VALID_PREFIX_LIST_TYPE_SUGGESTION = " " + PREFIX_CATEGORY + VALID_LIST_TYPE_SUGGESTION;

    //=====================For invalid list type=========================================================
    public static final String INVALID_LIST_TYPE_ADDRESS = "address";
    public static final String INVALID_PREFIX_LIST_TYPE_ADDRESS = " " + PREFIX_CATEGORY + INVALID_LIST_TYPE_ADDRESS;

    //=====================For GUI id's=========================================================
    public static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";
    public static final String EXERCISE_LIST_PANEL_LIST_VIEW_ID = "#exerciseListView";
    public static final String EXERCISE_LABEL_ID = "#exerciseTitle";
    public static final String RESULT_DISPLAY_FIELD_ID = "#resultDisplay";
    public static final String HELP_WINDOW_BUTTON_ID = "#copyButton";
    public static final String HELP_WINDOW_LABEL_ID = "#helpMessage";
    public static final String HELP_WINDOW_STAGE_TITLE = "Help";
    public static final String SCHEDULE_LIST_PANEL_LIST_VIEW_ID = "#scheduleListView";
    public static final String REGIME_LIST_PANEL_LIST_VIEW_ID = "#regimeListView";
    public static final String SUGGESTION_LIST_PANEL_LIST_VIEW_ID = "#suggestionListView";
    public static final String INFO_PANEL_STACK_PANE_ID = "#infoPanelPlaceholder";

    //=====================For GUI tests=========================================================
    public static final String GUI_TITLE_TEXT = "test title";
}
