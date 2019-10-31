package cs.f10.t1.nursetraverse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.importexport.CsvUtil;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.patient.Patient;

/**
 * Imports data from a csv file.
 * Replaces ALL data in the patient book with the imported data
 */
public class ImportReplaceCommand extends Command implements MutatorCommand {
    public static final String COMMAND_WORD = "app-import-replace";

    public static final String MESSAGE_SUCCESS = "Import success!";
    public static final String MESSAGE_FAILURE = "Import failed.";
    public static final String MESSAGE_INVALID_CSV = "Invalid fields in csv file.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> importedPatients = new ArrayList<>();

        try {
            importedPatients.addAll(CsvUtil.readPatientsFromCsv());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_INVALID_CSV);
        }

        model.replaceStagedPatientBook(importedPatients);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
