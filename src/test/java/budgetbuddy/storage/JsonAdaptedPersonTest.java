package budgetbuddy.storage;

import static budgetbuddy.storage.loans.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.storage.loans.JsonAdaptedLoan;
import budgetbuddy.storage.loans.JsonAdaptedPerson;
import budgetbuddy.testutil.TypicalPersons;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";

    private static final String VALID_NAME = TypicalPersons.BENSON.getName().toString();
    private static final List<JsonAdaptedLoan> VALID_LOANS = TypicalPersons.BENSON.getLoans().stream()
            .map(JsonAdaptedLoan::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(TypicalPersons.BENSON);
        assertEquals(TypicalPersons.BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_LOANS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_LOANS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
