package seedu.address.testutil;

import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.borrower.Email;
import seedu.address.model.borrower.Name;
import seedu.address.model.borrower.Phone;

/**
 * A utility class to help with building Borrower objects.
 */
public class BorrowerBuilder {
    public static final String DEFAULT_NAME = "Amy Anyhow";
    public static final String DEFAULT_BORROWER_ID = "K0001";
    public static final String DEFAULT_EMAIL = "amyanyhow@api.com";
    public static final String DEFAULT_PHONE_NUMBER = "62226111";

    private Name name;
    private Phone phone;
    private Email email;
    private BorrowerId borrowerId;

    public BorrowerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE_NUMBER);
        email = new Email(DEFAULT_EMAIL);
        borrowerId = new BorrowerId(DEFAULT_BORROWER_ID);
    }

    public BorrowerBuilder(Borrower borrowerToCopy) {
        name = borrowerToCopy.getName();
        phone = borrowerToCopy.getPhone();
        email = borrowerToCopy.getEmail();
        borrowerId = borrowerToCopy.getBorrowerId();
    }

    /**
     * Sets the {@code Name} of the {@code Borrower} that we are building.
     */
    public BorrowerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Borrower} that we are building.
     */
    public BorrowerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Borrower} that we are building.
     */
    public BorrowerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Borrower ID} of the {@code Borrower} that we are building.
     */
    public BorrowerBuilder withBorrowerId(String id) {
        this.borrowerId = new BorrowerId(id);
        return this;

    }

    public Borrower build() {
        return new Borrower(name, phone, email, borrowerId);
    }

    public Borrower actualBuild() {
        return new Borrower(name, phone, email);
    }
}
