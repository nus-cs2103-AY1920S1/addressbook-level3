package cs.f10.t1.nursetraverse.logic.commands;

import static java.util.Objects.requireNonNull;
import static  cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_FILENAME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.importexport.CsvUtil;
import cs.f10.t1.nursetraverse.importexport.ImportingException;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.patient.exceptions.DuplicatePatientException;

/**
 * Imports data from a csv file.
 * Replaces ALL data in the patient book with the imported data
 */
public class ImportReplaceCommand extends Command implements MutatorCommand {
    public static final String COMMAND_WORD = "app-import-replace";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports data from a .csv file in /imports.\n"
            + "All persons in the .csv will be imported. ALL EXISTING PERSONS WILL BE ERASED.\n"
            + "File name provided must exist and be in .csv format\n"
            + "Cannot import and replace when visit is ongoing. \n"
            + "Parameters: [" + PREFIX_FILENAME + "FILENAME]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILENAME + "assigned_patient_data";

    public static final String MESSAGE_SUCCESS = "Import success!";
    public static final String MESSAGE_FAILURE = "Import failed.\n"
            + "Check that the .csv file adheres to the format in the User Guide";
    public static final String MESSAGE_INVALID_CSV_FIELDS = "Invalid fields in csv file.";
    public static final String MESSAGE_DUPLICATE_CSV_PERSONS = "Duplicate persons exist in the csv file.\n"
            + "Duplicates are not allowed.";
    public static final String MESSAGE_VISIT_ONGOING = "Cannot import and replace when visit is ongoing";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "File does not exist: %s.csv cannot be found";

    private final String importFileName;

    public ImportReplaceCommand(String importFileName) {
        this.importFileName = importFileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getOngoingVisit().isPresent()) {
            throw new CommandException(MESSAGE_VISIT_ONGOING);
        }

        List<Patient> importedPatients = new ArrayList<>();

        try {
            importedPatients.addAll(CsvUtil.readPatientsFromCsv(importFileName));
            model.replaceStagedAddressBook(importedPatients);
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
