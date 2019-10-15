package seedu.jarvis.storage.history.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.storage.history.commands.address.JsonAdaptedAddAddressCommand;
import seedu.jarvis.storage.history.commands.address.JsonAdaptedClearAddressCommand;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonAdaptedClearAddressCommand.class, name = "JsonAdaptedClearAddressCommand"),
        @JsonSubTypes.Type(value = JsonAdaptedAddAddressCommand.class, name = "JsonAdaptedAddAddressCommand")
})
public abstract class JsonAdaptedCommand {
    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted command.
     */
    public abstract Command toModelType() throws IllegalValueException;
}
