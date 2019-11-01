package mams.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import mams.commons.core.time.TimeStamp;
import mams.commons.exceptions.IllegalValueException;
import mams.logic.InputOutput;

/**
 * Jackson-friendly version of {@link InputOutput}.
 */
class JsonAdaptedInputOutput {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "InputOutput's %s field is missing/blank!";

    public static final String INPUT_PROPERTY_NAME = "input";
    public static final String OUTPUT_PROPERTY_NAME = "output";
    public static final String EXECUTION_STATUS_PROPERTY_NAME = "isSuccessfulExecution";
    public static final String TIME_STAMP_PROPERTY_NAME = "timeStamp";

    private final String input;
    private final String output;
    private final boolean isSuccessfulExecution;
    private final long timeStamp;

    /**
     * Constructs a {@code JsonAdaptedInputOutput} with the given InputOutput details.
     */
    @JsonCreator
    public JsonAdaptedInputOutput(@JsonProperty(INPUT_PROPERTY_NAME) String input,
                                  @JsonProperty(OUTPUT_PROPERTY_NAME) String output,
                                  @JsonProperty(EXECUTION_STATUS_PROPERTY_NAME) boolean isSuccessfulExecution,
                                  @JsonProperty(TIME_STAMP_PROPERTY_NAME) long timeStamp) {
        this.input = input;
        this.output = output;
        this.isSuccessfulExecution = isSuccessfulExecution;
        this.timeStamp = timeStamp;
    }

    /**
     * Converts a given {@code InputOutput} into this class for Jackson use.
     */
    public JsonAdaptedInputOutput(InputOutput source) {
        input = source.getInput();
        output = source.getOutput();
        isSuccessfulExecution = source.checkSuccessful();
        timeStamp = source.getTimeStamp().asUnixTime();
    }

    /**
     * Converts this Jackson-friendly adapted InputOutput object into the logic's {@code InputOutput} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted InputOutput.
     */
    public InputOutput toLogicType() throws IllegalValueException {

        // it is possible to have a blank space user input, but MAMS will never give a blank-space output.
        if (input == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    INPUT_PROPERTY_NAME));
        }

        if (output == null || output.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OUTPUT_PROPERTY_NAME));
        }

        return new InputOutput(input, output, isSuccessfulExecution, new TimeStamp(timeStamp));
    }

}
