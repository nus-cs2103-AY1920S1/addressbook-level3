package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_EARNINGS_CS1231_T05;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_EARNINGS_CS2100_A01;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EARNINGS_CS1231_T05;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EARNINGS_CS2100_A01;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_EARNINGS_CS1231_T05;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_EARNINGS_CS2100_A01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TutorAid;
import seedu.address.model.earnings.Earnings;

/**
 * A utility class containing a list of {@code Earnings} objects to be used in tests.
 */
public class TypicalEarnings {

    public static final Earnings CS2103T_EARNINGS = new EarningsBuilder().withDate("02/02/2019")
            .withType("tut").withClassId("Tutorial 7")
            .withAmount("90.00").build();
    public static final Earnings CS2107_EARNINGS = new EarningsBuilder().withDate("03/04/2020")
            .withType("lab").withClassId("Tutorial 99")
            .withAmount("19.00").build();

    // Manually added - Earnings's details found in {@code CommandTestUtil}
    public static final Earnings CS2100 = new EarningsBuilder().withDate(VALID_DATE_EARNINGS_CS2100_A01)
            .withType(VALID_TYPE_EARNINGS_CS2100_A01)
            .withClassId(VALID_CLASSID_AMY)
            .withAmount(VALID_AMOUNT_EARNINGS_CS2100_A01)
            .build();
    public static final Earnings CS1231 = new EarningsBuilder().withDate(VALID_DATE_EARNINGS_CS1231_T05)
            .withType(VALID_TYPE_EARNINGS_CS1231_T05)
            .withClassId(VALID_CLASSID_BOB)
            .withAmount(VALID_AMOUNT_EARNINGS_CS1231_T05)
            .build();

    public static final String KEYWORD_MATCHING_03_04_2020 = "03/04/2020"; // A keyword that matches 03/04/2020

    private TypicalEarnings() {} // prevents instantiation

    /**
     * Returns an {@code TutorAid} with all the typical persons.
     */
    public static TutorAid getTypicalTutorAid() {
        TutorAid ab = new TutorAid();
        for (Earnings earning : getTypicalEarnings()) {
            ab.addEarnings(earning);
        }
        return ab;
    }

    public static List<Earnings> getTypicalEarnings() {
        return new ArrayList<>(Arrays.asList(CS2103T_EARNINGS, CS2107_EARNINGS));
    }
}
