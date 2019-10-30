package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.util.LinkedHashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.sorting.SortAttribute;
import seedu.moneygowhere.logic.sorting.SortField;
import seedu.moneygowhere.logic.sorting.SortOrder;
import seedu.moneygowhere.logic.sorting.SpendingComparator;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.SortUtil;
import seedu.moneygowhere.testutil.SpendingBuilder;

class SortCommandTest {
    private Model model;
    private Model expectedModel;

    @Test
    public void equals() {
        SortCommand sortCommand = new SortCommand(SortUtil.getDefaultSortFieldSet());

        // same values -> returns true
        SortCommand commandWithSameValues = new SortCommand(SortUtil.getDefaultSortFieldSet());
        assertTrue(sortCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(sortCommand.equals(sortCommand));

        // null -> returns false
        assertFalse(sortCommand == null);

        // different types -> returns false
        assertFalse(sortCommand.equals(new ClearCommand()));

        // different value -> returns false
        assertFalse(sortCommand.equals(new SortCommand(new LinkedHashSet<>())));
    }

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
    }

    @Test
    public void execute_defaultFieldsSortedList_success() {
        SortCommand command = new SortCommand(SortUtil.getDefaultSortFieldSet());
        SpendingComparator spendingComparator = new SpendingComparator();
        assertCommandSuccess(command, model, SortCommand.MESSAGE_SUCCESS + "\n" + spendingComparator, expectedModel);
    }

    @Test
    public void execute_oneFieldSortedList_success() {
        LinkedHashSet<SortField> fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.DATE, SortOrder.ASCENDING));

        SortCommand command = new SortCommand(fields);
        SpendingComparator spendingComparator = new SpendingComparator(fields);
        expectedModel.updateSortedSpendingList(spendingComparator);
        assertCommandSuccess(command, model, SortCommand.MESSAGE_SUCCESS + "\n" + spendingComparator, expectedModel);
        assertEquals(expectedModel.getFilteredSpendingList(), model.getFilteredSpendingList());
    }

    @Test
    public void execute_differentFieldsSortedList_success() {
        LinkedHashSet<SortField> fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.NAME, SortOrder.DESCENDING));
        fields.add(new SortField(SortAttribute.DATE, SortOrder.ASCENDING));
        fields.add(new SortField(SortAttribute.COST, SortOrder.ASCENDING));
        fields.add(new SortField(SortAttribute.REMARK, SortOrder.DESCENDING));

        Spending apple = new SpendingBuilder().withName("Apple2").build();
        model.addSpending(apple);
        expectedModel.addSpending(apple);

        SortCommand command = new SortCommand(fields);

        SpendingComparator spendingComparator = new SpendingComparator(fields);
        expectedModel.updateSortedSpendingList(spendingComparator);
        assertCommandSuccess(command, model, SortCommand.MESSAGE_SUCCESS + "\n" + spendingComparator, expectedModel);
        assertEquals(expectedModel.getFilteredSpendingList(), model.getFilteredSpendingList());
    }
}
