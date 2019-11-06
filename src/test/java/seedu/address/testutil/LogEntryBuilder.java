package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.Place;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.BorrowLogEntry;
import seedu.address.model.finance.logentry.IncomeLogEntry;
import seedu.address.model.finance.logentry.LendLogEntry;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;
import seedu.address.model.finance.util.SampleDataUtil;

/**
 * A utility class to help with building LogEntry objects.
 */
public class LogEntryBuilder {

    public static final String DEFAULT_AMOUNT = "2.80";
    public static final String DEFAULT_DAY = "22-11-2018";
    public static final String DEFAULT_DESCRIPTION = "UMian";
    public static final String DEFAULT_TRANSACTION_METHOD = "Cash";

    public static final String DEFAULT_PLACE = "Terrace";
    public static final String DEFAULT_PERSON = "Mr Mitch";

    private Amount amount;
    private TransactionDate tDate;
    private Description desc;
    private TransactionMethod tMethod;
    private Set<Category> cats;
    private Place place;
    private Person person;

    public LogEntryBuilder() {
        amount = new Amount(DEFAULT_AMOUNT);
        tDate = new TransactionDate(DEFAULT_DAY);
        desc = new Description(DEFAULT_DESCRIPTION);
        tMethod = new TransactionMethod(DEFAULT_TRANSACTION_METHOD);
        cats = new HashSet<>();
        place = new Place(DEFAULT_PLACE);
        person = new Person(DEFAULT_PERSON);
    }

    /**
     * Initializes the LogEntryBuilder with the data of {@code logEntryToCopy}.
     */
    public LogEntryBuilder(LogEntry logEntryToCopy) {
        amount = logEntryToCopy.getAmount();
        tDate = logEntryToCopy.getTransactionDate();
        desc = logEntryToCopy.getDescription();
        tMethod = logEntryToCopy.getTransactionMethod();
        cats = new HashSet<>(logEntryToCopy.getCategories());
        if (logEntryToCopy instanceof SpendLogEntry) {
            place = ((SpendLogEntry) logEntryToCopy).getPlace();
        }
        if (logEntryToCopy instanceof IncomeLogEntry) {
            person = ((IncomeLogEntry) logEntryToCopy).getFrom();
        }
        if (logEntryToCopy instanceof BorrowLogEntry) {
            person = ((BorrowLogEntry) logEntryToCopy).getFrom();
        }
        if (logEntryToCopy instanceof LendLogEntry) {
            person = ((LendLogEntry) logEntryToCopy).getTo();
        }
    }

    /**
     * Sets the {@code Amount} of the {@code LogEntry} that we are building.
     */
    public LogEntryBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Parses the {@code cats} into a {@code Set<Category>} and set it to the {@code LogEntry}
     * that we are building.
     */
    public LogEntryBuilder withCats(String ... cats) {
        this.cats = SampleDataUtil.getCategorySet(cats);
        return this;
    }

    /**
     * Sets the {@code TransactionDate} of the {@code LogEntry} that we are building.
     */
    public LogEntryBuilder withTransactionDate(String tDate) {
        this.tDate = new TransactionDate(tDate);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code LogEntry} that we are building.
     */
    public LogEntryBuilder withDescription(String description) {
        this.desc = new Description(description);
        return this;
    }

    /**
     * Sets the {@code TransactionMethod} of the {@code LogEntry} that we are building.
     */
    public LogEntryBuilder withTransactionMethod(String tMethod) {
        this.tMethod = new TransactionMethod(tMethod);
        return this;
    }

    /**
     * Sets the {@code Place} of the {@code SpendLogEntry} that we are building.
     */
    public LogEntryBuilder withPlace(String place) {
        this.place = new Place(place);
        return this;
    }

    /**
     * Sets the {@code Person} of the {@code LogEntry} that we are building.
     */
    public LogEntryBuilder withPerson(String person) {
        this.person = new Person(person);
        return this;
    }

    public LogEntry buildSpend() {
        return new SpendLogEntry(amount, tDate, desc, tMethod, cats, place);
    }

    public LogEntry buildIncome() {
        return new IncomeLogEntry(amount, tDate, desc, tMethod, cats, person);
    }

    public LogEntry buildBorrow() {
        return new BorrowLogEntry(amount, tDate, desc, tMethod, cats, person);
    }

    public LogEntry buildLend() {
        return new LendLogEntry(amount, tDate, desc, tMethod, cats, person);
    }

}
