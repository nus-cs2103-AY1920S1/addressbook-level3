package seedu.address.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_MINUTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_MONTHS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_WEEKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_YEARS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.datetime.EndDateTime;
import seedu.address.model.datetime.RecurringDateTime;
import seedu.address.model.datetime.StartDateTime;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing appointment in the appointment book.
 */
public class EditAppointmentCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "edit-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the index number used in the displayed appointment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_APPOINTMENT_START_DATE_AND_TIME + "APPOINTMENT_START_DATE_AND_TIME] "
            + "[" + PREFIX_APPOINTMENT_END_DATE_AND_TIME + "APPOINTMENT_END_DATE_AND_TIME] "
            + "[" + PREFIX_RECUR_YEARS + "YEARS] "
            + "[" + PREFIX_RECUR_MONTHS + "MONTHS] "
            + "[" + PREFIX_RECUR_WEEKS + "WEEKS] "
            + "[" + PREFIX_RECUR_DAYS + "DAYS] "
            + "[" + PREFIX_RECUR_HOURS + "HOURS] "
            + "[" + PREFIX_RECUR_MINUTES + "MINUTES] "
            + "[" + PREFIX_APPOINTMENT_DESCRIPTION + "DESCRIPTION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPOINTMENT_START_DATE_AND_TIME + "01-11-2019 1000 "
            + PREFIX_RECUR_MONTHS + "4 "
            + PREFIX_APPOINTMENT_DESCRIPTION + "Dental and health checkup";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the "
                                                                + "appointment book.";

    private final Index index;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public EditAppointmentCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getStagedAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToEdit = lastShownList.get(index.getZeroBased());
        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);

        if (!appointmentToEdit.isSameAppointment(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.setAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment));
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor) {
        assert appointmentToEdit != null;

        StartDateTime updatedStartDateTime = editAppointmentDescriptor.getStartDateTime()
                                                                      .orElse(appointmentToEdit.getStartDateTime());
        EndDateTime updatedEndDateTime = editAppointmentDescriptor.getEndDateTime()
                                                                  .orElse(appointmentToEdit.getEndDateTime());
        RecurringDateTime updatedFrequency = editAppointmentDescriptor.getFrequency()
                                                                              .orElse(appointmentToEdit.getFrequency());
        Person updatedPatient = editAppointmentDescriptor.getPatient().orElse(appointmentToEdit.getPatient());
        String updatedDescription = editAppointmentDescriptor.getDescription()
                                                             .orElse(appointmentToEdit.getDescription());

        return new Appointment(updatedStartDateTime, updatedEndDateTime, updatedFrequency, updatedPatient,
                updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditAppointmentCommand e = (EditAppointmentCommand) other;
        return index.equals(e.index)
                && editAppointmentDescriptor.equals(e.editAppointmentDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditAppointmentDescriptor {
        private StartDateTime startDateTime;
        private EndDateTime endDateTime;
        private RecurringDateTime frequency;
        private Person patient;
        private String description;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setStartDateTime(toCopy.startDateTime);
            setEndDateTime(toCopy.endDateTime);
            setFrequency(toCopy.frequency);
            setPatient(toCopy.patient);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(startDateTime, endDateTime, frequency, patient, description);
        }

        public void setStartDateTime(StartDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Optional<StartDateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public void setEndDateTime(EndDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<EndDateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setFrequency(RecurringDateTime frequency) {
            this.frequency = frequency;
        }

        public Optional<RecurringDateTime> getFrequency() {
            return Optional.ofNullable(frequency);
        }

        public void setPatient(Person patient) {
            this.patient = patient;
        }

        public Optional<Person> getPatient() {
            return Optional.ofNullable(patient);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            // state check
            EditAppointmentDescriptor e = (EditAppointmentDescriptor) other;

            return getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getFrequency().equals(e.getFrequency())
                    && getPatient().equals(e.getPatient())
                    && getDescription().equals(e.getDescription());
        }
    }
}
