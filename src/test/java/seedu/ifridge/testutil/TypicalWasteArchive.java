package seedu.ifridge.testutil;

import static seedu.ifridge.testutil.TypicalWasteList.APPLE;
import static seedu.ifridge.testutil.TypicalWasteList.BANANA;
import static seedu.ifridge.testutil.TypicalWasteList.CAKE;
import static seedu.ifridge.testutil.TypicalWasteList.DATES;
import static seedu.ifridge.testutil.TypicalWasteList.EGGS;
import static seedu.ifridge.testutil.TypicalWasteList.FRIES;
import static seedu.ifridge.testutil.TypicalWasteList.GRAPES;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.waste.WasteMonth;

/**
 * A typical waste archive.
 */
public class TypicalWasteArchive {
    public static final WasteList CURRENT_WASTE_LIST = new WasteListBuilder(
            new WasteMonth(LocalDate.now()))
            .withWasteItem(APPLE)
            .withWasteItem(BANANA)
            .withWasteItem(CAKE).build();
    public static final WasteList LAST_MONTH_WASTE_LIST = new WasteListBuilder(
            new WasteMonth(LocalDate.now().minusMonths(1)))
            .withWasteItem(DATES)
            .withWasteItem(EGGS)
            .withWasteItem(FRIES)
            .withWasteItem(GRAPES).build();

    private TypicalWasteArchive() {}

    public static TreeMap<WasteMonth, WasteList> getTypicalWasteArchive() {
        TreeMap<WasteMonth, WasteList> wasteArchive = new TreeMap<>();
        for (WasteList wl : getTypicalWasteLists()) {
            wasteArchive.put(wl.getWasteMonth(), wl);
        }
        return wasteArchive;
    }

    public static List<WasteList> getTypicalWasteLists() {
        return new ArrayList<>() {
            {
                add(CURRENT_WASTE_LIST);
                add(LAST_MONTH_WASTE_LIST);
            }
        };
    }
}
