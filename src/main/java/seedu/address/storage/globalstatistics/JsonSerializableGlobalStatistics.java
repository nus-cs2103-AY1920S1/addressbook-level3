package seedu.address.storage.globalstatistics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.model.globalstatistics.GlobalStatistics;

/**
 * A Global Statistics class that is serializable in JSON format.
 */
@JsonRootName(value = "globalstats")
public class JsonSerializableGlobalStatistics {
    private int numPlayed;
    private JsonAdaptedWeeklyPlayed weeklyPlayed;

    @JsonCreator
    public JsonSerializableGlobalStatistics(@JsonProperty("numPlayed") int numPlayed,
                                            @JsonProperty("weeklyPlayed") JsonAdaptedWeeklyPlayed weeklyPlayed) {
        this.numPlayed = numPlayed;
        this.weeklyPlayed = weeklyPlayed;
    }

    /**
     * Construct a json serializable version of the parameter {@code globalStats}.
     */
    public JsonSerializableGlobalStatistics(GlobalStatistics globalStats) {
        this.numPlayed = globalStats.getNumPlayed();
        this.weeklyPlayed = new JsonAdaptedWeeklyPlayed(globalStats.getWeeklyPlayed());
    }

    /**
     * Converts this into the {@code GlobalStatistics} object.
     */
    public GlobalStatistics toModelType() {
        return new GlobalStatistics(numPlayed,
                weeklyPlayed.toModelType());
    }

    @Override
    public String toString() {
        return numPlayed + " times played.";
    }
}
