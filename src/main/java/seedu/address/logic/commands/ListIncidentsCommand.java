package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INCIDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS;
import static seedu.address.model.Model.PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS;
import static seedu.address.model.Model.PREDICATE_SHOW_INCIDENT_LISTING_ERROR;
import static seedu.address.model.Model.PREDICATE_SHOW_SUBMITTED_INCIDENT_REPORTS;

import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;

/**
 * Lists incidents in the address book to the user according to specified predicate.
 */
public class ListIncidentsCommand extends Command {

    public static final String COMMAND_WORD = "list-i";

    private final Predicate<Incident> predicate;

    public ListIncidentsCommand(Predicate<Incident> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // get the filtered list before updating it.
        FilteredList<Incident> filteredIncidentsList = model.getFilteredIncidentList().filtered(predicate);

        String resultMessage;
        if (this.predicate.equals(PREDICATE_SHOW_ALL_INCIDENTS)) {
            resultMessage = handleAllIncidents(model, filteredIncidentsList);
        } else if (this.predicate.equals(PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS)) {
            resultMessage = handleDraftIncidents(model, filteredIncidentsList);
        } else if (this.predicate.equals(PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS)) {
            resultMessage = handleCompleteIncidents(model, filteredIncidentsList);
        } else if (this.predicate.equals(PREDICATE_SHOW_SUBMITTED_INCIDENT_REPORTS)) {
            resultMessage = handleSubmittedIncidents(model, filteredIncidentsList);
        } else if (this.predicate.equals(PREDICATE_SHOW_INCIDENT_LISTING_ERROR)) {
            resultMessage = Messages.MESSAGE_IRRELEVANT_PREFIXES;
        } else {
            resultMessage = Messages.MESSAGE_INVALID_COMMAND_FORMAT;
        }

        return new CommandResult(resultMessage);
    }

    /**
     * Handles the case for listing all incidents.
     * @param model current model operated on by command
     * @param incidents list of incidents to update
     * @return string representing command result
     */
    private String handleAllIncidents(Model model, FilteredList<Incident> incidents) {
        assert this.predicate.equals(PREDICATE_SHOW_ALL_INCIDENTS);
        model.updateFilteredIncidentList(predicate);
        if (incidents.isEmpty()) {
            return Messages.MESSAGE_NO_INCIDENTS_LISTED;
        } else {
            return Messages.MESSAGE_ALL_INCIDENTS_LISTED;
        }
    }

    /**
     * Handles the case for listing all draft incidents.
     * @param model current model operated on by command
     * @param incidents list of incidents to update
     * @return string representing command result
     */
    private String handleDraftIncidents(Model model, FilteredList<Incident> incidents) {
        assert this.predicate.equals(PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS);
        model.updateFilteredIncidentList(predicate);
        if (incidents.isEmpty()) {
            return Messages.MESSAGE_NO_DRAFTS_LISTED;
        } else {
            return Messages.MESSAGE_ALL_DRAFT_INCIDENTS_LISTED;
        }
    }

    /**
     * Handles the case for listing all complete incidents.
     * @param model current model operated on by command
     * @param incidents list of incidents to update
     * @return string representing command result
     */
    private String handleCompleteIncidents(Model model, FilteredList<Incident> incidents) {
        assert this.predicate.equals(PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS);
        model.updateFilteredIncidentList(predicate);
        if (incidents.isEmpty()) {
            return Messages.MESSAGE_NO_INCIDENT_TO_SUBMIT;
        } else {
            return Messages.MESSAGE_ALL_COMPLETE_INCIDENTS_LISTED;
        }
    }

    /**
     * Handles the case for listing all submitted incidents.
     * @param model current model operated on by command
     * @param incidents list of incidents to update
     * @return string representing command result
     */
    private String handleSubmittedIncidents(Model model, FilteredList<Incident> incidents) {
        assert this.predicate.equals(PREDICATE_SHOW_SUBMITTED_INCIDENT_REPORTS);
        model.updateFilteredIncidentList(predicate);
        if (incidents.isEmpty()) {
            return Messages.MESSAGE_NO_INCIDENT_TO_EDIT;
        } else {
            return Messages.MESSAGE_ALL_SUBMITTED_INCIDENTS_LISTED;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListIncidentsCommand // instanceof handles nulls
                && predicate.equals(((ListIncidentsCommand) other).predicate));
    }
}
