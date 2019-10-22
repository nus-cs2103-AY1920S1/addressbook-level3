package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALLER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
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
import seedu.address.model.incident.IncidentDateTime;
import seedu.address.model.vehicle.District;

/**
 * Edits the details of an existing incident in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the incident identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LOCATION + "DISTRICT] "
            + "[" + PREFIX_CALLER_NUMBER + "CALLER NUMBER] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATETIME + "01/10/2019 20:22 "
            + PREFIX_CALLER_NUMBER + "91302402";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_INCIDENT = "This incident already exists in the address book.";

    public static final String MESSAGE_EDIT_INCIDENT_SUCCESS = "Edited Incident: %1$s";

    private final Index index;
    private final EditIncident editIncident;



    /**
     * @param index of the person in the filtered person list to edit
     * @param editIncident details to edit the incident with
     */
    public EditCommand(Index index, EditIncident editIncident) {
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
        Incident editedIncident = createEditedIncident(incidentToEdit, editIncident);

        if (!incidentToEdit.equals(editedIncident) && model.hasIncident(editedIncident)) {
            throw new CommandException(MESSAGE_DUPLICATE_INCIDENT);
        }

        model.setIncident(incidentToEdit, editedIncident);
        model.updateFilteredIncidentList(PREDICATE_SHOW_ALL_INCIDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_INCIDENT_SUCCESS, editedIncident));
    }


    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Incident createEditedIncident(Incident incidentToEdit, EditIncident editIncident) {
        assert incidentToEdit != null;
        District updateDistrict = editIncident.getDistrict().orElse(incidentToEdit.getDistrict());
        CallerNumber updateCaller = editIncident.getCaller().orElse(incidentToEdit.getCallerNumber());
        IncidentDateTime updateDateTime = editIncident.getDateTime().orElse(incidentToEdit.getDateTime());
        Description updateDesc = editIncident.getDesc().orElse(incidentToEdit.getDesc());

        return new Incident(incidentToEdit.getIncidentId(), updateDistrict, updateDateTime, updateCaller, updateDesc);
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
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editIncident.equals(e.editIncident);
    }

    /**
     * Stores the details to edit the incident with. Each non-empty field value will replace the corresponding
     * field value of the person.
     */
    public static class EditIncident {
        private District district;
        private IncidentDateTime dateTime;
        private CallerNumber caller;
        private Description desc;

        public EditIncident() {}

        public EditIncident(EditIncident toCopy) {
            setDistrict(toCopy.district);
            setDateTime(toCopy.dateTime);
            setCaller(toCopy.caller);
            setDesc(toCopy.desc);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(district, dateTime, caller, desc);
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

        public void setDateTime(IncidentDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<IncidentDateTime> getDateTime() {
            return Optional.ofNullable(this.dateTime);
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
                    && getDateTime().equals(e.getDateTime())
                    && getDesc().equals(e.getDesc());
        }

    }

}
