package seedu.moneygowhere.logic.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_AMY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.SpendingBuilder;

//@@author Nanosync
class SpendingComparatorTest {

    @Test
    public void compare_greaterThan() {
        // Default order
        SpendingComparator spendingComparator = new SpendingComparator();

        SpendingBuilder builder1 = new SpendingBuilder().withDate(VALID_DATE_AMY);
        SpendingBuilder builder2 = new SpendingBuilder().withDate(VALID_DATE_BOB);

        int result = spendingComparator.compare(builder1.build(), builder2.build());
        assertEquals(1, result);
    }

    @Test
    public void compare_lessThan() {
        // Default order
        SpendingComparator spendingComparator = new SpendingComparator();

        SpendingBuilder builder1 = new SpendingBuilder().withDate(VALID_DATE_AMY);
        SpendingBuilder builder2 = new SpendingBuilder().withDate(VALID_DATE_BOB);

        int result = spendingComparator.compare(builder2.build(), builder1.build());
        assertEquals(-1, result);
    }

    @Test
    public void compare_equals() {
        // Default order
        SpendingComparator spendingComparator = new SpendingComparator();

        SpendingBuilder builder1 = new SpendingBuilder().withDate(VALID_DATE_AMY);
        SpendingBuilder builder2 = new SpendingBuilder().withDate(VALID_DATE_AMY);

        int result = spendingComparator.compare(builder1.build(), builder2.build());
        assertEquals(0, result);
    }

    @Test
    public void compare_nameSort_sortedCorrectly() {
        LinkedHashSet<SortField> fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.NAME, SortOrder.ASCENDING));

        SpendingBuilder builder1 = new SpendingBuilder()
                .withCost(VALID_COST_AMY) // 312
                .withDate(VALID_DATE_AMY) // 01/01
                .withRemark(VALID_REMARK_AMY)
                .withName("Apple");

        SpendingBuilder builder2 = new SpendingBuilder()
                .withCost(VALID_COST_AMY) // 312
                .withDate(VALID_DATE_AMY) // 01/01
                .withRemark(VALID_REMARK_AMY)
                .withName("apple");

        SpendingBuilder builder3 = new SpendingBuilder()
                .withCost(VALID_COST_AMY) // 312
                .withDate(VALID_DATE_AMY) // 01/01
                .withRemark(VALID_REMARK_AMY)
                .withName("applea");

        SpendingBuilder builder4 = new SpendingBuilder()
                .withCost(VALID_COST_AMY) // 312
                .withDate(VALID_DATE_AMY) // 01/01
                .withRemark(VALID_REMARK_AMY)
                .withName("Banana");

        SpendingComparator spendingComparator = new SpendingComparator(fields);

        List<Spending> spendingList = new ArrayList<>(
                Arrays.asList(builder4.build(), builder1.build(), builder3.build(), builder2.build()));
        spendingList.sort(spendingComparator);

        // Apple, apple, applea, Banana
        List<Spending> expectedSpendingList = new ArrayList<>(
                Arrays.asList(builder1.build(), builder2.build(), builder3.build(), builder4.build()));

        assertEquals(expectedSpendingList, spendingList);
    }

    @Test
    public void compare_multipleFields_valid() {
        LinkedHashSet<SortField> fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.COST, SortOrder.DESCENDING));
        fields.add(new SortField(SortAttribute.DATE, SortOrder.ASCENDING));
        fields.add(new SortField(SortAttribute.REMARK, SortOrder.ASCENDING));
        fields.add(new SortField(SortAttribute.NAME, SortOrder.DESCENDING));

        SpendingComparator spendingComparator = new SpendingComparator(fields);

        SpendingBuilder builder1 = new SpendingBuilder()
                .withCost(VALID_COST_AMY) // 312
                .withDate(VALID_DATE_AMY) // 01/01
                .withRemark("A")
                .withName(VALID_NAME_AMY);

        SpendingBuilder builder2 = new SpendingBuilder()
                .withCost(VALID_COST_BOB) // 123
                .withDate(VALID_DATE_BOB) // 02/01
                .withRemark("B")
                .withName(VALID_NAME_BOB);

        SpendingBuilder builder3 = new SpendingBuilder()
                .withCost(VALID_COST_BOB) // 123
                .withDate(VALID_DATE_AMY) // 01/01
                .withRemark("A")
                .withName("Chicken");

        SpendingBuilder builder4 = new SpendingBuilder()
                .withCost(VALID_COST_BOB) // 123
                .withDate(VALID_DATE_AMY) // 01/01
                .withRemark("A")
                .withName(VALID_NAME_BOB);

        // builder1 > builder2 (DESC - Cost)
        // builder1 > builder2 (ASC - Date)
        int result = spendingComparator.compare(builder1.build(), builder2.build());
        assertEquals(-1, result); // DESC

        result = spendingComparator.compare(builder1.build(), builder3.build());
        assertEquals(-1, result); // ASC

        result = spendingComparator.compare(builder3.build(), builder4.build());
        assertEquals(-1, result); // DESC
    }
}
