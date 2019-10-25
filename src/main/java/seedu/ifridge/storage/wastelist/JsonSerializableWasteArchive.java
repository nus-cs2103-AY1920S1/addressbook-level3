package seedu.ifridge.storage.wastelist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.waste.WasteMonth;

/**
 * An Immutable waste archive that is serializable to JSON format.
 */
@JsonRootName(value = "wastearchive")
public class JsonSerializableWasteArchive {
    public static final String MESSAGE_DUPLICATE_TEMPLATE = "Waste list storage contains duplicate waste list(s) "
            + "for the month of %s.";

    private final List<JsonSerializableWasteList> wastearchive = new ArrayList<>();


    @JsonCreator
    public JsonSerializableWasteArchive(@JsonProperty("wastearchive") List<JsonSerializableWasteList> wastearchive) {
        this.wastearchive.addAll(wastearchive);
    }

    public JsonSerializableWasteArchive(TreeMap<WasteMonth, WasteList> source) {
        Set<WasteMonth> wasteMonths = source.keySet();
        for (WasteMonth wm : wasteMonths) {
            JsonSerializableWasteList wasteList = new JsonSerializableWasteList(source.get(wm));
            wastearchive.add(wasteList);
        }
    }

    /**
     * Converts this waste archive into the model's waste archive.
     *
     * @throws IllegalValueException if there are any data constraints violated
     */
    public TreeMap<WasteMonth, WasteList> toModelType() throws IllegalValueException {
        TreeMap<WasteMonth, WasteList> modelWasteArchive = new TreeMap<>();
        for (JsonSerializableWasteList jsonSerializableWasteList : wastearchive) {
            WasteList wasteList = jsonSerializableWasteList.toModelType();
            WasteMonth wasteMonth = wasteList.getWasteMonth();
            modelWasteArchive.put(wasteMonth, wasteList);
        }
        return modelWasteArchive;
    }
}
