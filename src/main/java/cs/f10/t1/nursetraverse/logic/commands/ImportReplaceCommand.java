package cs.f10.t1.nursetraverse.logic.commands;

import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_FILENAME;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.importexport.CsvUtil;
import cs.f10.t1.nursetraverse.importexport.ImportExportPaths;
import cs.f10.t1.nursetraverse.importexport.exceptions.ImportingException;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.patient.Patient;

//@@author cheongsiuhong
/**
 * Imports data from a csv file.
 * Replaces ALL data in the patient book with the imported data
 */
public class ImportReplaceCommand extends MutatorCommand {
    public static final String COMMAND_WORD = "app-import-replace";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports data from a .csv file in /imports.\n"
            + "All patients in the .csv will be imported. ALL EXISTING PERSONS WILL BE ERASED.\n"
            + "Importing visit and appointment data is currently not supported.\n "
            + "ALL VISITS AND APPOINTMENTS WILL BE ERASED.\n"
            + "File name cannot be blank and can only contain alphanumerics, underscores and hyphens.\n"
            + "File name provided must exist and be in .csv format\n"
            + "Cannot import and replace when visit is ongoing. \n"
            + "Parameters: " + PREFIX_FILENAME + "FILENAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILENAME + "assigned_patient_data";

    public static final String MESSAGE_SUCCESS = "Import success!";
    public static final String MESSAGE_FAILURE = "Import failed.\n"
            + "Check that the .csv file adheres to the format in the User Guide";
    public static final String MESSAGE_INVALID_CSV_FIELDS = "Invalid fields in csv file.";
    public static final String MESSAGE_DUPLICATE_CSV_PATIENTS = "Duplicate patients exist in the csv file.\n"
            + "Duplicates are not allowed.";
    public static final String MESSAGE_VISIT_ONGOING = "Cannot import and replace when visit is ongoing";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "File does not exist: %s.csv cannot be found";
    public static final String MESSAGE_FILE_EMPTY = "Provided file is empty, nothing to import.";

    private final String importFileName;

    public ImportReplaceCommand(String importFileName) {
        this.importFileName = importFileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Replacing not allowed when there is an ongoing association
        if (model.getOngoingVisit().isPresent()) {
            throw new CommandException(MESSAGE_VISIT_ONGOING);
        }

        String pathString = ImportExportPaths.IMPORT_FOLDER + "/" + importFileName + ".csv";
        List<Patient> importedPatients = new ArrayList<>();

        try {
            importedPatients.addAll(CsvUtil.readPatientsFromCsv(pathString));
        } catch (ImportingException e) {
            throw new CommandException(String.format(MESSAGE_FILE_DOES_NOT_EXIST, importFileName), e);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE, e);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_CSV_FIELDS + "\n" + e.getMessage());
        }

        // Ensure imported list is unique.
        if (CsvUtil.importsContainDupes(importedPatients)) {
            throw new CommandException(MESSAGE_DUPLICATE_CSV_PATIENTS);
        }

        // Ensure imported list is non-empty.
        if (importedPatients.size() == 0) {
            throw new CommandException(MESSAGE_FILE_EMPTY);
        }

        model.replaceStagedPatientBook(importedPatients);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object that) {
        return this == that
                || (that instanceof ImportReplaceCommand
                && this.importFileName.equals(((ImportReplaceCommand) that).importFileName));
    }
}
