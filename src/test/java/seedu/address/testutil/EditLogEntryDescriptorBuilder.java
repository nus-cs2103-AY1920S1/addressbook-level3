package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.finance.commands.EditCommand.EditLogEntryDescriptor;
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

/**
 * A utility class to help with building EditLogEntryDescriptor objects.
 */
public class EditLogEntryDescriptorBuilder {

    private EditLogEntryDescriptor descriptor;

    public EditLogEntryDescriptorBuilder() {
        descriptor = new EditLogEntryDescriptor();
    }

    public EditLogEntryDescriptorBuilder(EditLogEntryDescriptor descriptor) {
        this.descriptor = new EditLogEntryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLogEntryDescriptor} with fields containing {@code Log Entry}'s details
     */
    public EditLogEntryDescriptorBuilder(LogEntry logEntry) {
        descriptor = new EditLogEntryDescriptor();
        descriptor.setAmount(logEntry.getAmount());
        descriptor.setTransactionDate(logEntry.getTransactionDate());
        descriptor.setDesc(logEntry.getDescription());
        descriptor.setTMethod(logEntry.getTransactionMethod());
        descriptor.setCategories(logEntry.getCategories());
        if (logEntry instanceof SpendLogEntry) {
            descriptor.setPlace(((SpendLogEntry) logEntry).getPlace());
        } else if (logEntry instanceof IncomeLogEntry) {
            descriptor.setFrom(((IncomeLogEntry) logEntry).getFrom());
        } else if (logEntry instanceof BorrowLogEntry) {
            descriptor.setFrom(((BorrowLogEntry) logEntry).getFrom());
        } else {
            descriptor.setTo(((LendLogEntry) logEntry).getTo());
        }
    }

    /**
     * Sets the {@code Amount} of the {@code EditLogEntryDescriptor} that we are building.
     */
    public EditLogEntryDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code TransactionDate} of the {@code EditLogEntryDescriptor} that we are building.
     */
    public EditLogEntryDescriptorBuilder withTDate(String tDate) {
        descriptor.setTransactionDate(new TransactionDate(tDate));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditLogEntryDescriptor} that we are building.
     */
    public EditLogEntryDescriptorBuilder withDescription(String desc) {
        descriptor.setDesc(new Description(desc));
        return this;
    }

    /**
     * Sets the {@code TransactionMethod} of the {@code EditLogEntryDescriptor} that we are building.
     */
    public EditLogEntryDescriptorBuilder withTransactionMethod(String tMet) {
        descriptor.setTMethod(new TransactionMethod(tMet));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code EditLogEntryDescriptor}
     * that we are building.
     */
    public EditLogEntryDescriptorBuilder withCats(String... cats) {
        Set<Category> catSet = Stream.of(cats).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(catSet);
        return this;
    }

    /**
     * Sets the {@code Place} of the {@code EditLogEntryDescriptor} that we are building.
     */
    public EditLogEntryDescriptorBuilder withPlace(String place) {
        descriptor.setPlace(new Place(place));
        return this;
    }

    /**
     * Sets the {@code Person} (income source/borrowed from) of the
     * {@code EditLogEntryDescriptor} that we are building.
     */
    public EditLogEntryDescriptorBuilder withFrom(String from) {
        descriptor.setFrom(new Person(from));
        return this;
    }

    /**
     * Sets the {@code Person} (lent to) of the {@code EditLogEntryDescriptor} that we are building.
     */
    public EditLogEntryDescriptorBuilder withTo(String to) {
        descriptor.setTo(new Person(to));
        return this;
    }

    public EditLogEntryDescriptor build() {
        return descriptor;
    }
}
