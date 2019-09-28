package thrift.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import thrift.model.ReadOnlyThrift;
import thrift.model.Thrift;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;

/**
 * Contains utility methods for populating {@code Thrift} with sample data.
 */
public class SampleDataUtil {
    public static Transaction[] getSampleTransaction() {
        return new Transaction[] {
            new Expense(new Description("Laksa"), new Value("3.50"), new TransactionDate("13/03/1937"),
                    getTagSet("Lunch")),
            new Expense(new Description("Airpods 2nd Generation"), new Value("300"),
                    new TransactionDate("14/03/1937"), getTagSet("Accessory")),
            new Income(new Description("Bursary"), new Value("500"), new TransactionDate("12/03/1937"),
                    getTagSet("Award"))
        };
    }

    public static ReadOnlyThrift getSampleThrift() {
        Thrift sampleThrift = new Thrift();
        for (Transaction sampleTransaction : getSampleTransaction()) {
            sampleThrift.addTransaction(sampleTransaction);
        }
        return sampleThrift;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
