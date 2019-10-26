package seedu.address.commons.util;

import static seedu.address.model.binitem.BinItem.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;

import seedu.address.model.binitem.BinItem;
import seedu.address.model.binitem.Binnable;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * A utility class to help with building BinItem objects.
 */
public class BinItemBuilder {

    public static final String DEFAULT_DATE_DELETED = "20 Oct 2019 at 10:00 AM";
    public static final String DEFAULT_EXPIRY_DATE = "21 Nov 2019 at 10:10 AM";

    private Binnable item;
    private LocalDateTime dateDeleted;
    private LocalDateTime expiryDate;

    public BinItemBuilder() {
        item = new PersonBuilder().build();
        dateDeleted = LocalDateTime.parse(DEFAULT_DATE_DELETED, DATE_TIME_FORMATTER);
        expiryDate = LocalDateTime.parse(DEFAULT_EXPIRY_DATE, DATE_TIME_FORMATTER);
    }

    /**
     * Initializes the Policy with the data of {@code binItemToCopy}.
     */
    public BinItemBuilder(BinItem binItemToCopy) {
        if (binItemToCopy.getItem() instanceof Person) {
            item = new PersonBuilder(((Person) binItemToCopy.getItem())).build();
        } else {
            item = new PolicyBuilder(((Policy) binItemToCopy.getItem())).build();
        }
        dateDeleted = LocalDateTime.parse(binItemToCopy.getDateDeleted(), DATE_TIME_FORMATTER);
        expiryDate = LocalDateTime.parse(binItemToCopy.getExpiryDate(), DATE_TIME_FORMATTER);
    }

    /**
     * Sets the {@code item} of the {@code BinItem} that we are building.
     */
    public BinItemBuilder withItem(Binnable binItem) {
        if (binItem instanceof Person) {
            item = new PersonBuilder(((Person) binItem)).build();
        } else {
            item = new PolicyBuilder(((Policy) binItem)).build();
        }
        return this;
    }

    /**
     * Sets the {@code dateDeleted} of the {@code BinItem} that we are building.
     */
    public BinItemBuilder withDateDeleted(String dateDeleted) {
        this.dateDeleted = LocalDateTime.parse(dateDeleted, DATE_TIME_FORMATTER);
        return this;
    }

    /**
     * Sets the {@code expiryDate} of the {@code BinItem} that we are building.
     */
    public BinItemBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = LocalDateTime.parse(expiryDate, DATE_TIME_FORMATTER);
        return this;
    }

    public BinItem build() {
        return new BinItem(item, dateDeleted, expiryDate);
    }

}
