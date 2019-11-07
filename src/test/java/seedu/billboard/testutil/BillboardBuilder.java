package seedu.billboard.testutil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.tag.Tag;

/**
 * A utility class to help with building Billboard objects.
 * Example usage: <br>
 *     {@code Billboard ab = new BillboardBuilder().withExpense("John", "Doe").build();}
 */
public class BillboardBuilder {

    private Billboard billboard;

    public BillboardBuilder() {
        billboard = new Billboard();
    }

    public BillboardBuilder(Billboard billboard) {
        this.billboard = billboard;
    }

    /**
     * Adds a new {@code Expense} to the {@code Billboard} that we are building.
     */
    public BillboardBuilder withExpense(Expense expense) {
        billboard.addExpense(expense);
        List<String> tagNames = expense.getTags().stream().map(x -> x.tagName).collect(Collectors.toList());
        Set<Tag> tags = billboard.retrieveTags(tagNames);
        billboard.incrementCount(tags);
        return this;
    }

    public Billboard build() {
        return billboard;
    }
}
