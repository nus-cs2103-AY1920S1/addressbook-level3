package seedu.ifridge.logic.commands;

import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_CURRENT_MONTH;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_LAST_MONTH;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_THREE_MONTHS_AGO;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_TWO_MONTHS_AGO;

import java.time.LocalDate;

import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteStatistic;

/**
 * A utilities class for Waste List Command tests.
 */
public class WasteListCommandTestUtil {

    public static final String VALID_MONTH_MAR_RELAXED = "Mar 2019";
    public static final String VALID_MONTH_SEP_RELAXED = "Sep 2019";
    public static final String VALID_MONTH_OCT_RELAXED = "Oct 2019";
    public static final String VALID_MONTH_RELATIVE = "last month";

    public static final String INVALID_MONTH = "invalid";

    // Waste List Parsing
    public static final String MONTH_SEP_RELAXED = " " + PREFIX_MONTH + VALID_MONTH_SEP_RELAXED;
    public static final String MONTH_RELATIVE_LAST_MONTH = " " + PREFIX_MONTH + VALID_MONTH_RELATIVE;
    public static final String MONTH_OCT_RELAXED = " " + PREFIX_MONTH + VALID_MONTH_OCT_RELAXED;
    public static final String MONTH_INVALID = " " + PREFIX_MONTH + INVALID_MONTH;
    public static final WasteMonth WASTE_MONTH_SEPTEMBER = new WasteMonth(9, 2019);
    public static final WasteMonth WASTE_MONTH_CURRENT_MONTH = new WasteMonth(LocalDate.now());
    public static final WasteMonth WASTE_MONTH_LAST_MONTH = new WasteMonth(LocalDate.now()).previousWasteMonth();

    // Waste Report Parsing
    public static final String START_MONTH_MAR_RELAXED = " " + PREFIX_START_MONTH + VALID_MONTH_MAR_RELAXED;
    public static final String START_MONTH_INVALID = " " + PREFIX_START_MONTH + INVALID_MONTH;
    public static final String START_MONTH_EMPTY = " ";
    public static final String END_MONTH_OCT_RELAXED = " " + PREFIX_END_MONTH + VALID_MONTH_OCT_RELAXED;
    public static final String END_MONTH_INVALID = " " + PREFIX_END_MONTH + INVALID_MONTH;
    public static final String END_MONTH_EMPTY = " ";
    public static final WasteMonth WASTE_MONTH_MAR2019 = new WasteMonth(3, 2019);
    public static final WasteMonth WASTE_MONTH_MAR2020 = new WasteMonth(3, 2020);
    public static final WasteMonth WASTE_MONTH_OCT2019 = new WasteMonth(10, 2019);
    public static final WasteMonth WASTE_MONTH_OCT2018 = new WasteMonth(10, 2018);
    public static final WasteMonth WASTE_MONTH_CURRENT_MONTH_LAST_YEAR = WASTE_MONTH_CURRENT_MONTH.minusWasteMonth(12);

    public static final WasteMonth WASTE_MONTH_NOT_IN_ARCHIVE = new WasteMonth(1, 2018);
    public static final WasteMonth WASTE_MONTH_NEXT_MONTH = new WasteMonth(LocalDate.now()).nextWasteMonth();
    public static final WasteMonth WASTE_MONTH_TWO_MONTHS_AGO = new WasteMonth(LocalDate.now()).minusWasteMonth(2);
    public static final WasteMonth WASTE_MONTH_THREE_MONTHS_AGO = new WasteMonth(LocalDate.now()).minusWasteMonth(3);

    public static final WasteStatistic WASTE_STATISTIC_CURRENT_MONTH = WasteStatistic
            .getWasteStatistic(WASTE_LIST_CURRENT_MONTH.getIterableWasteList());
    public static final WasteStatistic WASTE_STATISTIC_LAST_MONTH = WasteStatistic
            .getWasteStatistic(WASTE_LIST_LAST_MONTH.getIterableWasteList());
    public static final WasteStatistic WASTE_STATISTIC_TWO_MONTHS_AGO = WasteStatistic
            .getWasteStatistic(WASTE_LIST_TWO_MONTHS_AGO.getIterableWasteList());
    public static final WasteStatistic WASTE_STATISTIC_THREE_MONTHS_AGO = WasteStatistic
            .getWasteStatistic(WASTE_LIST_THREE_MONTHS_AGO.getIterableWasteList());
    public static final WasteStatistic WASTE_STATISTIC_MONTH_NO_WASTELIST = new WasteStatistic(0, 0, 0);

}
