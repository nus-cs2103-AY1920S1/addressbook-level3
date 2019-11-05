package seedu.address.testutil;

import seedu.address.logic.commands.EditIncidentCommand;
import seedu.address.logic.commands.EditIncidentCommand.EditIncident;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;
import seedu.address.model.vehicle.District;

/**
 * A utility class to help with the building of EditIncident objects.
 */
public class EditIncidentBuilder {
    private EditIncidentCommand.EditIncident editor;

    public EditIncidentBuilder() {
        editor = new EditIncidentCommand.EditIncident();
    }

    public EditIncidentBuilder(EditIncidentCommand.EditIncident editor) {
        this.editor = new EditIncidentCommand.EditIncident(editor);
    }

    /**
     * Returns an {@code EditIncident} with fields containing {@code incident}'s details
     */
    public EditIncidentBuilder (Incident incident) {
        editor = new EditIncidentCommand.EditIncident();
        editor.setDistrict(incident.getDistrict());
        editor.setCaller(incident.getCallerNumber());
        editor.setDesc(incident.getDesc());
    }

    /**
     * Sets the {@code District} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditIncidentBuilder withDistrict(String district) {
        editor.setDistrict(new District(Integer.parseInt(district)));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditIncidentBuilder} that we are building.
     */
    public EditIncidentBuilder withDesc(String description) {
        editor.setDesc(new Description(description));
        return this;
    }


    /**
     * Sets the {@code CallerNumber} of the {@code EditIncidentBuilder} that we are building.
     */
    public EditIncidentBuilder withCaller(String callerNumber) {
        editor.setCaller(new CallerNumber(callerNumber));
        return this;
    }


    public EditIncident build() {
        return editor;
    }
}
