package cs.f10.t1.nursetraverse.logic.commands.appointment;

import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END_DATE_AND_TIME;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START_DATE_AND_TIME;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_PATIENT_INDEX;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_DAYS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_HOURS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_MINUTES;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_MONTHS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_WEEKS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_RECUR_YEARS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import cs.f10.t1.nursetraverse.commons.core.Messages;
import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.EditCommand;
import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.RecurringDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;

/**
 * Edits the details of an existing appointment in the appointment book.
 */
public class EditAppointmentCommand extends MutatorCommand {

    public static final String COMMAND_WORD = "appt-edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the index number used in the displayed appointment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_APPOINTMENT_START_DATE_AND_TIME + "APPOINTMENT_START_DATE_AND_TIME] "
            + "[" + PREFIX_APPOINTMENT_END_DATE_AND_TIME + "APPOINTMENT_END_DATE_AND_TIME] "
            + "[" + PREFIX_PATIENT_INDEX + "APPOINTMENT_END_DATE_AND_TIME] "
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
        editedAppointment.setPatient(model.getPatientByIndex(editedAppointment.getPatientIndex()));

        if (!appointmentToEdit.isSameAppointment(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.setAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment));
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     * {@code appointmentToEdit} is supposed to be non-null (i.e. it exists in the appointment book already).
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor) {
        assert appointmentToEdit != null;

        StartDateTime updatedStartDateTime = editAppointmentDescriptor.getStartDateTime()
                                                                      .orElse(appointmentToEdit.getStartDateTime());
        EndDateTime updatedEndDateTime = editAppointmentDescriptor.getEndDateTime()
                                                                  .orElse(appointmentToEdit.getEndDateTime());

        Long[] editedAppointmentFrequency = updateFrequency(appointmentToEdit, editAppointmentDescriptor);
        RecurringDateTime updatedFrequency = new RecurringDateTime(editedAppointmentFrequency);

        Index updatedPatientIndex = editAppointmentDescriptor.getPatientIndex()
                                                             .orElse(appointmentToEdit.getPatientIndex());
        String updatedDescription = editAppointmentDescriptor.getDescription()
                                                             .orElse(appointmentToEdit.getDescription());

        return new Appointment(updatedStartDateTime, updatedEndDateTime, updatedFrequency, updatedPatientIndex,
                updatedDescription);
    }

    /**
     * Creates and returns a {@code Long[]} with the details of {@code appointmentToEdit}'s frequency
     * edited with {@code editAppointmentDescriptor}'s frequency.
     */
    public static Long[] updateFrequency(Appointment appointmentToEdit,
                                  EditAppointmentDescriptor editAppointmentDescriptor) {
        Long updatedYears = editAppointmentDescriptor.getYears().orElse(appointmentToEdit.getFrequency().getYears());
        Long updatedMonths = editAppointmentDescriptor.getMonths().orElse(appointmentToEdit.getFrequency().getMonths());
        Long updatedWeeks = editAppointmentDescriptor.getWeeks().orElse(appointmentToEdit.getFrequency().getWeeks());
        Long updatedDays = editAppointmentDescriptor.getDays().orElse(appointmentToEdit.getFrequency().getDays());
        Long updatedHours = editAppointmentDescriptor.getHours().orElse(appointmentToEdit.getFrequency().getHours());
        Long updatedMinutes = editAppointmentDescriptor.getMinutes().orElse(appointmentToEdit.getFrequency()
                .getMinutes());
        Long[] editedAppointmentFrequency = {updatedYears, updatedMonths, updatedWeeks, updatedDays, updatedHours,
                                             updatedMinutes};
        return editedAppointmentFrequency;
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
     * Stores the details to edit the patient with. Each non-empty field value will replace the
     * corresponding field value of the patient.
     */
    public static class EditAppointmentDescriptor {
        private StartDateTime startDateTime;
        private EndDateTime endDateTime;

        private Long years;
        private Long months;
        private Long weeks;
        private Long days;
        private Long hours;
        private Long minutes;

        private Index patientIndex;
        private String description;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setStartDateTime(toCopy.startDateTime);
            setEndDateTime(toCopy.endDateTime);
            setYears(toCopy.years);
            setMonths(toCopy.months);
            setWeeks(toCopy.weeks);
            setDays(toCopy.days);
            setHours(toCopy.hours);
            setMinutes(toCopy.minutes);
            setPatientIndex(toCopy.patientIndex);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(startDateTime, endDateTime, years, months, weeks, days, hours, minutes,
                                                patientIndex, description);
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

        public void setYears(Long years) {
            this.years = years;
        }

        public Optional<Long> getYears() {
            return Optional.ofNullable(years);
        }

        public void setMonths(Long months) {
            this.months = months;
        }

        public Optional<Long> getMonths() {
            return Optional.ofNullable(months);
        }

        public void setWeeks(Long weeks) {
            this.weeks = weeks;
        }

        public Optional<Long> getWeeks() {
            return Optional.ofNullable(weeks);
        }

        public void setDays(Long days) {
            this.days = days;
        }

        public Optional<Long> getDays() {
            return Optional.ofNullable(days);
        }

        public void setHours(Long hours) {
            this.hours = hours;
        }

        public Optional<Long> getHours() {
            return Optional.ofNullable(hours);
        }

        public void setMinutes(Long minutes) {
            this.minutes = minutes;
        }

        public Optional<Long> getMinutes() {
            return Optional.ofNullable(minutes);
        }

        public void setPatientIndex(Index patientIndex) {
            this.patientIndex = patientIndex;
        }

        public Optional<Index> getPatientIndex() {
            return Optional.ofNullable(patientIndex);
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
                    && getYears().equals(e.getYears())
                    && getMonths().equals(e.getMonths())
                    && getWeeks().equals(e.getWeeks())
                    && getDays().equals(e.getDays())
                    && getHours().equals(e.getHours())
                    && getMinutes().equals(e.getMinutes())
                    && getPatientIndex().equals(e.getPatientIndex())
                    && getDescription().equals(e.getDescription());
        }
    }
}
