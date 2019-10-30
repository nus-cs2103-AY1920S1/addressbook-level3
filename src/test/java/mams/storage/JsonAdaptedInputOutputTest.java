package mams.storage;

import static mams.testutil.Assert.assertThrows;
import static mams.testutil.TypicalCommandHistory.VALID_COMMAND_1;
import static mams.testutil.TypicalCommandHistory.VALID_COMMAND_OUTPUT_1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mams.commons.exceptions.IllegalValueException;
import mams.logic.InputOutput;

public class JsonAdaptedInputOutputTest {

    public static final String EMPTY_STRING = "";

    public static final String MISSING_INPUT =
            String.format(JsonAdaptedInputOutput.MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedInputOutput.INPUT_PROPERTY_NAME);

    public static final String MISSING_OUTPUT =
            String.format(JsonAdaptedInputOutput.MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedInputOutput.OUTPUT_PROPERTY_NAME);

    @Test
    public void toLogicType_validParams_returnsInputOutput() throws IllegalValueException {
        JsonAdaptedInputOutput inputOutput =
                new JsonAdaptedInputOutput(VALID_COMMAND_1, VALID_COMMAND_OUTPUT_1);
        InputOutput expectedInputOutput = new InputOutput(VALID_COMMAND_1, VALID_COMMAND_OUTPUT_1);
        assertEquals(inputOutput.toLogicType(), expectedInputOutput);
    }

    @Test
    public void toLogicType_nullInput_throwsIllegalValueException() {
        JsonAdaptedInputOutput inputOutput =
                new JsonAdaptedInputOutput(null, VALID_COMMAND_OUTPUT_1);
        assertThrows(IllegalValueException.class, MISSING_INPUT, inputOutput::toLogicType);
    }

    @Test
    public void toLogicType_nullOutput_throwsIllegalValueException() {
        JsonAdaptedInputOutput inputOutput =
                new JsonAdaptedInputOutput(VALID_COMMAND_1, null);
        assertThrows(IllegalValueException.class, MISSING_OUTPUT, inputOutput::toLogicType);
    }

    /**
     * it is possible to have a blank space user input, but MAMS will never give a blank-space output.
     */
    @Test
    public void toLogicType_emptyOutput_throwsIllegalValueException() {
        JsonAdaptedInputOutput inputOutput =
                new JsonAdaptedInputOutput(VALID_COMMAND_1, EMPTY_STRING);
        assertThrows(IllegalValueException.class, MISSING_OUTPUT, inputOutput::toLogicType);
    }

}
