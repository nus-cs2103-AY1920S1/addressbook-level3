package thrift.testutil;

import java.util.HashSet;
import java.util.Set;

import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Remark;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;
import thrift.model.util.SampleDataUtil;

/**
 * A utility class to help with building Transaction objects.
 */
public class ExpenseBuilder {

    public static final String DEFAULT_DESCRIPTION = "Laksa";
    public static final String DEFAULT_COST = "3.50";
    public static final String DEFAULT_REMARK = "One of the best Laksa";
    public static final String DEFAULT_DATE = "13/03/1937";

    private Description description;
    private Value value;
    private Remark remark;
    private TransactionDate date;
    private Set<Tag> tags;

    public ExpenseBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        value = new Value(DEFAULT_COST);
        remark = new Remark(DEFAULT_REMARK);
        date = new TransactionDate(DEFAULT_DATE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code transactionToCopy}.
     */
    public ExpenseBuilder(Transaction transactionToCopy) {
        description = transactionToCopy.getDescription();
        value = transactionToCopy.getValue();
        remark = transactionToCopy.getRemark();
        date = transactionToCopy.getDate();
        tags = new HashSet<>(transactionToCopy.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Expense} that we are building.
     */
    public ExpenseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Value} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withValue(String value) {
        this.value = new Value(value);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code TransactionDate} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDate(String date) {
        this.date = new TransactionDate(date);
        return this;
    }

    public Expense build() {
        return new Expense(description, value, remark, date, tags);
    }

}
