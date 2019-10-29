package cs.f10.t1.nursetraverse.logic;

import java.nio.file.Path;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ReadOnlyPatientBook;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the PatientBook.
     *
     * @see Model#getStagedPatientBook()
     */
    ReadOnlyPatientBook getPatientBook();

    /** Returns an unmodifiable view of the filtered list of patients */
    FilteredList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered list of appointments */
    FilteredList<Appointment> getFilteredAppointmentList();

    /**
     * Returns the user prefs' patient book file path.
     */
    Path getPatientBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns an unmodifiable view of the list of ongoing visits.
     * The current constraint is to only have 1 ongoing visit at any time.
     */
    ObservableList<Visit> getObservableOngoingVisitList();
}
