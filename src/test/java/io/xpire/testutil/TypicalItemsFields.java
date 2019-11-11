package io.xpire.testutil;

import java.time.LocalDate;

import io.xpire.commons.util.DateUtil;

/**
 * Contains all fields of TypicalItems.
 */
public class TypicalItemsFields {

    public static final String VALID_NAME_APPLE = "Apple";
    public static final String VALID_NAME_BANANA = "Banana";
    public static final String VALID_NAME_BAGEL = "Bagel";
    public static final String VALID_NAME_BISCUIT = "Biscuit";
    public static final String VALID_NAME_CHOCOLATE = "Chocolate";
    public static final String VALID_NAME_CORIANDER = "Coriander";
    public static final String VALID_NAME_COOKIE = "Cookie";
    public static final String VALID_NAME_CORN = "Corn";
    public static final String VALID_NAME_DUCK = "Duck";
    public static final String VALID_NAME_JELLY = "Jelly";
    public static final String VALID_NAME_PAPAYA = "Papaya";
    public static final String VALID_NAME_TUNA = "Tuna";
    public static final String VALID_NAME_WATERMELON = "Watermelon";
    public static final String VALID_NAME_EGG = "Egg";
    public static final String VALID_NAME_FISH = "Fish";
    public static final String VALID_NAME_GRAPE = "Grape";
    public static final String VALID_NAME_HONEY = "Honey";
    public static final String VALID_NAME_ICE_CREAM = "Ice cream";

    public static final String IN_A_MONTH = DateUtil.convertDateToString(LocalDate.now().plusDays(30));
    public static final String IN_A_WEEK = DateUtil.convertDateToString(LocalDate.now().plusDays(7));
    public static final String TODAY = DateUtil.convertDateToString(LocalDate.now());
    public static final String PASSED_A_WEEK = DateUtil.convertDateToString(LocalDate.now().minusDays(7));

    public static final String VALID_EXPIRY_DATE_APPLE = IN_A_WEEK;
    public static final String VALID_EXPIRY_DATE_BANANA = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_CORIANDER = "31/12/2099";
    public static final String VALID_EXPIRY_DATE_CORN = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_DUCK = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_KIWI = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_JELLY = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_PAPAYA = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_EGG = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_FISH = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_GRAPE = IN_A_WEEK;
    public static final String VALID_EXPIRY_DATE_HONEY = TODAY;
    public static final String VALID_EXPIRY_DATE_ICE_CREAM = PASSED_A_WEEK;

    public static final String VALID_QUANTITY_APPLE = "1";
    public static final String VALID_QUANTITY_BANANA = "1";
    public static final String VALID_QUANTITY_CORIANDER = "999";
    public static final String VALID_QUANTITY_CORN = "1";
    public static final String VALID_QUANTITY_DUCK = "1";
    public static final String VALID_QUANTITY_JELLY = "4";
    public static final String VALID_QUANTITY_PAPAYA = "1";
    public static final String VALID_QUANTITY_EGG = "10";
    public static final String VALID_QUANTITY_FISH = "4";
    public static final String VALID_QUANTITY_GRAPE = "1";
    public static final String VALID_QUANTITY_HONEY = "1";
    public static final String VALID_QUANTITY_ICE_CREAM = "2";

    public static final String VALID_TAG_BREAKFAST = "Breakfast";
    public static final String VALID_TAG_CADBURY = "Cadbury";
    public static final String VALID_TAG_COCOA = "Cocoa";
    public static final String VALID_TAG_DRINK = "Drink";
    public static final String VALID_TAG_FRIDGE = "Fridge";
    public static final String VALID_TAG_FRUIT = "Fruit";
    public static final String VALID_TAG_HERB = "Herb";
    public static final String VALID_TAG_PROTEIN = "Protein";
    public static final String VALID_TAG_SWEET = "Sweet";

    public static final String VALID_REMINDER_THRESHOLD_APPLE = "0";
    public static final String VALID_REMINDER_THRESHOLD_BANANA = "0";
    public static final String VALID_REMINDER_THRESHOLD_CORIANDER = "2";
    public static final String VALID_REMINDER_THRESHOLD_CORN = "15";
    public static final String VALID_REMINDER_THRESHOLD_DUCK = "0";
    public static final String VALID_REMINDER_THRESHOLD_PAPAYA = "0";
    public static final String VALID_REMINDER_THRESHOLD_EGG = "0";
    public static final String VALID_REMINDER_THRESHOLD_FISH = "10";
    public static final String VALID_REMINDER_THRESHOLD_GRAPE = "20";
    public static final String VALID_REMINDER_THRESHOLD_HONEY = "0";
    public static final String VALID_REMINDER_THRESHOLD_ICE_CREAM = "0";

    public static final String INVALID_NAME = "@pple";
    public static final String INVALID_EXPIRY_DATE = "50505000";
    public static final String INVALID_EXPIRY_DATE_RANGE = "50/50/5000";
    public static final String INVALID_EXPIRY_DATE_UPPER = "1/1/9999";
    public static final String INVALID_EXPIRY_DATE_LOWER = "1/1/1000";
    public static final String INVALID_TAG = "$cold";
    public static final String INVALID_QUANTITY_INTEGER = "-2";
    public static final String INVALID_REMINDER_THRESHOLD = "-5";
    public static final String INVALID_REMINDER_THRESHOLD_RANGE = "30000";

}
