package seedu.address.storage.wastelist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WasteList;
import seedu.address.model.waste.WasteMonth;

@JsonRootName(value = "wastearchive")
public class JsonSerializableWasteArchive {
    public static final String MESSAGE_DUPLICATE_TEMPLATE = "Waste list storage contains duplicate waste list(s) "
            + "for the month of %s.";

    private final List<JsonSerializableWasteList> wasteLists = new ArrayList<>();


    @JsonCreator
    public JsonSerializableWasteArchive(@JsonProperty("wastearchive") List<JsonSerializableWasteList> wasteLists) {
        this.wasteLists.addAll(wasteLists);
    }

    public JsonSerializableWasteArchive(TreeMap<WasteMonth, WasteList> source) {
        Set<WasteMonth> wasteMonths = source.keySet();
        for (WasteMonth wm : wasteMonths) {
            JsonSerializableWasteList wasteList = new JsonSerializableWasteList(source.get(wm));
            wasteLists.add(wasteList);
        }
    }

    public TreeMap<WasteMonth, WasteList> toModelType() throws IllegalValueException {
        TreeMap<WasteMonth, WasteList> modelWasteArchive = new TreeMap<>();
        for (JsonSerializableWasteList jsonSerializableWasteList : wasteLists) {
            WasteList wasteList = jsonSerializableWasteList.toModelType();
            WasteMonth wasteMonth = wasteList.getWasteMonth();
            modelWasteArchive.put(wasteMonth, wasteList);
        }
        return modelWasteArchive;
    }
}
