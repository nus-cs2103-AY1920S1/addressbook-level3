package seedu.ifridge.logic.commands;

import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_MONTH;

import java.time.LocalDate;

import seedu.ifridge.model.waste.WasteMonth;

/**
 * A utilities class for Waste List Command tests.
 */
public class WasteListCommandTestUtil {

    public static final String VALID_MONTH_MAR_RELAXED = "Mar 2019";
    public static final String VALID_MONTH_SEP_RELAXED = "Sep 2019";
    public static final String VALID_MONTH_OCT_RELAXED = "Oct 2019";
    public static final String VALID_MONTH_SEP_FORMAL = "09/2019";
    public static final String VALID_MONTH_RELATIVE = "last month";

    public static final String INVALID_MONTH = "invalid";

    // Waste List Parsing
    public static final String MONTH_SEP_RELAXED = " " + PREFIX_MONTH + VALID_MONTH_SEP_RELAXED;
    public static final String MONTH_SEP_FORMAL = " " + PREFIX_MONTH + VALID_MONTH_SEP_FORMAL;
    public static final String MONTH_RELATIVE_LAST_MONTH = " " + PREFIX_MONTH + VALID_MONTH_RELATIVE;
    public static final String MONTH_OCT_RELAXED = " " + PREFIX_MONTH + VALID_MONTH_OCT_RELAXED;
    public static final String MONTH_INVALID = " " + PREFIX_MONTH + INVALID_MONTH;
    public static final WasteMonth WASTE_MONTH_SEPTEMBER = new WasteMonth(9, 2019);
    public static final WasteMonth WASTE_MONTH_CURRENT_MONTH = new WasteMonth(LocalDate.now());
    public static final WasteMonth WASTE_MONTH_LAST_MONTH = new WasteMonth(LocalDate.now()).previousWasteMonth();

    // Waste Report Parsing


}
