package budgetbuddy.storage.loans;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.testutil.loanutil.TypicalDebtors;

public class JsonAdaptedDebtorTest {

    private static final String INVALID_DEBTOR = "R@chael";
    private static final List<String> INVALID_CREDITORS = List.of("Beck!", "Cl@rk");
    private static final List<Long> INVALID_AMOUNTS = List.of(-111L, -111111111111111111L);

    private static final String VALID_DEBTOR = TypicalDebtors.JOHN.getDebtor().getName().toString();
    private static final List<String> VALID_CREDITORS = TypicalDebtors.JOHN.getCreditors().keySet().stream()
            .map(person -> person.getName().toString())
            .collect(Collectors.toList());
    private static final List<Long> VALID_AMOUNTS = TypicalDebtors.JOHN.getCreditors().values().stream()
            .map(Amount::toLong)
            .collect(Collectors.toList());

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedDebtor(
                null, null, null));
    }

    @Test
    public void toModelType_validDebtorDetails_returnsDebtor() throws IllegalValueException {
        JsonAdaptedDebtor jsonAdaptedDebtor = new JsonAdaptedDebtor(TypicalDebtors.JOHN);
        assertEquals(TypicalDebtors.JOHN, jsonAdaptedDebtor.toModelType());
    }

    @Test
    public void toModelType_invalidDebtor_throwsIllegalValueException() {
        JsonAdaptedDebtor jsonAdaptedDebtor = new JsonAdaptedDebtor(INVALID_DEBTOR, VALID_CREDITORS, VALID_AMOUNTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedDebtor::toModelType);
    }

    @Test
    public void toModelType_invalidCreditors_throwsIllegalValueException() {
        JsonAdaptedDebtor jsonAdaptedDebtor = new JsonAdaptedDebtor(VALID_DEBTOR, INVALID_CREDITORS, VALID_AMOUNTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedDebtor::toModelType);
    }

    @Test
    public void toModelType_invalidAmounts_throwsIllegalValueException() {
        JsonAdaptedDebtor jsonAdaptedDebtor = new JsonAdaptedDebtor(VALID_DEBTOR, VALID_CREDITORS, INVALID_AMOUNTS);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedDebtor::toModelType);
    }

    @Test
    public void toModelType_creditorsAmountsNumbersMismatch_throwsIllegalValueException() {
        JsonAdaptedDebtor jsonAdaptedDebtor = new JsonAdaptedDebtor(VALID_DEBTOR, VALID_CREDITORS,
                TypicalDebtors.MARY.getCreditors().values().stream().map(Amount::toLong).collect(Collectors.toList()));
        String expectedMessage = JsonAdaptedDebtor.CREDITOR_AMOUNT_NUMBERS_MISMATCH;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedDebtor::toModelType);
    }
}
