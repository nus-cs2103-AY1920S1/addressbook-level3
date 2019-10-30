package seedu.address.testutil.activity;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_ADDRESS_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_ADDRESS_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LESSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SIGHTSEEING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ActivityManager;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivity {
    //Contact: name phone (OPT)email (OPT)address tags
    //Activity: name address (OPT)contact tags
    public static final Activity ACTIVITYONE = new ActivityBuilder().withName("Visit Golden Pavilion")
            .withAddress("Kyoto")
            .withContact("Sam Smith",
                    "92007122",
                    "sam1997@gmail.com",
                    "Nantan, Kyoto 601-0776, Japan",
                    "tourguide")
            .withTags("sightseeing").build();
    public static final Activity ACTIVITYTWO = new ActivityBuilder().withName("Visit Mount Fuji")
            .withAddress("Tokyo")
            .withContact("Matsafushi",
                    "82337121",
                    "matsafushi@gmail.com",
                    "150-2345 Tokyo-to, Shibuya-ku, Hommachi 2 choume, 4-7, Sunny Mansion 203",
                    "tourguide")
            .withTags("sightseeing").build();
    public static final Activity ACTIVITYTHREE = new ActivityBuilder().withName("sushi making")
            .withAddress("Kodaira, Tokyo")
            .withContact("Yui",
                    "93619823",
                    "Yui@hotmail.com",
                    "Akishima, Tokyo 196-0022").build();
    public static final Activity ACTIVITYFOUR = new ActivityBuilder().withName("Visit Nagoya Castle")
            .withAddress("Tokyo")
            .withContact("Himari",
                    "94523656",
                    "Himari@hotmail.com",
                    "5 Chome Josai, Nishi Ward, Nagoya, Aichi 451-0031",
                    "tourguide").build();
    public static final Activity ACTIVITYFIVE = new ActivityBuilder().withName("Shop at Dontobori")
            .withAddress("Tokyo")
            .withContact("kosuke",
                    "95255523",
                    "kosuke@hotmail.com",
                    "5 Chome Josai, Nishi Ward, Nagoya, Aichi 451-0031")
            .withTags("souvenirs").build();
    public static final Activity ACTIVITYSIX = new ActivityBuilder().withName("Visit Monkey Park")
            .withAddress("Yokoyu River")
            .withContact("Kakashi",
                    "95131415",
                    "kakashi@yahoo.com",
                    "Aioi Inuyama, Aichi 484-0081",
                    "tourguide", "experienced")
            .withTags("sightseeing").build();
    public static final Activity ACTIVITYSEVEN = new ActivityBuilder().withName("Walk through Bamboo Forest")
            .withAddress("Kyoto")
            .withContact("Maylin",
                    "95123444",
                    "Maylin@yahoo.com",
                    "Kita Ward, Kyoto, 603-8477",
                    "Japanfriend")
            .withTags("MUSTdo", "sightseeing").build();

    // Manually added
    public static final Activity ACTIVITYEIGHT = new ActivityBuilder().withName("Visit Ramen Museum")
            .withAddress("Shitamachi")
            .withContact("Jack",
                    "81241034",
                    "Jack@example.com",
                    "Doichi Ward, Shitamachi, 120-8222").build();
    public static final Activity ACTIVITYNINE = new ActivityBuilder().withName("Watch Kabuki show")
            .withAddress("Osaka")
            .withContact("Gaara",
                    "81241034",
                    "Gaara@example.com",
                    "Tennoji Ward, Osaka, 543-0017").build();

    // Manually added - Activity's details found in {@code CommandTestUtil}
    public static final Activity ACTIVITY_A = new ActivityBuilder().withName(VALID_ACTIVITY_NAME_A)
            .withAddress(VALID_ACTIVITY_ADDRESS_A)
            .withContact(VALID_NAME_AMY, VALID_PHONE_AMY,
                    VALID_EMAIL_AMY, VALID_ADDRESS_AMY, VALID_TAG_FRIEND)
            .withTags(VALID_TAG_SIGHTSEEING).build();
    public static final Activity ACTIVITY_B = new ActivityBuilder().withName(VALID_ACTIVITY_NAME_B)
            .withAddress(VALID_ACTIVITY_ADDRESS_B)
            .withContact(VALID_NAME_BOB, VALID_PHONE_BOB,
                    VALID_EMAIL_BOB, VALID_ADDRESS_BOB, VALID_TAG_FRIEND)
            .withTags(VALID_TAG_LESSON)
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
        return new ArrayList<>(Arrays.asList(ACTIVITYONE, ACTIVITYTWO, ACTIVITYTHREE,
                ACTIVITYFOUR, ACTIVITYFIVE, ACTIVITYSIX, ACTIVITYSEVEN));
    }
}
