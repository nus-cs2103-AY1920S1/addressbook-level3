package seedu.weme.testutil;

import java.util.Set;

import seedu.weme.model.records.Records;
import seedu.weme.model.records.RecordsManager;

/**
 * A utility class containing a typical {@code Records} object to be used in tests.
 */
public class TypicalRecords {

    public static final String PATH_WEME = "/home/me/Pictures/weme.jpg";
    public static final String PATH_CAT = "/home/me/Pictures/smiling_cat.png";
    public static final String PATH_GIRL = "/home/me/Downloads/disaster_girl.jpg";
    public static final String DESCRIPTION_FAVORITE = "OMG my favorite meme!!!";
    public static final String DESCRIPTION_PROGRMMING = "First meme about programming";
    public static final String DESCRIPTION_OLD = "Old memes";
    public static final String TAG_CS2103 = "CS2103";
    public static final String TAG_PROGRAMMING = "programming";
    public static final String TAG_COMPUTING = "Computing";
    public static final String NAME_GIRL = "Disaster Girl";
    public static final String NAME_CAT = "Cute Cat";
    public static final String NAME_SPONGE_BOB = "Sponge Bob";
    public static final String TEXT_LOL = "lol";
    public static final String TEXT_NAY = "nay";
    public static final String TEXT_SU = "Success starts with SU";

    public static final Set<String> PATH_RECORDS = Set.of(
            PATH_WEME,
            PATH_CAT,
            PATH_GIRL
    );

    public static final Set<String> DESCRIPTION_RECORDS = Set.of(
            DESCRIPTION_FAVORITE,
            DESCRIPTION_PROGRMMING,
            DESCRIPTION_OLD
    );

    public static final Set<String> TAG_RECORDS = Set.of(
            TAG_CS2103,
            TAG_PROGRAMMING,
            TAG_COMPUTING
    );

    public static final Set<String> NAME_RECORDS = Set.of(
            NAME_GIRL,
            NAME_CAT,
            NAME_SPONGE_BOB
    );

    public static final Set<String> TEXT_RECORDS = Set.of(
            TEXT_LOL,
            TEXT_NAY,
            TEXT_SU
    );

    private TypicalRecords() {} // prevents instantiation

    public static Records getTypicalRecords() {
        return new RecordsManager(PATH_RECORDS, DESCRIPTION_RECORDS, TAG_RECORDS, NAME_RECORDS, TEXT_RECORDS);
    }
}
