package dukecooks.logic.commands.health;

import dukecooks.logic.commands.profile.EditProfileCommand;
import dukecooks.model.Model;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Type;
import dukecooks.model.health.components.Value;
import dukecooks.model.profile.person.Height;
import dukecooks.model.profile.person.Weight;
import javafx.collections.ObservableList;

/**
 * Calls internally to link and share common data with profile.
 * Ensures data are in sync.
 * This class should be hidden for user's use.
 */
public class LinkProfile {

    /**
     * Edits Profile's Weight/Height with the latest data available.
     */
    public static void updateProfile(Model model, Type type) {
        if (model.hasProfile()) {
            ObservableList<Record> recordList = model.getFilteredRecordList();
            if (!recordList.isEmpty()) {
                // expect that list has already been sorted with latest timestamp as first record.
                Value val = recordList.get(0).getValue();
                EditProfileCommand.EditPersonDescriptor editPersonDescriptor =
                        new EditProfileCommand.EditPersonDescriptor();

                switch (type.name()) {
                case "Weight":
                    editPersonDescriptor.setWeight(new Weight(val.toString()));
                    break;
                case "Height":
                    editPersonDescriptor.setHeight(new Height(val.toString()));
                    break;
                default:
                    throw new AssertionError("Unable to update profile! Invalid record type!");
                }

                EditProfileCommand editProfileCommand = new EditProfileCommand(editPersonDescriptor,
                        false, false); // boolean param does not matter here
                editProfileCommand.updateProfile(model);
            }
        }
        // else ignore
    }
}
