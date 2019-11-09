package seedu.ifridge.logic.commands.wastelist;

import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_CURRENT_MONTH;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_LAST_MONTH;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_THREE_MONTHS_AGO;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_TWO_MONTHS_AGO;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.ReadOnlyWasteList;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteStatistic;

public class FeedbackWasteCommandTest {

    @Test
    public void execute_withFourMonths_success() {
        TreeMap<WasteMonth, WasteList> wasteArchiveFourMonths = getTypicalWasteArchive();
        Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                wasteArchiveFourMonths, getTypicalShoppingList(), getTypicalBoughtList(),
                new UnitDictionary(new HashMap<String, String>()));
        Model expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(),
                new UnitDictionary(new HashMap<String, String>()));
        String currentWastage = String.format(FeedbackWasteCommand.MESSAGE_CURRENT_WASTAGE, 0.000f, 0.000f, 14);
        List<ReadOnlyWasteList> pastWasteLists = List.of(
                WASTE_LIST_LAST_MONTH,
                WASTE_LIST_TWO_MONTHS_AGO,
                WASTE_LIST_THREE_MONTHS_AGO);
        WasteStatistic predictedWastageStats = WasteStatistic
                .getWeightedStatistics(WASTE_LIST_CURRENT_MONTH, pastWasteLists);
        String predictedWastage = String.format(FeedbackWasteCommand.MESSAGE_PREDICTED_WASTAGE,
                predictedWastageStats.getTotalWeight(),
                predictedWastageStats.getTotalVolume(),
                (int) Math.ceil(predictedWastageStats.getTotalQuantity()));
        assertCommandSuccess(new FeedbackWasteCommand(), model, currentWastage + predictedWastage, expectedModel);
    }

}
