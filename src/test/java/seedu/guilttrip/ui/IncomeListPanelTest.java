package seedu.guilttrip.ui;

//import static java.time.Duration.ofMillis;
//import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalIncomes;

import java.util.Collections;

//import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.ui.gui.guihandles.IncomeCardHandle;
import seedu.guilttrip.ui.gui.guihandles.IncomeListPanelHandle;
import seedu.guilttrip.ui.income.IncomeListPanel;

/**
 * Unit test for income list panel.
 */
public class IncomeListPanelTest extends GuiUnitTest {
    private static final ObservableList<Income> TYPICAL_INCOMES = FXCollections.observableList(getTypicalIncomes());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Income> selectedIncome = new SimpleObjectProperty<>();
    private IncomeListPanelHandle incomeListPanelHandle;

    /*@Test
    public void display() {
        initUi(TYPICAL_INCOMES);

        for (int i = 0; i < TYPICAL_INCOMES.size(); i++) {
            incomeListPanelHandle.navigateToCard(TYPICAL_INCOMES.get(i));
            Income expectedIncome = TYPICAL_INCOMES.get(i);

            IncomeCardHandle actualCard = incomeListPanelHandle.getIncomeCardHandle(i);

            assertCardDisplaysEntry(expectedIncome, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }*/

    private void assertCardDisplaysEntry(Income expectedIncome, IncomeCardHandle actualCard) {
    }

    /*@Test
    public void selection_modelSelectedIncomeChanged_selectionChanges() {
        initUi(TYPICAL_INCOMES);
        Income secondIncome = TYPICAL_INCOMES.get(INDEX_SECOND_ENTRY.getZeroBased());
        guiRobot.interact(() -> selectedIncome
                .set(secondIncome));
        guiRobot.pauseForHuman();

        IncomeCardHandle expectedIncome = incomeListPanelHandle
                .getIncomeCardHandle(INDEX_SECOND_ENTRY.getZeroBased());
        IncomeCardHandle selectedIncome = incomeListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedIncome, selectedIncome);
    }*/

    /**
     * Verifies that creating and deleting large number of entries in {@code IncomeListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    /*@Test
    public void performanceTest() {
        ObservableList<Income> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of income cards exceeded time limit");
    }*/

    /**
     * Returns a list of entries containing {@code incomeCount} entries that is used to populate the
     * {@code IncomeListPanel}.
     */
    private ObservableList<Income> createBackingList(int incomeCount) {
        ObservableList<Income> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < incomeCount; i++) {
            Description desc = new Description(i + "a");
            Amount amt = new Amount("20");
            Category category = new Category("Salary", "Income");
            Date date = new Date("2019 11 09");
            Income income = new Income(category, desc, date, amt, Collections.emptySet());
            backingList.add(income);
        }
        return backingList;
    }

    /**
     * Initializes {@code incomeListPanelHandle} with a {@code IncomeListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code IncomeListPanel}.
     */
    private void initUi(ObservableList<Income> backingList) {
        IncomeListPanel incomeListPanel =
                new IncomeListPanel(backingList);
        uiPartExtension.setUiPart(incomeListPanel);

        incomeListPanelHandle = new IncomeListPanelHandle(getChildNode(incomeListPanel.getRoot(),
                IncomeListPanelHandle.INCOME_LIST_VIEW_ID));
    }
}
