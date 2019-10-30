package budgetbuddy.storage.scripts;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import budgetbuddy.model.ScriptLibrary;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.script.Script;
import budgetbuddy.model.script.ScriptName;

/**
 * An immutable script description map that is serialisable to JSON.
 */
@JsonRootName("scriptDescriptionMap")
public class JsonSerializableScriptDescriptionMap {
    private final Map<String, String> descriptionMap;

    /**
     * Constructs a {@link JsonSerializableScriptDescriptionMap} from the given map.
     *
     * @param descriptionMap the map
     */
    public JsonSerializableScriptDescriptionMap(
            @JsonProperty("descriptionMap") Map<String, String> descriptionMap) {
        this.descriptionMap = descriptionMap;
    }

    /**
     * Constructs a {@link JsonSerializableScriptDescriptionMap} from the scripts in the given library.
     *
     * @param library the library
     */
    public JsonSerializableScriptDescriptionMap(ScriptLibrary library) {
        this.descriptionMap = new HashMap<String, String>();
        for (Script script : library.getScriptList()) {
            descriptionMap.put(script.getName().toString(), script.getDescription().getDescription());
        }
    }

    /**
     * Retrieves the description map.
     *
     * @return the map
     */
    public Map<ScriptName, Description> getDescriptionMap() {
        return Collections.unmodifiableMap(
                descriptionMap.entrySet().stream().collect(Collectors.toConcurrentMap(
                    entry -> new ScriptName(entry.getKey()),
                    entry -> new Description(entry.getValue()))));
    }
}
