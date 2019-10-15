package seedu.jarvis.storage.history.commands.address;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.ClearAddressCommand;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.storage.address.JsonAdaptedPerson;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Jackson-friendly version of {@link ClearAddressCommand}.
 */
public class JsonAdaptedClearAddressCommand extends JsonAdaptedCommand {
    public static final String MESSAGE_INVALID_COMMAND = "This command is not an ClearAddressCommand.";
    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedClearAddressCommand} with the given {@code List} of {@code Person}.
     * @param persons {@code List} of {@code Person} in Json format.
     */
    @JsonCreator
    public JsonAdaptedClearAddressCommand(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code Command} into this class for Jackson use.
     * {@code Command} should be a {@code ClearAddressCommand}.
     *
     * @param command {@code Command} to be used to construct the {@code JsonAdaptedClearAddressCommand}.
     * @throws InvalidCommandToJsonException If {@code Command} is not a {@code ClearAddressCommand}.
     */
    public JsonAdaptedClearAddressCommand(Command command) throws InvalidCommandToJsonException {
        if (!(command instanceof ClearAddressCommand)) {
            throw new InvalidCommandToJsonException(MESSAGE_INVALID_COMMAND);
        }
        ClearAddressCommand clearAddressCommand = (ClearAddressCommand) command;
        persons.addAll(clearAddressCommand.getClearedPersons()
                .stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted {@code ClearAddressCommand} object into the model's {@code Command}
     * object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code ClearAddressCommand}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code ClearAddressCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        List<Person> personList = new ArrayList<>();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            personList.add(jsonAdaptedPerson.toModelType());
        }
        return new ClearAddressCommand(personList);
    }


}
