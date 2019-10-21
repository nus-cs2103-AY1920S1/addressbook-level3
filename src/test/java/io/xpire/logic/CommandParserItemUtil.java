package io.xpire.logic;

import static io.xpire.model.item.ExpiryDate.DATE_FORMAT;

import java.time.LocalDate;

import io.xpire.commons.util.DateUtil;

/**
 * Contains all fields of all the test Items.
 */
public class CommandParserItemUtil {

    public static final String VALID_NAME_APPLE = "Apple";
    public static final String VALID_NAME_BANANA = "Banana";
    public static final String VALID_NAME_EXPIRED_MILK = "Milk";
    public static final String VALID_NAME_DUCK = "Duck";
    public static final String VALID_NAME_JELLY = "Jelly";
    public static final String VALID_NAME_KIWI = "Kiwi";
    public static final String VALID_NAME_EXPIRED_ORANGE = "Orange";
    public static final String VALID_NAME_EXPIRING_FISH = "Fish";

    public static final String TODAY = DateUtil.convertDateToString(LocalDate.now(), DATE_FORMAT);
    public static final String IN_A_WEEK = DateUtil.convertDateToString(LocalDate.now().plusDays(7), DATE_FORMAT);
    public static final String IN_TWO_WEEKS = DateUtil.convertDateToString(LocalDate.now().plusDays(14), DATE_FORMAT);
    public static final String IN_A_MONTH = DateUtil.convertDateToString(LocalDate.now().plusDays(30), DATE_FORMAT);
    public static final String PASSED_A_DAY = DateUtil.convertDateToString(LocalDate.now().minusDays(1), DATE_FORMAT);
    public static final String PASSED_A_WEEK = DateUtil.convertDateToString(LocalDate.now().minusDays(7), DATE_FORMAT);

    public static final String VALID_EXPIRY_DATE_APPLE = TODAY;
    public static final String VALID_EXPIRY_DATE_BANANA = IN_TWO_WEEKS;
    public static final String VALID_EXPIRY_DATE_EXPIRED_MILK = PASSED_A_WEEK;
    public static final String VALID_EXPIRY_DATE_DUCK = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_JELLY = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_KIWI = IN_A_MONTH;
    public static final String VALID_EXPIRY_DATE_ORANGE = PASSED_A_DAY;
    public static final String VALID_EXPIRY_DATE_FISH = IN_A_WEEK;

    public static final String VALID_QUANTITY_APPLE = "1";
    public static final String VALID_QUANTITY_BANANA = "5";
    public static final String VALID_QUANTITY_DUCK = "1";
    public static final String VALID_QUANTITY_JELLY = "4";
    public static final String VALID_QUANTITY_KIWI = "2";
    public static final String VALID_QUANTITY_EXPIRED_MILK = "2";
    public static final String VALID_QUANTITY_EXPIRING_FISH = "1";
    public static final String VALID_QUANTITY_EXPIRED_ORANGE = "1";

    public static final String VALID_TAG_FRUIT = "Fruit";
    public static final String VALID_TAG_DRINK = "Drink";
    public static final String VALID_TAG_FRIDGE = "Fridge";
    public static final String VALID_TAG_PROTEIN = "Protein";

    public static final String VALID_REMINDER_THRESHOLD_APPLE = "0";
    public static final String VALID_REMINDER_THRESHOLD_BANANA = "0";
    public static final String VALID_REMINDER_THRESHOLD_DUCK = "0";
    public static final String VALID_REMINDER_THRESHOLD_JELLY = "3";
    public static final String VALID_REMINDER_THRESHOLD_KIWI = "20";
    public static final String VALID_REMINDER_THRESHOLD_FISH = "8";
    public static final String VALID_REMINDER_THRESHOLD_ORANGE = "0";
    public static final String VALID_REMINDER_THRESHOLD_MILK = "0";

    public static final String INVALID_NAME = "@pple";
    public static final String INVALID_EXPIRY_DATE = "50505000";
    public static final String INVALID_EXPIRY_DATE_RANGE = "50/50/5000";
    public static final String INVALID_TAG = "$cold";
    public static final String INVALID_QUANTITY = "-2";
    public static final String INVALID_REMINDER_THRESHOLD = "-5";
}
