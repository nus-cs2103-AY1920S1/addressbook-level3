package seedu.planner.testutil.activity;

import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_ADDRESS_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_ADDRESS_B;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_B;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_COST_HUNDRED;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_COST_TWO_HUNDRED;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DURATION_A;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DURATION_B;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PRIORITY_SEVEN;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PRIORITY_SIX;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HIKING;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_SIGHTSEEING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.model.ActivityManager;
import seedu.planner.model.activity.Activity;

/**
 * An utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivity {

    //Contact: name phone (OPT)email (OPT)address tags
    //Activity: name address (OPT)contact tags
    public static final Activity ACTIVITY_ONE = new ActivityBuilder().withName("Visit Golden Pavilion")
            .withAddress("Kyoto")
            .withContact("Sam Smith",
                    "92007122",
                    "sam1997@gmail.com",
                    "Nantan, Kyoto 601-0776, Japan",
                    "tourguide")
            .withTags("sightseeing")
            .withDuration("30")
            .withPriority("1")
            .withCost("10").build();

    public static final Activity ACTIVITY_TWO = new ActivityBuilder().withName("Visit Mount Fuji")
            .withAddress("Tokyo")
            .withContact("Matsafushi",
                    "82337121",
                    "matsafushi@gmail.com",
                    "150-2345 Tokyo-to, Shibuya-ku, Hommachi 2 choume, 4-7, Sunny Mansion 203",
                    "tourguide")
            .withTags("sightseeing")
            .withDuration("60")
            .withPriority("2")
            .withCost("20").build();

    public static final Activity ACTIVITY_THREE = new ActivityBuilder().withName("sushi making")
            .withAddress("Kodaira, Tokyo")
            .withContact("Yui",
                    "93619823",
                    "Yui@hotmail.com",
                    "Akishima, Tokyo 196-0022")
            .withDuration("90")
            .withPriority("3")
            .withCost("30").build();

    public static final Activity ACTIVITY_FOUR = new ActivityBuilder().withName("Visit Nagoya Castle")
            .withAddress("Tokyo")
            .withContact("Himari",
                    "94523656",
                    "Himari@hotmail.com",
                    "5 Chome Josai, Nishi Ward, Nagoya, Aichi 451-0031",
                    "tourguide")
            .withDuration("120")
            .withPriority("4")
            .withCost("40").build();

    public static final Activity ACTIVITY_FIVE = new ActivityBuilder().withName("Shop at Dontobori")
            .withAddress("Tokyo")
            .withContact("kosuke",
                    "95255523",
                    "kosuke@hotmail.com",
                    "5 Chome Josai, Nishi Ward, Nagoya, Aichi 451-0031")
            .withTags("souvenirs")
            .withDuration("180")
            .withPriority("5")
            .withCost("50").build();

    public static final Activity ACTIVITY_SIX = new ActivityBuilder().withName("Visit Monkey Park")
            .withAddress("Yokoyu River")
            .withContact("Kakashi",
                    "95131415",
                    "kakashi@yahoo.com",
                    "Aioi Inuyama, Aichi 484-0081",
                    "tourguide", "experienced")
            .withTags("sightseeing")
            .withDuration("210")
            .withPriority("6")
            .withCost("60").build();

    public static final Activity ACTIVITY_SEVEN = new ActivityBuilder().withName("Walk through Bamboo Forest")
            .withAddress("Kyoto")
            .withContact("Maylin",
                    "95123444",
                    "Maylin@yahoo.com",
                    "Kita Ward, Kyoto, 603-8477",
                    "Japanfriend")
            .withTags("MUSTdo", "sightseeing")
            .withDuration("240")
            .withPriority("7")
            .withCost("70").build();

    // Manually added
    public static final Activity ACTIVITY_EIGHT = new ActivityBuilder().withName("Visit Ramen Museum")
            .withAddress("Shitamachi")
            .withContact("Jack",
                    "81241034",
                    "Jack@example.com",
                    "Doichi Ward, Shitamachi, 120-8222")
            .withDuration("270")
            .withPriority("4")
            .withCost("80").build();

    public static final Activity ACTIVITY_NINE = new ActivityBuilder().withName("Watch Kabuki show")
            .withAddress("Osaka")
            .withContact("Gaara",
                    "91037493",
                    "Gaara@example.com",
                    "Tennoji Ward, Osaka, 543-0017")
            .withDuration("300")
            .withPriority("5")
            .withCost("90").build();

    // Manually added - Activity's details found in {@code CommandTestUtil}
    public static final Activity ACTIVITY_A = new ActivityBuilder().withName(VALID_ACTIVITY_NAME_A)
            .withAddress(VALID_ACTIVITY_ADDRESS_A)
            .withContact(VALID_NAME_AMY, VALID_PHONE_AMY,
                    VALID_EMAIL_AMY, VALID_ADDRESS_AMY, VALID_TAG_FRIEND)
            .withTags(VALID_TAG_SIGHTSEEING)
            .withDuration(VALID_DURATION_A)
            .withPriority(VALID_PRIORITY_SIX)
            .withCost(VALID_COST_HUNDRED).build();

    public static final Activity ACTIVITY_B = new ActivityBuilder().withName(VALID_ACTIVITY_NAME_B)
            .withAddress(VALID_ACTIVITY_ADDRESS_B)
            .withContact(VALID_NAME_BOB, VALID_PHONE_BOB,
                    VALID_EMAIL_BOB, VALID_ADDRESS_BOB, VALID_TAG_FRIEND)
            .withTags(VALID_TAG_HIKING)
            .withDuration(VALID_DURATION_B)
            .withPriority(VALID_PRIORITY_SEVEN)
            .withCost(VALID_COST_TWO_HUNDRED)
            .build();

    private TypicalActivity() {
    } // prevents instantiation

    /**
     * Returns an {@code Planner} with all the typical activities.
     */
    public static ActivityManager getTypicalActivityManager() {
        ActivityManager am = new ActivityManager();
        for (Activity activity : getTypicalActivities()) {
            am.addActivity(activity);
        }
        return am;
    }

    public static List<Activity> getTypicalActivities() {
        return new ArrayList<>(Arrays.asList(ACTIVITY_ONE, ACTIVITY_TWO, ACTIVITY_THREE,
                ACTIVITY_FOUR, ACTIVITY_FIVE, ACTIVITY_SIX, ACTIVITY_SEVEN));
    }
}
