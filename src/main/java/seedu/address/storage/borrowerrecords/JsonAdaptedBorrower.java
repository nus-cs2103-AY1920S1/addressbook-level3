package seedu.address.storage.borrowerrecords;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.borrower.Email;
import seedu.address.model.borrower.Name;
import seedu.address.model.borrower.Phone;

/**
 * Jackson-friendly version of {@link Borrower}.
 */
class JsonAdaptedBorrower {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Borrower's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String borrowerId;

    /**
     * Constructs a {@code JsonAdaptedBorrower} with the given borrower detail.
     */
    @JsonCreator
    public JsonAdaptedBorrower(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email, @JsonProperty("borrowerId") String borrowerId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.borrowerId = borrowerId;
    }

    /**
     * Converts a given {@code Borrower} into this class for Jackson use.
     */
    public JsonAdaptedBorrower(Borrower source) {

        name = source.getName().name;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        borrowerId = source.getBorrowerId().value;
    }

    /**
     * Converts this Jackson-friendly adapted book object into the model's {@code Borrower} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Borrower toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }

        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (borrowerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BorrowerId.class.getSimpleName()));
        }

        if (!BorrowerId.isValidBorrowerId(borrowerId)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final BorrowerId modelBorrowerId = new BorrowerId(borrowerId);

        return new Borrower(modelName, modelPhone, modelEmail, modelBorrowerId);
    }

}
