package seedu.jarvis.storage.history.commands.cca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.cca.AddCcaCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.cca.JsonAdaptedCca;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link AddCcaCommand}.
 */
public class JsonAdaptedAddCcaCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_CCA = "Invalid cca.";

    private final JsonAdaptedCca cca;

    /**
     * Constructs a {@code JsonAdaptedAddCcaCommand} with the given {@code Cca} to add.
     *
     * @param cca {@code Cca} in Json format.
     */
    @JsonCreator
    public JsonAdaptedAddCcaCommand(@JsonProperty("cca") JsonAdaptedCca cca) {
        this.cca = cca;
    }

    /**
     * Converts a given {@code AddCcaCommand} into this class for Jackson use.
     *
     * @param addCcaCommand {@code AddCcaCommand} to be used to construct the {@code JsonAdaptedAddCcaCommand}.
     */
    public JsonAdaptedAddCcaCommand(AddCcaCommand addCcaCommand) {
        cca = new JsonAdaptedCca(addCcaCommand.getAddedCca());
    }

    /**
     * Converts this Jackson-friendly adapted {@code AddCcaCommand} object into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code AddCcaCommand}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted {@code AddCcaCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (cca == null) {
            throw new IllegalValueException(MESSAGE_INVALID_CCA);
        }
        return new AddCcaCommand(cca.toModelType());
    }
}
