package seedu.guilttrip.ui;

import static seedu.guilttrip.testutil.TypicalAutoExpenses.getTypicalAutoExpenses;

import java.util.Collections;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.util.Frequency;
import seedu.guilttrip.ui.autoexpense.AutoExpensesPanel;
import seedu.guilttrip.ui.gui.guihandles.AutoExpenseCardHandle;
import seedu.guilttrip.ui.gui.guihandles.AutoExpenseListPanelHandle;

/**
 * Unit test for autoExpense list panel.
 */
public class AutoExpenseListPanelTest extends GuiUnitTest {
    private static final ObservableList<AutoExpense> TYPICAL_AUTOEXPENSES =
            FXCollections.observableList(getTypicalAutoExpenses());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 5000;

    private final SimpleObjectProperty<AutoExpense> selectedAutoExpense = new SimpleObjectProperty<>();
    private AutoExpenseListPanelHandle autoExpenseListPanelHandle;

    /*@Test
    public void display() {
        initUi(TYPICAL_AUTOEXPENSES);

        for (int i = 0; i < TYPICAL_AUTOEXPENSES.size(); i++) {
            autoExpenseListPanelHandle.navigateToCard(TYPICAL_AUTOEXPENSES.get(i));
            AutoExpense expectedExpense = TYPICAL_AUTOEXPENSES.get(i);

            AutoExpenseCardHandle actualCard = autoExpenseListPanelHandle.getAutoExpenseCardHandle(i);

            assertCardDisplaysEntry(expectedExpense, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }*/

    private void assertCardDisplaysEntry(AutoExpense expectedAutoExpense, AutoExpenseCardHandle actualCard) {
    }

    /*@Test
    public void selection_modelSelectedExpenseChanged_selectionChanges() {
        initUi(TYPICAL_AUTOEXPENSES);
        AutoExpense secondAutoExpense = TYPICAL_AUTOEXPENSES.get(INDEX_SECOND_ENTRY.getZeroBased());
        guiRobot.interact(() -> selectedAutoExpense
                .set(secondAutoExpense));
        guiRobot.pauseForHuman();

        AutoExpenseCardHandle expectedAutoExpense = autoExpenseListPanelHandle
                .getAutoExpenseCardHandle(INDEX_SECOND_ENTRY.getZeroBased());
        AutoExpenseCardHandle selectedAutoExpense = autoExpenseListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedAutoExpense, selectedAutoExpense);
    }*/

    /**
     * Verifies that creating and deleting large number of entries in {@code AutoExpenseListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    /*@Test
    public void performanceTest() {
        ObservableList<AutoExpense> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of autoExpense cards exceeded time limit");
    }*/

    /**
     * Returns a list of entries containing {@code expenseCount} entries that is used to populate the
     * {@code AutoExpenseListPanel}.
     */
    private ObservableList<AutoExpense> createBackingList(int autoExpenseCount) {
        ObservableList<AutoExpense> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < autoExpenseCount; i++) {
            Description desc = new Description(i + "a");
            Amount amt = new Amount("20");
            Category category = new Category("Food", "Expense");
            Date date = new Date("2019 11 09");
            Frequency freq = Frequency.parse("weekly");
            AutoExpense autoExpense = new AutoExpense(category, desc, amt, Collections.emptySet(), freq, date);
            backingList.add(autoExpense);
        }
        return backingList;
    }

    /**
     * Initializes {@code autoExpenseListPanelHandle} with a {@code AutoExpenseListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code AutoExpenseListPanel}.
     */
    private void initUi(ObservableList<AutoExpense> backingList) {
        AutoExpensesPanel autoExpensePanel =
                new AutoExpensesPanel(backingList);
        uiPartExtension.setUiPart(autoExpensePanel);

        autoExpenseListPanelHandle = new AutoExpenseListPanelHandle(getChildNode(autoExpensePanel.getRoot(),
                AutoExpenseListPanelHandle.AUTOEXPENSE_LIST_VIEW_ID));
    }
}
