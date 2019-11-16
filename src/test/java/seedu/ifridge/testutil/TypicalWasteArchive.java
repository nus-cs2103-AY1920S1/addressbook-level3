package seedu.ifridge.testutil;

import static seedu.ifridge.testutil.TypicalWasteList.APPLE;
import static seedu.ifridge.testutil.TypicalWasteList.BANANA;
import static seedu.ifridge.testutil.TypicalWasteList.CAKE;
import static seedu.ifridge.testutil.TypicalWasteList.DATES;
import static seedu.ifridge.testutil.TypicalWasteList.EGGS;
import static seedu.ifridge.testutil.TypicalWasteList.FRIES;
import static seedu.ifridge.testutil.TypicalWasteList.GRAPES;
import static seedu.ifridge.testutil.TypicalWasteList.HOT_SAUCE;
import static seedu.ifridge.testutil.TypicalWasteList.INDOMIE;
import static seedu.ifridge.testutil.TypicalWasteList.JUICE;
import static seedu.ifridge.testutil.TypicalWasteList.KOKO_KRUNCH;
import static seedu.ifridge.testutil.TypicalWasteList.LOBSTER;
import static seedu.ifridge.testutil.TypicalWasteList.MILO;

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
    public static final WasteList WASTE_LIST_CURRENT_MONTH = new WasteListBuilder(
            new WasteMonth(LocalDate.now()))
            .withWasteItem(APPLE)
            .withWasteItem(BANANA)
            .withWasteItem(CAKE).build();
    public static final WasteList WASTE_LIST_LAST_MONTH = new WasteListBuilder(
            new WasteMonth(LocalDate.now().minusMonths(1)))
            .withWasteItem(DATES)
            .withWasteItem(EGGS)
            .withWasteItem(FRIES).build();
    public static final WasteList WASTE_LIST_TWO_MONTHS_AGO = new WasteListBuilder(
            new WasteMonth(LocalDate.now().minusMonths(2)))
            .withWasteItem(GRAPES)
            .withWasteItem(HOT_SAUCE)
            .withWasteItem(INDOMIE).build();
    public static final WasteList WASTE_LIST_THREE_MONTHS_AGO = new WasteListBuilder(
            new WasteMonth(LocalDate.now().minusMonths(3)))
            .withWasteItem(JUICE)
            .withWasteItem(KOKO_KRUNCH)
            .withWasteItem(LOBSTER)
            .withWasteItem(MILO).build();

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
                add(WASTE_LIST_CURRENT_MONTH);
                add(WASTE_LIST_LAST_MONTH);
                add(WASTE_LIST_TWO_MONTHS_AGO);
                add(WASTE_LIST_THREE_MONTHS_AGO);
            }
        };
    }
}
