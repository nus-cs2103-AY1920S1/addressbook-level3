package seedu.guilttrip.ui.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.ui.gui.guihandles.BudgetCardHandle;
import seedu.guilttrip.ui.gui.guihandles.EntryCardHandle;
import seedu.guilttrip.ui.gui.guihandles.EntryListPanelHandle;
import seedu.guilttrip.ui.gui.guihandles.ResultDisplayHandle;


/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(EntryCardHandle expectedCard, EntryCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getDesc(), actualCard.getDesc());
        assertEquals(expectedCard.getAmount(), actualCard.getAmount());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
        assertEquals(expectedCard.getTags(), actualCard.getTags());

        if (expectedCard instanceof BudgetCardHandle && actualCard instanceof BudgetCardHandle) {
            assertEquals(((BudgetCardHandle) expectedCard).getAmountSpent(), (
                    (BudgetCardHandle) actualCard).getAmountSpent());
        }
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEntry}.
     */
    public static void assertCardDisplaysEntry(Entry expectedEntry, EntryCardHandle actualCard) {
        assertEquals(expectedEntry.getDesc().fullDesc, actualCard.getDesc());
        assertEquals("$" + expectedEntry.getAmount().toString(), actualCard.getAmount());
        assertEquals(expectedEntry.getDate().toString(), actualCard.getDate());
        assertEquals(expectedEntry.getCategory().toString(), actualCard.getCategory());
        assertEquals(expectedEntry.getTags().stream().map(tag -> tag.tagName).sorted().collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code entryListPanelHandle} displays the details of {@code entries} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EntryListPanelHandle entryListPanelHandle, Entry... entries) {
        for (int i = 0; i < entries.length; i++) {
            entryListPanelHandle.navigateToCard(i);
            assertCardDisplaysEntry(entries[i], entryListPanelHandle.getEntryCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code entryListPanelHandle} displays the details of {@code entries} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EntryListPanelHandle entryListPanelHandle, List<Entry> entries) {
        assertListMatching(entryListPanelHandle, entries.toArray(new Entry[0]));
    }

    /**
     * Asserts the size of the list in {@code entryListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(EntryListPanelHandle entryListPanelHandle, int size) {
        int numberOfPeople = entryListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
