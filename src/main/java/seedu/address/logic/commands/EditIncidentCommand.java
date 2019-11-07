package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INCIDENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.Incident.Status;
import seedu.address.model.incident.IncidentDateTime;
import seedu.address.model.incident.IncidentId;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;

/**
 * Edits the details of an existing incident in the IMS.
 */
public class EditIncidentCommand extends Command {

    public static final String COMMAND_WORD = "edit-i";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the incident identified "
            + "by the index number used in the displayed incidents list. Or use "
            + COMMAND_WORD + " to list all submitted reports for edit. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DISTRICT + "DISTRICT] "
            + "[" + PREFIX_PHONE + "CALLER NUMBER] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91302402 "
            + PREFIX_DESCRIPTION + "This is a incident description.";

    public static final String MESSAGE_DUPLICATE_INCIDENT = "This incident already exists in the address book.";
    public static final String MESSAGE_EDIT_INCIDENT_SUCCESS = "Edited Incident: %1$s";
    public static final String MESSAGE_INCIDENT_NOT_EDITED = "No fields were provided, incident is not edited.";
    public static final String MESSAGE_EDIT_DRAFT = "You cannot edit a draft, please use fill command instead.";
    public static final String MESSAGE_UNAUTHORIZED_EDIT = "Only the admin and the operator who filled this report "
            + "can edit the report.";
    private final Index index;
    private final EditIncident editIncident;



    /**
     * @param index of the person in the filtered person list to edit
     * @param editIncident details to edit the incident with
     */
    public EditIncidentCommand(Index index, EditIncident editIncident) {
        requireNonNull(index);
        requireNonNull(editIncident);

        this.index = index;
        this.editIncident = new EditIncident(editIncident);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Incident> listOfIncidents = model.getFilteredIncidentList();

        if (index.getZeroBased() >= listOfIncidents.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INCIDENT_INDEX);
        }

        Incident incidentToEdit = listOfIncidents.get(index.getZeroBased());
        Incident editedIncident = createEditedIncident(incidentToEdit, editIncident, model);
        if (!incidentToEdit.getStatus().equals(Status.SUBMITTED_REPORT)) {
            throw new CommandException(MESSAGE_EDIT_DRAFT);
        }
        if ((!incidentToEdit.getOperator().equals(model.getLoggedInPerson()))
                && Person.isNotAdmin(model.getLoggedInPerson())) {
            throw new CommandException(MESSAGE_UNAUTHORIZED_EDIT);
        }
        if (!incidentToEdit.equals(editedIncident) && model.hasIncident(editedIncident)) {
            throw new CommandException(MESSAGE_DUPLICATE_INCIDENT);
        }
        if (editedIncident.equals(incidentToEdit) && incidentToEdit.getDesc().equals(editedIncident.getDesc())) {
            throw new CommandException(MESSAGE_INCIDENT_NOT_EDITED);
        }

        model.setIncident(incidentToEdit, editedIncident);
        model.updateFilteredIncidentList(PREDICATE_SHOW_ALL_INCIDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_INCIDENT_SUCCESS, editedIncident));
    }


    /**
     * Creates and returns a {@code Incident} with the details of {@code IncidentToEdit}
     * edited with {@code editIncident}.
     */
    private static Incident createEditedIncident(Incident incidentToEdit, EditIncident editIncident, Model model) {
        assert incidentToEdit != null;
        Person operator = model.getLoggedInPerson();
        District updateDistrict = editIncident.getDistrict().orElse(incidentToEdit.getDistrict());
        CallerNumber updateCaller = editIncident.getCaller().orElse(incidentToEdit.getCallerNumber());
        IncidentDateTime dateTime = incidentToEdit.getIncidentDateTime();
        Description updateDesc = editIncident.getDesc().orElse(incidentToEdit.getDesc());
        IncidentId incidentId = incidentToEdit.getIncidentId();
        Status status = incidentToEdit.getStatus();
        Vehicle vehicle = incidentToEdit.getVehicle();

        return new Incident(operator, updateDistrict, dateTime, incidentId, updateCaller,
                updateDesc, status, vehicle);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditIncidentCommand)) {
            return false;
        }

        // state check
        EditIncidentCommand e = (EditIncidentCommand) other;
        return index.equals(e.index)
                && editIncident.equals(e.editIncident);
    }

    /**
     * Stores the details to edit the incident with. Each non-empty field value will replace the corresponding
     * field value of the incident.
     */
    public static class EditIncident {
        private District district;
        private CallerNumber caller;
        private Description desc;

        public EditIncident() {}

        public EditIncident(EditIncident toCopy) {
            setDistrict(toCopy.district);
            setCaller(toCopy.caller);
            setDesc(toCopy.desc);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(district, caller, desc);
        }

        public void setDistrict(District district) {
            this.district = district;
        }

        public Optional<District> getDistrict() {
            return Optional.ofNullable(this.district);
        }

        public void setCaller(CallerNumber caller) {
            this.caller = caller;
        }

        public Optional<CallerNumber> getCaller() {
            return Optional.ofNullable(this.caller);
        }

        public void setDesc(Description desc) {
            this.desc = desc;
        }

        public Optional<Description> getDesc() {
            return Optional.ofNullable(this.desc);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditIncident)) {
                return false;
            }

            // state check
            EditIncident e = (EditIncident) other;

            return getDistrict().equals(e.getDistrict())
                    && getCaller().equals(e.getCaller())
                    && getDesc().equals(e.getDesc());
        }

    }

}
