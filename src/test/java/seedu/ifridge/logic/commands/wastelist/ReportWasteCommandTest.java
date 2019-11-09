package seedu.ifridge.logic.commands.wastelist;

import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_CURRENT_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_LAST_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_MAR2019;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_NEXT_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_OCT2018;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_OCT2019;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_THREE_MONTHS_AGO;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_TWO_MONTHS_AGO;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_STATISTIC_CURRENT_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_STATISTIC_LAST_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_STATISTIC_THREE_MONTHS_AGO;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_STATISTIC_TWO_MONTHS_AGO;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteReport;
import seedu.ifridge.model.waste.WasteStatistic;

public class ReportWasteCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
                new UnitDictionary(new HashMap<String, String>()));
    }

    @Test
    public void execute_startMonthAfterEndMonth_throwsCommandException() {
        ReportWasteCommand rwc = new ReportWasteCommand(WASTE_MONTH_OCT2019, WASTE_MONTH_MAR2019, true, true);
        assertCommandFailure(rwc, model, ReportWasteCommand.MESSAGE_INVALID_START_END_ORDER);
    }

    @Test
    public void execute_endingMonthBeforeEarliestRecord_throwsCommandException() {
        ReportWasteCommand rwc = new ReportWasteCommand(WASTE_MONTH_OCT2018, WASTE_MONTH_MAR2019, true, true);
        String errorMessage = String.format(ReportWasteCommand.MESSAGE_NO_EXISTING_RECORDS,
                WASTE_MONTH_OCT2018, WASTE_MONTH_MAR2019, WASTE_MONTH_THREE_MONTHS_AGO);
        assertCommandFailure(rwc, model, errorMessage);
    }

    @Test
    public void execute_startingMonthAfterLatestRecord_throwsCommandException() {
        ReportWasteCommand rwc = new ReportWasteCommand(WASTE_MONTH_NEXT_MONTH, WASTE_MONTH_NEXT_MONTH.nextWasteMonth(),
                true, true);
        String errorMessage = String.format(ReportWasteCommand.MESSAGE_START_MONTH_AFTER_CURRENT_MONTH,
                WASTE_MONTH_CURRENT_MONTH);
        assertCommandFailure(rwc, model, errorMessage);
    }

    @Test
    public void execute_allMonthsPresent_success() {
        ReportWasteCommand rwc = new ReportWasteCommand(WASTE_MONTH_THREE_MONTHS_AGO, WASTE_MONTH_CURRENT_MONTH,
                true, true);
        String successMessage = String.format(ReportWasteCommand.MESSAGE_SUCCESS, WASTE_MONTH_THREE_MONTHS_AGO,
                WASTE_MONTH_CURRENT_MONTH);
        Map<WasteMonth, WasteStatistic> data = Map.ofEntries(
                Map.entry(WASTE_MONTH_THREE_MONTHS_AGO, WASTE_STATISTIC_THREE_MONTHS_AGO),
                Map.entry(WASTE_MONTH_TWO_MONTHS_AGO, WASTE_STATISTIC_TWO_MONTHS_AGO),
                Map.entry(WASTE_MONTH_LAST_MONTH, WASTE_STATISTIC_LAST_MONTH),
                Map.entry(WASTE_MONTH_CURRENT_MONTH, WASTE_STATISTIC_CURRENT_MONTH)
        );
        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(),
                new UnitDictionary(new HashMap<String, String>()));
        WasteReport wr = new WasteReport(data);
        expectedModel.setWasteReport(wr);
        assertCommandSuccess(rwc, model, successMessage, expectedModel);
    }
}
