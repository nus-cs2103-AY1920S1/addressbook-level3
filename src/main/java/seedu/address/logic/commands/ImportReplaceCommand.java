package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.importexport.CsvUtil;
import seedu.address.importexport.exceptions.ImportingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Imports data from a csv file.
 * Replaces ALL data in the address book with the imported data
 */
public class ImportReplaceCommand extends Command implements MutatorCommand {
    public static final String COMMAND_WORD = "import-replace";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports data from a .csv file in /imports.\n"
            + "All data in the .csv will be imported. ALL EXISTING DATA WILL BE ERASED.\n"
            + "File name provided must exist and be in .csv format\n"
            + "Cannot import when visit is ongoing. \n"
            + "Parameters: [" + PREFIX_FILENAME + "FILENAME]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILENAME + "assigned_patient_data";

    public static final String MESSAGE_SUCCESS = "Import success!";
    public static final String MESSAGE_FAILURE = "Import failed.";
    public static final String MESSAGE_INVALID_CSV_FIELDS = "Invalid fields in csv file.";
    public static final String MESSAGE_DUPLICATE_CSV_PERSONS = "Duplicate persons exist in the csv file." +
            " Duplicates are not allowed.";
    public static final String MESSAGE_VISIT_ONGOING = "Cannot import when visit is ongoing";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "File does not exist: %s.csv cannot be found";

    private final String importFileName;

    public ImportReplaceCommand(String importFileName) {
        this.importFileName = importFileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> importedPersons = new ArrayList<>();

        try {
            importedPersons.addAll(CsvUtil.readPersonsFromCsv(importFileName));
            model.replaceStagedAddressBook(importedPersons);
        } catch (ImportingException e) {
            throw new CommandException(String.format(MESSAGE_FILE_DOES_NOT_EXIST, importFileName), e);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE, e);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_CSV_FIELDS, e);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_CSV_PERSONS, e);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
