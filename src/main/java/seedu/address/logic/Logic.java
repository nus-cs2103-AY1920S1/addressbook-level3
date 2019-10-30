//@@author SakuraBlossom
package seedu.address.logic;

import java.nio.file.Path;
import java.util.function.Consumer;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.OmniPanelTab;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReferenceId;
import seedu.address.model.ReferenceIdResolver;
import seedu.address.model.events.Event;
import seedu.address.model.person.Person;
import seedu.address.model.queue.Room;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException,
        ParseException;

    /**
     * Evalutes the command eagerly.
     *
     * @param commandText The command as entered by the user.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    void eagerEvaluate(String commandText);

    /**
     * Returns an unmodifiable view of the filtered list of patients
     */
    ObservableList<Person> getFilteredPatientList();

    /**
     * Returns an unmodifiable view of the filtered list of appointments
     */
    ObservableList<Event> getFilteredAppointmentList();

    /**
     * Returns an unmodifiable view of the filtered list of staff members
     */
    ObservableList<Person> getFilteredStaffList();

    /**
     * Returns an unmodifiable view of the filtered list of duty shifts
     */
    ObservableList<Event> getFilteredDutyShiftList();

    /**
     * Updates AutoComplete to the current command text.
     *
     * @param commandText The command as entered by the user.
     */
    //AutoCompleter updateAutoCompleter(String commandText);

    /**
     * Returns the a resolver class for mapping {@code ReferenceId} to {@code Person}.
     */
    ReferenceIdResolver getReferenceIdResolver();

    /**
     * Returns an unmodifiable view of the list of available consultation rooms.
     */
    ObservableList<Room> getConsultationRoomList();

    /**
     * Returns an unmodifiable view of the queue list.
     */
    ObservableList<ReferenceId> getQueueList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Binds the OmniPanel tab selector.
     */
    void bindOmniPanelTabConsumer(Consumer<OmniPanelTab> omniPanelTabConsumer);
}
