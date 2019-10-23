package seedu.moneygowhere.logic.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.LinkedHashSet;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.testutil.SpendingBuilder;

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
