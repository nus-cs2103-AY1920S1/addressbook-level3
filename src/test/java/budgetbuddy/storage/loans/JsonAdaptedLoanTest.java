package budgetbuddy.storage.loans;

import static budgetbuddy.testutil.Assert.assertThrows;
import static budgetbuddy.testutil.loanutil.TypicalLoans.JOHN_OUT_UNPAID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;

public class JsonAdaptedLoanTest {

    private static final String INVALID_PERSON = "R@chel";
    private static final String INVALID_DIRECTION = "NOWHERE";
    private static final Long INVALID_AMOUNT = -11111L;
    private static final Long INVALID_AMOUNT_ZERO = 0L;
    private static final String INVALID_DATE = "1st Oct";
    private static final String INVALID_DESCRIPTION = "d".repeat(Description.MAX_LENGTH + 1);
    private static final String INVALID_STATUS = "DEAD";

    private static final String VALID_PERSON = JOHN_OUT_UNPAID.getPerson().getName().toString();
    private static final String VALID_DIRECTION = JOHN_OUT_UNPAID.getDirection().toString();
    private static final Long VALID_AMOUNT = JOHN_OUT_UNPAID.getAmount().toLong();
    private static final String VALID_DATE = JOHN_OUT_UNPAID.getDateString();
    private static final String VALID_DESCRIPTION = JOHN_OUT_UNPAID.getDescription().toString();
    private static final String VALID_STATUS = JOHN_OUT_UNPAID.getStatus().toString();

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedLoan(
                null, null, VALID_AMOUNT,
                null, null, null));
    }

    @Test
    public void toModelType_validLoanDetails_returnsLoan() throws IllegalValueException {
        JsonAdaptedLoan jsonAdaptedLoan = new JsonAdaptedLoan(JOHN_OUT_UNPAID);
        assertEquals(JOHN_OUT_UNPAID, jsonAdaptedLoan.toModelType());
    }

    @Test
    public void toModelType_invalidPerson_throwsIllegalValueException() {
        JsonAdaptedLoan jsonAdaptedLoan = new JsonAdaptedLoan(INVALID_PERSON, VALID_DIRECTION, VALID_AMOUNT,
                VALID_DATE, VALID_DESCRIPTION, VALID_STATUS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedLoan::toModelType);
    }

    @Test
    public void toModelType_invalidDirection_throwsIllegalValueException() {
        JsonAdaptedLoan jsonAdaptedLoan = new JsonAdaptedLoan(VALID_PERSON, INVALID_DIRECTION, VALID_AMOUNT,
                VALID_DATE, VALID_DESCRIPTION, VALID_STATUS);
        String expectedMessage = Direction.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedLoan::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedLoan jsonAdaptedLoan = new JsonAdaptedLoan(VALID_PERSON, VALID_DIRECTION, INVALID_AMOUNT,
                VALID_DATE, VALID_DESCRIPTION, VALID_STATUS);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedLoan::toModelType);
    }

    @Test
    public void toModelType_invalidAmountZero_throwsIllegalValueException() {
        JsonAdaptedLoan jsonAdaptedLoan = new JsonAdaptedLoan(VALID_PERSON, VALID_DIRECTION, INVALID_AMOUNT_ZERO,
                VALID_DATE, VALID_DESCRIPTION, VALID_STATUS);
        String expectedMessage = Loan.MESSAGE_AMOUNT_POSITIVE_CONSTRAINT;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedLoan::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedLoan jsonAdaptedLoan = new JsonAdaptedLoan(VALID_PERSON, VALID_DIRECTION, VALID_AMOUNT,
                INVALID_DATE, VALID_DESCRIPTION, VALID_STATUS);
        String expectedMessage = JsonAdaptedLoan.MESSAGE_DATE_ERROR;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedLoan::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedLoan jsonAdaptedLoan = new JsonAdaptedLoan(VALID_PERSON, VALID_DIRECTION, VALID_AMOUNT,
                VALID_DATE, INVALID_DESCRIPTION, VALID_STATUS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedLoan::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedLoan jsonAdaptedLoan = new JsonAdaptedLoan(VALID_PERSON, VALID_DIRECTION, VALID_AMOUNT,
                VALID_DATE, VALID_DESCRIPTION, INVALID_STATUS);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedLoan::toModelType);
    }
}
