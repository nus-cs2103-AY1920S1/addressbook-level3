package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.storage.JsonAdaptedCustomProperty.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.ENDDATE;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.ParameterType;

class JsonAdaptedCustomPropertyTest {
    private static final String INVALID_PREFIX_NAME = "1";
    private static final String INVALID_FULL_NAME = "";
    private static final String INVALID_PARAMETER_TYPE = "integer";

    private static final String VALID_PREFIX_NAME = RATING.getPrefix().getPrefixName();
    private static final String VALID_FULL_NAME = RATING.getFullName();
    private static final String VALID_PARAMETER_TYPE = RATING.getParameterType().getParameterName();

    private static final String FULL_NAME_FIELD = "Full Name";

    @Test
    public void toModelType_nullPrefix_throwsIllegalValueException() {
        JsonAdaptedCustomProperty jsonAdaptedCustomProperty =
                new JsonAdaptedCustomProperty(null, VALID_FULL_NAME, VALID_PARAMETER_TYPE);
        assertThrows(IllegalValueException.class,
            String.format(MISSING_FIELD_MESSAGE_FORMAT, Prefix.class.getSimpleName()), ()
                -> jsonAdaptedCustomProperty.toModelType());
    }

    @Test
    public void toModelType_invalidPrefix_throwsIllegalValueException() {
        JsonAdaptedCustomProperty jsonAdaptedCustomProperty =
                new JsonAdaptedCustomProperty(INVALID_PREFIX_NAME, VALID_FULL_NAME, VALID_PARAMETER_TYPE);
        assertThrows(IllegalValueException.class,
            CustomProperty.PREFIX_NAME_CONSTRAINTS, ()
                -> jsonAdaptedCustomProperty.toModelType());
    }

    @Test
    public void toModelType_nullFullName_throwsIllegalValueException() {
        JsonAdaptedCustomProperty jsonAdaptedCustomProperty =
                new JsonAdaptedCustomProperty(VALID_PREFIX_NAME, null, VALID_PARAMETER_TYPE);
        assertThrows(IllegalValueException.class,
            String.format(MISSING_FIELD_MESSAGE_FORMAT, FULL_NAME_FIELD), ()
                -> jsonAdaptedCustomProperty.toModelType());
    }

    @Test
    public void toModelType_invalidFullName_throwsIllegalValueException() {
        JsonAdaptedCustomProperty jsonAdaptedCustomProperty =
                new JsonAdaptedCustomProperty(VALID_PREFIX_NAME, INVALID_FULL_NAME, VALID_PARAMETER_TYPE);
        assertThrows(IllegalValueException.class,
                CustomProperty.FULL_NAME_CONSTRAINTS, ()
                -> jsonAdaptedCustomProperty.toModelType());
    }

    @Test
    public void toModelType_nullParameterType_throwsIllegalValueException() {
        JsonAdaptedCustomProperty jsonAdaptedCustomProperty =
                new JsonAdaptedCustomProperty(VALID_PREFIX_NAME, VALID_FULL_NAME, null);
        assertThrows(IllegalValueException.class,
            String.format(MISSING_FIELD_MESSAGE_FORMAT, ParameterType.class.getSimpleName()), ()
                -> jsonAdaptedCustomProperty.toModelType());
    }

    @Test
    public void toModelType_invalidParameterType_throwsIllegalValueException() {
        JsonAdaptedCustomProperty jsonAdaptedCustomProperty =
                new JsonAdaptedCustomProperty(VALID_PREFIX_NAME, VALID_FULL_NAME, INVALID_PARAMETER_TYPE);
        assertThrows(IllegalValueException.class,
            ParameterType.PARAMETER_CONSTRAINTS, ()
                -> jsonAdaptedCustomProperty.toModelType());
    }

    @Test
    public void toModelType_validParameterTypes_returnParameterType() throws Exception {
        //Number
        assertEquals(new JsonAdaptedCustomProperty(RATING).toModelType(), RATING);
        //Text
        assertEquals(new JsonAdaptedCustomProperty(REMARK).toModelType(), REMARK);
        //Date
        assertEquals(new JsonAdaptedCustomProperty(ENDDATE).toModelType(), ENDDATE);
    }

}
