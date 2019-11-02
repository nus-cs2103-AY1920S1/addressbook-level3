package mams.storage;

import static mams.testutil.Assert.assertThrows;
import static mams.testutil.TypicalCommandHistory.SUCCESSFUL_IO_1;
import static mams.testutil.TypicalCommandHistory.UNSUCCESSFUL_IO_1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mams.commons.exceptions.IllegalValueException;
import mams.logic.history.InputOutput;


public class JsonAdaptedInputOutputTest {

    public static final String EMPTY_STRING = "";
    public static final String BLANK_STRING = "     ";

    public static final String MISSING_INPUT =
            String.format(JsonAdaptedInputOutput.MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedInputOutput.INPUT_PROPERTY_NAME);

    public static final String MISSING_OUTPUT =
            String.format(JsonAdaptedInputOutput.MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedInputOutput.OUTPUT_PROPERTY_NAME);

    public static final String MISSING_TIME_STAMP =
            String.format(JsonAdaptedInputOutput.MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedInputOutput.TIME_STAMP_PROPERTY_NAME);

    @Test
    public void toLogicType_validParams_returnsInputOutput() throws IllegalValueException {
        JsonAdaptedInputOutput inputOutput =
                new JsonAdaptedInputOutput(SUCCESSFUL_IO_1.getInput(), SUCCESSFUL_IO_1.getOutput(),
                        SUCCESSFUL_IO_1.checkSuccessful(), SUCCESSFUL_IO_1.getTimeStamp().asUnixTime());
        InputOutput test = inputOutput.toLogicType();
        assertEquals(test, SUCCESSFUL_IO_1);

        inputOutput =
                new JsonAdaptedInputOutput(UNSUCCESSFUL_IO_1.getInput(), UNSUCCESSFUL_IO_1.getOutput(),
                        UNSUCCESSFUL_IO_1.checkSuccessful(), UNSUCCESSFUL_IO_1.getTimeStamp().asUnixTime());
        assertEquals(inputOutput.toLogicType(), UNSUCCESSFUL_IO_1);
    }

    @Test
    public void toLogicType_nullInput_throwsIllegalValueException() {
        JsonAdaptedInputOutput inputOutput =
                new JsonAdaptedInputOutput(null, SUCCESSFUL_IO_1.getOutput(),
                        SUCCESSFUL_IO_1.checkSuccessful(), SUCCESSFUL_IO_1.getTimeStamp().asUnixTime());
        assertThrows(IllegalValueException.class, MISSING_INPUT, inputOutput::toLogicType);
    }

    @Test
    public void toLogicType_nullOutput_throwsIllegalValueException() {
        JsonAdaptedInputOutput inputOutput =
                new JsonAdaptedInputOutput(SUCCESSFUL_IO_1.getInput(), null,
                        SUCCESSFUL_IO_1.checkSuccessful(), SUCCESSFUL_IO_1.getTimeStamp().asUnixTime());
        assertThrows(IllegalValueException.class, MISSING_OUTPUT, inputOutput::toLogicType);
    }

    /**
     * it is possible to have a empty String user input, but MAMS will never give an empty String output.
     */
    @Test
    public void toLogicType_emptyOutput_throwsIllegalValueException() {
        JsonAdaptedInputOutput inputOutput =
                new JsonAdaptedInputOutput(SUCCESSFUL_IO_1.getInput(), EMPTY_STRING,
                        SUCCESSFUL_IO_1.checkSuccessful(), SUCCESSFUL_IO_1.getTimeStamp().asUnixTime());
        assertThrows(IllegalValueException.class, MISSING_OUTPUT, inputOutput::toLogicType);
    }

    /**
     * it is possible to have a blank space user input, but MAMS will never give a blank space output.
     */
    @Test
    public void toLogicType_blankOutput_throwsIllegalValueException() {
        JsonAdaptedInputOutput inputOutput =
                new JsonAdaptedInputOutput(SUCCESSFUL_IO_1.getInput(), BLANK_STRING,
                        SUCCESSFUL_IO_1.checkSuccessful(), SUCCESSFUL_IO_1.getTimeStamp().asUnixTime());
        assertThrows(IllegalValueException.class, MISSING_OUTPUT, inputOutput::toLogicType);
    }
}
