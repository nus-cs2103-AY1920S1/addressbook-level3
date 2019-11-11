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
import cs.f10.t1.nursetraverse.model.patient.exceptions.DuplicatePatientException;


/**
 * Imports data from a .csv file.
 * Patients from the .csv are batch added into the AB.
 */
public class ImportMergeCommand extends MutatorCommand {
    public static final String COMMAND_WORD = "app-import-merge";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports data from a .csv file in /imports.\n"
            + "All patients in the .csv will be imported and merged with existing data.\n"
            + "Importing visit and appointment data is currently not supported.\n "
            + "File name cannot be blank and can only contain alphanumerics, underscores and hyphens.\n"
            + "File name provided must exist and be in .csv format\n"
            + "Parameters: " + PREFIX_FILENAME + "FILENAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILENAME + "new_patients_data";

    public static final String MESSAGE_SUCCESS = "Import success!";
    public static final String MESSAGE_FAILURE = "Import failed.\n"
            + "Check that the .csv file adheres to the format in the User Guide";
    public static final String MESSAGE_INVALID_CSV_FIELDS = "Invalid fields in csv file.";
    public static final String MESSAGE_DUPLICATE_CSV_PATIENTS = "Operation will result in duplicate patients.\n"
            + "Duplicates are not allowed.";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "File does not exist: %s.csv cannot be found";
    public static final String MESSAGE_FILE_EMPTY = "Provided file is empty, nothing to import.";

    private final String importFileName;

    public ImportMergeCommand(String importFileName) {
        this.importFileName = importFileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String pathString = ImportExportPaths.IMPORT_FOLDER + "/" + importFileName + ".csv";
        List<Patient> importedPatients = new ArrayList<>();

        try {
            importedPatients.addAll(CsvUtil.readPatientsFromCsv(pathString));
        } catch (ImportingException e) {
            throw new CommandException(String.format(MESSAGE_FILE_DOES_NOT_EXIST, importFileName));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE, e);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_CSV_FIELDS + "\n" + e.getMessage());
        } catch (DuplicatePatientException e) {
            throw new CommandException(MESSAGE_DUPLICATE_CSV_PATIENTS, e);
        }

        // Check that the operation will not cause duplicates
        if (CsvUtil.importsContainDupes(importedPatients)
            || model.hasAnyPatientInGivenList(importedPatients)) {
            throw new CommandException(MESSAGE_DUPLICATE_CSV_PATIENTS);
        }

        // Ensure imported list is non-empty.
        if (importedPatients.size() == 0) {
            throw new CommandException(MESSAGE_FILE_EMPTY);
        }

        model.addPatients(importedPatients);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object that) {
        return this == that
                || (that instanceof ImportMergeCommand
                    && this.importFileName.equals(((ImportMergeCommand) that).importFileName));
    }
}
