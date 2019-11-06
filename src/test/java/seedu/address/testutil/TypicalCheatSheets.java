package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHEATSHEET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FORMULA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_GEM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_MATH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.StudyBuddyPro;
import seedu.address.model.cheatsheet.CheatSheet;

/**
 * A utility class containing a list of {@code CheatSheet} objects to be used in tests.
 */
public class TypicalCheatSheets {

    public static final CheatSheet CS1 = new CheatSheetBuilder()
            .withTitle("Cheatsheet 1")
            .withTags("cs2103t").build();
    public static final CheatSheet CS2 = new CheatSheetBuilder()
            .withTitle("cs 2")
            .withTags("cs2101").build();
    public static final CheatSheet CS3 = new CheatSheetBuilder()
            .withTitle("Cheatsheetszxc 3")
            .withTags("cs2102").build();
    public static final CheatSheet CS4 = new CheatSheetBuilder()
            .withTitle("ccss 4")
            .withTags("cs2105").build();
    public static final CheatSheet CS5 = new CheatSheetBuilder()
            .withTitle("sc 5")
            .withTags("lsm1301").build();

    // Manually added - CheatSheet's details found in {@code CommandTestUtil}
    public static final CheatSheet CS6 = new CheatSheetBuilder()
            .withTitle(VALID_TITLE_MATH)
            .withTags(VALID_TAG_CHEATSHEET, VALID_TAG_FORMULA).build();
    public static final CheatSheet CS7 = new CheatSheetBuilder()
            .withTitle(VALID_TITLE_GEM)
            .withTags(VALID_TAG_CHEATSHEET, VALID_TAG_IMPORTANT).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCheatSheets() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical cheatsheets.
     */
    public static StudyBuddyPro getTypicalAddressBook() {
        StudyBuddyPro ab = new StudyBuddyPro();
        for (CheatSheet cheatSheet : getTypicalCheatSheets()) {
            ab.addCheatSheet(cheatSheet);
        }
        return ab;
    }

    public static List<CheatSheet> getTypicalCheatSheets() {
        return new ArrayList<>(Arrays.asList(CS1, CS2, CS3, CS4, CS5, CS6, CS7));
    }
}
