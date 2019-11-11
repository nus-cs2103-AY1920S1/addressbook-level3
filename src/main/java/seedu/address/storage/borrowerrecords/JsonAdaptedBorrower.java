package seedu.address.storage.borrowerrecords;

import static seedu.address.commons.core.Messages.MESSAGE_LOAN_ID_DOES_NOT_EXISTS;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.borrower.Email;
import seedu.address.model.borrower.Name;
import seedu.address.model.borrower.Phone;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;
import seedu.address.model.loan.LoanList;

/**
 * Jackson-friendly version of {@link Borrower}.
 */
class JsonAdaptedBorrower {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Borrower's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String borrowerId;
    private final List<String> currentLoanList = new ArrayList<>();
    private final List<String> returnedLoanList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBorrower} with the given borrower detail.
     */
    @JsonCreator
    public JsonAdaptedBorrower(@JsonProperty("name") String name,
                               @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email,
                               @JsonProperty("borrowerId") String borrowerId,
                               @JsonProperty("currentLoanList") List<String> currentLoanList,
                               @JsonProperty("returnedLoanList") List<String> returnedLoanList) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.borrowerId = borrowerId;
        this.currentLoanList.addAll(currentLoanList);
        this.returnedLoanList.addAll(returnedLoanList);
    }

    /**
     * Converts a given {@code Borrower} into this class for Jackson use.
     */
    public JsonAdaptedBorrower(Borrower source) {
        name = source.getName().name;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        borrowerId = source.getBorrowerId().value;
        source.getCurrentLoanList().forEach(loan -> currentLoanList.add(loan.getLoanId().toString()));
        source.getReturnedLoanList().forEach(loan -> returnedLoanList.add(loan.getLoanId().toString()));
    }

    /**
     * Converts this Jackson-friendly adapted book object into the model's {@code Borrower} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Borrower toModelType(ReadOnlyLoanRecords initialLoanRecords) throws IllegalValueException {

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

        if (currentLoanList == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LoanList.class.getSimpleName()));
        }
        final LoanList modelCurrentLoanList = getModelLoanList(currentLoanList, initialLoanRecords);

        if (returnedLoanList == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LoanList.class.getSimpleName()));
        }
        final LoanList modelReturnedLoanList = getModelLoanList(returnedLoanList, initialLoanRecords);

        return new Borrower(modelName, modelPhone, modelEmail, modelBorrowerId,
                modelCurrentLoanList, modelReturnedLoanList);
    }

    private LoanList getModelLoanList(List<String> loanList, ReadOnlyLoanRecords initialLoanRecords)
            throws IllegalValueException {
        LoanList modelLoanList = new LoanList();

        for (String loanIdString : loanList) {
            if (!LoanId.isValidLoanId(loanIdString)) {
                throw new IllegalValueException(LoanId.MESSAGE_CONSTRAINTS);
            }

            LoanId loanId = new LoanId(loanIdString);
            Loan modelLoan = initialLoanRecords.getLoansMap().get(loanId);
            if (modelLoan == null) {
                throw new IllegalValueException(String.format(MESSAGE_LOAN_ID_DOES_NOT_EXISTS, loanId));
            }

            modelLoanList.add(modelLoan);
        }

        return modelLoanList;
    }
}
