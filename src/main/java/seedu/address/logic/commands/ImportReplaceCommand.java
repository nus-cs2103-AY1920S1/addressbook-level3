package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.export.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Imports data from a csv file.
 * Replaces ALL data in the address book with the imported data
 */
public class ImportReplaceCommand extends Command implements MutatorCommand {
    public static final String COMMAND_WORD = "import-replace";

    public static final String MESSAGE_SUCCESS = "Import success!";
    public static final String MESSAGE_FAILURE = "Import failed.";
    public static final String MESSAGE_INVALID_CSV = "Invalid fields in csv file.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> importedPersons = new ArrayList<>();

        try {
            importedPersons.addAll(CsvUtil.readPersonsFromCsv());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_CSV);
        }

        model.replaceStagedAddressBook(importedPersons);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
