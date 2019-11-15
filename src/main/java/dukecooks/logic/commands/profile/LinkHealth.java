package dukecooks.logic.commands.profile;

import java.util.HashSet;

import dukecooks.logic.parser.DateParser;
import dukecooks.model.Model;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Timestamp;
import dukecooks.model.health.components.Type;
import dukecooks.model.health.components.Value;
import dukecooks.model.profile.person.Person;

/**
 * Calls internally to link and share common data with Health Records.
 * Ensures data are in sync.
 * This class should be hidden for user's use.
 */
public class LinkHealth {

    /**
     * Edits Health Records with the latest update of weight/height.
     */
    public static void updateHealth(Model model, Person toAdd, boolean isWeightEdited,
                                    boolean isHeightEdited) {
        if (isWeightEdited) {
            updateHealth(model, Type.Weight, toAdd.getWeight().toString());
        }

        if (isHeightEdited) {
            updateHealth(model, Type.Height, toAdd.getHeight().toString());
        }
        // else ignore
    }

    /**
     * Helper function to update health records.
     */
    private static void updateHealth(Model model, Type type, String val) {
        Record r = new Record(type, new Value(val),
                new Timestamp(DateParser.getCurrentTimestamp()), new HashSet<>());
        if (!model.hasRecord(r)) {
            model.addRecord(r);
        }
    }
}
